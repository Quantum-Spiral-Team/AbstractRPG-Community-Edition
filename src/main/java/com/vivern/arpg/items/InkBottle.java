package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InkBottle extends Item {
   public int burntime = -1;
   public boolean beacon = false;
   public boolean ench = false;

   public InkBottle(String name, int maxdamage) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
      this.setMaxStackSize(1);
   }

   @Override
   public boolean isDamageable() {
      return true;
   }

   public InkBottle setEnchantGlow() {
      this.ench = true;
      return this;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public boolean hasEffect(ItemStack stack) {
      return this.ench ? this.ench : super.hasEffect(stack);
   }
}
