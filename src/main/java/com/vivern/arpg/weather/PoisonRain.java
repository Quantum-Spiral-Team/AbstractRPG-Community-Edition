package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.Weapons;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PoisonRain extends WorldEvent {

    public Random rand = new Random();

    public PoisonRain(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
    }

    @Override
    public boolean canOverlapWith(WorldEvent other) {
        return false;
    }

    @Override
    public int getCooldown() {
        return 5000 + this.worldProvider.getWorld().rand.nextInt(5000);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % 30 == 0) {
            this.updateAllLoaded(this.rand, 0.01F, 1);
            if (this.ticksExisted % 90 == 0) {
                World world = this.worldProvider.getWorld();

                for (Entity entity : world.loadedEntityList) {
                    if (entity instanceof EntityLivingBase && this.isRainingAt(entity)) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
                        PotionEffect eff = new PotionEffect(MobEffects.POISON, 300, 0);
                        entitylivingbase.addPotionEffect(eff);
                    }
                }
            }
        }
    }

    @Override
    public void updateLoadedXZ(World world, int posX, int posZ) {
        BlockPos pos = new BlockPos(posX, 0, posZ);
        pos = world.getPrecipitationHeight(pos).down();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == Blocks.SAPLING) {
            world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState(), 2);
        } else if (block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.FARMLAND) {
            BlockPos posup = pos.up();
            if (world.getBlockState(posup).getMaterial() == Material.PLANTS && Weapons.easyBreakBlockFor(world, 0.5F, posup)) {
                world.setBlockToAir(posup);
            }

            world.setBlockState(pos, BlocksRegister.TOXIC_DIRT.getDefaultState(), 2);
            if (this.rand.nextFloat() < 0.08F && world.isAirBlock(posup)) {
                world.setBlockState(posup, BlocksRegister.TOXIC_TALLGRASS.getDefaultState(), 2);
            }
        }
    }

}
