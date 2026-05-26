package com.vivern.arpg.items;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Buckshot extends Item {
   public Buckshot() {
      this.setRegistryName("buckshot");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("buckshot");
   }

   public static ItemStack getBuckshotStack(String bullet, int amount) {
      ItemStack stack = new ItemStack(ItemsRegister.BUCKSHOT, amount);
      NBTHelper.GiveNBTstring(stack, bullet, "bullet");
      NBTHelper.SetNBTstring(stack, bullet, "bullet");
      NBTHelper.SetNBTstring(stack, bullet, "bullet");
      return stack;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      tooltip.add("Ammo: " + NBTHelper.GetNBTstring(stack, "bullet"));
   }
}
