package com.vivern.arpg.enchants;

import com.vivern.arpg.items.IWeapon;
import com.vivern.arpg.main.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ReloadingEnch extends Enchantment {
   public ReloadingEnch() {
      super(Rarity.RARE, EnchantmentInit.enchantmentTypeWeapon, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
      this.setName("Reloading");
      this.setRegistryName("arpg:reloading_ench");
      EnchantmentInit.ENCHANTMENTSLIST.add(this);
   }

   @Override
   public int getMinEnchantability(int enchantmentLevel) {
      return 8 + (enchantmentLevel - 1) * 10;
   }

   @Override
   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 20;
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
