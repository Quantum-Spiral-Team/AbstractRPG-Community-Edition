package com.vivern.arpg.items;

import com.vivern.arpg.entity.CryoDestroyerSpray;
import com.vivern.arpg.items.animation.EnumFlick;
import com.vivern.arpg.items.animation.FlickInertia;
import com.vivern.arpg.items.animation.Flicks;
import com.vivern.arpg.main.*;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class CryoDestroyer extends ItemWeapon {

    public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
    public static int maxammo = 256;

    public CryoDestroyer() {
        this.setRegistryName("cryo_destroyer");
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setTranslationKey("cryo_destroyer");
        this.setMaxDamage(5500);
        this.setMaxStackSize(1);
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 3.5F;
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
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new AnimationCapabilityProvider();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void boom(int param) {
        if (param == 0) {
            Boom.lastTick = 20;
            Boom.frequency = -0.157F;
            Boom.x = -1.0F;
            Boom.y = (itemRand.nextFloat() - 0.5F) * 0.5F;
            Boom.z = (itemRand.nextFloat() - 0.5F) * 0.5F;
            Boom.power = 0.32F;
            Boom.FOVlastTick = 20;
            Boom.FOVfrequency = -0.157F;
            Boom.FOVpower = -0.02F;
        } else {
            Boom.lastTick = 14;
            Boom.frequency = 0.6F;
            Boom.x = 0.0F;
            Boom.y = (float) itemRand.nextGaussian();
            Boom.z = (float) itemRand.nextGaussian();
            Boom.power = 0.03F;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
        if (state == 1) {
            int cooldown = this.getCooldownTime(itemstack);
            Flicks.INSTANCE.setClientAnimation(player, slot, EnumFlick.SHOOT, 0, cooldown, -1, cooldown);
            FlickInertia flickInertia = Flicks.INSTANCE.getOrCreateFlickInertia(player, slot, EnumFlick.INFO, -127, 127, -7, -100, 0);
            flickInertia.valueTarget = (int) Math.min(flickInertia.value + 40.0F + itemRand.nextInt(60), 127.0F);
            flickInertia.hasTarget = true;
        }

        if (state == 2) {
            FlickInertia flickInertia = Flicks.INSTANCE.getOrCreateFlickInertia(player, slot, EnumFlick.INFO, -127, 127, -7, -100, 0);
            flickInertia.valueTarget = -107 + itemRand.nextInt(40);
            flickInertia.hasTarget = true;
        }

        if (state == 3) {
            Flicks.INSTANCE.setClientAnimation(player, slot, EnumFlick.RELOAD, 0, 50, -1, 50);
        }
    }

    public boolean tryCryoDestroy(World world, BlockPos pos, BlockBreaking blockBreaking, int fortune, boolean silktouch, float toolPower, EntityPlayer player) {
        if (!world.isAirBlock(pos)) {
            AxisAlignedBB aabb = new AxisAlignedBB(pos);
            List<CryonedBlock> list = world.getEntitiesWithinAABB(CryonedBlock.class, aabb);
            if (!list.isEmpty()) {
                toolPower += 5.0F;
            }

            if (blockBreaking.damageBlock(player, pos, toolPower, "pickaxe", 6, fortune, silktouch)) {
                aabb = aabb.shrink(0.1);

                for (CryonedBlock cryonedBlock : list) {
                    if (cryonedBlock.getEntityBoundingBox().intersects(aabb)) {
                        cryonedBlock.setDead();
                    }
                }

                return true;
            }
        }

        return false;
    }

    public void recursiveDestroy(ArrayList<BlockPos> posesDestroyed, World world, BlockPos pos, EnumFacing facingFrom, int recursionLast, BlockBreaking blockBreaking, int fortune, boolean silktouch, float toolPower, EntityPlayer player) {
        if (!posesDestroyed.contains(pos)) {
            posesDestroyed.add(pos);
            if (this.tryCryoDestroy(world, pos, blockBreaking, fortune, silktouch, toolPower, player) && recursionLast > 0) {
                recursionLast--;
                facingFrom = facingFrom == null ? null : facingFrom.getOpposite();

                for (EnumFacing facing : EnumFacing.VALUES) {
                    if (facing != facingFrom) {
                        this.recursiveDestroy(posesDestroyed, world, pos.offset(facing), facing, recursionLast, blockBreaking, fortune, silktouch, toolPower, player);
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isRemote) {
            this.setCanShoot(itemstack, entityIn);
            if (IWeapon.canShoot(itemstack)) {
                EntityPlayer player = (EntityPlayer) entityIn;
                this.decreaseReload(itemstack, player);
                boolean click = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.PRIMARY);
                boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
                float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
                if ((click || click2) && player.getHeldItemMainhand() == itemstack) {
                    if (ammo > 0 && this.isReloaded(itemstack)) {
                        if (!player.getCooldownTracker().hasCooldown(this)) {
                            if (click) {
                                player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                                player.addStat(StatList.getObjectUseStats(this));
                                IWeapon.fireBomEffect(this, player, world, 0);
                                Weapons.setPlayerAnimationOnServer(player, 44, EnumHand.MAIN_HAND);
                                IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
                                RayTraceResult result = GetMOP.fixedRayTraceBlocks(world, player, 5.0 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack) * 0.6, 0.4, true, false, true, false);
                                BlockPos destroyPos = result.getBlockPos();
                                world.playSound(null, destroyPos.getX() + 0.5, destroyPos.getY() + 0.5, destroyPos.getZ() + 0.5, Sounds.cryo_destroyer, SoundCategory.AMBIENT, 0.9F, 0.95F + itemRand.nextFloat() / 10.0F);
                                float toolPower = 5.0F * (1.0F + 0.3F * EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemstack));
                                if (world instanceof WorldServer) {
                                    if (player.isSneaking()) {
                                        this.tryCryoDestroy(world, destroyPos, BlockBreaking.getBlockBreaking((WorldServer) world), EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack), EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0, toolPower, player);
                                    } else {
                                        this.recursiveDestroy(new ArrayList<>(), world, destroyPos, result.sideHit == null ? null : result.sideHit.getOpposite(), 3, BlockBreaking.getBlockBreaking((WorldServer) world), EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack), EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0, toolPower, player);
                                    }
                                }

                                if (result.entityHit != null) {
                                    Weapons.dealDamage(new WeaponDamage(itemstack, player, null, false, false, result, WeaponDamage.heavymelee), (Freezing.canImmobilizeEntity(result.entityHit) ? 20 : 10) + EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemstack) * 2, player, result.entityHit, true, 1.5F + EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack) * 0.5F);
                                    result.entityHit.hurtResistantTime = 0;
                                }

                                if (!player.capabilities.isCreativeMode) {
                                    this.addAmmo(ammo, itemstack, -1);
                                    itemstack.damageItem(5, player);
                                }
                            } else if (click2 && player.ticksExisted % 2 == 0) {
                                if (player.ticksExisted % 4 == 0) {
                                    Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                                    world.playSound(null, player.posX, player.posY, player.posZ, Sounds.cryo_destroyer_spray, SoundCategory.AMBIENT, 0.7F, 0.95F + itemRand.nextFloat() / 10.0F);
                                }

                                CryoDestroyerSpray projectile = new CryoDestroyerSpray(world, player, itemstack);
                                Weapons.shoot(projectile, EnumHand.MAIN_HAND, player, player.rotationPitch - 2.0F, player.rotationYaw, 0.0F, 1.0F, 1.0F / (1.0F + acclvl * 2.0F), -0.22F, 0.5F, 0.5F);
                                world.spawnEntity(projectile);
                                if (!player.capabilities.isCreativeMode) {
                                    int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                                    if (itemRand.nextFloat() < (reuse > 0 ? (reuse == 1 ? 0.8F : 1.0F - reuse * 0.15F) : 1.0F)) {
                                        this.addAmmo(ammo, itemstack, -1);
                                    }

                                    itemstack.damageItem(1, player);
                                }
                            }
                        }
                    } else if (this.initiateReload(itemstack, player, ItemsRegister.CRYOGEN_CELL, maxammo, ItemsRegister.EMPTY_CELL)) {
                        world.playSound(null, player.posX, player.posY, player.posZ, Sounds.cryo_destroyer_rel, SoundCategory.AMBIENT, 0.7F, 1.0F);
                        Weapons.setPlayerAnimationOnServer(player, 43, EnumHand.MAIN_HAND);
                        IWeapon.sendIWeaponState(itemstack, player, 3, itemSlot, EnumHand.MAIN_HAND);
                    }
                }
            }
        } else if (IWeapon.canShoot(itemstack) && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            boolean click2 = ServerKeyTracker.isKeyPressed(player, ServerKeyTracker.Keys.SECONDARY);
            if (click2 && player.getHeldItemMainhand() == itemstack && ammo > 0 && this.isReloaded(itemstack) && !player.getCooldownTracker().hasCooldown(this)) {
                GUNParticle bigsmoke = new GUNParticle(largecloud, 0.07F + itemRand.nextFloat() * 0.05F, 0.01F, 8, 220, world, entityIn.posX, entityIn.posY, entityIn.posZ, 0.0F, 0.0F, 0.0F, 0.75F + itemRand.nextFloat() / 5.0F, 1.0F, 1.0F, true, itemRand.nextInt(360), true, 1.5F);
                bigsmoke.alphaGlowing = true;
                bigsmoke.alphaTickAdding = -0.11F;
                bigsmoke.scaleTickAdding = 0.08F;
                Weapons.shoot(bigsmoke, EnumHand.MAIN_HAND, player, player.rotationPitch - 2.0F, player.rotationYaw, 0.0F, 1.0F, 1.0F, -0.22F, 0.5F, 0.5F);
                world.spawnEntity(bigsmoke);
                if (player.ticksExisted % 4 == 0) {
                    this.boom(1);
                    this.onStateReceived(player, itemstack, (byte) 2, itemSlot);
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
    public int getReloadTime(ItemStack itemstack) {
        int rellvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack);
        return 50 - rellvl * 10;
    }

    @Override
    public int getCooldownTime(ItemStack itemstack) {
        int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
        return 22 - rapidity * 3;
    }

    @Override
    public WeaponHandleType getWeaponHandleType() {
        return WeaponHandleType.TWO_HANDED;
    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 0.0F;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type == EnumEnchantmentType.DIGGER || enchantment.type == EnumEnchantmentType.BREAKABLE || enchantment.type == EnchantmentInit.enchantmentTypeWeapon || super.canApplyAtEnchantingTable(stack, enchantment);
    }

}
