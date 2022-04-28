package com.PoolNoodL;

import com.PoolNoodL.commands.Commands;
import com.PoolNoodL.inventories.ClassSelectGUI;
import com.PoolNoodL.items.Compass;
import com.PoolNoodL.items.ItemAbilities;
import com.PoolNoodL.items.MagicWand;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {

    public static Main Singleton = null;
    public static DungeonManager dm;
    public static PlayerManager pm;
    public static Commands dc;
    public static World dungeonWorld = null;

    @Override
    public void onEnable() {
        System.out.println("§f[DungeonGenerator]§2Plugin enabled");

        if (Singleton == null) { Singleton = this; }

        //register
        {
            dm = new DungeonManager();
            pm = new PlayerManager();
            dc = new Commands();
            dungeonWorld = null;

            Commands command = new Commands();
            getCommand("generate").setExecutor(command);
            getCommand("lobby").setExecutor(command);

            getServer().getPluginManager().registerEvents(new PlayerManager(), this);
            getServer().getPluginManager().registerEvents(new ClassSelectGUI(), this);
            getServer().getPluginManager().registerEvents(new Compass(), this);
            getServer().getPluginManager().registerEvents(new MagicWand(), this);
            getServer().getPluginManager().registerEvents(new ItemAbilities(), this);
            getServer().getPluginManager().registerEvents(new DungeonManager(), this);

        }
    }

    @Override
    public void onDisable() {System.out.println("§f[DungeonGenerator]§cPlugin disabled");}

    public ItemStack createItem(String name, Material mat, List<String> lore, Integer amount, boolean isEnchant) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setUnbreakable(true);
        if (isEnchant) {
            meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
        }
        item.setItemMeta(meta);
        return item;
    }
}
