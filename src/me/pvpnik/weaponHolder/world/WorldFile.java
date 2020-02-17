package me.PvPNiK.wh.world;

import me.PvPNiK.wh.WeaponHolder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WorldFile {
    private String fileName = "world.yml";
    private File file = null;
    private YamlConfiguration yaml = new YamlConfiguration();

    public WorldFile() {
        file = new File(WeaponHolder.getInstance().getDataFolder(), fileName);
        mkdir();
        loadYamls();
    }

    private void mkdir() {
        if (!file.exists()) {
            WeaponHolder.getInstance().saveResource(fileName, false);
        }
    }

    private void loadYamls() {
        try {
            yaml.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getFile() {
        return yaml;
    }

    public void saveFile() {
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
