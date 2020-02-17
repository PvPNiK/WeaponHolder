package me.PvPNiK.wh.holder;

import me.PvPNiK.wh.WeaponHolder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class HolderFile {

    private String name;
    private File file;
    private FileConfiguration config;

    private static final File mainfolder = new File("plugins/", WeaponHolder.getInstance().getName());

    public HolderFile() {
        this.name = "holders";
        this.file = new File(mainfolder + "/" + name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public boolean fileExists() {
        return file.exists();
    }

    public boolean create() {
        if (!mainfolder.exists()) mainfolder.mkdir();
        if (fileExists()) return false;
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§cError while creating: §6" + name + ".yml");
            return false;
        }
    }

    public boolean delete() {
        if (!mainfolder.exists()) return false;
        if (!fileExists()) return false;
        return file.delete();
    }

    public boolean save() {
        try {
            config.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§cError while saving: §6" + name + ".yml");
            return false;
        }
    }

    public boolean contains(String path) {
        return getConfig().contains(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

}
