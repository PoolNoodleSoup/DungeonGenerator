package com.PoolNoodL;

import com.PoolNoodL.commands.DungeonCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;

public class DungeonManager implements Listener {

    public static DungeonCommand dc = new DungeonCommand();
    public static PlayerManager pm = new PlayerManager();
    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    World dungeonWorld = dc.getDungeonWorld();
    Random r = new Random();
    EntityType type;


    @EventHandler
    public void SpawnEvent(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {e.setCancelled(true);}
    }
    /*
    @EventHandler
    public void SpawnEvent(SpawnerSpawnEvent e) {

        switch (r.nextInt(3)) {
            case 0 -> type = EntityType.ZOMBIE;
            case 1 -> type = EntityType.SKELETON;
            case 2 -> type = EntityType.SPIDER;
            case 3 -> type = EntityType.SLIME;
        }

        dungeonWorld.spawnEntity(e.getLocation(), type);
        e.setCancelled(true);
    }
     */

    public void GameTick() {
        scheduler.scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
            List<Player> players = pm.players;
            Location loc;
            @Override
            public void run() {
                for (Player p:players) {
                    loc = new Location(dungeonWorld,p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
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

                    if (!dungeonWorld.getBlockAt(loc).isPassable()) {
                        run();
                        return;
                    }
                    switch (r.nextInt(4)) {
                        default -> type = EntityType.SKELETON;
                        case 1 -> type = EntityType.ZOMBIE;
                        case 2 -> type = EntityType.SPIDER;
                        case 3 -> type = EntityType.SLIME;
                    }
                    dungeonWorld.spawnEntity(loc, type);
                }

                if (Main.Singleton.isRunning) {
                    GameTick();
                }
            }

        }, 5*20);
    }


}
