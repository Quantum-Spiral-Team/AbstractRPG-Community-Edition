package com.vivern.arpg.enchants;

import com.vivern.arpg.items.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class SpecialEnch extends Enchantment {

    public SpecialEnch() {
        super(Rarity.VERY_RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("Special");
        this.setRegistryName("arpg:special_ench");
        EnchantmentInit.ENCHANTMENTSLIST.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 17;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof IWeapon;
    }

}
