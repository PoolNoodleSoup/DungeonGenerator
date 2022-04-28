package com.PoolNoodL.items;

import com.PoolNoodL.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class ClassItems {
    public ItemStack getItem(String name) {
        return switch (name) {
            default -> null;

            case "39_1" -> Main.Singleton.createItem("§2§lLeather cap", Material.LEATHER_HELMET, Collections.singletonList("§r§7Basic armor"), 1, false);
            case "38_1" -> Main.Singleton.createItem("§2§lLeather chestplate", Material.LEATHER_CHESTPLATE, Collections.singletonList("§r§7Basic armor"), 1, false);
            case "37_1" -> Main.Singleton.createItem("§2§lLeather leggings", Material.LEATHER_LEGGINGS, Collections.singletonList("§r§7Basic armor"), 1, false);
            case "36_1" -> Main.Singleton.createItem("§2§lLeather boots", Material.LEATHER_BOOTS,Collections.singletonList("§r§7Basic armor"),1,false);

            case "39_2" -> Main.Singleton.createItem("§a§lChainmail cap", Material.CHAINMAIL_HELMET, Collections.singletonList("§r§7Advanced armor"), 1, false);
            case "38_2" -> Main.Singleton.createItem("§a§lChainmail chestplate", Material.CHAINMAIL_CHESTPLATE, Collections.singletonList("§r§7Advanced armor"), 1, false);
            case "37_2" -> Main.Singleton.createItem("§a§lChainmail leggings", Material.CHAINMAIL_LEGGINGS, Collections.singletonList("§r§7Advanced armor"), 1, false);
            case "36_2" -> Main.Singleton.createItem("§a§lChainmail boots", Material.CHAINMAIL_BOOTS,Collections.singletonList("§r§7Advanced armor"),1,false);

            case "Sword_1" -> Main.Singleton.createItem("§2§lSword", Material.STONE_SWORD, Collections.singletonList("§r§7Basic weapon"), 1, false);
            case "Shield" -> Main.Singleton.createItem("§a§lShield", Material.SHIELD, Collections.singletonList("§r§7Blocks hits"), 1, false);
            case "Bow" -> Main.Singleton.createItem("§a§lBow", Material.BOW, Collections.singletonList("§r§7Ranged weapon"), 1, true);
            case "Quiver" -> Main.Singleton.createItem("§a§lQuiver", Material.ARROW, Collections.singletonList("§r§7Infinite quiver"), 1, true);

            case "Shovel_1" -> Main.Singleton.createItem("§2§lShovel", Material.STONE_SHOVEL, Collections.singletonList("§r§7Basic tool"), 1, false);
            case "Pick_1" -> Main.Singleton.createItem("§2§lPickaxe", Material.STONE_PICKAXE, Collections.singletonList("§r§7Basic tool"), 1, false);
            case "Shovel_2" -> Main.Singleton.createItem("§a§lShovel", Material.IRON_SHOVEL, Collections.singletonList("§r§7Advanced tool"), 1, false);
            case "Pick_2" -> Main.Singleton.createItem("§a§lPickaxe", Material.IRON_PICKAXE, Collections.singletonList("§r§7Advanced tool"), 1, false);

            case "SpecialShovel" -> Main.Singleton.createItem("§a§lShovel", Material.IRON_SHOVEL, Collections.singletonList("§r§7Advanced weapon?"), 1, true);
            case "Wand" -> Main.Singleton.createItem("§a§lMagic Wand", Material.BLAZE_ROD, Collections.singletonList("§r§7A basic weapon"), 1, true);

            case "Ability" -> Main.Singleton.createItem("§e§lAbility", Material.GRAY_DYE, Collections.singletonList("§r§7title"), 1, true);
            case "Heal" -> Main.Singleton.createItem("§e§lHealing Herbs", Material.GREEN_DYE, Collections.singletonList("§r§7Right click to heal someone to full"), 1, true);
            case "Rage" -> Main.Singleton.createItem("§e§lRage", Material.RED_DYE, Collections.singletonList("§r§7Right click to unleash your inner strength"), 1, true);
            case "Speed" -> Main.Singleton.createItem("§e§lGlucose", Material.LIGHT_BLUE_DYE, Collections.singletonList("§r§7Consuming this will result in accelerated movement"), 1, true);
            case "Boost" -> Main.Singleton.createItem("§e§lBoost", Material.YELLOW_DYE, Collections.singletonList("§r§7Boosts nearby players"), 1, true);
        };
    }
}
