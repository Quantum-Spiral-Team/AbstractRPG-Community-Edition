package com.vivern.arpg.items;

import com.vivern.arpg.entity.VacuumGunShoot;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class VacuumGun extends ItemWeapon {

    public static int maxammo = 8;

    public VacuumGun() {
        this.setRegistryName("vacuum_gun");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("vacuum_gun");
        this.setMaxDamage(530);
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
                EntityPlayer player = (EntityPlayer) entityIn;
                this.decreaseReload(itemstack, player);
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
                boolean clickcec = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                if (click && player.getHeldItemMainhand() == itemstack) {
                    if (ammo > 0 && this.isReloaded(itemstack)) {
                        if (!player.getCooldownTracker().hasCooldown(this)) {
                            world.playSound(null, player.posX, player.posY, player.posZ, Sounds.vacuum_gun, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                            player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                            player.addStat(StatList.getObjectUseStats(this));
                            IWeapon.fireBomEffect(this, player, world, 0);
                            Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                            VacuumGunShoot projectile = new VacuumGunShoot(world, player, itemstack);
                            Weapons.shoot(projectile, EnumHand.MAIN_HAND, player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc), -0.15F, 0.5F, 0.3F);
                            projectile.livetime = parameters.getInt("live_time");
                            projectile.special = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0;
                            world.spawnEntity(projectile);
                            if (!player.capabilities.isCreativeMode) {
                                this.addAmmo(ammo, itemstack, -1);
                                itemstack.damageItem(1, player);
                            }
                        }
                    } else if (this.initiateReload(itemstack, player, ItemsRegister.VACUUM_GUN_PELLETS, maxammo)) {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.head_shooter_rel, SoundCategory.AMBIENT, 0.7F, 0.95F + itemRand.nextFloat() / 10.0F);
                        Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                    }
                }

                if (clickcec && player.getHeldItemMainhand() == itemstack) {
                    double damageRadius = 50.0;
                    AxisAlignedBB axisalignedbb = player.getEntityBoundingBox().expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0).offset(-damageRadius, -damageRadius, -damageRadius);
                    List<VacuumGunShoot> list = player.world.getEntitiesWithinAABB(VacuumGunShoot.class, axisalignedbb);
                    if (!list.isEmpty()) {
                        for (VacuumGunShoot shoot : list) {
                            if (shoot.getThrower() == player) {
                                shoot.vacuum = true;
                            }
                        }
                    }
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

    @Override
    public boolean autoCooldown(ItemStack itemstack) {
        return false;
    }

}
