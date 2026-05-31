package com.vivern.arpg.items;

import com.vivern.arpg.entity.PistolFishStrike;
import com.vivern.arpg.main.*;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PistolFish extends ItemWeapon {
   public static int maxammo = 47;

   public PistolFish() {
      this.setRegistryName("pistol_fish");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("pistol_fish");
      this.setMaxDamage(1600);
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
   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(itemIn);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            EnumHand hand = click && player.getHeldItemMainhand() == itemstack
               ? EnumHand.MAIN_HAND
               : (click2 && player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (hand != null) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if ((!hascooldown || hand != EnumHand.MAIN_HAND) && (!hascooldown2 || hand != EnumHand.OFF_HAND)) {
                     world.playSound(
                             null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.pistol_fish,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 5, hand);
                     if (!player.capabilities.isCreativeMode) {
                        if (itemRand.nextFloat()
                           < parameters.getEnchantedF("ammo_consume_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack))) {
                           this.addAmmo(ammo, itemstack, -1);
                        }

                        itemstack.damageItem(1, player);
                     }

                     PistolFishStrike projectile = new PistolFishStrike(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        hand,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        parameters.getFloat("velocity"),
                        parameters.getEnchantedF("inaccuracy", acc),
                        -0.1F,
                        0.4F,
                        0.2F
                     );
                     projectile.livetime = parameters.getEnchantedI("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     world.spawnEntity(projectile);
                     if (itemRand.nextFloat()
                        < parameters.getEnchantedF("special_shoot_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack))) {
                        Entity htarget = projectile.getHypotheticalTarget();
                        if (htarget != null) {
                           double radius = player.getDistance(htarget) * 0.3;
                           List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(
                              world, GetMOP.entityCenterPos(htarget), radius, radius / 2.0, player
                           );
                           if (list.size() >= 1) {
                              for (int i = 0; i < 6; i++) {
                                 EntityLivingBase newtarget = list.get(itemRand.nextInt(list.size()));
                                 if (GetMOP.thereIsNoBlockCollidesBetween(
                                    world, player.getPositionEyes(1.0F), GetMOP.entityCenterPos(newtarget), null, false
                                 )) {
                                    PistolFishStrike projectile2 = new PistolFishStrike(world, player, itemstack);
                                    Weapons.shoot(projectile2, hand, player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0F, 0.0F, -0.1F, 0.4F, 0.2F);
                                    Vec3d pos1 = projectile2.getPositionVector();
                                    Vec3d pos2 = GetMOP.entityCenterPos(newtarget);
                                    Vec3d pos3 = pos2.subtract(pos1);
                                    double dist = Math.sqrt(pos3.x * pos3.x + pos3.z * pos3.z);
                                    SuperKnockback.setMove(
                                       projectile2, -parameters.getFloat("velocity"), pos2.x, pos2.y + dist * 0.1, pos2.z
                                    );
                                    projectile2.livetime = parameters.getEnchantedI(
                                       "livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack)
                                    );
                                    world.spawnEntity(projectile2);
                                    world.playSound(
                                            null,
                                       player.posX,
                                       player.posY,
                                       player.posZ,
                                       Sounds.pistol_fish,
                                       SoundCategory.AMBIENT,
                                       0.9F,
                                       1.4F + itemRand.nextFloat() / 5.0F
                                    );
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               } else if (this.initiateReload(itemstack, player, ItemsRegister.FISH_FEED, maxammo)) {
                  world.playSound(
                          null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.pistol_fish_rel,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, hand);
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   private void bom(boolean mainhand) {
      Boom.lastTick = 11;
      Boom.frequency = -0.285F;
      Boom.x = 1.0F;
      Boom.y = (float)itemRand.nextGaussian() / 10.0F;
      Boom.z = (float)itemRand.nextGaussian() / 10.0F;
      Boom.power = 0.22F;
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
