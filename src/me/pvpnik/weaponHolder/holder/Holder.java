package me.pvpnik.weaponHolder.holder;

import com.mysql.jdbc.Buffer;
import me.pvpnik.weaponHolder.itemPosition.Position;
import me.pvpnik.weaponHolder.utils.Utils;
import me.pvpnik.weaponHolder.WeaponHolder;
import me.pvpnik.weaponHolder.itemPosition.ItemPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class Holder {

    private Location holderLocation;
    private ItemStack itemStack;
    private Position position;
    private ArmorStand armorStand;

    public Holder(Location holderLocation, ItemStack itemStack, Position position) {
        this.holderLocation = holderLocation;
        this.itemStack = itemStack;
        this.position = position;
    }

    public void spawn() {
        Location positionLocation = position.getLocation();

        Material material = itemStack.getType();
        if (WeaponHolder.getInstance().itemPositionManager.contains(material)) {
            ItemPosition itemPosition = WeaponHolder.getInstance().itemPositionManager.get(material);
            if (itemPosition.hasLocation(position))
                positionLocation = itemPosition.getLocation(position);
        }

        Location blockLocation = getHolderLocation().clone();
        blockLocation.add(positionLocation);
        blockLocation.setYaw(position.getYaw());
        blockLocation.setPitch(position.getPitch());

        armorStand = Utils.getNewArmorStand(blockLocation);
        if (Utils.isOneHandedVersion())
            armorStand.setItemInHand(itemStack);
        else
            armorStand.getEquipment().setItemInMainHand(itemStack);
    }

    public Location getHolderLocation() {
        return holderLocation;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Position getPosition() {
        return position;
    }
}
