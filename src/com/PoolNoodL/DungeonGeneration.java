package com.PoolNoodL;

import com.PoolNoodL.commands.Commands;
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
    public static Commands dc = new Commands();

    Path structFile = Path.of(new File(Main.Singleton.getDataFolder() + File.separator + "/structures").getPath());
    Structure str = null;
    Structure start;
    Structure end;
    static final Location startLoc = new Location(Main.dungeonWorld,0,10,0);
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

        start.place(new Location(Main.dungeonWorld, startLoc.getX() - 21, startLoc.getY(), startLoc.getZ()), true, StructureRotation.NONE, Mirror.NONE, 0, 1, r);
        end.place(new Location(Main.dungeonWorld, startLoc.getX() + 21 * size, startLoc.getY(), startLoc.getZ() + 21 * (size - 1)), true, StructureRotation.NONE, Mirror.NONE, 0, 1, r);
        for (int x = 0; x<size; x++) {
            for (int i = 0; i < size; i++) {
                getRandomStructure();
                StructureRotation rand = randomRot();
                str.place(new Location(Main.dungeonWorld, startLoc.getX() + offsetX + 21*i, startLoc.getY(), startLoc.getZ()+offsetZ+ 21*x), true, rand, Mirror.NONE, 0, 1, r);
            }
        }

        double x = startLoc.getX() + 9;
        double y = startLoc.getY();
        double z = startLoc.getZ() -1;
        for (int j = 0; j<2; j++) {
            for (int i = 0; i<4; i++) {
                for (int cX = 0; cX < 3; cX++) {
                    for (int cY = 0; cY < 5; cY++) {
                        Main.dungeonWorld.getBlockAt(new Location(Main.dungeonWorld, x+cX, y+cY, z)).setType(Material.BLACK_CONCRETE);
                    }
                }
                x += 21;
            }
            x = startLoc.getX() + 9;
            z = size*21;
        }
        z = 30;
        x = -1;
        for (int j = 0; j<2; j++) {
            for (int i = 0; i<3; i++) {
                for (int cZ = 0; cZ < 3; cZ++) {
                    for (int cY = 0; cY < 5; cY++) {
                        Main.dungeonWorld.getBlockAt(new Location(Main.dungeonWorld, x, y+cY, z+cZ)).setType(Material.BLACK_CONCRETE);
                    }
                }
                z += 21;
            }
            z = 9;
            x = size*21;
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
