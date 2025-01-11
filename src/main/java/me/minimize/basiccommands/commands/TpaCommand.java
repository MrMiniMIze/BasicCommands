package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TpaCommand implements CommandExecutor {

    // Key: target's UUID (the one who must accept), Value: requester's UUID (the one who typed /tpa)
    private static final HashMap<UUID, UUID> tpaRequests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("basiccommands.tpa")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Usage: /tpa <player>");
            return true;
        }

        Player requester = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            requester.sendMessage(BasicCommands.getInstance().getMessage("messages.player-not-found"));
            return true;
        }

        // Save the request
        tpaRequests.put(target.getUniqueId(), requester.getUniqueId());

        // Notify both
        requester.sendMessage(
                BasicCommands.getInstance().getMessage("messages.tpa-request-sent")
                        .replace("%player%", target.getName())
        );
        target.sendMessage(
                BasicCommands.getInstance().getMessage("messages.tpa-request-received")
                        .replace("%player%", requester.getName())
        );

        return true;
    }

    /**
     * Retrieve the UUID of the player who requested to teleport to 'targetUuid'.
     */
    public static UUID getTpaRequester(UUID targetUuid) {
        return tpaRequests.get(targetUuid);
    }

    /**
     * Remove the request from memory after accept/deny (or after some timeout).
     */
    public static void removeTpaRequest(UUID targetUuid) {
        tpaRequests.remove(targetUuid);
    }
}
