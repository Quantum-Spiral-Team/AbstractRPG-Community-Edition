package com.vivern.arpg.items;

import com.vivern.arpg.entity.EntitySlimeBullet;
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

public class SlimeShotgun extends ItemWeapon {

    public static int maxammo = 10;

    public SlimeShotgun() {
        this.setRegistryName("slime_shotgun");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("slime_shotgun");
        this.setMaxDamage(560);
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
        Boom.lastTick = 2 + param;
        Boom.frequency = -Boom.getFrequencyForTicks(Boom.lastTick);
        Boom.x = 1.0F;
        Boom.y = 0.0F;
        Boom.z = 0.0F;
        Boom.power = 0.06F * param + (param > 6 ? 0.3F * (param - 6) : 0.0F);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            this.setCanShoot(itemstack, entityIn);
            if (IWeapon.canShoot(itemstack)) {
                EntityPlayer player = (EntityPlayer) entityIn;
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                this.decreaseReload(itemstack, player);
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                if (click && player.getHeldItemMainhand() == itemstack) {
                    if (ammo > 0 && this.isReloaded(itemstack)) {
                        if (!player.getCooldownTracker().hasCooldown(this)) {
                            world.playSound(null, player.posX, player.posY, player.posZ, Sounds.slimeshotgun, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                            player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                            player.addStat(StatList.getObjectUseStats(this));
                            Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                            int shots = parameters.getInt("shots");
                            shots += (int) (NBTHelper.GetNBTint(itemstack, "charge") * parameters.getFloat("charge_to_shots"));
                            IWeapon.fireBomEffect(this, player, world, shots);

                            for (int ss = 0; ss < shots; ss++) {
                                EntitySlimeBullet bullet = new EntitySlimeBullet(world, player, itemstack);
                                Weapons.shoot(bullet, EnumHand.MAIN_HAND, player, player.rotationPitch, player.rotationYaw, 0.2F, parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc), -0.13F, 0.5F, 0.5F);
                                world.spawnEntity(bullet);
                            }

                            if (!player.capabilities.isCreativeMode) {
                                if (itemRand.nextFloat() > reuse * parameters.getFloat("reus_ammo_save_chance")) {
                                    this.addAmmo(ammo, itemstack, -1);
                                }

                                itemstack.damageItem(1, player);
                            }

                            NBTHelper.SetNBTint(itemstack, 0, "charge");
                        }
                    } else if (this.initiateReload(itemstack, player, ItemsRegister.SLIME_CELL, maxammo, ItemsRegister.EMPTY_CELL)) {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.slimeshotgunreload, SoundCategory.AMBIENT, 0.7F, 0.95F + itemRand.nextFloat() / 20.0F);
                        Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
                    }
                } else if (!click && player.getHeldItemMainhand() == itemstack && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                    NBTHelper.GiveNBTint(itemstack, 0, "charge");
                    if (NBTHelper.GetNBTint(itemstack, "charge") < parameters.getFloat("max_charge")) {
                        NBTHelper.AddNBTint(itemstack, 1, "charge");
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
