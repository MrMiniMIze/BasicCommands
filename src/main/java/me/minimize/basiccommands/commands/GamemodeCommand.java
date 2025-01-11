package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check permission
        if (!sender.hasPermission("basiccommands.gamemode")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        // Must have at least 1 argument -> gamemode
        if (args.length < 1) {
            sender.sendMessage("Usage: /gamemode <survival|creative|adventure|spectator> [player]");
            return true;
        }

        // Convert argument to a GameMode
        GameMode gameMode;
        switch (args[0].toLowerCase()) {
            case "survival":
            case "0":
                gameMode = GameMode.SURVIVAL;
                break;
            case "creative":
            case "1":
                gameMode = GameMode.CREATIVE;
                break;
            case "adventure":
            case "2":
                gameMode = GameMode.ADVENTURE;
                break;
            case "spectator":
            case "3":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.invalid-gamemode"));
                return true;
        }

        // Determine target
        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-not-found"));
                return true;
            }
        } else {
            // If no player argument, must be a player
            if (!(sender instanceof Player)) {
                sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
                return true;
            }
            target = (Player) sender;
        }

        // Set game mode
        target.setGameMode(gameMode);

        // Send messages
        if (target.equals(sender)) {
            // Self
            sender.sendMessage(
                    BasicCommands.getInstance().getMessage("messages.gamemode-updated")
                            .replace("%gamemode%", gameMode.toString())
            );
        } else {
            // Changed someone else's
            sender.sendMessage(
                    BasicCommands.getInstance().getMessage("messages.gamemode-updated-other")
                            .replace("%player%", target.getName())
                            .replace("%gamemode%", gameMode.toString())
            );
            target.sendMessage(
                    BasicCommands.getInstance().getMessage("messages.gamemode-updated")
                            .replace("%gamemode%", gameMode.toString())
            );
        }

        return true;
    }
}
