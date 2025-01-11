package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpacceptCommand implements CommandExecutor {

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

        Player target = (Player) sender; // The target is who typed /tpaccept
        UUID requesterUuid = TpaCommand.getTpaRequester(target.getUniqueId());

        if (requesterUuid == null) {
            // No pending TPA
            target.sendMessage(BasicCommands.getInstance().getMessage("messages.no-tpa-request"));
            return true;
        }

        // We found someone who wants to teleport to 'target'
        Player requester = Bukkit.getPlayer(requesterUuid);

        if (requester == null) {
            // The requesting player is offline or null
            target.sendMessage(BasicCommands.getInstance().getMessage("messages.player-not-found"));
            // Remove from map to clean up
            TpaCommand.removeTpaRequest(target.getUniqueId());
            return true;
        }

        // Accept it: teleport the requester to target
        target.sendMessage(
                BasicCommands.getInstance().getMessage("messages.tpa-accepted")
                        .replace("%player%", requester.getName())
        );
        requester.sendMessage(
                BasicCommands.getInstance().getMessage("messages.tpa-accepted-sender")
                        .replace("%player%", target.getName())
        );

        requester.teleport(target.getLocation());

        // Remove from map
        TpaCommand.removeTpaRequest(target.getUniqueId());
        return true;
    }
}
