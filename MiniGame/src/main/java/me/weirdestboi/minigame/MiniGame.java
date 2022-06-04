package me.weirdestboi.minigame;

import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class MiniGame extends JavaPlugin {



    public ArrayList<Player> Queue= new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("pvpgame").setExecutor(new startCommand(this));
    }
}
