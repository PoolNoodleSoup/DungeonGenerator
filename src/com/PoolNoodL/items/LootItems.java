package com.PoolNoodL.items;

import com.PoolNoodL.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class LootItems {

    public ItemStack getTreasure() {
        return Main.Singleton.createItem("§e§lTreasure", Material.GOLD_NUGGET, Collections.singletonList("§7It's valuable, what else do you need?"),1,true);
    }
    public ItemStack getMaterial() {
        return Main.Singleton.createItem("§7§lMaterial", Material.CHARCOAL, Collections.singletonList("§7It can surely be used for something."),1,false);
    }
    public ItemStack getCrystal() {
        return Main.Singleton.createItem("§7§lMagical Crystal", Material.AMETHYST_SHARD, Collections.singletonList("§7Vibrates with energy."),1,false);
    }
    public ItemStack getJewel() {
        return Main.Singleton.createItem("§b§lRoyal Jewel", Material.DIAMOND, Collections.singletonList("§7Valuable gemstone."),1,true);
    }
}
