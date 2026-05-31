package com.vivern.arpg.enchants;

import com.vivern.arpg.items.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class RangeEnch extends Enchantment {

    public RangeEnch() {
        super(Rarity.UNCOMMON, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("Range");
        this.setRegistryName("arpg:range_ench");
        EnchantmentInit.ENCHANTMENTSLIST.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 8 + (enchantmentLevel - 1) * 12;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof IWeapon;
    }

}
