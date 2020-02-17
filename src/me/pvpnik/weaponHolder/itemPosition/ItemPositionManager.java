package me.PvPNiK.wh.itemPosition;

import me.PvPNiK.wh.Position;
import me.PvPNiK.wh.Utils;
import me.PvPNiK.wh.WeaponHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Set;

public class ItemPositionManager {

    private HashMap<Material, ItemPosition> itemPositionHashMap;

    public ItemPositionManager() {
        itemPositionHashMap = new HashMap<>();
        load();
    }

    public boolean contains(Material material) {
        return itemPositionHashMap.containsKey(material);
    }

    public ItemPosition get(Material material) {
        return itemPositionHashMap.get(material);
    }

    public void reload() {
        itemPositionHashMap.clear();
        load();
    }

    public void load() {
        YamlConfiguration file = WeaponHolder.getInstance().itemPositionFile.getFile();

        if (!file.contains("items"))
            return;

        ConfigurationSection cs = file.getConfigurationSection("items");
        Set<String> materials = cs.getKeys(false);
        if (materials.isEmpty())
            return;

        for (String materialName : materials) {
            Material material = getMaterial(materialName);

            if (material == null)
                return;

            HashMap<Position, Location> positions = new HashMap<>();
            for (Position position : Position.values()) {
                String path = "items." + materialName + "." + position.name().toLowerCase();

                Location location = getLocation(file, path);
                if (location.equals(Utils.emptyLoc))
                    continue;

                positions.put(position, location);
            }

            itemPositionHashMap.put(material, new ItemPosition(material, positions));
        }

    }

    private Material getMaterial(String materialName) {
        Material material = Material.getMaterial(materialName.toUpperCase());

        if (material == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WeaponHolder! Material Error");
            Bukkit.getConsoleSender().sendMessage("Could not find material by the name: " + materialName);
            return null;
        }

        if (itemPositionHashMap.containsKey(material)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WeaponHolder! Material Duplicate Error");
            Bukkit.getConsoleSender().sendMessage("Material: " + materialName + " have already position!");
            return null;
        }

        return material;
    }

    private Location getLocation(YamlConfiguration file, String path) {
        Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0, 0, 0);
        if (!file.contains(path))
            return loc;

        if (file.contains(path + ".x"))
            loc.setX(file.getDouble(path + ".x"));

        if (file.contains(path + ".y"))
            loc.setY(file.getDouble(path + ".y"));

        if (file.contains(path + ".z"))
            loc.setZ(file.getDouble(path + ".z"));

        return loc;
    }

}
