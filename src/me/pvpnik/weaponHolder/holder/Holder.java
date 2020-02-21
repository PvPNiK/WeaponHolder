package me.pvpnik.weaponHolder.holder;

import lombok.Getter;
import lombok.Setter;
import me.pvpnik.weaponHolder.itemPosition.Position;
import me.pvpnik.weaponHolder.utils.Utils;
import me.pvpnik.weaponHolder.WeaponHolder;
import me.pvpnik.weaponHolder.itemPosition.ItemPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Holder {

    @Getter private Location holderLocation;
    @Getter private ArmorStand armorStand;
    @Getter @Setter private ItemStack itemStack;
    @Getter @Setter private Position position;
    @Getter @Setter private UUID owner;

    public Holder(Location holderLocation, ItemStack itemStack, Position position, UUID owner) {
        this.holderLocation = holderLocation;
        this.armorStand = null;
        this.itemStack = itemStack;
        this.position = position;
        this.owner = owner;
    }

    public void spawn() {
        Location positionLocation = position.getLocation();

        Material material = itemStack.getType();
        if (WeaponHolder.getInstance().itemPositionManager.contains(material)) {
            ItemPosition itemPosition = WeaponHolder.getInstance().itemPositionManager.get(material);
            if (itemPosition.hasLocation(position))
                positionLocation = itemPosition.getLocation(position);
        }

        Location blockLocation = holderLocation.clone();
        blockLocation.add(positionLocation);
        blockLocation.setYaw(position.getYaw());
        blockLocation.setPitch(position.getPitch());

        armorStand = Utils.getNewArmorStand(blockLocation);
        if (Utils.isOneHandedVersion())
            armorStand.setItemInHand(itemStack);
        else
            armorStand.getEquipment().setItemInMainHand(itemStack);
    }
}
