package me.pvpnik.weaponHolder.commands;

import me.pvpnik.weaponHolder.WeaponHolder;
import me.pvpnik.weaponHolder.utils.Messages;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeaponHolderCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {

        if (!command.getName().equalsIgnoreCase("weaponholder"))
            return false;

        if (commandSender instanceof Player) {
            if (!commandSender.isOp() && !commandSender.hasPermission("weaponholder.reload")) {
                commandSender.sendMessage(Messages.noPermission());
            }
        }

        if (args.length != 1) {
            commandSender.sendMessage(Messages.PREFIX + "Usage: /wh reload");
            return false;
        }

        String option = args[0];

        if (!option.equalsIgnoreCase("reload")) {
            commandSender.sendMessage(Messages.PREFIX + "Usage: /wh reload");
            return false;
        }

        WeaponHolder.getInstance().itemPositionManager.reload();
        WeaponHolder.getInstance().worldManager.reload();
        commandSender.sendMessage(Messages.PREFIX + "Reloaded worlds.yml and itemPosition.yml");

        return true;
    }

}
