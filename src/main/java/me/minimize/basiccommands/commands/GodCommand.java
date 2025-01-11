package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class GodCommand implements CommandExecutor {

    private static final String GOD_META_KEY = "godmode";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission check
        if (!sender.hasPermission("basiccommands.god")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        // Determine target
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

        // Toggle god mode
        boolean isGod = target.hasMetadata(GOD_META_KEY);

        if (!isGod) {
            // Enable
            target.setMetadata(GOD_META_KEY, new FixedMetadataValue(BasicCommands.getInstance(), true));
            if (target.equals(sender)) {
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.god-enabled"));
            } else {
                sender.sendMessage(
                        BasicCommands.getInstance().getMessage("messages.god-enabled-other")
                                .replace("%player%", target.getName())
                );
            }
        } else {
            // Disable
            target.removeMetadata(GOD_META_KEY, BasicCommands.getInstance());
            if (target.equals(sender)) {
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.god-disabled"));
            } else {
                sender.sendMessage(
                        BasicCommands.getInstance().getMessage("messages.god-disabled-other")
                                .replace("%player%", target.getName())
                );
            }
        }

        return true;
    }
}
