package com.vivern.arpg.items;

import com.vivern.arpg.entity.GraveLurkerProjectile;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GraveLurker extends ItemWeapon {

    public GraveLurker() {
        this.setRegistryName("grave_lurker");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("grave_lurker");
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
                float mana = Mana.getMana(player);
                float spee = Mana.getManaSpeed(player);
                float power = Mana.getMagicPowerMax(player);
                int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
                if (player.getHeldItemMainhand() == itemstack && mana > 3.0F - (float) sor / 2 && click && !player.getCooldownTracker().hasCooldown(this)) {
                    boolean iscritt;
                    if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                        iscritt = NBTHelper.GetNBTboolean(itemstack, "crit");
                    } else {
                        iscritt = itemRand.nextFloat() < ColorConverters.InBorder(0.2F, 0.4F, 0.2F * power);
                    }

                    player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                    player.addStat(StatList.getObjectUseStats(this));
                    IWeapon.fireBomEffect(this, player, world, 0);
                    Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                    if (!iscritt) {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.gravelurker, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                        GraveLurkerProjectile projectile = new GraveLurkerProjectile(world, player, itemstack, power, false);
                        projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.2F, 2.4F / (acc + 1));
                        world.spawnEntity(projectile);
                    } else {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.gravelurker_crit, SoundCategory.AMBIENT, 0.9F, 0.9F + itemRand.nextFloat() / 5.0F);
                        NBTHelper.SetNBTboolean(itemstack, false, "crit");
                        GraveLurkerProjectile projectile = new GraveLurkerProjectile(world, player, itemstack, power, true);
                        projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.2F, 2.4F / (acc + 1));
                        world.spawnEntity(projectile);
                    }

                    if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -3.0F + (float) sor / 2);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(1, player);
                    }

                    if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 && itemRand.nextFloat() < ColorConverters.InBorder(0.2F, 0.4F, 0.2F * power)) {
                        NBTHelper.giveNBTboolean(itemstack, false, "crit");
                        NBTHelper.SetNBTboolean(itemstack, true, "crit");
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void boom(int param) {
        Boom.lastTick = 14;
        Boom.frequency = -0.225F;
        Boom.x = 1.0F;
        Boom.y = (itemRand.nextFloat() - 0.5F) / 4.0F;
        Boom.z = (itemRand.nextFloat() - 0.5F) / 4.0F;
        Boom.power = 0.3F;
    }

    @Override
    public boolean autoCooldown(ItemStack itemstack) {
        return false;
    }

    @Override
    public int getCooldownTime(ItemStack itemstack) {
        int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
        return 9 - rapidity * 2;
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
