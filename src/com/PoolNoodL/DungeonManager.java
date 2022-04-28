package com.PoolNoodL;

import com.PoolNoodL.commands.Commands;
import com.PoolNoodL.items.Compass;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonManager implements Listener {

    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    public static DungeonGeneration dg = new DungeonGeneration();
    public static float timeUntilStart = 10;
    public static boolean isRunning = false;
    public static int time = 0;
    public static int treasures = 0;

    Random r = new Random();
    EntityType type;
    Location loc;
    BukkitTask timerID;

    public static List<Player> pQueue = new ArrayList<>();
    public static List<Player> pRunning = new ArrayList<>();
    public static List<Player> pFinished = new ArrayList<>();


    @EventHandler
    public void SpawnEvent(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL) && e.getLocation().getWorld()==Main.dungeonWorld) {e.setCancelled(true);}
    }

    public int getTime() {
        return time;
    }

    public void QueueTimer() {
        if (isRunning) {return;}
        scheduler.runTaskTimer(Main.getPlugin(Main.class), new Runnable() {
            public void run() {
                for (Player pl:pQueue) {
                    pl.sendMessage("§c" + timeUntilStart + "§f seconds until start!");
                    pl.playSound(pl.getLocation(), Sound.BLOCK_BAMBOO_HIT,1,2);
                }

                if (timeUntilStart <= 0) {
                    for (Player p : pQueue) {
                        p.sendTitle("§c§lDungeon opened", "§4You have 5 minutes to escape", 1, 50, 1);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL,1,1);
                        p.getInventory().removeItem(Compass.getStar());
                        Main.pm.AssignClass();

                        Main.dungeonWorld.getBlockAt(new Location(Main.dungeonWorld, dg.getStartLoc().getX() - 1, dg.getStartLoc().getY() + 1, dg.getStartLoc().getZ() + 10)).setType(Material.AIR);
                        Main.dungeonWorld.getBlockAt(new Location(Main.dungeonWorld, dg.getStartLoc().getX() - 1, dg.getStartLoc().getY() + 2, dg.getStartLoc().getZ() + 10)).setType(Material.AIR);
                    }
                    pRunning = pQueue;
                    scheduler.cancelTasks(Main.getPlugin(Main.class));
                    GameTick();
                    Timer();
                    isRunning = true;
                }

                timeUntilStart--;
            }
        },0,20);
    }

    public void Timer() {
        timerID = scheduler.runTaskTimer(Main.getPlugin(Main.class), new Runnable() {
            public void run() {
                time++;
                if (time == 240) {
                    for (Player player:pRunning) {
                        player.sendMessage("§fDungeon collapses in §c60 §fseconds!");
                    }
                }
                if (time > 290) {
                    for (Player player:pRunning) {
                        player.sendMessage("§fDungeon collapses in §c" + (300 - time) + "§fseconds!");
                    }
                    if (time > 300) {
                        Main.pm.Complete(false);
                    }
                }
            }
        },0,20);
    }

    public void GameTick() {
        scheduler.runTaskTimer(Main.getPlugin(Main.class), new Runnable() {
            int tries = 0;
            @Override
            public void run() {
                for (Player p:pRunning) {
                    loc = new Location(Main.dungeonWorld,p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
                    int minus = r.nextInt(8);

                    int x = r.nextInt(3) + 1;
                    int y = r.nextInt(2);
                    int z = r.nextInt(3) + 1;

                    switch (minus) {
                        default -> loc.add(x,y,z);
                        case 1 -> loc.add(-x,y,z);
                        case 2 -> loc.add(x,-y,z);
                        case 3 -> loc.add(x,y,-z);
                        case 4 -> loc.add(-x,-y,z);
                        case 5 -> loc.add(x,-y,-z);
                        case 6 -> loc.add(-x,y,-z);
                        case 7 -> loc.add(-x,-y,-z);
                    }

                    if (!Main.dungeonWorld.getBlockAt(loc).isPassable()) {
                        tries++;
                        if (tries < 7) {
                            run();
                            return;
                        } else {
                            loc = p.getLocation().add(3,1,3);
                        }

                    }

                    switch (r.nextInt(4)) {
                        default -> type = EntityType.SKELETON;
                        case 1 -> type = EntityType.ZOMBIE;
                        case 2 -> type = EntityType.SPIDER;
                        case 3 -> type = EntityType.SLIME;
                    }

                    Main.dungeonWorld.spawnEntity(loc, type);
                    tries = 0;
                }
            }
        },0,20*5);
    }

    @EventHandler
    public void Suffocation(EntityDamageEvent e) {
        if (e.getEntity().getWorld() != Main.dungeonWorld) {return;}
        if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {e.setCancelled(true);}
    }

    public void End() {
        Main.dungeonWorld.getEntities().clear();
        File deleteFolder = Bukkit.getWorld("world_dungeon").getWorldFolder();
        Bukkit.unloadWorld(Main.dungeonWorld,false);
        deleteWorld(deleteFolder);
        scheduler.cancelTasks(Main.getPlugin(Main.class));
        reset();
    }

    public void reset() {
        Main.dungeonWorld = null;
        isRunning = false;
        time = 0;
        timeUntilStart = 10;
        timerID.cancel();
        pQueue.clear();
        pRunning.clear();
        pFinished.clear();
        treasures = 0;
        Commands.isGenerated = false;
    }

    public void deleteWorld(File path) {
        if(path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteWorld(file);
                } else {
                    file.delete();
                }
            }
        }
    }

}
