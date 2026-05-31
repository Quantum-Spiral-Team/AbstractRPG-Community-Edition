package com.vivern.arpg.items;

import com.vivern.arpg.main.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdamantiumBattleAxe extends ItemWeapon {
   public AdamantiumBattleAxe() {
      this.setRegistryName("adamantium_battle_axe");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("adamantium_battle_axe");
      this.setMaxDamage(4000);
      this.setMaxStackSize(1);
   }

   @Override
   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void boom(int param) {
      if (param == 0) {
         Boom.lastTick = 32;
         Boom.frequency = -Boom.getFrequencyForTicks(Boom.lastTick);
         Boom.x = -1.0F;
         Boom.y = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Boom.z = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Boom.power = 0.35F;
      }

      if (param == 2) {
         Boom.lastTick = 22;
         Boom.frequency = 0.43F;
         Boom.x = itemRand.nextFloat() < 0.5 ? 0.1F : -0.1F;
         Boom.y = 0.0F;
         Boom.z = 1.0F;
         Boom.power = itemRand.nextFloat() < 0.5 ? -0.1F : 0.1F;
      }

      if (param == 3) {
         Boom.lastTick = 25;
         Boom.frequency = 0.126F;
         Boom.x = 1.0F;
         Boom.y = 0.0F;
         Boom.z = 0.0F;
         Boom.power = 0.1F;
      }
   }

   @Override
   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            if (player.getHeldItemMainhand() == itemstack) {
               if (delay <= 0 && click && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 5, "atdelay");
                  Weapons.setPlayerAnimationOnServer(player, 15, EnumHand.MAIN_HAND);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               } else if (delay <= 0
                  && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0
                  && ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY)
                  && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 11, "atdelay");
                  NBTHelper.giveNBTboolean(itemstack, true, "specattack");
                  NBTHelper.SetNBTboolean(itemstack, true, "specattack");
                  Weapons.setPlayerAnimationOnServer(player, 46, EnumHand.MAIN_HAND);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                  world.playSound(
                          null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.swosh_d,
                     SoundCategory.PLAYERS,
                     0.7F,
                     0.7F + itemRand.nextFloat() / 5.0F
                  );
                  IWeapon.fireBomEffect(this, player, world, 0);
               }

               if (delay == 1) {
                  if (NBTHelper.GetNBTboolean(itemstack, "specattack")) {
                     if (doAlternativeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, false, 70, 7).success) {
                        world.playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_block,
                           SoundCategory.PLAYERS,
                           0.9F,
                           0.6F + itemRand.nextFloat() / 5.0F
                        );
                        IWeapon.fireBomEffect(this, player, world, 2);
                        world.playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
                           SoundCategory.PLAYERS,
                           0.9F,
                           0.8F + itemRand.nextFloat() / 5.0F
                        );
                     } else {
                        world.playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_miss_axe,
                           SoundCategory.PLAYERS,
                           0.6F,
                           0.7F + itemRand.nextFloat() / 5.0F
                        );
                        IWeapon.fireBomEffect(this, player, world, 3);
                     }

                     player.addExhaustion(0.2F);
                     NBTHelper.SetNBTboolean(itemstack, false, "specattack");
                  } else {
                     if (IWeapon.doMeleeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, false, 70, 7).success) {
                        world.playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_axe,
                           SoundCategory.PLAYERS,
                           0.7F,
                           0.8F + itemRand.nextFloat() / 5.0F
                        );
                     } else {
                        world.playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_miss_axe,
                           SoundCategory.PLAYERS,
                           0.6F,
                           0.8F + itemRand.nextFloat() / 5.0F
                        );
                     }

                     player.addExhaustion(0.2F);
                  }
               }
            }
         }
      }
   }

   public static MeleeAttackResult doAlternativeHammerAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical, int sweepAngle, int sweepStepAngle
   ) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return IWeapon.doMeleeHammerAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchantedF("damage_special", sharpness),
         parameters.getEnchantedF("knockback", knockback),
         parameters.getEnchantedF("length", sweeping),
         parameters.getEnchantedF("size", sweeping),
         parameters.getEnchantedF("end_size", sweeping),
         isCritical,
         sweepAngle,
         sweepStepAngle
      );
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return parameters.getEnchantedI(NBTHelper.GetNBTboolean(itemstack, "specattack") ? "cooldown_special" : "cooldown", rapidity);
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
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
