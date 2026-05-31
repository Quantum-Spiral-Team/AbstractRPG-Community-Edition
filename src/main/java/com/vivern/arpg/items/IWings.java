package com.vivern.arpg.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public interface IWings {

    String[] itemsNoCompatibleNames = new String[]{"minecraft:elytra"};

    int getMaxFlyTime(ItemStack var1);

    static boolean isEquippableWithChestplate(Item chestplate) {
        return !Arrays.asList(itemsNoCompatibleNames).contains(chestplate.getRegistryName());
    }

    static void chestplateReturnToInv(EntityPlayer player) {
        if (!isEquippableWithChestplate(player.inventory.armorInventory.get(2).getItem())) {
            ItemStack transpStack = player.inventory.armorInventory.get(2);
            int empty = player.inventory.getFirstEmptyStack();
            player.inventory.armorInventory.set(2, ItemStack.EMPTY);
            if (empty != -1) {
                player.inventory.setInventorySlotContents(empty, transpStack);
            } else if (!player.world.isRemote) {
                player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, transpStack));
            }
        }
    }

}
