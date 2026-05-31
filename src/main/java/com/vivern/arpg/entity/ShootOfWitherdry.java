package com.vivern.arpg.entity;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.items.StaffOfWitherdry;
import com.vivern.arpg.main.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ShootOfWitherdry extends EntityThrowable {

    public final ItemStack weaponstack;
    public float magicPower = 1.0F;
    public int livetime = 32;

    public ShootOfWitherdry(World world) {
        super(world);
        this.weaponstack = new ItemStack(ItemsRegister.STAFF_OF_WITHERDRY);
        this.setSize(0.1F, 0.1F);
    }

    public ShootOfWitherdry(World world, EntityLivingBase thrower) {
        super(world, thrower);
        this.weaponstack = new ItemStack(ItemsRegister.STAFF_OF_WITHERDRY);
        this.setSize(0.1F, 0.1F);
    }

    public ShootOfWitherdry(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.weaponstack = new ItemStack(ItemsRegister.STAFF_OF_WITHERDRY);
        this.setSize(0.1F, 0.1F);
    }

    public ShootOfWitherdry(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
        super(world, thrower);
        this.weaponstack = itemstack;
        this.magicPower = power;
        this.setSize(0.1F, 0.1F);
    }

    @Override
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
        float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
        this.shoot(f, f1, f2, velocity, inaccuracy);
        double mot = 0.3F;
        this.motionX = this.motionX + entityThrower.motionX * mot;
        this.motionZ = this.motionZ + entityThrower.motionZ * mot;
        if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * mot;
        }
    }

    @Override
    protected float getGravityVelocity() {
        return -0.003F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote && this.ticksExisted > this.livetime) {
            this.setDead();
        }

        float size = 0.1F + this.ticksExisted / 23.0F;
        this.setSize(size, size);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return Debugger.floats[9] != 0.0F && super.isInRangeToRenderDist(distance);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                    WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
                    float decr = Math.max(1.0F - this.ticksExisted * parameters.getFloat("tick_damage_reduction"), 0.0F);
                    int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                    int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
                    float damage = parameters.getEnchantedF("damage", might) * this.magicPower * decr;
                    Weapons.dealDamage(new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.fire), damage, this.getThrower(), result.entityHit, true, parameters.getEnchantedF("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack)));
                    result.entityHit.hurtResistantTime = 0;
                    if (Weapons.canDealDamageTo(result.entityHit)) {
                        result.entityHit.setFire(parameters.getEnchantedI("fire", witchery));
                    }

                    if (result.entityHit instanceof EntityLivingBase) {
                        EntityLivingBase elb = (EntityLivingBase) result.entityHit;
                        if (elb.getHealth() <= 0.0F && elb.deathTime < 2 && this.rand.nextFloat() < parameters.getFloat("incinerate_chance")) {
                            DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_FIRE);
                            if (elb.getMaxHealth() >= parameters.getFloat("mob_health_for_charge") && NBTHelper.GetNBTint(this.weaponstack, "charge") < StaffOfWitherdry.maxcharge(this.weaponstack)) {
                                NBTHelper.AddNBTint(this.weaponstack, 1, "charge");
                            }
                        }
                    }
                }
            } else if (this.ticksExisted > 1 && result.typeOfHit == Type.BLOCK) {
                if (this.world.getBlockState(result.getBlockPos()).getBlock().getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos()) != null) {
                    float frictionMultipl = 1.4F;
                    if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
                        this.motionY = 0.0;
                        this.motionX /= 1.005F * frictionMultipl;
                        this.motionZ /= 1.005F * frictionMultipl;
                    }

                    if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
                        this.motionZ = 0.0;
                        this.motionX /= 1.005F * frictionMultipl;
                        this.motionY /= 1.001F * frictionMultipl;
                    }

                    if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
                        this.motionX = 0.0;
                        this.motionZ /= 1.005F * frictionMultipl;
                        this.motionY /= 1.001F * frictionMultipl;
                    }
                }

                this.tryPlaceFire(result);
            }
        }
    }

    public void tryPlaceFire(RayTraceResult result) {
        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
            BlockPos pos = result.sideHit == EnumFacing.UP ? this.getPosition() : this.getPosition().down();
            if (Blocks.FIRE.canPlaceBlockAt(this.world, pos)) {
                IBlockState state = this.world.getBlockState(pos);
                if (Weapons.easyBreakBlockFor(this.world, 0.4F, pos)) {
                    if (!state.getBlock().isReplaceable(this.world, pos)) {
                        this.world.destroyBlock(pos, true);
                    }

                    this.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
            }
        }
    }

}
