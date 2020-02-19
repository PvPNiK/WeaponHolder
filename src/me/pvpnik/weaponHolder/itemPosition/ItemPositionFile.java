package me.pvpnik.weaponHolder.itemPosition;

import me.pvpnik.weaponHolder.WeaponHolder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ItemPositionFile {
    private File itemPosition = null;
    private YamlConfiguration itemPositionYaml = new YamlConfiguration();

    public ItemPositionFile() {
        itemPosition = new File(WeaponHolder.getInstance().getDataFolder(), "itemPosition.yml");
        mkdir();
        loadYamls();
    }

    private void mkdir() {
        if (!itemPosition.exists()) {
            WeaponHolder.getInstance().saveResource("itemPosition.yml", false);
        }
    }

    public void loadYamls() {
        try {
            itemPositionYaml.load(itemPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getFile() {
        return itemPositionYaml;
    }

    public void saveFile() {
        try {
            itemPositionYaml.save(itemPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
