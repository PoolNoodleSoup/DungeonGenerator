package com.PoolNoodL.commands;

import com.PoolNoodL.DungeonGeneration;
import com.PoolNoodL.DungeonManager;
import com.PoolNoodL.Main;
import com.PoolNoodL.items.Compass;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    public static DungeonGeneration dg = new DungeonGeneration();
    public static boolean isGenerated = false;
    static final Location startloc = dg.getStartLoc();



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("generate")) {

                if (Main.dungeonWorld == null) {
                    p.sendMessage("§f[DungeonGenerator] generating dungeon...");
                    createDungeonWorld();
                }

                if (!DungeonManager.isRunning) {
                    if (!isGenerated) {
                        isGenerated = true;
                        if (args.length >= 1) {
                            dg.generateDungeon(Integer.parseInt(args[0]));
                        } else {
                            dg.generateDungeon(4);
                        }
                    }
                } else {
                    if (!DungeonManager.pRunning.contains(p)) {
                        p.sendMessage("§f[DungeonGenerator] Dungeon already running!");
                        return true;
                    }
                }

                p.sendMessage("§f[DungeonGenerator] joining dungeon...");
                Location loc = new Location(Main.dungeonWorld, dg.getStartLoc().getX() -10, dg.getStartLoc().getY() +1, dg.getStartLoc().getZ() +10);
                p.getInventory().clear();
                p.getInventory().setItem(4, Compass.getStar());
                p.teleport(loc);
                p.setGameMode(GameMode.SURVIVAL);
            }
            else if(cmd.getName().equalsIgnoreCase("lobby")) {

                if (p.getWorld()  == Main.dungeonWorld) {
                    World w = Main.Singleton.getServer().getWorld("world");
                    p.teleport(new Location(w, w.getSpawnLocation().getX(),w.getSpawnLocation().getY(),w.getSpawnLocation().getZ()));
                }
            }
        }
        return false;
    }


    public void createDungeonWorld() {
        World dungeonWorld;
        if (Main.dungeonWorld == null) {
            WorldCreator wc = new WorldCreator("world_dungeon");
            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.FLAT);
            wc.generateStructures(false);
            dungeonWorld = wc.createWorld();
            Main.dungeonWorld = dungeonWorld;
        }
    }
}
