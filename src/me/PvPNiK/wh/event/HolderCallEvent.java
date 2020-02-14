package me.PvPNiK.wh.event;

import me.PvPNiK.wh.Position;
import me.PvPNiK.wh.Utils;
import me.PvPNiK.wh.WeaponHolder;
import me.PvPNiK.wh.cooldown.Cooldown;
import me.PvPNiK.wh.cooldown.CooldownManager;
import me.PvPNiK.wh.holder.Holder;
import me.PvPNiK.wh.holder.HolderManager;
import me.PvPNiK.wh.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HolderCallEvent implements Listener {

    @EventHandler
    public void onHolderBreak(BlockBreakEvent e) {
        HolderManager holderManager = WeaponHolder.getInstance().holderManager;
        Block block = e.getBlock();
        Location blockLocation = block.getLocation();

        if (!holderManager.contains(blockLocation))
            return;

        Holder holder = holderManager.getHolder(blockLocation);
        Bukkit.getPluginManager().callEvent(new HolderBreakEvent(holder, e.getPlayer()));
    }

    @EventHandler
    public void onStringPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() != Material.TRIPWIRE)
            return;

        Block b = null;
        for (BlockFace blockFace : BlockFace.values()) {
            if (blockFace.name().contains("_") || blockFace == BlockFace.SELF || blockFace == BlockFace.UP || blockFace == BlockFace.DOWN)
                continue;

            b = e.getBlock().getRelative(blockFace);

            if (b == null || b.getType() != Material.TRIPWIRE_HOOK)
                continue;

            Location blockLocation = b.getLocation();

            if (!WeaponHolder.getInstance().holderManager.contains(blockLocation))
                continue;

            Holder holder = WeaponHolder.getInstance().holderManager.getHolder(blockLocation);
            Bukkit.getPluginManager().callEvent(new HolderBreakEvent(holder, e.getPlayer()));
        }

    }

    @EventHandler
    public void holderRemoveItem(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getHand() != EquipmentSlot.HAND)
            return;
        if (e.getClickedBlock().getType() != Material.TRIPWIRE_HOOK)
            return;

        Player player = e.getPlayer();

        if (CooldownManager.isInCooldown(player.getUniqueId()))
            return;

        HolderManager holderManager = WeaponHolder.getInstance().holderManager;
        Block block = e.getClickedBlock();
        Location blockLocation = block.getLocation();

        if (!holderManager.contains(blockLocation))
            return;

        WorldManager worldManager = WeaponHolder.getInstance().worldManager;
        String worldName = blockLocation.getWorld().getName();
        if (!worldManager.isEnabled(worldName))
            return;

        Holder holder = holderManager.getHolder(blockLocation);
        Bukkit.getPluginManager().callEvent(new HolderUnequipItemEvent(holder, player));
    }

    @EventHandler
    public void holderAddItem(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getHand() != EquipmentSlot.HAND)
            return;
        if (e.getClickedBlock().getType() != Material.TRIPWIRE_HOOK)
            return;

        Player player = e.getPlayer();

        if (CooldownManager.isInCooldown(player.getUniqueId()))
            return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null || itemStack.getType() == Material.AIR)
            return;

        if (itemStack.getType() == Material.STRING) {
            if (player.isSneaking()) {
                return;
            } else {
                e.setCancelled(true);
            }
        }

        HolderManager holderManager = WeaponHolder.getInstance().holderManager;
        Block block = e.getClickedBlock();

        if (holderManager.contains(block.getLocation()))
            return;

        WorldManager worldManager = WeaponHolder.getInstance().worldManager;
        String worldName = block.getLocation().getWorld().getName();
        if (!worldManager.isEnabled(worldName))
            return;

        Position position = Utils.getPosition(block.getData());
        if (position == null)
            return;

        Holder holder = new Holder(block.getLocation(), itemStack, position);
        Bukkit.getPluginManager().callEvent(new HolderEquipItemEvent(holder, player));
    }

}
