package me.PvPNiK.wh.holder;

import me.PvPNiK.wh.Position;
import me.PvPNiK.wh.WeaponHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class HolderManager {

    HashMap<Location, Holder> holders;

    public HolderManager() {
        holders = new HashMap<>();
        load();
    }

    public boolean contains(Location location) {
        return holders.containsKey(location);
    }

    public void save() {
        if (holders.isEmpty())
            return;

        HolderFile holderFile = new HolderFile();
        holderFile.delete();
        holderFile.create();

        int count = 0;

        for (Location location : holders.keySet()) {
            Holder holder = holders.get(location);
            holderFile.set("holders." + count + ".loc.position", holder.getPosition().name());
            saveLocation(holder.getHolderLocation(), holderFile, "holders." + count + ".loc");
            holderFile.set("holders." + count + ".item", holder.getItemStack());
            count++;
        }
    }

    public void load() {
        HolderFile holderFile = new HolderFile();

        if (!holderFile.fileExists())
            return;
        if (!holderFile.contains("holders"))
            return;

        for (String count : holderFile.getConfig().getConfigurationSection("holders").getKeys(false)) {
            Location location = loadLocation(holderFile, "holders." + count + ".loc");

            ItemStack itemStack = itemStackCheck(holderFile, count);
            if (itemStack == null) return;

            Position position = positionCheck(holderFile, count);
            if (position == null) return;

            Holder holder = new Holder(location, itemStack, position);
            add(holder);
            Bukkit.getScheduler().runTaskLater(WeaponHolder.getInstance(), (T) -> holder.spawn(), 5l);
        }
        holderFile.delete();
    }

    private Position positionCheck(HolderFile holderFile, String count) {
        Position position = null;

        if (holderFile.contains("holders." + count + ".loc.position"))
            position = Position.valueOf(holderFile.getConfig().getString("holders." + count + ".loc.position"));

        if (position == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WeaponHolder! Null Position");
            Bukkit.getConsoleSender().sendMessage("Could not load position: " + count);
        }
        return position;
    }

    private ItemStack itemStackCheck(HolderFile holderFile, String count) {
        ItemStack itemStack = null;

        if (holderFile.contains("holders." + count + ".item"))
            itemStack = holderFile.getConfig().getItemStack("holders." + count + ".item");

        if (itemStack == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WeaponHolder! Null ItemStack");
            Bukkit.getConsoleSender().sendMessage("Could not load itemstack: " + count);
        }
        return itemStack;
    }

    private void saveLocation(Location location, HolderFile holderFile, String path) {
        holderFile.set(path + ".x", location.getX());
        holderFile.set(path + ".y", location.getY());
        holderFile.set(path + ".z", location.getZ());
    }

    private Location loadLocation(HolderFile holderFile, String path) {
        double x = 0;
        if (holderFile.contains(path + ".x"))
            x = holderFile.getConfig().getDouble(path + ".x");

        double y = 0;
        if (holderFile.contains(path + ".y"))
            y = holderFile.getConfig().getDouble(path + ".y");

        double z = 0;
        if (holderFile.contains(path + ".z"))
            z = holderFile.getConfig().getDouble(path + ".z");

        return new Location(Bukkit.getWorlds().get(0), x, y, z, 0, 0);
    }

    public void remove(Location location) {
        holders.remove(location);
    }

    public void add(Holder holder) {
        holders.put(holder.getHolderLocation(), holder);
    }

    public Holder getHolder(Location location) {
        return holders.get(location);
    }

}
