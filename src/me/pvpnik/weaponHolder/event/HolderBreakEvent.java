package me.pvpnik.weaponHolder.event;

import lombok.Getter;
import me.pvpnik.weaponHolder.holder.Holder;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HolderBreakEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    @Nullable
    @Getter private Player player;
    @Getter private Holder holder;
    @Getter private BreakCause breakCause;

    public HolderBreakEvent(Holder holder, Player player, BreakCause breakCause) {
        this.holder = holder;
        this.player = player;
        this.breakCause = breakCause;
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
