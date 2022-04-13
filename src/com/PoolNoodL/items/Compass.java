package com.PoolNoodL.items;

import com.PoolNoodL.Main;
import com.PoolNoodL.inventories.ClassSelectGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collections;

public class Compass implements Listener {

    ClassSelectGUI gui = new ClassSelectGUI();

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().equals(Main.Singleton.createItem("§l§fSelection Star", Material.NETHER_STAR, Collections.singletonList("§7Open the class selection GUI"),1,true))) {
            p.openInventory(gui.getInventory());
        }
    }
}
