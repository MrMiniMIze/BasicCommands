package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpdenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("basiccommands.tpa")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
            return true;
        }

        Player target = (Player) sender; // The target is who typed /tpdeny
        UUID requesterUuid = TpaCommand.getTpaRequester(target.getUniqueId());

        if (requesterUuid == null) {
            target.sendMessage(BasicCommands.getInstance().getMessage("messages.no-tpa-request"));
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUuid);
        if (requester == null) {
            // The requester went offline
            target.sendMessage(BasicCommands.getInstance().getMessage("messages.player-not-found"));
            TpaCommand.removeTpaRequest(target.getUniqueId());
            return true;
        }

        // Deny request
        target.sendMessage(
                BasicCommands.getInstance().getMessage("messages.tpa-denied")
                        .replace("%player%", requester.getName())
        );
        requester.sendMessage(
                BasicCommands.getInstance().getMessage("messages.tpa-denied-sender")
                        .replace("%player%", target.getName())
        );

        // Remove from map
        TpaCommand.removeTpaRequest(target.getUniqueId());
        return true;
    }
}
