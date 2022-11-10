package net.bingecraft.nether_regions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class PlayerPortalOverride implements Listener {
  private final List<WorldLink> worldLinks = new LinkedList<>();
  private final Server server;

  public PlayerPortalOverride(Server server) {
    this.server = server;

    World world = requireWorld("world");
    World worldNether = requireWorld("world_nether");
    World highlands = requireWorld("highlands");

    Location worldCenter = new Location(world, 0.5, 0, 0.5);
    Location netherCenter0 = new Location(worldNether, 0.5 / 8, 0, 0.5 / 8);
    Location highlandsCenter = new Location(highlands, -2444.18524, 0, -1711.11460);
    Location netherCenter1 = new Location(worldNether, -2444.18524 / 8, 0, -1711.11460 / 8);

    worldLinks.add(new WorldLink(worldCenter, 2000, netherCenter0, 2000d / 8));
    worldLinks.add(new WorldLink(netherCenter0, 2000d / 8, worldCenter, 2000));
    worldLinks.add(new WorldLink(highlandsCenter, 750, netherCenter1, 750d / 8));
    worldLinks.add(new WorldLink(netherCenter1, 750d / 8, highlandsCenter, 750));
  }

  private World requireWorld(String name) {
    World world = server.getWorld(name);
    if (world == null) {
      String message = String.format("could not load world: %s", name);
      throw new RuntimeException(message);
    }
    return world;
  }

  @EventHandler
  public void onPlayerPortal(PlayerPortalEvent event) {
    if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) return;

    getLink(event.getFrom()).ifPresentOrElse(
      link -> {
        double scale = link.toRadius / link.fromRadius;
        Location to = event.getFrom().clone();
        to.set(to.getX() * scale, to.getY(), to.getZ() * scale);
        to.setWorld(link.to.getWorld());
        event.setTo(to);
      },
      () -> {
        event.setCancelled(true);
        event.getPlayer().sendMessage(Component.text("a curse stops you from proceeding", NamedTextColor.RED));
      }
    );
  }

  private Optional<WorldLink> getLink(Location location) {
    for (WorldLink link : worldLinks) {
      if (
        link.from.getWorld().equals(location.getWorld())
          && horizontalDistance(link.from, location) <= link.fromRadius
      ) {
        return Optional.of(link);
      }
    }
    return Optional.empty();
  }

  private static double horizontalDistance(Location l0, Location l1) {
    double dx = l0.getX() - l1.getX();
    double dz = l0.getZ() - l1.getZ();
    return Math.sqrt(dx * dx + dz * dz);
  }
}