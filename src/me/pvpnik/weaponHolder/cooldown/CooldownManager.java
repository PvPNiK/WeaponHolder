package me.pvpnik.weaponHolder.cooldown;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    private static HashMap<UUID, BukkitRunnable> Timer = new HashMap<UUID, BukkitRunnable>();

    public static void addToCooldown(UUID uuid, BukkitRunnable br) {
        if (isInCooldown(uuid)) {
            removeCooldown(uuid);
        }
        Timer.put(uuid, br);
    }

    public static boolean isInCooldown(UUID uuid) {
        return Timer.containsKey(uuid);
    }

    public static boolean removeCooldown(UUID uuid) {
        if (isInCooldown(uuid)) {
            Timer.get(uuid).cancel();
            Timer.remove(uuid);
            return true;
        }
        return false;
    }

}
