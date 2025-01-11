package me.minimize.basiccommands;

import me.minimize.basiccommands.commands.*;
import me.minimize.basiccommands.listeners.GodListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicCommands extends JavaPlugin {

    private static BasicCommands instance;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;

        // Save default config.yml if it doesn't exist
        saveDefaultConfig();
        this.config = getConfig();

        // Register commands
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("openinv").setExecutor(new OpenInvCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("fix").setExecutor(new FixCommand());

        // TPA system (tpa, tpaccept, tpdeny)
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("tpdeny").setExecutor(new TpdenyCommand());

        // Trash command (with inventory-close listener inside)
        getCommand("trash").setExecutor(new TrashCommand());

        // Register God mode listener
        getServer().getPluginManager().registerEvents(new GodListener(), this);

        getLogger().info("BasicCommands has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BasicCommands has been disabled!");
    }

    public static BasicCommands getInstance() {
        return instance;
    }

    /**
     * Retrieve a string from config.yml and apply color codes.
     *
     * @param path The path in config.yml
     * @return Colored message or fallback text
     */
    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path, path));
    }
}
