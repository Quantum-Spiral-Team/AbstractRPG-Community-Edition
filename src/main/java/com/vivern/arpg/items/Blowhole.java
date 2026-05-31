package com.vivern.arpg.items;

import com.vivern.arpg.entity.BlowholeShoot;
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

public class Blowhole extends ItemWeapon {

    public static int maxammo = 160;

    public Blowhole() {
        this.setRegistryName("blowhole");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("blowhole");
        this.setMaxDamage(3400);
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
        if (param < 8) {
            Boom.lastTick = 8;
            Boom.frequency = -0.395F;
        } else if (param < 16) {
            Boom.lastTick = 12;
            Boom.frequency = -0.262F;
        } else {
            Boom.lastTick = 16;
            Boom.frequency = -0.196F;
        }

        Boom.x = 1.0F;
        Boom.y = 0.0F;
        Boom.z = 0.0F;
        Boom.power = 0.3F * (param / 10.0F);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            this.setCanShoot(itemstack, entityIn);
            if (IWeapon.canShoot(itemstack)) {
                EntityPlayer player = (EntityPlayer) entityIn;
                this.decreaseReload(itemstack, player);
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                int special = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
                int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
                NBTHelper.GiveNBTint(itemstack, 0, "charge");
                int charge = NBTHelper.GetNBTint(itemstack, "charge");
                EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
                boolean click3 = hand == EnumHand.MAIN_HAND ? click : click2;
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                int maxcharge = parameters.getInt("max_charge");
                if (hand != null) {
                    boolean anotherHandEmpty = player.getHeldItem(hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND).isEmpty();
                    if (click3) {
                        if (ammo > 0 && this.isReloaded(itemstack)) {
                            if (charge == 4) {
                                world.playSound(null, player.posX, player.posY, player.posZ, Sounds.blowhole_charge, SoundCategory.NEUTRAL, 0.7F, 0.95F + itemRand.nextFloat() / 10.0F);
                            }

                            NBTHelper.AddNBTint(itemstack, parameters.getInt("charge_add"), "charge");
                            if (charge > 7 && charge % 9 == 0) {
                                Weapons.setPlayerAnimationOnServer(player, anotherHandEmpty ? 11 : 10, hand);
                                world.playSound(null, player.posX, player.posY, player.posZ, Sounds.blowhole_loop, SoundCategory.NEUTRAL, 0.6F, 0.9F + itemRand.nextFloat() / 5.0F);
                            }
                        } else if (this.initiateReload(itemstack, player, ItemsRegister.BLOWHOLE_PELLETS, maxammo)) {
                            world.playSound(null, player.posX, player.posY, player.posZ, Sounds.blowhole_rel, SoundCategory.NEUTRAL, 0.7F, 0.95F + itemRand.nextFloat() / 10.0F);
                            Weapons.setPlayerAnimationOnServer(player, 4, hand);
                            NBTHelper.SetNBTint(itemstack, 0, "charge");
                        }
                    } else if (charge > 0) {
                        charge = Math.min(charge, maxcharge);
                        if (!player.getCooldownTracker().hasCooldown(this)) {
                            world.playSound(null, player.posX, player.posY, player.posZ, Sounds.blowhole, SoundCategory.AMBIENT, 0.9F, 1.25F + itemRand.nextFloat() / 10.0F - charge / 50.0F);
                            player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                            player.addStat(StatList.getObjectUseStats(this));
                            IWeapon.fireBomEffect(this, player, world, charge);
                            Weapons.setPlayerAnimationOnServer(player, anotherHandEmpty ? 3 : 5, hand);
                            BlowholeShoot shoot = new BlowholeShoot(world, player, itemstack, charge * parameters.getEnchantedF("bubble_size_multiplier", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack)));
                            Weapons.shoot(shoot, hand, player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.getEnchantedF("velocity", special) + charge * parameters.getEnchantedF("velocity_charge_multiplier", special), parameters.getEnchantedF("inaccuracy", acc), -0.2F, 0.5F, 0.15F);
                            world.spawnEntity(shoot);
                            NBTHelper.SetNBTint(itemstack, 0, "charge");
                            if (!player.capabilities.isCreativeMode) {
                                int ammoadd = 0;
                                if (charge < 8) {
                                    ammoadd = parameters.getEnchantedI("ammo_add", reuse);
                                } else {
                                    ammoadd = (int) (-charge * parameters.getEnchantedF("ammo_add_charged_multiplier", reuse));
                                }

                                this.addAmmo(ammo, itemstack, ammoadd);
                                itemstack.damageItem(1 + charge / 6, player);
                            }
                        }
                    }
                } else {
                    NBTHelper.SetNBTint(itemstack, MathHelper.clamp(charge - 1, 0, maxcharge), "charge");
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getAdditionalDurabilityBar(ItemStack stack) {
        return MathHelper.clamp((float) NBTHelper.GetNBTint(stack, "ammo") / maxammo, 0.0F, 1.0F);
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
        return WeaponHandleType.SEMI_ONE_HANDED;
    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

}
