package com.PoolNoodL;

import com.PoolNoodL.commands.Commands;
import com.PoolNoodL.inventories.LootGUI;
import com.PoolNoodL.items.ClassItems;
import com.PoolNoodL.items.LootItems;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

import java.util.*;

public class PlayerManager implements Listener {

    public static DungeonManager dm = Main.dm;
    public static LootItems li = new LootItems();
    public static ClassItems ci = new ClassItems();
    LootGUI gui = new LootGUI();

    public static Integer reqPlayerCount = 2;
    public static HashMap<UUID,String> PlayerClass = new HashMap<>();

    public static void checkReqPlayersStart() {
        if (DungeonManager.pQueue.size() != reqPlayerCount) {
            for (Player pl : DungeonManager.pQueue) {
                pl.sendMessage("§f You need exactly " +reqPlayerCount+ " players to start a dungeon!");
            }
        }else{
            for (Player p: DungeonManager.pQueue) {
                p.sendMessage("§f Enough players joined, dungeon starts in 60 seconds! Please choose your class in the meantime!");
            }
            dm.QueueTimer();
        }
    }

    public void AssignClass() {
        for (Player pl: DungeonManager.pQueue) {
            PlayerClass.putIfAbsent(pl.getUniqueId(), "§c§lW");
        }
        for (int i = 0; i<PlayerClass.size(); i++) {
            for (UUID u: PlayerClass.keySet()) {
                Player p = Bukkit.getPlayer(u);
                switch (PlayerClass.get(u)) {
                    case "§c§lW" -> {
                        p.getInventory().setItem(0, ci.getItem("Sword_1"));
                        p.getInventory().setItem(1, ci.getItem("Rage"));
                        for (int j = 0; j < 3; j++) {
                            p.getInventory().getItem(1).setAmount(p.getInventory().getItem(1).getAmount() +1);
                        }
                        p.getInventory().setItem(40, ci.getItem("Shield"));
                        p.getInventory().setItem(39, ci.getItem("39_1"));
                        p.getInventory().setItem(38, ci.getItem("38_2"));
                        p.getInventory().setItem(37, ci.getItem("37_1"));
                        p.getInventory().setItem(36, ci.getItem("36_1"));
                    }
                    case "§e§lE" -> {
                        p.getInventory().setItem(0, ci.getItem("SpecialShovel"));
                        p.getInventory().setItem(1, ci.getItem("Pick_2"));
                        p.getInventory().setItem(2, ci.getItem("Speed"));
                        for (int j = 0; j < 3; j++) {
                            p.getInventory().getItem(2).setAmount(p.getInventory().getItem(2).getAmount() +1);
                        }
                        p.getInventory().setItem(38,ci.getItem("38_1"));
                        p.getInventory().setItem(37,ci.getItem("37_2"));
                        p.getInventory().setItem(36,ci.getItem("36_1"));
                    }
                    case "§a§lD" -> {
                        p.getInventory().setItem(0, ci.getItem("Sword_1"));
                        p.getInventory().setItem(1, ci.getItem("Bow"));
                        p.getInventory().setItem(2, ci.getItem("Heal"));
                        for (int j = 0; j < 3; j++) {
                            p.getInventory().getItem(2).setAmount(p.getInventory().getItem(2).getAmount() +1);
                        }
                        p.getInventory().setItem(9, ci.getItem("Quiver"));
                        p.getInventory().setItem(39, ci.getItem("39_2"));
                        p.getInventory().setItem(38, ci.getItem("38_1"));
                    }
                    case "§d§lM" -> {
                        p.getInventory().setItem(0, ci.getItem("Sword_1"));
                        p.getInventory().setItem(1, ci.getItem("Wand"));
                        p.getInventory().setItem(2, ci.getItem("Boost"));
                        for (int j = 0; j < 3; j++) {
                            p.getInventory().getItem(2).setAmount(p.getInventory().getItem(2).getAmount() +1);
                        }
                        p.getInventory().setItem(39, ci.getItem("39_1"));
                        p.getInventory().setItem(37, ci.getItem("37_1"));
                        p.getInventory().setItem(36, ci.getItem("36_2"));
                    }
                }
            }
        }

    }

