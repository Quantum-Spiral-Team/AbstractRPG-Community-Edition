package com.vivern.arpg.items;

import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.EntityStreamLaserP;
import com.vivern.arpg.main.*;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class Charger extends ItemWeapon {

    public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/blueexplode.png");
    public static ResourceLocation start = new ResourceLocation("arpg:textures/plasma_beam.png");

    public Charger() {
        this.setRegistryName("charger");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("charger");
        this.setMaxDamage(12000);
        this.setMaxStackSize(1);
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 25.0F;
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

    public EntityLivingBase nextTarget(Vec3d vec, World worldIn, EntityPlayer thrower, List<EntityLivingBase> targs, double damageRadius) {
        double max = Double.MAX_VALUE;
        EntityLivingBase targ = null;
        AxisAlignedBB aabb = new AxisAlignedBB(vec.x - damageRadius, vec.y - damageRadius, vec.z - damageRadius, vec.x + damageRadius, vec.y + damageRadius, vec.z + damageRadius);
        List<EntityLivingBase> list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
        if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
                if (Team.checkIsOpponent(thrower, entitylivingbase) && entitylivingbase != thrower && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase) && !targs.contains(entitylivingbase)) {
                    RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec, entitylivingbase.getPositionVector().add(0.0, entitylivingbase.height / 2.0F, 0.0), false, true, false);
                    if (raytraceresult == null) {
                        double dist = entitylivingbase.getPositionVector().distanceTo(vec);
                        if (dist < max) {
                            max = dist;
                            targ = entitylivingbase;
                        }
                    }
                }
            }
        }

        return targ;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        this.setCanShoot(itemstack, entityIn);
        if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer) entityIn;
            float mana = Mana.getMana(player);
            float spee = Mana.getManaSpeed(player);
            float power = Mana.getMagicPowerMax(player);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            int spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
            float rapidMult = 1.0F + rapidity * parameters.getFloat("rapid_multiplier");
            float manacost = parameters.getEnchantedF("manacost", sor) * rapidMult;
            boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
            boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
            EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            Item cooldownItem = hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP;
            boolean b1 = true;
            if ((click && hand == EnumHand.MAIN_HAND || click2 && hand == EnumHand.OFF_HAND) && mana > manacost && !player.getCooldownTracker().hasCooldown(cooldownItem)) {
                Vec3d vec = GetMOP.posRayTrace(parameters.getEnchantedF("distance", range), 1.0F, player, 0.08, 0.08);
                world.playSound(null, player.posX, player.posY, player.posZ, Sounds.charger, SoundCategory.AMBIENT, 0.8F, 1.0F);
                b1 = false;
                double damageRadius = 0.3;
                AxisAlignedBB aabb = new AxisAlignedBB(vec.x - damageRadius, vec.y - damageRadius, vec.z - damageRadius, vec.x + damageRadius, vec.y + damageRadius, vec.z + damageRadius);
                List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                if (!world.isRemote) {
                    int animSend = NBTHelper.GetNBTint(itemstack, "animSend");
                    if (animSend <= 0) {
                        NBTHelper.GiveNBTint(itemstack, 0, "animSend");
                        NBTHelper.SetNBTint(itemstack, 15, "animSend");
                        Weapons.setPlayerAnimationOnServer(player, 10, hand);
                    } else {
                        NBTHelper.AddNBTint(itemstack, -1, "animSend");
                    }

                    if (!list.isEmpty()) {
                        double nextTargetRadius = parameters.getEnchantedF("damage_radius", range);

                        for (EntityLivingBase entitylivingbase : list) {
                            if (Team.checkIsOpponent(player, entitylivingbase)) {
                                List<EntityLivingBase> targs = new ArrayList<>();
                                targs.add(entitylivingbase);
                                Vec3d vect = vec;

                                for (int ss = 0; ss < parameters.getEnchantedF("targets", spec); ss++) {
                                    EntityLivingBase livb = this.nextTarget(vect, world, player, targs, nextTargetRadius);

                                    targs.add(livb);
                                    Vec3d newvec = livb.getPositionVector().add(0.0, livb.height / 2.0F + itemRand.nextGaussian() / 20.0, 0.0);
                                    IWeapon.fireEffect(this, player, world, 64.0, vect.x, vect.y, vect.z, 0.0, 0.0, 0.0, newvec.x, newvec.y, newvec.z);
                                    vect = newvec;
                                }

                                for (int i = 0; i < targs.size(); i++) {
                                    EntityLivingBase entity = targs.get(i);
                                    if (Team.checkIsOpponent(player, entity)) {
                                        Weapons.dealDamage(new WeaponDamage(itemstack, player, null, false, false, i == 0 ? vec : targs.get(i - 1), WeaponDamage.plasma), parameters.getEnchantedF("damage", might) * power * rapidMult, player, entity, true, parameters.getEnchantedF("knockback", impulse) * rapidMult, vec.x, vec.y, vec.z);
                                        entity.hurtResistantTime = 0;
                                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC, 0.4F);
                                    }
                                }
                                break;
                            }
                        }
                    }

                    if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -manacost);
                        Mana.setManaSpeed(player, 0.001F);
                        itemstack.damageItem(GetMOP.floatToIntWithChance(rapidMult, itemRand), player);
                    }
                }

                float horizoffset = 0.0F;
                if (player.getHeldItemMainhand() == itemstack) {
                    horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
                }

                if (player.getHeldItemOffhand() == itemstack) {
                    horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? -0.2F : 0.2F;
                }

                if (world.isRemote) {
                    GUNParticle bigsmoke = new GUNParticle(largesmoke, 0.3F + (float) itemRand.nextGaussian() / 20.0F, 0.0F, 10, 240, world, vec.x, vec.y, vec.z, (float) itemRand.nextGaussian() / 29.0F, (float) itemRand.nextGaussian() / 29.0F, (float) itemRand.nextGaussian() / 29.0F, 0.95F + (float) itemRand.nextGaussian() / 10.0F, 1.0F, 1.0F, true, itemRand.nextInt(360));
                    bigsmoke.alphaTickAdding = -0.1F;
                    bigsmoke.alphaGlowing = true;
                    world.spawnEntity(bigsmoke);
                    EntityStreamLaserP laser = new EntityStreamLaserP(world, player, start, 0.05F, 240, 1.0F, 1.0F, 1.0F, 0.5F, player.getDistance(vec.x, vec.y, vec.z), 1, 0.3F, 8.0F);
                    laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
                    laser.horizOffset = horizoffset;
                    world.spawnEntity(laser);
                }

                IWeapon.fireEffectExcl(this, player, player, world, 64.0, vec.x, vec.y, vec.z, player.rotationPitch, player.rotationYaw, horizoffset, player.posX, player.posY, player.posZ);
            }

            if (b1 && !world.isRemote) {
                NBTHelper.SetNBTint(itemstack, 0, "animSend");
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
        if (c == 0.0) {
            Vec3d vect = new Vec3d(x, y, z);
            Vec3d newvec = new Vec3d(d1, d2, d3);
            BetweenParticle laser = new BetweenParticle(world, start, 0.05F, 240, 1.0F, 1.0F, 1.0F, 0.5F, vect.distanceTo(newvec), 1, 0.3F, 8.0F, vect, newvec);
            laser.setPosition(vect.x, vect.y, vect.z);
            laser.alphaGlowing = true;
            world.spawnEntity(laser);
        } else {
            GUNParticle bigsmoke = new GUNParticle(largesmoke, 0.3F + (float) itemRand.nextGaussian() / 20.0F, 0.0F, 10, 240, world, x, y, z, (float) itemRand.nextGaussian() / 29.0F, (float) itemRand.nextGaussian() / 29.0F, (float) itemRand.nextGaussian() / 29.0F, 0.95F + (float) itemRand.nextGaussian() / 10.0F, 1.0F, 1.0F, true, itemRand.nextInt(360));
            bigsmoke.alphaTickAdding = -0.1F;
            bigsmoke.alphaGlowing = true;
            world.spawnEntity(bigsmoke);
            double dd0 = d1 - x;
            double dd1 = d2 - y;
            double dd2 = d3 - z;
            double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
            EntityStreamLaserP laser = new EntityStreamLaserP(world, start, 0.05F, 240, 1.0F, 1.0F, 1.0F, 0.5F, dist, 1, 0.3F, 8.0F, (float) a, (float) b, player.ticksExisted);
            laser.setPosition(d1, d2 + 1.55, d3);
            laser.horizOffset = (float) c;
            world.spawnEntity(laser);
        }
    }

    @Override
    public boolean autoCooldown(ItemStack itemstack) {
        return true;
    }

    @Override
    public WeaponHandleType getWeaponHandleType() {
        return WeaponHandleType.SEMI_ONE_HANDED;
    }

}
