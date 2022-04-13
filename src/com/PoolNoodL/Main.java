package com.PoolNoodL;

import com.PoolNoodL.commands.DungeonCommand;
import com.PoolNoodL.inventories.ClassSelectGUI;
import com.PoolNoodL.items.Compass;
import com.PoolNoodL.items.MagicWand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Main extends JavaPlugin {

    public static Main Singleton = null;
    public float timeUntilStart = 10;
    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    boolean isTimer = true;
    public boolean isRunning = false;
    public static DungeonGeneration dg;
    public static DungeonManager dm;
    public static PlayerManager pm;

    List<Player> players = new ArrayList<>();

    @Override
    public void onEnable() {
        System.out.println("§f[DungeonGenerator]§2Plugin enabled");

        if (Singleton == null) {
            Singleton = this;
        }
        //register commands
        DungeonCommand command = new DungeonCommand();
        getCommand("generate").setExecutor(command);

        //register Listeners
        getServer().getPluginManager().registerEvents(new PlayerManager(), this);
        getServer().getPluginManager().registerEvents(new ClassSelectGUI(), this);
        getServer().getPluginManager().registerEvents(new Compass(), this);
        getServer().getPluginManager().registerEvents(new MagicWand(), this);
        getServer().getPluginManager().registerEvents( new DungeonManager(), this);

        dg = new DungeonGeneration();
        dm = new DungeonManager();
        pm = new PlayerManager();
    }

    @Override
    public void onDisable() {System.out.println("§f[DungeonGenerator]§cPlugin disabled");}



    public void StartTimer() {
        scheduler.scheduleSyncDelayedTask(this, new Runnable() {

            @Override
            public void run() {
                List<Player> players = pm.players;
                for (Player p : players) {
                    p.sendMessage("§c" + timeUntilStart + "§f seconds until start!");
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND,1,2);
                }

                timeUntilStart--;
                if (timeUntilStart <= 0) {
                    timeUntilStart = 10;
                    for (Player p : players) {
                        p.sendTitle("§c§lDungeon opened", "", 1, 50, 1);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,1,1);
                        pm.AssignClass();
                        dg.Start();
                        dm.GameTick();
                    }
                } else{
                    if (isTimer) {
                        StartTimer();
                    }else {
                        for (Player p : players) {
                            p.sendMessage("§cCancelled!");
                            timeUntilStart = 10;
                        }
                    }
                }
            }
        }, 20);
    }


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
