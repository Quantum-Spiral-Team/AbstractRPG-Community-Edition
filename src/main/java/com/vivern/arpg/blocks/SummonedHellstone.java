package com.vivern.arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class SummonedHellstone extends Block {

    public static int EXPIRE_MAX = 4;
    public static final PropertyInteger EXPIRE = PropertyInteger.create("expire", 0, EXPIRE_MAX);

    public SummonedHellstone() {
        super(Material.ROCK);
        this.setRegistryName("summoned_hellstone");
        this.setTranslationKey("summoned_hellstone");
        this.blockHardness = 1.0F;
        this.blockResistance = 1.0F;
        this.setSoundType(SoundTypeShards.STONE);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setTickRandomly(true);
        this.setLightLevel(0.3F);
    }

    @Override
    public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
        double xRand = (float) (i >> 16 & 15L) / 15.0F - 0.5;
        double yRand = (float) (i >> 20 & 15L) / 15.0F - 1.0;
        double zRand = (float) (i >> 24 & 15L) / 15.0F - 0.5;
        return new Vec3d(xRand * 0.25, yRand * 0.15, zRand * 0.25);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        return 15728880;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (!world.isRemote) {
            int expireInt = state.getValue(EXPIRE);
            if (expireInt < EXPIRE_MAX) {
                world.setBlockState(pos, state.withProperty(EXPIRE, expireInt + 1));
            } else {
                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        IBlockState state = worldIn.getBlockState(pos);
        int expireInt = state.getValue(EXPIRE);
        if (expireInt > 0) {
            worldIn.setBlockState(pos, state.withProperty(EXPIRE, 0));
        }
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(EXPIRE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(EXPIRE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{EXPIRE});
    }

}
