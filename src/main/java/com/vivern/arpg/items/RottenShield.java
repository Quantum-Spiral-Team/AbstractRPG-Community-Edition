package com.vivern.arpg.items;

import com.vivern.arpg.items.armor.IItemAttacked;
import com.vivern.arpg.entity.EntityLiveHeart;
import com.vivern.arpg.main.*;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RottenShield extends ItemWeapon implements IItemAttacked {
   public RottenShield() {
      this.setRegistryName("rotten_shield");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("rotten_shield");
      this.setMaxDamage(350);
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
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("916DD27B-A123-455F-8C7F-6112A1B50A4C"), "Shield speed mh", -0.05, 0)
            );
         }

         if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("134CA27A-B123-501F-4D8F-3782C6B52C0A"), "Shield speed oh", -0.05, 0)
            );
         }
      }

      return multimap;
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
            float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            NBTHelper.GiveNBTint(itemstack, 0, "blocking");
            int blocks = NBTHelper.GetNBTint(itemstack, "blocking");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (player.getHeldItemMainhand() == itemstack && click || player.getHeldItemOffhand() == itemstack && click2) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (blocks <= 0) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.shield_block,
                        SoundCategory.AMBIENT,
                        0.4F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                     NBTHelper.SetNBTint(itemstack, parameters.getInt("max_hits"), "blocking");
                     player.addExhaustion(parameters.getEnchantedF("exhaustion_on_use", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack)));
                  } else if (player.ticksExisted % 7 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                  }
               }
            } else if (blocks > 0) {
               float starvedCooldownMultiplier = parameters.getFloat("starved_cooldown_multiplier");
               float foodToStarve = parameters.getEnchantedF("food_level_to_starve", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
               NBTHelper.SetNBTint(itemstack, 0, "blocking");
               player.getCooldownTracker()
                  .setCooldown(
                     this, (int)((player.getFoodStats().getFoodLevel() <= foodToStarve ? starvedCooldownMultiplier : 1.0F) * this.getCooldownTime(itemstack))
                  );
            }
         }
      }
   }

   @Override
   public float onAttackedWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         float damageBlocks = parameters.getEnchantedF("damage_reduce", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
         float starvedCooldownMultiplier = parameters.getFloat("starved_cooldown_multiplier");
         float foodToStarve = parameters.getEnchantedF("food_level_to_starve", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack));
         Entity attacker = source.getImmediateSource() == null ? source.getTrueSource() : source.getImmediateSource();
         if (!IWeapon.checkShieldAngle(stack, player, source)) {
            return hurtdamage;
         } else {
            int blocking = NBTHelper.GetNBTint(stack, "blocking") - 1;
            if (attacker != null
               || source != DamageSource.CRAMMING
                  && source != DamageSource.DROWN
                  && source != DamageSource.FALL
                  && source != DamageSource.HOT_FLOOR
                  && source != DamageSource.IN_FIRE
                  && source != DamageSource.IN_WALL
                  && source != DamageSource.LAVA
                  && source != DamageSource.MAGIC
                  && source != DamageSource.ON_FIRE
                  && source != DamageSource.OUT_OF_WORLD
                  && source != DamageSource.STARVE
                  && source != DamageSource.WITHER) {
               if (player.hurtResistantTime > 0) {
                  return 0.0F;
               } else {
                  if (attacker != null) {
                     SuperKnockback.applyShieldBlock(
                        player,
                        attacker,
                        parameters.getEnchantedF("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack)),
                        parameters.getFloat("self_knockback")
                     );
                     float eatChance = parameters.getEnchantedF("eat_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, stack));
                     if (itemRand.nextFloat() < eatChance && player.isPotionActive(MobEffects.HUNGER)) {
                        float damage = parameters.getEnchantedF("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
                        DamageSource wDamageSource = new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.bite).setIsThornsDamage();
                        if (Weapons.dealDamage(wDamageSource, damage, player, attacker, true)) {
                           player.world
                              .playSound(
                                      null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 SoundEvents.ENTITY_GENERIC_EAT,
                                 SoundCategory.PLAYERS,
                                 0.7F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                           EntityLiveHeart.spawnHearts(
                              player.world,
                              attacker.posX,
                              attacker.posY,
                              attacker.posZ,
                              2,
                              parameters.getFloat("heart_health"),
                              true,
                              4.0F,
                              player
                           );
                        }
                     }
                  }

                  NBTHelper.AddNBTint(stack, -1, "blocking");
                  if (blocking <= 0) {
                     player.world
                        .playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_break,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     player.getCooldownTracker()
                        .setCooldown(
                           this,
                           (int)((player.getFoodStats().getFoodLevel() <= foodToStarve ? starvedCooldownMultiplier : 1.0F) * this.getCooldownTime(stack))
                        );
                  } else {
                     player.world
                        .playSound(
                                null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_hit_soft,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                  }

                  player.addExhaustion(parameters.getEnchantedF("exhaustion_on_block", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack)));
                  if (!player.capabilities.isCreativeMode) {
                     stack.damageItem(1, player);
                  }

                  player.hurtResistantTime = 10;
                  return hurtdamage - damageBlocks;
               }
            } else {
               return hurtdamage;
            }
         }
      } else {
         return hurtdamage;
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp(
         (float)NBTHelper.GetNBTint(itemstack, "blocking") / WeaponParameters.getWeaponParameters(itemstack.getItem()).getInt("max_hits"), 0.0F, 1.0F
      );
   }

   @SideOnly(Side.CLIENT)
   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "blocking") > 0;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean cancelOnNull() {
      return true;
   }
}
