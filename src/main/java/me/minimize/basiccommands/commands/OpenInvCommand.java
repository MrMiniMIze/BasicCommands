package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenInvCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission check
        if (!sender.hasPermission("basiccommands.openinv")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        // /openinv <player> needed
        if (args.length < 1) {
            sender.sendMessage("Usage: /openinv <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-not-found"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
            return true;
        }

        Player player = (Player) sender;
        Inventory targetInv = target.getInventory();
        player.openInventory(targetInv);

        player.sendMessage(
                BasicCommands.getInstance().getMessage("messages.inventory-opened")
                        .replace("%player%", target.getName())
        );

        return true;
    }
}
