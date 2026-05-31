package com.vivern.arpg.items;

import com.vivern.arpg.entity.WandColdShoot;
import com.vivern.arpg.entity.WandColdWave;
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

public class WandOfCold extends ItemWeapon {

    public WandOfCold() {
        this.setRegistryName("wand_of_cold");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("wand_of_cold");
        this.setMaxDamage(4000);
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
        if (param == 0) {
            Boom.FOVlastTick = 10;
            Boom.FOVfrequency = -0.315F;
            Boom.FOVpower = 0.004F;
        }

        if (param == 1) {
            Boom.lastTick = 19;
            Boom.frequency = -0.165F;
            Boom.x = 0.0F;
            Boom.y = 0.0F;
            Boom.z = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
            Boom.power = 0.23F;
        }
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            this.setCanShoot(itemstack, entityIn);
            if (IWeapon.canShoot(itemstack)) {
                EntityPlayer player = (EntityPlayer) entityIn;
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
                float power = Mana.getMagicPowerMax(player);
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                float manacost = parameters.getEnchantedF("manacost", sor);
                NBTHelper.GiveNBTint(itemstack, 0, "fdelay");
                int firedelay = NBTHelper.GetNBTint(itemstack, "fdelay");
                if (player.getHeldItemMainhand() == itemstack) {
                    if (firedelay > 0) {
                        NBTHelper.AddNBTint(itemstack, -1, "fdelay");
                    }

                    if (click && Mana.getMana(player) > manacost && !player.getCooldownTracker().hasCooldown(this)) {
                        NBTHelper.GiveNBTint(itemstack, 0, "charges");
                        Weapons.setPlayerAnimationOnServer(player, 26, EnumHand.MAIN_HAND);
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.wand_of_cold, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        int nn = itemRand.nextInt(parameters.getInt("max_shots")) + 1;

                        for (int i = 0; i < nn; i++) {
                            WandColdShoot projectile = new WandColdShoot(world, player, itemstack, power);
                            projectile.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.35, player.posZ);
                            projectile.shoot(player, player.rotationPitch, player.rotationYaw + itemRand.nextInt(9) - 4.0F, 0.0F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc) + i);
                            world.spawnEntity(projectile);
                        }

                        if (!player.capabilities.isCreativeMode) {
                            Mana.changeMana(player, -manacost);
                            Mana.setManaSpeed(player, 0.001F);
                            itemstack.damageItem(1, player);
                        }
                    }

                    if (click2 && firedelay == 0 && NBTHelper.GetNBTint(itemstack, "charges") >= maxcharge(itemstack) && !player.getCooldownTracker().hasCooldown(this)) {
                        NBTHelper.SetNBTint(itemstack, 5, "fdelay");
                        player.getCooldownTracker().setCooldown(this, parameters.getInt("wave_cooldown"));
                        Weapons.setPlayerAnimationOnServer(player, 26, EnumHand.MAIN_HAND);
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 1);
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.wand_of_cold_wave, SoundCategory.AMBIENT, 1.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                    }

                    if (firedelay == 1) {
                        NBTHelper.SetNBTint(itemstack, 0, "charges");
                        WandColdWave projectile = new WandColdWave(world, player, itemstack, power);
                        projectile.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.35, player.posZ);
                        projectile.damage = parameters.getEnchantedF("wave_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                        projectile.knockback = parameters.getEnchantedF("wave_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                        Weapons.shoot(projectile, EnumHand.MAIN_HAND, player, player.rotationPitch - 1.0F, player.rotationYaw, 0.0F, parameters.getFloat("wave_velocity"), parameters.getEnchantedF("wave_inaccuracy", acc), -0.45F, 0.0F, 0.5F, 0.0F);
                        projectile.setCutterSize(parameters.getEnchantedF("wave_width", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack)));
                        world.spawnEntity(projectile);
                    }
                } else {
                    NBTHelper.SetNBTint(itemstack, 0, "fdelay");
                }
            } else {
                NBTHelper.SetNBTint(itemstack, 0, "fdelay");
            }
        }
    }

    public static int maxcharge(ItemStack itemstack) {
        return WeaponParameters.getWeaponParameters(itemstack.getItem()).getEnchantedI("max_charges", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getAdditionalDurabilityBar(ItemStack stack) {
        return MathHelper.clamp((float) NBTHelper.GetNBTint(stack, "charges") / maxcharge(stack), 0.0F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
        return NBTHelper.GetNBTint(itemstack, "charges") > 0;
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
    public int getItemEnchantability() {
        return 2;
    }

}
