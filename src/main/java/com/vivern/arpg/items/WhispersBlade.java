package com.vivern.arpg.items;

import com.vivern.arpg.entity.WhispersShoot;
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

public class WhispersBlade extends ItemWeapon {

    public static ShardType shardneed = ShardType.AIR;

    public WhispersBlade() {
        this.setRegistryName("whispers_blade");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("whispers_blade");
        this.setMaxDamage(5200);
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
            NBTHelper.GiveNBTint(itemstack, -1, "level_stop_at");
            int level_stop_at = NBTHelper.GetNBTint(itemstack, "level_stop_at");
            if (level_stop_at != -1 && entityIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityIn;
                if (player.experienceLevel > level_stop_at) {
                    WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                    Weapons.addOrRemoveExperience(player, -parameters.getInt("xp_decrease"));
                } else {
                    NBTHelper.SetNBTint(itemstack, -1, "level_stop_at");
                    world.playSound(null, player.posX, player.posY, player.posZ, Sounds.ae_unpower, SoundCategory.AMBIENT, 1.0F, 1.0F);
                }
            }

            boolean powered = level_stop_at != -1;
            this.setCanShoot(itemstack, entityIn);
            if (IWeapon.canShoot(itemstack)) {
                EntityPlayer player = (EntityPlayer) entityIn;
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                float power = Mana.getMagicPowerMax(player);
                int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
                int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
                NBTHelper.GiveNBTint(itemstack, 0, "fdelay");
                int firedelay = NBTHelper.GetNBTint(itemstack, "fdelay");
                WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                float manacost = parameters.getEnchantedF("mana_cost", sor);
                if (powered) {
                    manacost *= 2.0F;
                }

                EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
                if (hand != null) {
                    if (firedelay > 0) {
                        NBTHelper.AddNBTint(itemstack, -1, "fdelay");
                    }

                    if ((!click || hand != EnumHand.MAIN_HAND) && (!click2 || hand != EnumHand.OFF_HAND)) {
                        boolean powerOn = click2 && hand == EnumHand.MAIN_HAND || click && hand == EnumHand.OFF_HAND;
                        if (powerOn && level_stop_at == -1) {
                            int levelStopAtNew = CrystalStar.getLevelToStopEmpower(player, itemstack);
                            if (levelStopAtNew != -1) {
                                world.playSound(null, player.posX, player.posY, player.posZ, Sounds.ae_power, SoundCategory.AMBIENT, 1.0F, 1.0F);
                                NBTHelper.SetNBTint(itemstack, levelStopAtNew, "level_stop_at");
                            }
                        }
                    } else if (firedelay == 0 && Mana.getMana(player) > manacost && !player.getCooldownTracker().hasCooldown(this)) {
                        world.playSound(null, player.posX, player.posY, player.posZ, powered ? Sounds.whispers_blade_powered : Sounds.whispers_blade, SoundCategory.AMBIENT, 1.0F, 0.9F + itemRand.nextFloat() * 0.2F);
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        NBTHelper.SetNBTint(itemstack, 5, "fdelay");
                        Weapons.setPlayerAnimationOnServer(player, 24, hand);
                        if (!player.capabilities.isCreativeMode) {
                            Mana.changeMana(player, -manacost);
                            Mana.setManaSpeed(player, 0.001F);
                            itemstack.damageItem(1, player);
                        }
                    }
                } else {
                    NBTHelper.SetNBTint(itemstack, 0, "fdelay");
                }

                if (firedelay == 1) {
                    WhispersShoot projectile = new WhispersShoot(world, player, itemstack, power);
                    projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, powered ? parameters.getFloat("velocity_powered") : parameters.getFloat("velocity"), parameters.getEnchantedF("inaccuracy", acc));
                    projectile.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.2, player.posZ);
                    projectile.rotationRoll = itemRand.nextInt(23) - 11;
                    projectile.cutterSize = powered ? parameters.getEnchantedF("cutter_size_powered", range) : parameters.getEnchantedF("cutter_size", range);
                    projectile.powered = powered;
                    if (powered) {
                        projectile.lastBounces = parameters.getEnchantedI("bounces", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
                    }

                    world.spawnEntity(projectile);
                }
            } else {
                NBTHelper.SetNBTint(itemstack, 0, "fdelay");
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void boom(int param) {
        Boom.lastTick = 17;
        Boom.frequency = -0.185F;
        Boom.x = 1.0F;
        Boom.y = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
        Boom.z = 0.0F;
        Boom.power = -0.27F;
    }

    @Override
    public WeaponHandleType getWeaponHandleType() {
        return WeaponHandleType.SEMI_ONE_HANDED;
    }

    @Override
    public int getCooldownTime(ItemStack itemstack) {
        WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
        boolean powered = NBTHelper.GetNBTint(itemstack, "level_stop_at") != -1;
        int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
        return powered ? parameters.getEnchantedI("cooldown_powered", rapidity) : parameters.getEnchantedI("cooldown", rapidity);
    }

    @Override
    public boolean autoCooldown(ItemStack itemstack) {
        return false;
    }

}
