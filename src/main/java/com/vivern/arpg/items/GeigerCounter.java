package com.vivern.arpg.items;

import com.vivern.arpg.main.Mana;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GeigerCounter extends Item {
   public GeigerCounter() {
      this.setRegistryName("geiger_counter");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("geiger_counter");
   }

   @Override
   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (Minecraft.getMinecraft().player == entityIn
         && (Minecraft.getMinecraft().player.getHeldItemMainhand() == stack || Minecraft.getMinecraft().player.getHeldItemOffhand() == stack)) {
         Minecraft.getMinecraft()
            .ingameGUI
            .setOverlayMessage(TextFormatting.GREEN + "Your radiation: " + Mana.getRad(Minecraft.getMinecraft().player), false);
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      if (Minecraft.getMinecraft().player != null) {
         tooltip.add(TextFormatting.GREEN + "Your radiation: " + Mana.getRad(Minecraft.getMinecraft().player));
      }
   }
}
