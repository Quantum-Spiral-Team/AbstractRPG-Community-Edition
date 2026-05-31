package com.vivern.arpg.items;

import com.vivern.arpg.entity.PlasmaPistolShoot;
import com.vivern.arpg.main.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaPistol extends ItemWeapon implements IEnergyItem {

    public PlasmaPistol() {
        this.setRegistryName("plasma_pistol");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("plasma_pistol");
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
                EntityPlayer player = (EntityPlayer) entityIn;
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                this.decreaseReload(itemstack, player);
                boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
                boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                int RFtoShoot = parameters.getEnchantedI("rf_to_shoot", reuse);
                if (click && player.getHeldItemMainhand() == itemstack && this.getEnergyStored(itemstack) >= RFtoShoot && !hascooldown) {
                    world.playSound(null, player.posX, player.posY, player.posZ, Sounds.plasma_pistol, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                    player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                    player.addStat(StatList.getObjectUseStats(this));
                    IWeapon.fireBomEffect(this, player, world, 0);
                    Weapons.setPlayerAnimationOnServer(player, 5, EnumHand.MAIN_HAND);
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                    }

                    PlasmaPistolShoot projectile = new PlasmaPistolShoot(world, player, itemstack);
                    Weapons.shoot(projectile, EnumHand.MAIN_HAND, player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc), -0.05F, 0.5F, 0.2F);
                    projectile.livetime = parameters.getEnchantedI("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                    world.spawnEntity(projectile);
                }

                if (click2 && player.getHeldItemOffhand() == itemstack && this.getEnergyStored(itemstack) >= RFtoShoot && !hascooldown2) {
                    world.playSound(null, player.posX, player.posY, player.posZ, Sounds.plasma_pistol, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                    player.getCooldownTracker().setCooldown(ItemsRegister.EXP, this.getCooldownTime(itemstack));
                    player.addStat(StatList.getObjectUseStats(this));
                    IWeapon.fireBomEffect(this, player, world, 0);
                    Weapons.setPlayerAnimationOnServer(player, 5, EnumHand.OFF_HAND);
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                    }

                    PlasmaPistolShoot projectile = new PlasmaPistolShoot(world, player, itemstack);
                    Weapons.shoot(projectile, EnumHand.OFF_HAND, player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc), -0.05F, 0.5F, 0.2F);
                    projectile.livetime = parameters.getEnchantedI("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                    world.spawnEntity(projectile);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void boom(int param) {
        Boom.lastTick = 11;
        Boom.frequency = -0.285F;
        Boom.x = 1.0F;
        Boom.y = (float) itemRand.nextGaussian() / 10.0F;
        Boom.z = (float) itemRand.nextGaussian() / 10.0F;
        Boom.power = 0.22F;
    }

    @Override
    public WeaponHandleType getWeaponHandleType() {
        return WeaponHandleType.ONE_HANDED;
    }

    @Override
    public boolean autoCooldown(ItemStack itemstack) {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
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
