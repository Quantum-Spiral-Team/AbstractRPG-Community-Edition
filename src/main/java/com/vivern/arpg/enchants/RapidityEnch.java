package com.vivern.arpg.enchants;

import com.vivern.arpg.items.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class RapidityEnch extends Enchantment {

    public RapidityEnch() {
        super(Rarity.RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("Rapidity");
        this.setRegistryName("arpg:rapidity_ench");
        EnchantmentInit.ENCHANTMENTSLIST.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 17 + (enchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof IWeapon;
    }

}
