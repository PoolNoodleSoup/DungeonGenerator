package com.PoolNoodL.items;

import com.PoolNoodL.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Collections;

public class MagicWand implements Listener {

    @EventHandler
    public void beam (PlayerInteractEvent event){

        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() != null){
            if (event.getPlayer().getItemInHand().equals(Main.Singleton.createItem("§a§lMagic Wand", Material.BLAZE_ROD, Collections.singletonList("§r§7A basic weapon"), 1, true))){
                Location loc = player.getLocation();
                Vector direction = loc.getDirection();
                outerloop: for(double t = 0; t < 16; t+=1){
                    loc.add(direction);
                    loc.add(0, 1.5, 0);
                    player.spawnParticle(Particle.CRIT,loc,3);
                    for (Entity e : loc.getChunk().getEntities()){
                        if (e.getLocation().distance(loc) < 1.5) {
                            if (e != (player)){
                                if(e.getType().isAlive()) {
                                    Damageable d = (Damageable) e;
                                    d.damage(5, player);
                                    player.spawnParticle(Particle.CRIT,loc,3);
                                    break outerloop;
                                }
                            }
                        }
                    }
                    loc.subtract(0, 1.5, 0);
                }
            }
        }
    }
}
