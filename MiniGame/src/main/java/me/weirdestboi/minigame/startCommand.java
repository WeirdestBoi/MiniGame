package me.weirdestboi.minigame;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class startCommand implements CommandExecutor {


    MiniGame plugin;

    public startCommand(MiniGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String PREFIX = plugin.getConfig().getString("PREFIX");
        PREFIX = PREFIX.replace("&", "§");
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
                sender.sendMessage(PREFIX + ChatColor.YELLOW + "Duel MiniGame Plugin by " + ChatColor.RED + "WeirdestBoi");
                sender.sendMessage(PREFIX + ChatColor.GRAY + "Use /pvpgame help for Help!");
                sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
            } else if (args.length > 0) {


                //Help Page
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
                    sender.sendMessage(PREFIX + ChatColor.YELLOW + "Duel MiniGame commands:");
                    sender.sendMessage(ChatColor.GRAY + "/pvpgame createarena <arena>");
                    sender.sendMessage(ChatColor.GRAY + "/pvpgame setspawn1 <arena>");
                    sender.sendMessage(ChatColor.GRAY + "/pvpgame setspawn2 <arena>");
                    sender.sendMessage(ChatColor.GRAY + "/pvpgame help");
                    sender.sendMessage(ChatColor.GRAY + "/pvpgame prefix");
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");

                    //create Arenas
                } else if (args[0].equalsIgnoreCase("createarena")) {
                    if (args.length == 1) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please Define an Arenaname! Try again!");
                    } else if (args.length == 2) {
                        sender.sendMessage(PREFIX + ChatColor.GREEN + "The Arena has " + args[1] + " been created!");

                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1", 0);
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2", 0);
                        plugin.saveConfig();

                    } else if (args.length > 2) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please don't provide more than one Argument!");
                    }


                    //setSpawnPoints
                } else if (args[0].equalsIgnoreCase("setspawn1")) {
                    if (args.length == 1) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please provide the name of the arena!");
                    } else if (args.length == 2) {
                        sender.sendMessage(PREFIX + ChatColor.GREEN + "The first SpawnPoint has been set successfully! Continue with the second!");
                        Location loc1 = p.getLocation();
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1", loc1);
                        plugin.saveConfig();

                    } else if (args.length > 2) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please don't provide more than one Argument!");
                    }

                    //setSpawnPoint Numbero dos
                } else if (args[0].equalsIgnoreCase("setspawn2")) {
                    if (args.length == 1) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please provide the name of the arena!");
                    } else if (args.length == 2) {
                        sender.sendMessage(PREFIX + ChatColor.GREEN + "You finished the Arena setup!");
                        Location loc2 = p.getLocation();
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2", loc2);
                        plugin.saveConfig();

                    } else {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please don't provide more than one Argument!");
                    }


                    // Prefix Command
                } else if (args[0].equalsIgnoreCase("prefix")) {
                    sender.sendMessage(ChatColor.GRAY + "The set Prefix is: " + PREFIX);

                    // Reload Command
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(PREFIX + ChatColor.GREEN + "The Configuration File has been reloaded!");

                    // Start Command
                } else if (args[0].equalsIgnoreCase("join")) {
                    if (args.length == 1) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please Define an Arena! Try again!");
                    } else {

                        if (plugin.Queue.contains(p)) {
                            plugin.Queue.remove(p);
                            sender.sendMessage(PREFIX + ChatColor.GREEN + "You have left the Queue!");
                        } else if (!plugin.Queue.contains(p)) {
                            plugin.Queue.add(p);
                            sender.sendMessage(PREFIX + ChatColor.GREEN + "You have sucessfully joined the Queue!");
                            if (plugin.Queue.size() == 2) {

                                // start the Game
                                sender.sendMessage(PREFIX + ChatColor.GREEN + "The Game is starting!");

                                // get the Players
                                Player player1 = plugin.Queue.get(0);
                                Player player2 = plugin.Queue.get(1);

                                // Delete Players from Queue ArrayList
                                plugin.Queue.clear();

                                // get Destinations from Config
                                Location loc1 = plugin.getConfig().getLocation("Arenas." + args[1] + "spawn1");
                                Location loc2 = plugin.getConfig().getLocation("Arenas." + args[1] + "spawn2");

                                // Teleport the Players to the Destinations
                                player1.teleport(loc1);
                                player2.teleport(loc2);

                                // Show Countdown to players
                                new BukkitRunnable() {
                                    int counter = 5;

                                    @Override
                                    public void run() {
                                        if (counter >= 0) {
                                            BountifulAPI.sendTitle(player1, 0, 20, 0, "§e" + counter, "§cThe Game Starts!");
                                            BountifulAPI.sendTitle(player2, 0, 20, 0, "§e" + counter, "§cThe Game Starts!");
                                            counter--;
                                        } else {
                                            BountifulAPI.sendTitle(player1, 0, 20, 0, "§LetsoGoo", "§cThe Game Starts!");
                                            BountifulAPI.sendTitle(player2, 0, 20, 0, "§LetsoGoo", "§cThe Game Starts!");
                                            cancel();
                                        }
                                    }
                                }.runTaskTimer(plugin, 0, 20);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}