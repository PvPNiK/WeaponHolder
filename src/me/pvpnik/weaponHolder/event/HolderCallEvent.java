package me.pvpnik.weaponHolder.event;

import me.pvpnik.weaponHolder.itemPosition.Position;
import me.pvpnik.weaponHolder.utils.Messages;
import me.pvpnik.weaponHolder.utils.Utils;
import me.pvpnik.weaponHolder.WeaponHolder;
import me.pvpnik.weaponHolder.cooldown.CooldownManager;
import me.pvpnik.weaponHolder.holder.Holder;
import me.pvpnik.weaponHolder.holder.HolderManager;
import me.pvpnik.weaponHolder.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HolderCallEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHolderBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        HolderManager holderManager = WeaponHolder.getInstance().holderManager;
        for (BlockFace blockFace : BlockFace.values()) {
            if (blockFace.name().contains("_"))
                continue;

            Block block = e.getBlock().getRelative(blockFace);
            Location blockLocation = block.getLocation();

            if (!holderManager.contains(blockLocation))
                continue;

            Holder holder = holderManager.getHolder(blockLocation);
            BreakCause breakCause = blockFace == BlockFace.SELF ? BreakCause.TRIPWIRE_HOOK_BREAK : BreakCause.NEAR_BLOCK_BREAK;
            Bukkit.getPluginManager().callEvent(new HolderBreakEvent(holder, e.getPlayer(), breakCause));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPistonBreakHolder(BlockPistonExtendEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Block block = e.getBlock().getRelative(e.getDirection());

        if (block.getType() != Material.TRIPWIRE_HOOK)
            return;

        HolderManager holderManager = WeaponHolder.getInstance().holderManager;
        Location blockLocation = block.getLocation();

        if (!holderManager.contains(blockLocation))
            return;

        Holder holder = holderManager.getHolder(blockLocation);
        Bukkit.getPluginManager().callEvent(new HolderBreakEvent(holder, null, BreakCause.PISTON_EXTEND));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWaterBreakHolder(BlockPhysicsEvent e) {
        if (e.isCancelled() || !e.getSourceBlock().isLiquid()) {
            return;
        }
        Block block = e.getBlock();

        if (block.getType() != Material.TRIPWIRE_HOOK)
            return;

        HolderManager holderManager = WeaponHolder.getInstance().holderManager;
        Location blockLocation = block.getLocation();

        if (!holderManager.contains(blockLocation))
            return;

        Holder holder = holderManager.getHolder(blockLocation);
        Bukkit.getPluginManager().callEvent(new HolderBreakEvent(holder, null, BreakCause.LIQUID));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStringPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
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
            Bukkit.getPluginManager().callEvent(new HolderBreakEvent(holder, e.getPlayer(), BreakCause.STRING_PLACE));
        }

    }

    @EventHandler
    public void holderRemoveItem(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!toExecute(e, player))
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

        if (!player.isOp() && !player.hasPermission("weaponholder.bypass")) {
            if (!player.getUniqueId().equals(holder.getOwner())) {
                player.sendMessage(Messages.weaponHolderIsUsed(holder.getOwner()));
                return;
            }
        }

        Bukkit.getPluginManager().callEvent(new HolderUnequipItemEvent(holder, player));
    }

    @EventHandler
    public void holderAddItem(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!toExecute(e, player))
            return;

        ItemStack itemStack = Utils.isOneHandedVersion() ? player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();

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

        Holder holder = new Holder(block.getLocation(), itemStack, position, player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new HolderEquipItemEvent(holder, player));
    }

    private boolean toExecute(PlayerInteractEvent e, Player player) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return false;

        if (!Utils.isOneHandedVersion())
            if (e.getHand() != EquipmentSlot.HAND)
                return false;

        if (e.getClickedBlock().getType() != Material.TRIPWIRE_HOOK)
            return false;

        if (CooldownManager.isInCooldown(player.getUniqueId()))
            return false;

        return true;
    }

}
