package com.vivern.arpg.items;

import com.vivern.arpg.entity.StingerBoltEntity;
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

public class Stinger extends ItemWeapon {
   public static int maxammo = 16;

   public Stinger() {
      this.setRegistryName("stinger");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("stinger");
      this.setMaxDamage(2000);
      this.setMaxStackSize(1);
   }

   @Override
   public float getXpRepairRatio(ItemStack stack) {
      return 3.0F;
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
      Boom.lastTick = 7;
      Boom.frequency = -0.45F;
      Boom.x = 1.0F;
      Boom.y = 0.0F;
      Boom.z = 0.0F;
      Boom.power = 0.27F;
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
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.stinger,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     StingerBoltEntity stinger = new StingerBoltEntity(world, player, itemstack);
                     Weapons.shoot(
                        stinger,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.getFloat("velocity"),
                        parameters.getEnchantedF("inaccuracy", acc),
                        -0.05F,
                        0.5F,
                        0.5F
                     );
                     world.spawnEntity(stinger);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.STINGER_BOLTS, maxammo)) {
                  world.playSound(
                          null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.stinger_reload,
                     SoundCategory.NEUTRAL,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
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
