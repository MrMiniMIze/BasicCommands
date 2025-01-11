package me.minimize.basiccommands.commands;

import me.minimize.basiccommands.BasicCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class FixCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Permission check
        if (!sender.hasPermission("basiccommands.fix")) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.no-permission"));
            return true;
        }

        // Must be a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(BasicCommands.getInstance().getMessage("messages.player-only-command"));
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType().isAir()) {
            player.sendMessage("You must hold an item to fix it!");
            return true;
        }

        // Check if it's damageable
        if (item.getItemMeta() instanceof Damageable damageable) {
            damageable.setDamage(0);
            item.setItemMeta(damageable);
            player.sendMessage(BasicCommands.getInstance().getMessage("messages.fix-success"));
        } else {
            player.sendMessage("This item cannot be repaired!");
        }

        return true;
    }
}
