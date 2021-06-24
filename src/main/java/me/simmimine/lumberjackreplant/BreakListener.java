package me.simmimine.lumberjackreplant;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class BreakListener implements Listener {

    List<Material> logTypes= Arrays.asList(Material.OAK_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM);
    List<Material> saplingTypes = Arrays.asList(Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.ACACIA_SAPLING, Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING, Material.JUNGLE_SAPLING, Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS);
    List<Material> dirtTypes = Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.PODZOL, Material.GRASS_BLOCK, Material.FARMLAND);

    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerBreak(BlockBreakEvent event) {
        Bukkit.getLogger().info("event happened");
        if (event.getBlock().getType().equals(Material.OAK_LOG) || event.getBlock().getType().equals(Material.DARK_OAK_LOG) || event.getBlock().getType().equals(Material.ACACIA_LOG) || event.getBlock().getType().equals(Material.SPRUCE_LOG) || event.getBlock().getType().equals(Material.BIRCH_LOG) || event.getBlock().getType().equals(Material.JUNGLE_LOG) || event.getBlock().getType().equals(Material.CRIMSON_STEM) || event.getBlock().getType().equals(Material.WARPED_STEM)) {
            if (event.getPlayer().hasPermission("lumberjack.replant")) ;{
                Location loc = event.getBlock().getLocation();
                loc.setY(event.getBlock().getY() + 1);
                if (logTypes.contains(loc.getBlock().getType())) {
                    if (event.getPlayer().getInventory().contains(saplingTypes.get(logTypes.indexOf(event.getBlock().getType())))) {
                        loc.setY(event.getBlock().getY() - 1);
                        if (dirtTypes.contains(loc.getBlock().getType())) {
                            loc.setY(event.getBlock().getY());
                            saplingReplanter(loc, saplingTypes.get(logTypes.indexOf(event.getBlock().getType())));
                            ItemStack sapling = new ItemStack(saplingTypes.get(logTypes.indexOf(event.getBlock().getType())), 1);
                            event.getPlayer().getInventory().removeItem(sapling);
                        }
                    }
                }
            }
        }
    }

    public void saplingReplanter(Location loc, Material sapling) {
        Bukkit.getScheduler().runTaskLater(LumberjackReplant.getInstance(), () -> {
            loc.getBlock().setType(sapling);
        }, 20L);
    }
}
