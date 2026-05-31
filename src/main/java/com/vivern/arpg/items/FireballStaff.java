package com.vivern.arpg.items;

import com.vivern.arpg.entity.StaffFireballEntity;
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
import net.minecraft.world.World;

public class FireballStaff extends ItemWeapon {

    public FireballStaff() {
        this.setRegistryName("fireball_staff");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("fireball_staff");
        this.setMaxDamage(80);
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
                boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
                float power = Mana.getMagicPowerMax(player);
                float mana = Mana.getMana(player);
                int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                float manacost = parameters.getEnchantedF("mana_cost", sor);
                if (click && player.getHeldItemMainhand() == itemstack && mana > manacost && !hascooldown) {
                    world.playSound(null, player.posX, player.posY, player.posZ, Sounds.fire, SoundCategory.AMBIENT, 1.3F, 0.6F + itemRand.nextFloat() / 6.0F);
                    player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                    player.addStat(StatList.getObjectUseStats(this));
                    Weapons.setPlayerAnimationOnServer(player, 14, EnumHand.MAIN_HAND);
                    if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -manacost);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(1, player);
                    }

                    StaffFireballEntity projectile = new StaffFireballEntity(world, player, itemstack, power);
                    Weapons.shoot(projectile, EnumHand.MAIN_HAND, player, player.rotationPitch, player.rotationYaw, 0.3F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc), 0.0F, 0.5F, 0.2F);
                    world.spawnEntity(projectile);
                }

                if (click2 && player.getHeldItemOffhand() == itemstack && mana > manacost && !hascooldown) {
                    world.playSound(null, player.posX, player.posY, player.posZ, Sounds.fire, SoundCategory.AMBIENT, 1.3F, 0.6F + itemRand.nextFloat() / 6.0F);
                    player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                    player.addStat(StatList.getObjectUseStats(this));
                    Weapons.setPlayerAnimationOnServer(player, 14, EnumHand.OFF_HAND);
                    if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -manacost);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(1, player);
                    }

                    StaffFireballEntity projectile = new StaffFireballEntity(world, player, itemstack, power);
                    Weapons.shoot(projectile, EnumHand.OFF_HAND, player, player.rotationPitch, player.rotationYaw, 0.3F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc), 0.0F, 0.5F, 0.2F);
                    world.spawnEntity(projectile);
                }
            }
        }
    }

    @Override
    public WeaponHandleType getWeaponHandleType() {
        return WeaponHandleType.ONE_HANDED;
    }

    @Override
    public int getCooldownTime(ItemStack itemstack) {
        int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
        return 220 - rapidity * 20;
    }

    @Override
    public boolean autoCooldown(ItemStack itemstack) {
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

}
