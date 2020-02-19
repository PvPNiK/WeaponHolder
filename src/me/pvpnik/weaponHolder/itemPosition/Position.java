package me.pvpnik.weaponHolder.itemPosition;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Position {
    SOUTH(0.875, -0.85, 0.95, 0, 0),
    WEST(0.07, -0.85, 0.876, 90, 0),
    NORTH(0.115, -0.85, 0.06, 180, 0),
    EAST(0.93, -0.85, 0.125, -90, 0);

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    Position(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorlds().get(0), x, y, z, yaw, pitch);
    }
}
