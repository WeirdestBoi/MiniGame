package me.weirdestboi.minigame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class testCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Title", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "DUELS");

        Score s1 = objective.getScore("  ");
        Score s2 = objective.getScore("Opponent: " );
        Score s3 = objective.getScore("Oppo. Health:" );
        Score s4 = objective.getScore("Your Health:");
        Score s5 = objective.getScore("");
        Score s6 = objective.getScore(ChatColor.YELLOW + "WeirdestGames.net");

        s1.setScore(6);
        s2.setScore(5);
        s3.setScore(4);
        s4.setScore(3);
        s5.setScore(2);
        s6.setScore(1);

        player.setScoreboard(scoreboard);

        return true;
    }
}
