package com.vivern.arpg.items;

import com.vivern.arpg.entity.EntityFrostBolt;
import com.vivern.arpg.main.*;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IceSword extends ItemWeapon {

    static ResourceLocation sweeptex = new ResourceLocation("arpg:textures/sweep2.png");

    public IceSword() {
        this.setRegistryName("ice_sword");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("ice_sword");
        this.setMaxDamage(400);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            this.setCanShoot(itemstack, entityIn);
            if (IWeapon.canShoot(itemstack)) {
                EntityPlayer player = (EntityPlayer) entityIn;
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
                boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
                EnumHand hand = null;
                if (click && player.getHeldItemMainhand() == itemstack && !hascooldown) {
                    hand = EnumHand.MAIN_HAND;
                } else if (click2 && player.getHeldItemOffhand() == itemstack && !hascooldown2) {
                    hand = EnumHand.OFF_HAND;
                }

                if (hand != null) {
                    WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                    if (IWeapon.doMeleeSwordAttack(this, itemstack, player, hand, itemRand.nextFloat() < parameters.getFloat("freeze_chance")).success) {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.melee_sword, SoundCategory.PLAYERS, 0.7F, 0.9F + itemRand.nextFloat() / 5.0F);
                    } else {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.melee_miss_sword, SoundCategory.PLAYERS, 0.6F, 0.9F + itemRand.nextFloat() / 5.0F);
                    }

                    Weapons.setPlayerAnimationOnServer(player, 1, hand);
                    player.addExhaustion(0.1F);
                    double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                    player.getCooldownTracker().setCooldown(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                    if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 && itemRand.nextFloat() < parameters.getFloat("special_shot_chance")) {
                        EntityLivingBase entityLivingBase = GetMOP.findNearestEnemy(world, player, player.posX, player.posY, player.posZ, parameters.getEnchantedF("special_shot_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack)), true);
                        if (entityLivingBase != null) {
                            EntityFrostBolt projectile = new EntityFrostBolt(world, player, itemstack, Mana.getMagicPowerMax(player));
                            projectile.damage = parameters.getEnchantedF("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                            projectile.knockback = parameters.getEnchantedF("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                            SuperKnockback.setMove(projectile, -1.0F, entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.height / 2.0F, entityLivingBase.posZ);
                            world.spawnEntity(projectile);
                            world.playSound(null, player.posX, player.posY, player.posZ, Sounds.magic_c, SoundCategory.AMBIENT, 0.4F, 0.9F * itemRand.nextFloat() * 0.3F);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
        float artropods = 1.0F;
        float holy = 1.0F;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entitylb = (EntityLivingBase) entity;
            artropods = entitylb.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F : 1.0F;
            holy = entitylb.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F : 1.0F;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack);
            int potionValue1 = parameters.getEnchantedI("potion_time_add", witchery);
            int potionValue2 = parameters.getEnchantedI("potion_power_add", witchery);
            int potionValue3 = parameters.getEnchantedI("potion_time_max", witchery);
            int potionValue4 = parameters.getEnchantedI("potion_power_max", witchery);
            if (isCritical) {
                potionValue1 *= 2;
            }

            PotionEffect lastdebaff = Weapons.mixPotion(entitylb, PotionEffects.FREEZING, (float) potionValue1, isCritical ? potionValue2 : 0.0F, Weapons.EnumPotionMix.WITH_MAXIMUM, Weapons.EnumPotionMix.WITH_MAXIMUM, Weapons.EnumMathOperation.PLUS, Weapons.EnumMathOperation.PLUS, potionValue3, potionValue4);
            Freezing.tryPlaySound(entitylb, lastdebaff);
        }

        boolean ret = entity.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy);
        entity.hurtResistantTime = 0;
        return ret;
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
    public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

}
