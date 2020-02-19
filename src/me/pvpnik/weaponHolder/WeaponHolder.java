package me.pvpnik.weaponHolder;

import me.pvpnik.weaponHolder.commands.WeaponHolderCommand;
import me.pvpnik.weaponHolder.event.HolderCallEvent;
import me.pvpnik.weaponHolder.holder.HolderListener;
import me.pvpnik.weaponHolder.holder.HolderManager;
import me.pvpnik.weaponHolder.itemPosition.ItemPositionFile;
import me.pvpnik.weaponHolder.itemPosition.ItemPositionManager;
import me.pvpnik.weaponHolder.world.WorldFile;
import me.pvpnik.weaponHolder.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class WeaponHolder extends JavaPlugin {
    private static WeaponHolder INSTANCE;
    public static WeaponHolder getInstance() {
        return INSTANCE;
    }

    public ItemPositionFile itemPositionFile;
    public ItemPositionManager itemPositionManager;
    public HolderManager holderManager;
    public WorldFile worldFile;
    public WorldManager worldManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        itemPositionFile = new ItemPositionFile();
        itemPositionManager = new ItemPositionManager();
        holderManager = new HolderManager();
        worldFile = new WorldFile();
        worldManager = new WorldManager();

        Bukkit.getPluginManager().registerEvents(new HolderCallEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HolderListener(), this);
        getCommand("weaponholder").setExecutor(new WeaponHolderCommand());

        for (World world : Bukkit.getWorlds()) {
            for (Entity en : world.getEntities()) {
                if (en instanceof ArmorStand) {
                    if (en.getCustomName() != null && en.getCustomName().equals(getDescription().getName())) {
                        en.remove();
                    }
                }
            }
        }
    }

}
