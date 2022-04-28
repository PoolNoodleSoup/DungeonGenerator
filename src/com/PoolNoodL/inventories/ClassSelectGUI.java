package com.PoolNoodL.inventories;

import com.PoolNoodL.DungeonGeneration;
import com.PoolNoodL.Main;
import com.PoolNoodL.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class ClassSelectGUI implements InventoryHolder, Listener {

    private Inventory inv;

    public ClassSelectGUI() {
        inv = Bukkit.createInventory(this, 54, "Select a Class");
        Init();
    }

    private void Init() {
        for (int i=0;i<54;i++) {
            inv.setItem(i, new ItemStack(Main.Singleton.createItem("",Material.BLACK_STAINED_GLASS_PANE, Collections.singletonList(""),1,false)));
        }
        inv.setItem(19, new ItemStack(Main.Singleton.createItem("§c§lWARRIOR",Material.IRON_SWORD, Collections.singletonList("§7Wield a sword and shield like a true warrior."),1, false)));
        inv.setItem(21, new ItemStack(Main.Singleton.createItem("§e§lEXPLORER",Material.SPYGLASS, Collections.singletonList("§7A team is not complete without good excavation tools."),1, false)));
        inv.setItem(23, new ItemStack(Main.Singleton.createItem("§a§lDRUID",Material.BOW, Collections.singletonList("§7Not every hero fights in the front lines, someone has to heal the wounded."),1, false)));
        inv.setItem(25, new ItemStack(Main.Singleton.createItem("§d§lMAGE",Material.BLAZE_ROD, Collections.singletonList("§7Harness the true power of the elements."),1, false)));
    }


    @Override
    public Inventory getInventory() { return inv; }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof  ClassSelectGUI) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) {return;}
            switch (e.getCurrentItem().getType()) {
                case IRON_SWORD:
                    p.sendMessage("§fSelected class: §c§lWARRIOR");
                    if (PlayerManager.PlayerClass.containsKey(p.getUniqueId())) {
                        PlayerManager.PlayerClass.replace(p.getUniqueId(),"§c§lW");
                    } else {
                        PlayerManager.PlayerClass.put(p.getUniqueId(),"§c§lW");
                    }
                    break;
                case SPYGLASS:
                    p.sendMessage("§fSelected class: §e§lEXPLORER");
                    if (PlayerManager.PlayerClass.containsKey(p.getUniqueId())) {
                        PlayerManager.PlayerClass.replace(p.getUniqueId(),"§e§lE");
                    } else {
                        PlayerManager.PlayerClass.put(p.getUniqueId(),"§e§lE");
                    }
                    break;
                case BOW:
                    p.sendMessage("§fSelected class: §a§lDRUID");
                    if (PlayerManager.PlayerClass.containsKey(p.getUniqueId())) {
                        PlayerManager.PlayerClass.replace(p.getUniqueId(),"§a§lD");
                    } else {
                        PlayerManager.PlayerClass.put(p.getUniqueId(),"§a§lD");
                    }
                    break;
                case BLAZE_ROD:
                    p.sendMessage("§fSelected class: §d§lMAGE");
                    if (PlayerManager.PlayerClass.containsKey(p.getUniqueId())) {
                        PlayerManager.PlayerClass.replace(p.getUniqueId(),"§d§lM");
                    } else {
                        PlayerManager.PlayerClass.put(p.getUniqueId(),"§d§lM");
                    }
                    break;
            }
        }
    }
}
