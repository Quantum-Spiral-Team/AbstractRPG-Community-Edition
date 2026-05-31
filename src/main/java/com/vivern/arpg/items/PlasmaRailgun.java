package com.vivern.arpg.items;

import com.vivern.arpg.entity.PlasmaRailgunShoot;
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

public class PlasmaRailgun extends ItemWeapon implements IEnergyItem {
   public static int maxammo = 9;

   public PlasmaRailgun() {
      this.setRegistryName("plasma_railgun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("plasma_railgun");
      this.setMaxDamage(1300);
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
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int RFtoShoot = parameters.getEnchantedI("rf_to_shoot", reuse);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!hascooldown && this.getEnergyStored(itemstack) >= RFtoShoot) {
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.plasma_railgun,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                        this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                     }

                     PlasmaRailgunShoot projectile = new PlasmaRailgunShoot(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.getFloat("velocity"),
                        parameters.getEnchantedF("inaccuracy", acc),
                        -0.15F,
                        0.25F,
                        0.7F,
                        0.15F
                     );
                     projectile.livetime = parameters.getEnchantedI("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(projectile);
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.PLASMA_RAILGUN_BOLTS, maxammo)) {
                  world.playSound(
                          null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.plasma_railgun_rel,
                     SoundCategory.AMBIENT,
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
   public void boom(int param) {
      Boom.lastTick = 13;
      Boom.frequency = -0.245F;
      Boom.x = 1.0F;
      Boom.y = 0.0F;
      Boom.z = 0.0F;
      Boom.power = 0.5F;
      Boom.FOVlastTick = 10;
      Boom.FOVfrequency = -0.32F;
      Boom.FOVpower = 0.025F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.8F;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public float getAdditionalDurabilityBar(ItemStack stack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(stack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.TOPAZITRON_CAPACITY;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.TOPAZITRON_THROUGHPUT;
   }
}
