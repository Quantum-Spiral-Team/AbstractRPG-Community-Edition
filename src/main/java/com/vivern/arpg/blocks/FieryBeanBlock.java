package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class FieryBeanBlock extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", facing -> facing != EnumFacing.DOWN && facing != EnumFacing.UP);
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);

    public FieryBeanBlock() {
        super(Material.WOOD);
        this.setRegistryName("fiery_bean_block");
        this.setTranslationKey("fiery_bean_block");
        this.blockHardness = 1.2F;
        this.blockResistance = 0.5F;
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(AGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (rand.nextFloat() < 0.5 && this.checkForDrop(world, pos, state)) {
            world.setBlockState(pos, state.withProperty(AGE, Math.min(state.getValue(AGE) + 1, 2)));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : EnumFacing.HORIZONTALS) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.checkForDrop(worldIn, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.checkForDrop(worldIn, pos, state)) {
            return true;
        } else {
            EnumFacing enumfacing = state.getValue(FACING);
            Axis enumfacing$axis = enumfacing.getAxis();
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            BlockPos blockpos = pos.offset(enumfacing1);
            boolean flag = false;
            if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
                flag = true;
            } else if (enumfacing$axis.isVertical()) {
                flag = true;
            }

            if (flag) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        return facing != EnumFacing.UP && facing != EnumFacing.DOWN && iblockstate.getBlock() == BlocksRegister.FIERY_BEAN_LOG;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlocksRegister.FIERY_BEAN_SAPLING);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return state.getValue(AGE) == 2 ? 1 : 0;
    }

    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(FACING))) {
            return true;
        } else {
            if (worldIn.getBlockState(pos).getBlock() == this) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }

            return false;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        int i = meta / 4;
        return iblockstate.withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4)).withProperty(AGE, i);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = state.getValue(AGE) * 4;
        return state.getValue(FACING).getHorizontalIndex() + i;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, AGE});
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.canPlaceAt(worldIn, pos, facing)) {
            return this.getDefaultState().withProperty(FACING, facing);
        } else {
            for (EnumFacing enumfacing : Plane.HORIZONTAL) {
                if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

}