    @EventHandler
    public void DropEvent(PlayerDropItemEvent e) {
        if (e.getPlayer().getWorld() == Main.dungeonWorld) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerChangeWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld() == Main.dungeonWorld) {
            DungeonManager.pQueue.add(p);
            checkReqPlayersStart();
        } else if (e.getFrom() == Main.dungeonWorld) {
            DungeonManager.pQueue.remove(p);
            checkReqPlayersStart();
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        if (e.getPlayer().getWorld() == Main.dungeonWorld) {
            DungeonManager.pQueue.remove(e.getPlayer());
            DungeonManager.pRunning.remove(e.getPlayer());
            checkReqPlayersStart();
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        World w = Main.Singleton.getServer().getWorld("world");
        e.getPlayer().teleport(new Location(w, w.getSpawnLocation().getX(),w.getSpawnLocation().getY(),w.getSpawnLocation().getZ()));
    }

    @EventHandler
    public void Death(PlayerDeathEvent e) {
        if (e.getEntity().getWorld() == Main.dungeonWorld) {
            if (!DungeonManager.isRunning) {return;}
            DungeonManager.pRunning.remove(e.getEntity());
            DungeonManager.pQueue.remove(e.getEntity());
            if (DungeonManager.pFinished.size() >= DungeonManager.pRunning.size()) {
                World w = Main.Singleton.getServer().getWorld("world");
                for (Player pl : DungeonManager.pFinished) {
                    pl.teleport(new Location(w, w.getSpawnLocation().getX(),w.getSpawnLocation().getY(),w.getSpawnLocation().getZ()));
                    pl.sendTitle("§c§lDungeon failed", "§4Final time: " + dm.getTime() + " seconds, Treasures collected: " + DungeonManager.treasures, 10, 100, 10);
                    pl.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,1,1);
                    pl.setGameMode(GameMode.SURVIVAL);
                }
                dm.End();
            }
        }

    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getWorld() == Main.dungeonWorld) {
            if (e.getBlock().getType() == Material.GOLD_BLOCK) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                e.getPlayer().getInventory().addItem(li.getTreasure());
                DungeonManager.treasures++;
            }else if(e.getBlock().getType() == Material.GRAVEL) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                e.getPlayer().getInventory().addItem(li.getMaterial());
            } else {
                e.setCancelled(true);
            }
        }
    }

    public void Complete(boolean success) {
        System.out.println("complete" + Main.dungeonWorld + " yes, but "+DungeonManager.pRunning);
        World w = Main.Singleton.getServer().getWorld("world");
        if (success) {
            for (Player pl : DungeonManager.pFinished) {
                pl.teleport(new Location(w, w.getSpawnLocation().getX(),w.getSpawnLocation().getY(),w.getSpawnLocation().getZ()));
                pl.sendTitle("§a§lDungeon completed!", "§2Final time: " + dm.getTime() + " seconds, Treasures collected: " + DungeonManager.treasures, 10, 100, 10);
                pl.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,1,1);
                pl.setGameMode(GameMode.SURVIVAL);
            }
        } else {
            List<Player> pFailed = new ArrayList<>(DungeonManager.pRunning);
            for (Player pFin: DungeonManager.pFinished) {
                if (!pFailed.contains(pFin)) {pFailed.add(pFin);}
            }
            System.out.println("complete2" + pFailed);

            for (Player pl : pFailed) {
                pl.teleport(new Location(w, w.getSpawnLocation().getX(), w.getSpawnLocation().getY(), w.getSpawnLocation().getZ()));
                pl.sendTitle("§c§lDungeon failed", "§4Final time: " + dm.getTime() + " seconds, Treasures collected: " + DungeonManager.treasures, 10, 100, 10);
                pl.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                pl.setGameMode(GameMode.SURVIVAL);
            }
        }
        System.out.println("calling end");
        dm.End();
    }

    @EventHandler
    public void FinishDungeon(PlayerPortalEvent e) {
        if (e.getPlayer().getWorld() == Main.dungeonWorld) {
            if (DungeonManager.treasures >= 10) {
                DungeonManager.pFinished.add(e.getPlayer());
                DungeonManager.pRunning.remove(e.getPlayer());
                if (DungeonManager.pRunning.size() < reqPlayerCount) {

                }
                if (DungeonManager.pFinished.size() == reqPlayerCount) {
                    Complete(true);
                } else if (DungeonManager.pRunning.size() == 0) {
                    Complete(false);
                } else {
                    e.getPlayer().sendTitle("§a§lYou escaped!", "§2Your time: " + dm.getTime() + " seconds", 10, 100, 10);
                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                }
            } else {
                e.getPlayer().sendTitle("§c§lYou need at least 10 treasures to exit", "", 1, 40, 1);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OpenChest(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {return;}
        if (e.getClickedBlock().getType() == Material.CHEST && e.getPlayer().getWorld() == Main.dungeonWorld) {
            e.getPlayer().openInventory(gui.getInventory());
            e.getClickedBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void cancelFall(EntityDamageEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER) {return;}
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity().getWorld() == Main.dungeonWorld) {e.setCancelled(true);}
    }

    @EventHandler
    public void cancelPvP(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER) {return;}
        if (e.getDamager().getType() == EntityType.PLAYER) {e.setCancelled(true);}
    }

}
