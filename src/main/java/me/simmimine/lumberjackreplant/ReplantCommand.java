package me.simmimine.lumberjackreplant;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplantCommand implements CommandExecutor {

    private final LumberjackReplant plugin;

    public ReplantCommand(LumberjackReplant plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Console cannot issue this command");
            return false;
        }
        Player p = (Player) sender;
        if (!label.equals("lumberjackreplant") || args.length != 0) {
            p.sendMessage(ChatColor.RED + "Correct usage: /lumberjackreplant");
            return false;
        }
        if (!configAdder(p.getUniqueId())) {
            plugin.getConfig().set(p.getUniqueId().toString(), true);
            plugin.saveConfig();
            p.sendMessage(ChatColor.GREEN + "Autoreplant now on");
            return false;
        }
        if (plugin.getConfig().get(p.getUniqueId().toString()).equals(true)) {
            configSwitcher(true , p.getUniqueId());
            p.sendMessage(ChatColor.GREEN + "Autoreplant now off");
            return false;
        }
        if (plugin.getConfig().get(p.getUniqueId().toString()).equals(false)) {
            configSwitcher(false , p.getUniqueId());
            p.sendMessage(ChatColor.GREEN + "Autoreplant now on");
            return false;
        }
        return false;
    }

    public void configSwitcher(Boolean y, UUID p) {
        if (y) {
            plugin.getConfig().set(p.toString(), false);
            plugin.saveConfig();
            return;
        }
        plugin.getConfig().set(p.toString(), true);
        plugin.saveConfig();
    }

    public boolean configAdder(UUID p) {
        for (String x : plugin.getConfig().getKeys(false)) {
            if (x == null) continue;
            if (x.equals(p.toString())) {
                return true;
            }
        }
        return false;
    }
}
