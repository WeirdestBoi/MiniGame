package me.weirdestboi.minigame.Methods;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import me.weirdestboi.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class GameStartMethod {


    public static void startGame(Player player1, Player player2, String args) {

        MiniGame plugin;
        

        // get Destinations from Players
        Location loc1 = player1.getLocation();
        Location loc2 = player2.getLocation();

        // set Location coords.
        loc1.setX(plugin.getConfig().getDouble("Arenas." + args + ".spawn1.x"));
        loc1.setY(plugin.getConfig().getDouble("Arenas." + args + ".spawn1.y"));
        loc1.setZ(plugin.getConfig().getDouble("Arenas." + args + ".spawn1.z"));
        loc1.setWorld(Bukkit.getWorld(plugin.getConfig().getString("Arenas." + args + ".spawn1.world")));

        loc2.setX(plugin.getConfig().getDouble("Arenas." + args + ".spawn2.x"));
        loc2.setY(plugin.getConfig().getDouble("Arenas." + args + ".spawn2.y"));
        loc2.setZ(plugin.getConfig().getDouble("Arenas." + args + ".spawn2.z"));
        loc1.setWorld(Bukkit.getWorld(plugin.getConfig().getString("Arenas." + args + ".spawn2.world")));

        // Teleport the Players to the Destinations
        player1.teleport(loc1);
        player2.teleport(loc2);

        plugin.Starting.add(player1);
        plugin.Starting.add(player2);


        // Show Countdown to players
        new BukkitRunnable() {
            int counter = 5;
            @Override
            public void run() {
                if (counter > 0) {
                    BountifulAPI.sendTitle(player1, 0, 20, 0, "§e" + counter, "§cThe Game Starts!");
                    BountifulAPI.sendTitle(player2, 0, 20, 0, "§e" + counter, "§cThe Game Starts!");
                    counter--;
                } else {
                    BountifulAPI.sendTitle(player1, 0, 20, 0, "§e§lLetsoGoo", "§cThe Game Starts!");
                    BountifulAPI.sendTitle(player2, 0, 20, 0, "§e§lLetsoGoo", "§cThe Game Starts!");
                    plugin.Starting.remove(player1);
                    plugin.Starting.remove(player2);
                    plugin.inGame.add(player1);
                    plugin.inGame.add(player2);

                    // Give Items to players
                    giveKitMethod.giveKit(player1);
                    giveKitMethod.giveKit(player2);

                    // Set the ScoreBoard for the First Player
                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getNewScoreboard();

                    Objective objective = scoreboard.registerNewObjective("Title", "dummy");
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "DUELS");

                    Score s1 = objective.getScore(" ");
                    Score s2 = objective.getScore("Opponent: " + ChatColor.GREEN + player2.getDisplayName());
                    Score s3 = objective.getScore("Oppo. Health: " + ChatColor.GREEN + player2.getHealth());
                    Score s4 = objective.getScore("Your Health: " + ChatColor.YELLOW + player1.getHealth());
                    Score s5 = objective.getScore("");
                    Score s6 = objective.getScore(ChatColor.YELLOW + "WeirdestGames.net");

                    s1.setScore(6);
                    s2.setScore(5);
                    s3.setScore(4);
                    s4.setScore(3);
                    s5.setScore(2);
                    s6.setScore(1);

                    player1.setScoreboard(scoreboard);

                    // Set the Scoreboard for Second Player
                    ScoreboardManager manager2 = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard2 = manager2.getNewScoreboard();

                    Objective objective2 = scoreboard2.registerNewObjective("Title", "dummy");
                    objective2.setDisplaySlot(DisplaySlot.SIDEBAR);
                    objective2.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "DUELS");

                    Score b1 = objective2.getScore(" ");
                    Score b2 = objective2.getScore("Opponent: " + ChatColor.GREEN + player1.getDisplayName());
                    Score b3 = objective2.getScore("Oppo. Health: " + ChatColor.GREEN + player1.getHealth());
                    Score b4 = objective2.getScore("Your Health: " + ChatColor.YELLOW + player2.getHealth());
                    Score b5 = objective2.getScore("");
                    Score b6 = objective2.getScore(ChatColor.YELLOW + "WeirdestGames.net");

                    b1.setScore(6);
                    b2.setScore(5);
                    b3.setScore(4);
                    b4.setScore(3);
                    b5.setScore(2);
                    b6.setScore(1);

                    player2.setScoreboard(scoreboard2);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);

    }
}
