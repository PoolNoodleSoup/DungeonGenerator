package com.PoolNoodL;

import com.PoolNoodL.commands.DungeonCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class DungeonGeneration {
    StructureManager manager = Main.Singleton.getServer().getStructureManager();
    Random r = new Random();
    public static DungeonCommand dc = new DungeonCommand();
    public static PlayerManager pm = new PlayerManager();
    public static DungeonManager dm = new DungeonManager();
    World dungeonWorld = dc.getDungeonWorld();

    Path structFile = Path.of(new File(Main.Singleton.getDataFolder() + File.separator + "/structures").getPath());
    Structure str = null;
    Structure start;
    Structure end;
    Location startLoc = new Location(dungeonWorld,0,10,0);
    File f = new File(String.valueOf(structFile));
    ArrayList<File> files;
    int offsetX = 0;
    int offsetZ = 0;


    public void generateDungeon(float size) {
        try {
            start = manager.loadStructure(new File(Main.Singleton.getDataFolder() + File.separator + "/roomstart.nbt"));
            end = manager.loadStructure(new File(Main.Singleton.getDataFolder() + File.separator + "/roomend.nbt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        files = new ArrayList<>(Arrays.asList(Objects.requireNonNull(f.listFiles())));


        start.place(new Location(dungeonWorld, startLoc.getX() - 21, startLoc.getY(), startLoc.getZ()), true, StructureRotation.NONE, Mirror.NONE, 0, 1, r);
        end.place(new Location(dungeonWorld, startLoc.getX() + 21 * size, startLoc.getY(), startLoc.getZ() + 21 * (size - 1)), true, StructureRotation.NONE, Mirror.NONE, 0, 1, r);
        for (int x = 0; x<size; x++) {
            for (int i = 0; i < size; i++) {
                getRandomStructure();
                StructureRotation rand = randomRot();
                str.place(new Location(dungeonWorld, startLoc.getX() + offsetX + 21*i, startLoc.getY(), startLoc.getZ()+offsetZ+ 21*x), true, rand, Mirror.NONE, 0, 1, r);
            }
        }
    }

    public StructureRotation randomRot() {
        Random r = new Random();
        int i = r.nextInt(4);
        switch (i) {
            default: offsetX=0; offsetZ =0;
                return StructureRotation.NONE;
            case 1: offsetX=20; offsetZ =0;
                return StructureRotation.CLOCKWISE_90;
            case 2: offsetX=20; offsetZ =20;
                return StructureRotation.CLOCKWISE_180;
            case 3: offsetX=0; offsetZ =20;
                return StructureRotation.COUNTERCLOCKWISE_90;
        }
    }
    public Mirror randomMirr() {
        Random r = new Random();
        int i = r.nextInt(3);
        return switch (i) {
            default -> Mirror.NONE;
            case 1 -> Mirror.FRONT_BACK;
            case 2 -> Mirror.LEFT_RIGHT;
        };
    }

    public void Start() {
        dungeonWorld.getBlockAt(new Location(dungeonWorld, startLoc.getX() - 1, startLoc.getY() + 1, startLoc.getZ() + 10)).setType(Material.AIR);
        dungeonWorld.getBlockAt(new Location(dungeonWorld, startLoc.getX() - 1, startLoc.getY() + 2, startLoc.getZ() + 10)).setType(Material.AIR);
        Main.Singleton.isRunning = true;
    }

    public void getRandomStructure() {
        try {
            str = manager.loadStructure(files.get(r.nextInt(files.size())));
            if (str == start){
                getRandomStructure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Location getStartLoc() {
        return startLoc;
    }

}
