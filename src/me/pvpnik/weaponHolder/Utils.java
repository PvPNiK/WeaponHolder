package me.PvPNiK.wh;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class Utils {

    public static final Location emptyLoc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0, 0, 0);

    public static boolean isAttached(byte b) {
        int i;
        for (i = 4; i <= 7; i++) {
            if (b == i) {
                return true;
            }
        }
        for (i = 12; i <= 15; i++) {
            if (b == i) {
                return true;
            }
        }
        return false;
    }

    public static Position getPosition(byte b) {
        switch (b) {
            case 0:
                return Position.SOUTH;
            case 1:
                return Position.WEST;
            case 2:
                return Position.NORTH;
            case 3:
                return Position.EAST;
            default:
                return null;
        }
    }

    public static ArmorStand getNewArmorStand(Location location) {
        ArmorStand as = location.getWorld().spawn(location, ArmorStand.class);

        as.setVisible(false);
        as.setInvulnerable(true);

        as.setCustomName(WeaponHolder.getInstance().getDescription().getName());
        as.setCustomNameVisible(false);

        as.setBasePlate(false);
        as.setArms(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.setRightArmPose(new EulerAngle(Math.toRadians(90), 0, 0));
        return as;
    }

}
