package me.weirdestboi.minigame;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import me.weirdestboi.minigame.Methods.GameStartMethod;
import me.weirdestboi.minigame.Methods.giveKitMethod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

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
                sender.sendMessage(PREFIX + ChatColor.GRAY + "Use /game help for Help!");
                sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
            } else if (args.length > 0) {


                //Help Page
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
                    sender.sendMessage(PREFIX + ChatColor.YELLOW + "Duel MiniGame commands:");
                    sender.sendMessage(ChatColor.GRAY + "/game createarena <arena>");
                    sender.sendMessage(ChatColor.GRAY + "/game setspawn1 <arena>");
                    sender.sendMessage(ChatColor.GRAY + "/game setspawn2 <arena>");
                    sender.sendMessage(ChatColor.GRAY + "/game help");
                    sender.sendMessage(ChatColor.GRAY + "/game reload");
                    sender.sendMessage(ChatColor.GRAY + "/game prefix");
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");

                    //create Arenas
                } else if (args[0].equalsIgnoreCase("createarena")) {
                    if (args.length == 1) {
                        sender.sendMessage(PREFIX + ChatColor.RED + "Please Define an Arenaname! Try again!");
                    } else if (args.length == 2) {
                        sender.sendMessage(PREFIX + ChatColor.GREEN + "The Arena has " + args[1] + " been created!");


                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.x", 0);
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.y", 0);
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.z", 0);

                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.x", 0);
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.y", 0);
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.z", 0);
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
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.x", p.getLocation().getX());
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.y", p.getLocation().getY());
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.z", p.getLocation().getZ());
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn1.world", p.getWorld().getName());
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
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.x", p.getLocation().getX());
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.y", p.getLocation().getY());
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.z", p.getLocation().getZ());
                        plugin.getConfig().set("Arenas." + args[1] + ".spawn2.world", p.getWorld().getName());
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

                                GameStartMethod.startGame(player1, player2, args[1]);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}