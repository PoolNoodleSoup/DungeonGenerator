package com.PoolNoodL.inventories;

import com.PoolNoodL.DungeonGeneration;
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
    static PlayerManager pm = new PlayerManager();

    public ClassSelectGUI() {
        inv = Bukkit.createInventory(this, 54, "Select a Class");
        Init();
    }

    private void Init() {
        ItemStack item;
        for (int i=0;i<54;i++) {
            inv.setItem(i, new ItemStack(createItem("",Material.BLACK_STAINED_GLASS_PANE, Collections.singletonList(""),1)));
        }
        inv.setItem(19, new ItemStack(createItem("§l§cWARRIOR",Material.IRON_SWORD, Collections.singletonList("§7Lif stel"),1)));
        inv.setItem(21, new ItemStack(createItem("§l§eEXPLORER",Material.SPYGLASS, Collections.singletonList("§7showel n' pick"),1)));
        inv.setItem(23, new ItemStack(createItem("§l§aDRUID",Material.BOW, Collections.singletonList("§7bow, heal"),1)));
        inv.setItem(25, new ItemStack(createItem("§l§dMAGE",Material.BLAZE_ROD, Collections.singletonList("§7aoe, heal"),1)));
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof  ClassSelectGUI) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) {return;}
            switch (e.getCurrentItem().getType()) {
                case IRON_SWORD:
                    p.sendMessage("§fSelected class: §l§cWARRIOR");
                    if (pm.PlayerClass.containsKey(p.getUniqueId())) {
                        pm.PlayerClass.replace(p.getUniqueId(),"§cW");
                    } else {
                        pm.PlayerClass.put(p.getUniqueId(),"§cW");
                    }
                    break;
                case SPYGLASS:
                    p.sendMessage("§fSelected class: §l§eEXPLORER");
                    if (pm.PlayerClass.containsKey(p.getUniqueId())) {
                        pm.PlayerClass.replace(p.getUniqueId(),"§eE");
                    } else {
                        pm.PlayerClass.put(p.getUniqueId(),"§eE");
                    }
                    break;
                case BOW:
                    p.sendMessage("§fSelected class: §l§aDRUID");
                    if (pm.PlayerClass.containsKey(p.getUniqueId())) {
                        pm.PlayerClass.replace(p.getUniqueId(),"§aD");
                    } else {
                        pm.PlayerClass.put(p.getUniqueId(),"§aD");
                    }
                    break;
                case BLAZE_ROD:
                    p.sendMessage("§fSelected class: §l§dMAGE");
                    if (pm.PlayerClass.containsKey(p.getUniqueId())) {
                        pm.PlayerClass.replace(p.getUniqueId(),"§dM");
                    } else {
                        pm.PlayerClass.put(p.getUniqueId(),"§dM");
                    }
                    break;
            }
        }
    }








    public ItemStack createItem(String name, Material mat, List<String> lore, Integer amount) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;

    }
}
