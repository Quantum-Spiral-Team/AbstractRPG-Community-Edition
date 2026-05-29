package com.vivern.arpg.items;

import com.vivern.arpg.items.animation.EnumFlick;
import com.vivern.arpg.items.animation.Flicks;
import com.vivern.arpg.entity.EntityMinigunIcicle;
import com.vivern.arpg.main.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IcicleMinigun extends ItemWeapon {
   public static int maxammo = 230;

   public IcicleMinigun() {
      this.setRegistryName("icicle_minigun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("icicle_minigun");
      this.setMaxDamage(7000);
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
   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state == 1) {
         Flicks.INSTANCE.setClientAnimation(player, slot, EnumFlick.RELOAD, 0, 65, -1, 65);
      }
   }

   @Override
   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote) {
         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            if (Flicks.INSTANCE.confirmPack(player).get(itemSlot, EnumFlick.SHOOT) == null) {
               Flicks.INSTANCE.setClientAnimation(player, itemSlot, EnumFlick.SHOOT, 0, Integer.MAX_VALUE, 0, 0);
            } else {
               int attackTime = NBTHelper.GetNBTint(itemstack, "attack_time");
               Flicks.INSTANCE.setTendency(player, itemSlot, EnumFlick.SHOOT, Math.min(MathHelper.ceil(attackTime / 3.0F), 50));
            }
         }
      } else {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            int attackTime = NBTHelper.GetNBTint(itemstack, "attack_time");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            boolean isAttacking = false;
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  NBTHelper.GiveNBTint(itemstack, 0, "attack_time");
                  NBTHelper.AddNBTint(itemstack, 1, "attack_time");
                  isAttacking = true;
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.icicle_minigun,
                        SoundCategory.AMBIENT,
                        0.8F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     EntityMinigunIcicle bullet = new EntityMinigunIcicle(world, player, itemstack);
                     Weapons.shoot(
                        bullet,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.getFloat("velocity"),
                        parameters.getEnchantedF("inaccuracy", acc),
                        -0.35F,
                        0.5F,
                        0.8F
                     );
                     bullet.livetime = parameters.getEnchantedI("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(bullet);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateMetadataReload(
                  itemstack, player, new ItemStack(ItemsRegister.ICICLE_MINIGUN_CLIP, 1, 1), maxammo, new ItemStack(ItemsRegister.ICICLE_MINIGUN_CLIP, 1, 0)
               )) {
                  world.playSound(
                          null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.icicle_minigun_rel,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 20.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 43, EnumHand.MAIN_HAND);
                  IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
               }
            }

            if (!isAttacking && attackTime > 0) {
               int slowdown = parameters.getEnchantedI("slowdown", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
               int attackTimeForFast = parameters.getEnchantedI("attacktime_for_fast", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack));
               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) != 0 && ammo > 0) {
                  NBTHelper.SetNBTint(itemstack, Math.max(attackTime - slowdown, 0), "attack_time");
               } else {
                  NBTHelper.SetNBTint(itemstack, MathHelper.clamp(attackTime - slowdown, 0, attackTimeForFast), "attack_time");
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 4;
      Booom.frequency = -0.9F;
      Booom.x = 1.0F;
      Booom.y = (itemRand.nextFloat() - 0.5F) / 3.0F;
      Booom.z = (itemRand.nextFloat() - 0.5F) / 3.0F;
      Booom.power = 0.58F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int attackTime = NBTHelper.GetNBTint(itemstack, "attack_time");
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      int minCooldown = parameters.getInt("min_cooldown");
      int maxCooldown = parameters.getInt("max_cooldown");
      int attackTimeForFast = parameters.getEnchantedI("attacktime_for_fast", rapidity);
      float ft1 = 1.0F - GetMOP.getFromTo((float)attackTime, 0.0F, (float)attackTimeForFast);
      return minCooldown + Math.round((maxCooldown - minCooldown) * ft1);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }
}
