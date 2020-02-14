package me.PvPNiK.wh;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {

        if (!command.getName().equalsIgnoreCase("weaponholder"))
            return false;

        if (commandSender instanceof Player) {
            if (!commandSender.isOp() && !commandSender.hasPermission("weaponholder.reload")) {
                commandSender.sendMessage("not enough permissions.");
            }
        }

        if (args.length != 1) {
            commandSender.sendMessage("Usage: /wh reload");
            return false;
        }

        String option = args[0];

        if (!option.equalsIgnoreCase("reload")) {
            commandSender.sendMessage("Usage: /wh reload");
            return false;
        }

        WeaponHolder.getInstance().itemPositionManager.reload();
        WeaponHolder.getInstance().worldManager.reload();
        commandSender.sendMessage("Reloaded worlds.yml and itemPosition.yml");

        return true;
    }

}
