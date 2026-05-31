package com.vivern.arpg.items;

import com.vivern.arpg.entity.EntityCrystalCutter;
import com.vivern.arpg.main.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalCutter extends ItemWeapon {
   public static int maxammo = 7;

   public CrystalCutter() {
      this.setRegistryName("crystal_cutter");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("crystal_cutter");
      this.setMaxDamage(780);
      this.setMaxStackSize(1);
   }

   @Override
   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   @Override
   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   @Override
   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void boom(int param) {
      Boom.lastTick = 16;
      Boom.frequency = -0.196F;
      Boom.FOVlastTick = 16;
      Boom.FOVfrequency = -0.196F;
      Boom.x = 1.0F;
      Boom.y = 0.0F;
      Boom.z = 0.0F;
      Boom.power = 0.2F;
      Boom.FOVpower = 0.08F;
   }

   @Override
   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            int anim = NBTHelper.GetNBTint(itemstack, "animation");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            NBTHelper.SetNBTint(itemstack, anim, "prevanimation");
            if (ammo < maxammo) {
               NBTHelper.SetNBTint(itemstack, Math.min(anim + 1, (maxammo - ammo) * 10), "animation");
            }

            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.crystal_cutter,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     EntityCrystalCutter shoot = new EntityCrystalCutter(world, player, itemstack);
                     Weapons.shoot(
                        shoot,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.getFloat("velocity"),
                        parameters.getEnchantedF("inaccuracy", acc),
                        -0.4F,
                        0.4F,
                        0.2F
                     );
                     if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                        shoot.triple = true;
                     }

                     shoot.cutterSize = parameters.getEnchantedF("cutter_size", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     shoot.livetime = parameters.getEnchantedI("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(shoot);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                        NBTHelper.GiveNBTint(itemstack, 0, "animation");
                        NBTHelper.GiveNBTint(itemstack, 0, "prevanimation");
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.CRYSTAL_CUTTER_AMMO, maxammo)) {
                  world.playSound(
                          null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.crystal_cutter_rel,
                     SoundCategory.NEUTRAL,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                  NBTHelper.SetNBTint(itemstack, 0, "animation");
                  NBTHelper.SetNBTint(itemstack, 0, "prevanimation");
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public float getAdditionalDurabilityBar(ItemStack stack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(stack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
