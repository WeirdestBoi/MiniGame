package me.weirdestboi.minigame.Methods;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class giveKitMethod {

    public static void giveKit(Player p) {

        p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
        p.getInventory().addItem(new ItemStack(Material.BOW));
        p.getInventory().addItem(new ItemStack(Material.ARROW, 32));
        p.getInventory().addItem( new ItemStack(Material.GOLDEN_APPLE, 4));
        p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
    }
}
