package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class TrashCommand implements CommandExecutor, Listener {

    private final BasicCommands plugin;

    public TrashCommand() {
        this.plugin = BasicCommands.getInstance();
        // Register this class as a Listener
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission check
        if (!sender.hasPermission("basiccommands.trash")) {
            sender.sendMessage(plugin.getMessage("messages.no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("messages.player-only-command"));
            return true;
        }

        Player player = (Player) sender;

        // Create a 3-row inventory titled "Trash"
        Inventory trashInventory = Bukkit.createInventory(null, 27, ChatColor.RED + "Trash");
        player.openInventory(trashInventory);

        // Notify
        player.sendMessage(plugin.getMessage("messages.trash-opened"));
        return true;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Check if the closed inventory is the trash
        if (event.getView().getTitle().equals(ChatColor.RED + "Trash")) {
            // Clear items
            event.getInventory().clear();
        }
    }
}
