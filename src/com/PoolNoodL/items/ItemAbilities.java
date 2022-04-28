package com.PoolNoodL.items;

import com.PoolNoodL.Main;
import com.PoolNoodL.commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class ItemAbilities implements Listener {
    public static ClassItems ci = new ClassItems();
    public static HashMap<UUID,Integer> abilityLeft = new HashMap<>();



    @EventHandler
    public void ItemUseOnE(PlayerInteractEntityEvent e){
        if (e.getPlayer().getWorld() != Main.dungeonWorld) {return;}
        Player p = e.getPlayer();


        if (p.getInventory().getItemInHand().getType() == Material.GRAY_DYE) {
            p.sendMessage("This is an ability");
        }
        else if (p.getInventory().getItemInHand().getType() == ci.getItem("Heal").getType()) {
            if (e.getRightClicked().getType() == EntityType.PLAYER) {
                Player pl = (Player) e.getRightClicked();
                pl.setHealth(pl.getMaxHealth());
                p.getInventory().getItem(e.getHand()).setAmount(p.getInventory().getItem(e.getHand()).getAmount() -1);
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE,1,1);
            }
        }

    }

    @EventHandler
    public void ItemUse(PlayerInteractEvent e){
        if (e.getPlayer().getWorld() != Main.dungeonWorld) {return;}
        if (e.getPlayer().getInventory().getItem(e.getPlayer().getInventory().getHeldItemSlot()) == null) {return;}
        Player p = e.getPlayer();
        if (p.getInventory().getItemInHand().getType() == ci.getItem("Rage").getType()) {
            if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                p.sendMessage("You already have an active effect");
            } else {
                p.getInventory().getItem(e.getHand()).setAmount(p.getInventory().getItem(e.getHand()).getAmount() -1);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,150, 3));
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE,1,1);
            }
        }

        if (p.getInventory().getItemInHand().getType() == ci.getItem("Speed").getType()) {
            if (p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                p.sendMessage("You already have an active effect");
            } else {
                p.getInventory().getItem(e.getHand()).setAmount(p.getInventory().getItem(e.getHand()).getAmount() -1);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,60, 1));
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,80, 3));
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE,1,1);
            }
        }

        if (p.getInventory().getItemInHand().getType() == ci.getItem("Boost").getType()) {

            p.getInventory().getItem(e.getHand()).setAmount(p.getInventory().getItem(e.getHand()).getAmount() -1);
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (p.getLocation().distance(other.getLocation()) <= 5) {
                    other.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,60, 1));
                    other.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100, 4));
                }
            }

            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,60, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,80, 3));
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE,1,1);
        }
    }
}
