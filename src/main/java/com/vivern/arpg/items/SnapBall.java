package com.vivern.arpg.items;

import com.vivern.arpg.entity.EntitySnapball;
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

public class SnapBall extends ItemWeapon implements IEnergyItem {
   public static int maxammo = 1;

   public SnapBall() {
      this.setRegistryName("snap_ball");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("snap_ball");
      this.setMaxDamage(3500);
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

   @Override
   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if ((click || click2) && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.snap_ball,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     IWeapon.fireBomEffect(this, player, world, 0);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }

                     NBTHelper.GiveNBTint(itemstack, 0, "charge");
                     int charge = NBTHelper.GetNBTint(itemstack, "charge");
                     boolean isPowered = charge > parameters.getInt("charge_to_powered");
                     if (click) {
                        EntitySnapball projectile = new EntitySnapball(world, player, itemstack);
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw,
                           0.0F,
                           isPowered ? parameters.getFloat("velocity_charged") : parameters.getFloat("velocity"),
                           parameters.getEnchantedF("inaccuracy", acc),
                           -0.15F,
                           0.5F,
                           0.4F
                        );
                        world.spawnEntity(projectile);
                        if (isPowered) {
                           world.playSound(
                                   null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.snap_ball_explode,
                              SoundCategory.AMBIENT,
                              0.7F,
                              1.2F + itemRand.nextFloat() / 5.0F
                           );
                           projectile.powered = true;
                           NBTHelper.SetNBTint(itemstack, 0, "charge");
                        }
                     } else if (click2) {
                        EntitySnapball projectile = new EntitySnapball(world, player, itemstack);
                        projectile.enablePhysics = true;
                        projectile.exploding = true;
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw,
                           0.0F,
                           parameters.getFloat("velocity_grenade"),
                           parameters.getEnchantedF("inaccuracy", acc),
                           -0.15F,
                           0.5F,
                           0.4F
                        );
                        world.spawnEntity(projectile);
                        if (isPowered) {
                           world.playSound(
                                   null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.snap_ball_explode,
                              SoundCategory.AMBIENT,
                              0.7F,
                              1.2F + itemRand.nextFloat() / 5.0F
                           );
                           projectile.powered = true;
                           NBTHelper.SetNBTint(itemstack, 0, "charge");
                        }
                     }
                  }
               } else {
                  int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                  int RFtoReload = parameters.getEnchantedI("rf_to_reload", reuse);
                  int RF = this.getEnergyStored(itemstack);
                  if (RF >= RFtoReload && this.initiateReload(itemstack, player, ItemsRegister.SNAP_BALL_AMMO, maxammo)) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.snap_ball_rel,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.95F + (itemRand.nextFloat() + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack)) / 10.0F
                     );
                     this.extractEnergyFromItem(itemstack, RFtoReload, false);
                  }
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public float getAdditionalDurabilityBar(ItemStack stack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int charge = NBTHelper.GetNBTint(stack, "charge");
      int chtp = parameters.getInt("charge_to_powered") + 1;
      return MathHelper.clamp((float)charge / chtp, 0.0F, 1.0F);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 13;
      Booom.frequency = -0.245F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.5F;
      Booom.FOVlastTick = 10;
      Booom.FOVfrequency = -0.32F;
      Booom.FOVpower = 0.025F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.LI_ION_CAPACITY * 2;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.LI_ION_THROUGHPUT;
   }
}
