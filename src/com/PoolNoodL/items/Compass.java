package com.PoolNoodL.items;

import com.PoolNoodL.Main;
import com.PoolNoodL.inventories.ClassSelectGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class Compass implements Listener {

    ClassSelectGUI gui = new ClassSelectGUI();

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().equals(getStar())) {
            p.openInventory(gui.getInventory());
        }
    }

    public static ItemStack getStar() {
        return Main.Singleton.createItem("§f§lSelection Star", Material.NETHER_STAR, Collections.singletonList("§7Open the class selection GUI"),1,true);
    }
}
