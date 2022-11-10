package net.bingecraft.nether_regions;

import org.bukkit.Location;

class WorldLink {
  public final Location from;
  public final double fromRadius;
  public final Location to;
  public final double toRadius;

  public WorldLink(
    final Location from,
    final double fromRadius,
    final Location to,
    final double toRadius
  ) {
    this.from = from;
    this.fromRadius = fromRadius;
    this.to = to;
    this.toRadius = toRadius;
  }
}
