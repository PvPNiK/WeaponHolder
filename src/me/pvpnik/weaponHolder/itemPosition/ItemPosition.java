package me.PvPNiK.wh.itemPosition;

import me.PvPNiK.wh.Position;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;

public class ItemPosition {

    private Material material;
    private HashMap<Position, Location> positions;

    public ItemPosition(Material material, HashMap<Position, Location> positions) {
        this.material = material;
        this.positions = new HashMap<>(positions);
    }

    public boolean hasLocation(Position position) {
        return positions.containsKey(position);
    }

    public Location getLocation(Position position) {
        return positions.get(position);
    }

}
