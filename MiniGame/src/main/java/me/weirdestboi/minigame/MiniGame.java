package me.weirdestboi.minigame;

import me.weirdestboi.minigame.Methods.GameStartMethod;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class MiniGame extends JavaPlugin {



    public ArrayList<Player> Queue= new ArrayList<>();
    public ArrayList<Player> Starting= new ArrayList<>();
    public ArrayList<Player> inGame= new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("game").setExecutor(new startCommand(this));
        getCommand("sb").setExecutor(new testCommand());
        getServer().getPluginManager().registerEvents(new StartListener(this), this);
    }
}
