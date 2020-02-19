package me.pvpnik.weaponHolder.world;

import me.pvpnik.weaponHolder.WeaponHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class WorldManager {

    HashMap<String, Boolean> worlds;
    WorldFile worldFile;

    public WorldManager() {
        worlds = new HashMap<>();
        worldFile = WeaponHolder.getInstance().worldFile;
        load();
    }

    public boolean isEnabled(String worldName) {
        if (!worlds.containsKey(worldName))
            return false;

        return worlds.get(worldName);
    }

    public void reload() {
        worlds.clear();
        load();
    }

    public void load() {
        worldFile.loadYamls();
        YamlConfiguration config = worldFile.getFile();

        if (!config.contains("worlds"))
            return;
        for (String worldName : config.getConfigurationSection("worlds").getKeys(false)) {
            if (config.contains("worlds." + worldName + ".enable")) {
                try {
                    worlds.put(worldName, config.getBoolean("worlds." + worldName + ".enable"));
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WeaponHolder! Boolean not found, worlds.yml");
                    Bukkit.getConsoleSender().sendMessage("Boolean now found in: " + "worlds." + worldName + ".enable");
                }
            }

        }

    }

}
