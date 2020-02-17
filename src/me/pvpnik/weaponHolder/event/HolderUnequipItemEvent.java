package me.PvPNiK.wh.event;

import me.PvPNiK.wh.holder.Holder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HolderUnequipItemEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    private Player player;
    private Holder holder;

    public HolderUnequipItemEvent(Holder holder, Player player) {
        this.holder = holder;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Holder getHolder() {
        return holder;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() { return  HANDLERS; }
}
