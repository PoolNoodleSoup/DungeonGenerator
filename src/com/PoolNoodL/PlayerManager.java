package com.PoolNoodL;

import com.PoolNoodL.commands.DungeonCommand;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class PlayerManager implements Listener {

    public static DungeonCommand dc = new DungeonCommand();
    public static Integer reqPlayerCount = 1;
    public static HashMap<UUID,String> PlayerClass = new HashMap<>();

    World dungeonWorld = dc.getDungeonWorld();
    static List<Player> players = new ArrayList<>();

    public void Start() {
        if (players.size() != reqPlayerCount) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).sendMessage("§f You need exactly " +reqPlayerCount+ " players to start a dungeon!");
            }
        }else{
            for (Player p: players) {
                p.sendMessage("§f Enough players joined, dungeon starts in 60 seconds! Please choose your class in the meantime!");
            }
            Main.Singleton.StartTimer();
        }
    }
    //TODO playerClass != players
    public void AssignClass() {
        for (Player pl: players) {
            PlayerClass.putIfAbsent(pl.getUniqueId(), "§cW");
        }
        for (int i = 0; i<PlayerClass.size(); i++) {
            for (UUID u: PlayerClass.keySet()) {
                Player p = Bukkit.getPlayer(u);
                p.getInventory().setItem(8,Main.Singleton.createItem("§l§fSelection Star", Material.NETHER_STAR,Collections.singletonList("§7Open the class selection GUI"),1,true));
                switch (PlayerClass.get(u)) {
                    default:
                    case "§cW":
                        p.getInventory().setItem(0,Main.Singleton.createItem("§l§cSword", Material.STONE_SWORD,Collections.singletonList("§r§7A basic weapon"),1,false));

                        p.getInventory().setItem(38,Main.Singleton.createItem("§l§cChain chestplate", Material.CHAINMAIL_CHESTPLATE,Collections.singletonList("§r§7Advanced armor"),1,false));
                        p.getInventory().setItem(36,Main.Singleton.createItem("§l§cLeather boots", Material.LEATHER_BOOTS,Collections.singletonList("§r§7Basic armor"),1,false));
                        break;
                    case "§eE":
                        p.getInventory().setItem(0,Main.Singleton.createItem("§l§eShovel", Material.IRON_SHOVEL,Collections.singletonList("§r§7A basic weapon?"),1,false));

                        p.getInventory().setItem(38,Main.Singleton.createItem("§l§eLeather chestplate", Material.LEATHER_CHESTPLATE,Collections.singletonList("§r§7Basic armor"),1,false));
                        p.getInventory().setItem(37,Main.Singleton.createItem("§l§eLeather leggings", Material.LEATHER_LEGGINGS,Collections.singletonList("§r§7Basic armor"),1,false));
                        p.getInventory().setItem(36,Main.Singleton.createItem("§l§eLeather boots", Material.LEATHER_BOOTS,Collections.singletonList("§r§7Basic armor"),1,false));
                        break;
                    case "§aD":
                        p.getInventory().setItem(0,Main.Singleton.createItem("§l§aSword", Material.WOODEN_SWORD,Collections.singletonList("§r§7A basic weapon"),1,false));
                        p.getInventory().setItem(1,Main.Singleton.createItem("§l§aBow", Material.BOW,Collections.singletonList("§r§7A basic weapon"),1,true));
                        p.getInventory().setItem(9,Main.Singleton.createItem("§l§aMagical arrow", Material.ARROW,Collections.singletonList("§r§7Infinite quiver"),1,true));

                        p.getInventory().setItem(39,Main.Singleton.createItem("§l§aLeather cap", Material.LEATHER_HELMET,Collections.singletonList("§r§7Basic armor"),1,false));
                        p.getInventory().setItem(38,Main.Singleton.createItem("§l§aLeather chestplate", Material.LEATHER_CHESTPLATE,Collections.singletonList("§r§7Basic armor"),1,false));
                        break;
                    case "§dM":
                        p.getInventory().setItem(0,Main.Singleton.createItem("§l§dMagic Wand", Material.BLAZE_ROD,Collections.singletonList("§r§7A basic weapon"),1,true));

                        p.getInventory().setItem(39,Main.Singleton.createItem("§l§dLeather cap", Material.LEATHER_HELMET,Collections.singletonList("§r§7Basic armor"),1,false));
                        p.getInventory().setItem(37,Main.Singleton.createItem("§l§dLeather leggings", Material.LEATHER_LEGGINGS,Collections.singletonList("§r§7Basic armor"),1,false));
                        p.getInventory().setItem(36,Main.Singleton.createItem("§l§dLeather boots", Material.LEATHER_BOOTS,Collections.singletonList("§r§7Basic armor"),1,false));
                        break;
                }
            }
        }

    }


    @EventHandler
    public void playerJoin(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld() == dungeonWorld) {
            players.add(p);
            Main.Singleton.isTimer = true;
            Start();
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        if (e.getPlayer().getWorld() == dungeonWorld) {
            players.remove(e.getPlayer());
            Main.Singleton.isTimer = false;
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        World w = Main.Singleton.getServer().getWorld("world");
        e.getPlayer().teleport(new Location(w, w.getSpawnLocation().getX(),w.getSpawnLocation().getY(),w.getSpawnLocation().getZ()));
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getWorld() == dungeonWorld) {
            if (e.getBlock().getType() == Material.GOLD_BLOCK) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_CROSSBOW_HIT,0,0);
            }else if(e.getBlock().getType() == Material.GRAVEL) {
                e.getBlock().getDrops().clear();
            } else {
                e.setCancelled(true);
            }
        }
    }
}
