package com.vivern.arpg.items;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.renders.ManaBar;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.vivern.arpg.sound.MovingSoundWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public abstract class ItemWeapon extends Item implements IWeapon {
   @SideOnly(Side.CLIENT)
   public static ArrayList<MovingSoundWeapon> loopedSoundsPlayed = new ArrayList<>();
   @SideOnly(Side.CLIENT)
   public static Predicate<? super MovingSoundWeapon> loopedSoundsRemover = snd -> snd.isDonePlaying();

   @SideOnly(Side.CLIENT)
   public boolean hasSpecialDescription() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      IEnergyItem.addRFInformation(stack, worldIn, tooltip, flagIn);
      if (this.maxFluidForDescription() > 0 && stack.hasTagCompound() && stack.getTagCompound().hasKey("Fluid")) {
         NBTTagCompound tagFluid = stack.getTagCompound().getCompoundTag("Fluid");
         String fluidName = tagFluid.getString("FluidName");
         Fluid fluid = FluidRegistry.getFluid(fluidName);
         fluidName = fluid == null ? fluidName : I18n.translateToLocal(fluid.getUnlocalizedName());
         int stored = tagFluid.getInteger("Amount");
         int maxStored = this.maxFluidForDescription();
         tooltip.add(TextFormatting.RED + "Fluid <" + fluidName + "> " + stored + "/" + maxStored);
         addTooltipBar((float) stored / maxStored, 9689, TextFormatting.GOLD, TextFormatting.DARK_GRAY, tooltip);
      }

      if (Keyboard.isKeyDown(42)) {
         addTooltip(parameters, tooltip, "damage");
         addTooltip(parameters, tooltip, "knockback");
         addTooltip(parameters, tooltip, "damage_radius");
         addTooltip(parameters, tooltip, "rf_to_shoot");
         addTooltip(parameters, tooltip, "manacost");
         addTooltip(parameters, tooltip, "length");
         addTooltip(parameters, tooltip, "end_size");
         addTooltip(parameters, tooltip, "min_pull_time");
         addTooltip(parameters, tooltip, "max_pull_time");
         addTooltip(parameters, tooltip, "shield_angle");
         addTooltip(parameters, tooltip, "damage_reduce");
         addTooltip(parameters, tooltip, "max_hits");
         float maxPullTime = parameters.getFloat("max_pull_time");
         if (maxPullTime != 0.0F) {
            addTooltip(parameters, tooltip, "velocity");
            tooltip.add(TextFormatting.GRAY + Ln.translate("bow_damage_velocity"));
         }
      }

      tooltip.add(TextFormatting.DARK_AQUA + Ln.translate("cooldown") + ": " + this.getCooldownTime(stack));
      int reload = this.getReloadTime(stack);
      if (reload > 0) {
         tooltip.add(TextFormatting.DARK_AQUA + Ln.translate("reload") + ": " + reload);
      }

      String name = this.getRegistryName().getPath();
      tooltip.add(TextFormatting.WHITE + Ln.translate("description." + name));
      if (this.hasSpecialDescription()) {
         if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) > 0) {
            tooltip.add(TextFormatting.LIGHT_PURPLE + "*Special* " + TextFormatting.AQUA + Ln.translate("descspecial." + name));
         } else if (Keyboard.isKeyDown(42)) {
            tooltip.add(TextFormatting.DARK_PURPLE + "*If special* " + TextFormatting.DARK_AQUA + Ln.translate("descspecial." + name));
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public static void addTooltip(WeaponParameters parameters, List<String> tooltip, String parameter) {
      float value = parameters.getFloat(parameter);
      if (value != 0.0F) {
         tooltip.add(TextFormatting.GRAY + Ln.translate(parameter) + ": " + ManaBar.asString(value));
      }
   }

   @SideOnly(Side.CLIENT)
   public static void addTooltipBar(float progress, int charCode, TextFormatting filledColor, TextFormatting depletedColor, List<String> tooltip) {
      StringBuilder sb = new StringBuilder();
      sb.append(filledColor);
      boolean depleted = false;
      int count = (int)(progress * 16.0F);

      for (int i = 0; i < 16; i++) {
         if (i >= count && !depleted) {
            sb.append(depletedColor);
            depleted = true;
         }

         sb.append((char)charCode);
      }

      tooltip.add(sb.toString());
   }

   @SideOnly(Side.CLIENT)
   public void playOrContinueLoopSound(
      EntityLivingBase entity,
      SoundEvent soundEvent,
      SoundCategory category,
      float volume,
      float pitch,
      int playtime,
      int startTime,
      int endTime,
      float startPitch,
      float endPitch
   ) {
      loopedSoundsPlayed.removeIf(loopedSoundsRemover);

      for (MovingSoundWeapon soundhas : loopedSoundsPlayed) {
         if (soundhas.soundEvent == soundEvent && soundhas.entity == entity) {
            soundhas.endDate = entity.world.getTotalWorldTime() + playtime;
            return;
         }
      }

      MovingSoundWeapon sound = new MovingSoundWeapon(
         entity, this, soundEvent, category, volume, pitch, playtime, startTime, endTime, startPitch, endPitch
      );
      Minecraft.getMinecraft().getSoundHandler().playSound(sound);
      loopedSoundsPlayed.add(sound);
   }

   @SideOnly(Side.CLIENT)
   public int maxFluidForDescription() {
      return 0;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }
}
