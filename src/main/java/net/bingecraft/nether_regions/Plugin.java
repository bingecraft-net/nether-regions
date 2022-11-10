package net.bingecraft.nether_regions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
  @Override
  public void onEnable() {
    FileConfiguration config = getConfig();
    config.options().copyDefaults(true);
    saveConfig();

    PlayerPortalOverride playerPortalOverride = new PlayerPortalOverride(getServer());
    getServer().getPluginManager().registerEvents(playerPortalOverride, this);
  }
}