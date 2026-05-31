package com.vivern.arpg.blocks;

import com.vivern.arpg.main.OreDicHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class NativeSilver extends Block {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UPPER = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    protected static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.2000000059604645, 0.2, 0.2000000059604645, 0.8000000238418579, 0.8, 0.8000000238418579);

    public NativeSilver() {
        super(Material.ROCK);
        this.setRegistryName("native_silver");
        this.setTranslationKey("native_silver");
        this.blockHardness = 2.0F;
        this.blockResistance = 0.3F;
        this.setSoundType(SoundTypeShards.METAL);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        boolean west = worldIn.isSideSolid(pos.west(), EnumFacing.EAST, false) || worldIn.getBlockState(pos.west()).isFullCube();
        boolean east = worldIn.isSideSolid(pos.east(), EnumFacing.WEST, false) || worldIn.getBlockState(pos.east()).isFullCube();
        boolean south = worldIn.isSideSolid(pos.south(), EnumFacing.NORTH, false) || worldIn.getBlockState(pos.south()).isFullCube();
        boolean north = worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH, false) || worldIn.getBlockState(pos.north()).isFullCube();
        boolean up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN, false) || worldIn.getBlockState(pos.up()).isFullCube();
        boolean down = worldIn.isSideSolid(pos.down(), EnumFacing.UP, false) || worldIn.getBlockState(pos.down()).isFullCube();
        return state.withProperty(WEST, west).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(UPPER, up).withProperty(DOWN, down);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        boolean west = worldIn.isSideSolid(pos.west(), EnumFacing.EAST) || worldIn.isBlockFullCube(pos.west());
        boolean east = worldIn.isSideSolid(pos.east(), EnumFacing.WEST) || worldIn.isBlockFullCube(pos.east());
        boolean south = worldIn.isSideSolid(pos.south(), EnumFacing.NORTH) || worldIn.isBlockFullCube(pos.south());
        boolean north = worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH) || worldIn.isBlockFullCube(pos.north());
        boolean up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN) || worldIn.isBlockFullCube(pos.up());
        boolean down = worldIn.isSideSolid(pos.down(), EnumFacing.UP) || worldIn.isBlockFullCube(pos.down());
        return east || north || south || west || up || down;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return ALL_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return random.nextInt(3 + fortune) + (random.nextFloat() < 0.3F ? 1 : 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return OreDicHelper.get("nuggetSilver", 1).getItem();
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
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, UPPER, DOWN});
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollision(worldIn, pos, state, entityIn);
        if (entityIn instanceof EntityLiving && ((EntityLiving) entityIn).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            entityIn.attackEntityFrom(DamageSource.MAGIC, 2.0F);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        boolean west;
        boolean east;
        boolean south;
        boolean north;
        boolean up;
        boolean down;
        if (placer != null && placer.isSneaking()) {
            west = facing == EnumFacing.EAST;
            east = facing == EnumFacing.WEST;
            south = facing == EnumFacing.NORTH;
            north = facing == EnumFacing.SOUTH;
            up = facing == EnumFacing.DOWN;
            down = facing == EnumFacing.UP;
        } else {
            west = worldIn.isSideSolid(pos.west(), EnumFacing.EAST) || worldIn.isBlockFullCube(pos.west());
            east = worldIn.isSideSolid(pos.east(), EnumFacing.WEST) || worldIn.isBlockFullCube(pos.east());
            south = worldIn.isSideSolid(pos.south(), EnumFacing.NORTH) || worldIn.isBlockFullCube(pos.south());
            north = worldIn.isSideSolid(pos.north(), EnumFacing.SOUTH) || worldIn.isBlockFullCube(pos.north());
            up = worldIn.isSideSolid(pos.up(), EnumFacing.DOWN) || worldIn.isBlockFullCube(pos.up());
            down = worldIn.isSideSolid(pos.down(), EnumFacing.UP) || worldIn.isBlockFullCube(pos.down());
        }

        return this.getDefaultState().withProperty(WEST, west).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(UPPER, up).withProperty(DOWN, down);
    }

}
