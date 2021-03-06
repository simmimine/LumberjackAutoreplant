package me.simmimine.lumberjackreplant;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LumberjackReplant extends JavaPlugin {

    private static Plugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BreakListener(this), this);
        PluginCommand command = getCommand("lumberjackreplant");
        command.setExecutor(new ReplantCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getInstance() {
        return instance;
    }
}
