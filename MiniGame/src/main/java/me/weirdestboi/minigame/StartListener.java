package me.weirdestboi.minigame;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class StartListener implements Listener {


    MiniGame plugin;

    public StartListener(MiniGame plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (plugin.Starting.contains(e.getPlayer())) {
            e.getPlayer().teleport(e.getFrom());
        }
    }

}
