package me.PvPNiK.wh;

import me.PvPNiK.wh.event.HolderCallEvent;
import me.PvPNiK.wh.holder.HolderListener;
import me.PvPNiK.wh.holder.HolderManager;
import me.PvPNiK.wh.itemPosition.ItemPositionFile;
import me.PvPNiK.wh.itemPosition.ItemPositionManager;
import me.PvPNiK.wh.world.WorldFile;
import me.PvPNiK.wh.world.WorldManager;
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
        getCommand("weaponholder").setExecutor(new Command());

        for (World world : Bukkit.getWorlds()) {
            for (Entity en : world.getEntities()) {
                if (en instanceof ArmorStand) {
                    if (en.getCustomName().equals(getDescription().getName())) {
                        en.remove();
                    }
                }
            }
        }
    }

}
