package me.PvPNiK.wh.holder;

import me.PvPNiK.wh.WeaponHolder;
import me.PvPNiK.wh.cooldown.Cooldown;
import me.PvPNiK.wh.cooldown.CooldownManager;
import me.PvPNiK.wh.event.HolderBreakEvent;
import me.PvPNiK.wh.event.HolderEquipItemEvent;
import me.PvPNiK.wh.event.HolderUnequipItemEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.ItemStack;

public class HolderListener implements Listener {

    @EventHandler
    public void onHolderEquipItem(HolderEquipItemEvent e) {
        Holder holder = e.getHolder();
        Player player = e.getPlayer();

        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        WeaponHolder.getInstance().holderManager.add(holder);
        holder.spawn();
        CooldownManager.addToCooldown(player.getUniqueId(), new Cooldown(player.getUniqueId()));
    }

    @EventHandler
    public void onHolderUnequipItem(HolderUnequipItemEvent e) {
        Holder holder = e.getHolder();
        Player player = e.getPlayer();

        if (holder.getArmorStand() != null && holder.getArmorStand().isValid()) {
            holder.getArmorStand().remove();
        }

        Location location = e.getHolder().getHolderLocation();

        if (player.getInventory().firstEmpty() == -1)
            location.getWorld().dropItem(location, holder.getItemStack());
        else
            player.getInventory().addItem(holder.getItemStack());

        WeaponHolder.getInstance().holderManager.remove(location);
        CooldownManager.addToCooldown(player.getUniqueId(), new Cooldown(player.getUniqueId()));
    }

    @EventHandler
    public void onHolderBreak(HolderBreakEvent e) {
        Holder holder = e.getHolder();
        Player player = e.getPlayer();

        if (holder.getArmorStand() != null && holder.getArmorStand().isValid()) {
            holder.getArmorStand().remove();
        }

        Location location = e.getHolder().getHolderLocation();

        location.getWorld().dropItem(location, holder.getItemStack());
        WeaponHolder.getInstance().holderManager.remove(location);
    }

    @EventHandler
    public void onArmorStandInteract(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof ArmorStand))
            return;

        if (e.getRightClicked().getCustomName().equals(WeaponHolder.getInstance().getDescription().getName()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent e) {
        for (Entity en : e.getWorld().getEntities()) {
            if (en instanceof ArmorStand) {
                if (en.getCustomName().equals(WeaponHolder.getInstance().getDescription().getName())) {
                    en.remove();
                }
            }
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        WeaponHolder.getInstance().holderManager.save();
    }

}
