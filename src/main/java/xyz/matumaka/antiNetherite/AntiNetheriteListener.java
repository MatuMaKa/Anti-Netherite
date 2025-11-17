package xyz.matumaka.antiNetherite;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static xyz.matumaka.antiNetherite.ColorUtil.translateHexColors;

public class AntiNetheriteListener implements Listener {
    private final JavaPlugin plugin;

    public AntiNetheriteListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private boolean isEnabled() {
        return this.plugin.getConfig().getBoolean("enabled", true);
    }

    private void denyMessage(Player player, String action) {
        player.sendMessage(translateHexColors(this.plugin.getConfig().getString("messages.disabled-message", "&cNetherite is disabled! You can't %action%.").replaceAll("%action%", action)));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!isEnabled()) return;
        if (!this.plugin.getConfig().getBoolean("antinetherite.block-placing", true)) return;

        Player player = event.getPlayer();
        if (player.hasPermission("antinetherite.bypass")) return;

        if (event.getBlock().getType() == Material.ANCIENT_DEBRIS || event.getBlock().getType() == Material.NETHERITE_BLOCK) {
            event.setCancelled(true);
            denyMessage(player, "place netherite blocks or ancient debris");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        if (!isEnabled()) return;
        if (!this.plugin.getConfig().getBoolean("antinetherite.block-breaking", true)) return;

        Player player = event.getPlayer();
        if (player.hasPermission("antinetherite.bypass")) return;

        if (event.getBlock().getType() == Material.ANCIENT_DEBRIS || event.getBlock().getType() == Material.NETHERITE_BLOCK) {
            event.setCancelled(true);
            denyMessage(player, "break netherite blocks or ancient debris");
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (!isEnabled()) return;
        if (!this.plugin.getConfig().getBoolean("antinetherite.crafting", true)) return;

        Player player = (Player) event.getView().getPlayer();
        if (player.hasPermission("antinetherite.bypass")) return;

        if (event.getRecipe() == null) return;
        ItemStack result = event.getRecipe().getResult();
        if (result != null && result.getType().toString().contains("NETHERITE")) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent event) {
        if (!isEnabled()) return;
        if (!this.plugin.getConfig().getBoolean("antinetherite.smelting", true)) return;
        if (event.getSource().getType() == Material.ANCIENT_DEBRIS) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSmithing(PrepareSmithingEvent event) {
        if (!isEnabled()) return;
        if (!this.plugin.getConfig().getBoolean("antinetherite.smithing", true)) return;

        Player player = (Player) event.getView().getPlayer();
        if (player.hasPermission("antinetherite.bypass")) return;

        ItemStack result = event.getResult();
        if (result != null && result.getType().toString().contains("NETHERITE")){
            event.setResult(null);
        }
    }

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent event) {
        if (!isEnabled()) return;
        if (!this.plugin.getConfig().getBoolean("antinetherite.enchanting", true)) return;

        Player player = event.getEnchanter();
        if (player.hasPermission("antinetherite.bypass")) return;

        ItemStack item = event.getItem();
        if (item != null && item.getType().toString().contains("NETHERITE")){
            event.setCancelled(true);
            denyMessage(player, "enchant netherite items");
        }
    }
}
