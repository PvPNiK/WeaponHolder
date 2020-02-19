package me.pvpnik.weaponHolder.cooldown;

import me.pvpnik.weaponHolder.WeaponHolder;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Cooldown extends BukkitRunnable {

    private UUID uuid;

    public Cooldown(UUID uuid) {
        this.uuid = uuid;
        this.runTaskLater(WeaponHolder.getInstance(), 5L);
    }

    @Override
    public void run() {
        CooldownManager.removeCooldown(uuid);
    }

}
