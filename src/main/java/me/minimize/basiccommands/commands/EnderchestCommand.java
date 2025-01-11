package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EnderchestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission check
        if (!sender.hasPermission("basiccommands.enderchest")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        Player target;
        if (args.length >= 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-not-found"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
                return true;
            }
            target = (Player) sender;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
            return true;
        }

        Player player = (Player) sender;
        Inventory enderChest = target.getEnderChest();
        player.openInventory(enderChest);

        player.sendMessage(
                BasicCommands.getInstance().getMessage("messages.enderchest-opened")
                        .replace("%player%", target.getName())
        );
        return true;
    }
}
