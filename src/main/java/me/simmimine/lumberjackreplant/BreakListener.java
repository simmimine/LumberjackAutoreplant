package me.simmimine.lumberjackreplant;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BreakListener implements Listener {

    private final LumberjackReplant plugin;

    public BreakListener(LumberjackReplant plugin) {
        this.plugin = plugin;
    }

    List<Material> logTypes= Arrays.asList(Material.OAK_LOG, Material.DARK_OAK_LOG, Material.ACACIA_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM);
    List<Material> saplingTypes = Arrays.asList(Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.ACACIA_SAPLING, Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING, Material.JUNGLE_SAPLING, Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS);
    List<Material> dirtTypes = Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.PODZOL, Material.GRASS_BLOCK, Material.FARMLAND, Material.WARPED_NYLIUM, Material.CRIMSON_NYLIUM);

    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerBreak(BlockBreakEvent event) {
        if (configChecker(event.getPlayer().getUniqueId())) {
            if (plugin.getConfig().get(event.getPlayer().getUniqueId().toString()).equals(true)){
                Location loc = event.getBlock().getLocation();
                if (event.getPlayer().hasPermission("lumberjack.replant")) ;{
                if (treeChecker(loc)) {
                    if (event.getPlayer().getInventory().contains(saplingTypes.get(logTypes.indexOf(event.getBlock().getType())))) {
                        loc.setY(event.getBlock().getY());
                        saplingReplanter(loc, saplingTypes.get(logTypes.indexOf(event.getBlock().getType())));
                        saplingRemover(event.getPlayer(), event);
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

    public boolean treeChecker(Location loc) {
        if (logTypes.contains(loc.getBlock().getType())) {
            loc.setY(loc.getBlock().getY()-1);
            if (dirtTypes.contains(loc.getBlock().getType())) {
                return true;
            }
        }
        return false;
    }

    public void saplingRemover(Player p, BlockBreakEvent e) {
        ItemStack sapling = new ItemStack(saplingTypes.get(logTypes.indexOf(e.getBlock().getType())), 1);
        p.getInventory().removeItem(sapling);
    }

    public boolean configChecker(UUID p) {
        for (String i : plugin.getConfig().getKeys(false)) {
            if (i == null) continue;
            if (i.equals(p.toString())) return true;
        }
        return false;
    }
}
