# Nether Regions

Nether Regions is a Minecraft server plugin that enables linking regions of the nether
to different worlds upon nether portal transport and disallowing nether portal transport
outside of those regions. It is intended to be used with multiple overworlds with world 
borders where the nether is a mode of transport between them.

## demo

The Nether Regions plugin is running on the [bingecraft.net Minecraft server](https://bingecraft.net).


## installation

```
git clone git@github.com:bingecraft-net/nether-regions.git
cd nether-regions
mvn clean package
```

In the `target` directory there will now be a file called `nether-regions-$version.jar`. Put this in your Bukkit
server's `plugins` directory and start the server to activate the plugin.
