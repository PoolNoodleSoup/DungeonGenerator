package com.PoolNoodL.commands;

import com.PoolNoodL.DungeonGeneration;
import com.PoolNoodL.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class DungeonCommand implements CommandExecutor {

    public static DungeonGeneration dg = new DungeonGeneration();

    World dungeonWorld;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("generate")) {
                if (dungeonWorld == null) {
                    dungeonWorld = getDungeonWorld();
                }


                if (!Main.Singleton.isRunning) {
                    p.sendMessage("§f[DungeonGenerator] generating dungeon...");
                    if (args.length >= 1) {
                        dg.generateDungeon(Integer.parseInt(args[0]));
                    } else {
                        dg.generateDungeon(4);
                        //loc = new Location(dungeonWorld, -10, 11, 10);
                    }
                }
                p.sendMessage("§f[DungeonGenerator] joining dungeon...");
                Location loc = new Location(dungeonWorld, dg.getStartLoc().getX() -10, dg.getStartLoc().getY() +1, dg.getStartLoc().getZ() +10);
                if (!p.getInventory().contains(Main.Singleton.createItem("§l§fSelection Star", Material.NETHER_STAR, Collections.singletonList("§7Open the class selection GUI"),1,true))) {
                    p.getInventory().addItem(Main.Singleton.createItem("§l§fSelection Star", Material.NETHER_STAR, Collections.singletonList("§7Open the class selection GUI"),1,true));
                }
                p.teleport(loc);
            }
        }
        return false;
    }


    public World getDungeonWorld() {
        WorldCreator wc = new WorldCreator("world_dungeon");
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.FLAT);
        wc.generateStructures(false);
        return wc.createWorld();
    }

}
