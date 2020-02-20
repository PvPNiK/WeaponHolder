package me.pvpnik.weaponHolder.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

public class Messages {
    private Messages(){}

    public static final String PREFIX = ChatColor.RED + "[" + ChatColor.WHITE + "WeaponHolder" + ChatColor.RED + "] " + ChatColor.RESET;

    public static String noPermission() {
        return PREFIX + "Not enough permissions!";
    }

    public static String weaponHolderIsUsed(UUID uuid) {
        return PREFIX + Bukkit.getOfflinePlayer(uuid).getName() + "'s item.";
    }

}
