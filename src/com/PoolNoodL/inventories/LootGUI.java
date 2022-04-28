package com.PoolNoodL.inventories;

import com.PoolNoodL.Main;
import com.PoolNoodL.items.LootItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Random;
import java.util.function.Function;

public class LootGUI implements InventoryHolder {
    private Inventory inv;
    Random r = new Random();
    LootItems li = new LootItems();

    public LootGUI() {
        inv = Generate();
    }

    private Inventory Generate() {
        Inventory generated = Bukkit.createInventory(this,27,"Loot chest");
        for (int i = 0; i < r.nextInt(3); i++) {
            generated.setItem(r.nextInt(27), li.getCrystal());
        }
        for (int i = 0; i < r.nextInt(10) + 2; i++) {
            generated.setItem(r.nextInt(27), li.getMaterial());
        }
        for (int i = 0; i < r.nextInt(5) + 2; i++) {
            generated.setItem(r.nextInt(27), li.getTreasure());
        }
        int j = r.nextInt(10);
        if (j == 9) {
            generated.setItem(13, li.getJewel());
        }
        return generated;
    }

    @Override
    public Inventory getInventory() { return Generate(); }
}
