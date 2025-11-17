package xyz.matumaka.antiNetherite;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static xyz.matumaka.antiNetherite.ColorUtil.translateHexColors;

public final class AntiNetherite extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        boolean enabled = getConfig().getBoolean("enabled", true);
        getCommand("antinetherite").setExecutor((CommandExecutor) this);
        if (enabled) {
            getServer().getPluginManager().registerEvents(new AntiNetheriteListener(this), (Plugin) this);
            getLogger().info("Netherite protection is enabled.");
        } else {
            getLogger().info("Netherite protection is disabled in the config.");
        }
    }

    private void replaceDebrisInLoadedNetherChunks() {
        getServer().getScheduler().runTaskLater((Plugin) this, () -> {
            int replaced = 0;
            for (World world : getServer().getWorlds()) {
                if (world.getEnvironment() != World.Environment.NETHER)
                    continue;
                for (Chunk chunk : world.getLoadedChunks()) {
                    for (int x = 0; x < 16; x++) {
                        for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                            for (int z = 0; z < 16; z++) {
                                Block block = chunk.getBlock(x, y, z);
                                if (block.getType() == Material.ANCIENT_DEBRIS) {
                                    block.setType(Material.NETHER_QUARTZ_ORE);
                                    replaced++;
                                }
                            }
                        }
                    }
                }
            }
            getLogger().info("Anti-Netherite: Replaced " + replaced + " Ancient Debris in loaded Nether chunks.");
        }, 40L);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(translateHexColors(getConfig().getString("messages.reload-message", "&aAnti-Netherite configuration has been reloaded. &cIF YOU CHANGED 'enabled' THEN RESTART THE SERVER")));
            reloadConfig();
            return true;
        }
        sender.sendMessage(translateHexColors(getConfig().getString("messages.usage-message", "&cUsage: &a%command%").replaceAll("%command%", "/antinetherite reload")));
        return true;
    }

    @Override
    public void onDisable() {}
}
