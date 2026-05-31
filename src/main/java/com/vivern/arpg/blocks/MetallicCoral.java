package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetallicCoral extends Block implements IBlockHardBreak {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UPPER = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
    public static final PropertyBool WET = PropertyBool.create("wet");
    public static double aabbLow = 0.2F;
    public static double aabbHigh = 0.8F;
    public static double aabbMidLow = 0.15F;
    public static double aabbMidHigh = 0.85F;

    private static final AxisAlignedBB MID_AABB = new AxisAlignedBB(aabbMidLow, aabbMidLow, aabbMidLow, aabbMidHigh, aabbMidHigh, aabbMidHigh);
    private static final AxisAlignedBB VERTICAL_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, aabbHigh, 1.0, aabbHigh);
    private static final AxisAlignedBB U_HALF_AABB = new AxisAlignedBB(0.0, aabbLow, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB U_AABB = new AxisAlignedBB(aabbLow, 0.5, aabbLow, aabbHigh, 1.0, aabbHigh);
    private static final AxisAlignedBB D_AABB = new AxisAlignedBB(aabbLow, 0.0, aabbLow, aabbHigh, 0.5, aabbHigh);

    private static final int BIT_NORTH = 1; // 1
    private static final int BIT_EAST = 1 << 1; // 2
    private static final int BIT_SOUTH = 1 << 2; // 4
    private static final int BIT_WEST = 1 << 3; // 8
    private static final int BIT_UPPER = 1 << 4; // 16
    private static final int BIT_DOWN = 1 << 5; // 32
    private static final AxisAlignedBB[] AABB_LOOKUP = new AxisAlignedBB[64];

    public MetallicCoral() {
        super(Material.ROCK);
        this.setRegistryName("metallic_coral");
        this.setTranslationKey("metallic_coral");
        this.blockHardness = BlocksRegister.HR_METALLIC_CORALS.hardness;
        this.blockResistance = BlocksRegister.HR_METALLIC_CORALS.resistance;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHarvestLevel("pickaxe", BlocksRegister.HR_METALLIC_CORALS.lvl);
    }

    @Override
    public BlocksRegister.HardRes getHardRes() {
        return BlocksRegister.HR_METALLIC_CORALS;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return this.isAroundWater(world, pos) ? world.setBlockState(pos, Blocks.WATER.getDefaultState(), world.isRemote ? 10 : 2) : world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 10 : 2);
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return state.getValue(WET) ? Material.WATER : Material.ROCK;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.isInWater(world, pos)) {
            world.setBlockState(pos, state.withProperty(WET, false));
        } else {
            world.setBlockState(pos, state.withProperty(WET, true));
        }

        super.neighborChanged(state, world, pos, blockIn, fromPos);
    }

    public boolean isInWater(World worldIn, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.values()) {
            IBlockState state = worldIn.getBlockState(pos.offset(facing));
            if (!(state.getMaterial() == Material.WATER || state.isOpaqueCube())) {
                return false;
            }
        }
        return true;
    }

    public boolean isAroundWater(World world, BlockPos pos) {
        int count = 0;

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos poss = pos.offset(facing);
            IBlockState state2 = world.getBlockState(poss);
            if (state2.getBlock() == Blocks.WATER && state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
                if (++count >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    public boolean isBlockConnecting(IBlockState state) {
        if (state.getMaterial() == Material.AIR) {
            return false;
        } else {
            return state.getBlock() != Blocks.WATER && state.getBlock() != Blocks.FLOWING_WATER && state.getBlock() != Blocks.LAVA && state.getBlock() != Blocks.FLOWING_LAVA && !(state.getBlock() instanceof IFluidBlock);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        BlockPos wb = pos.west();
        BlockPos eb = pos.east();
        BlockPos sb = pos.south();
        BlockPos nb = pos.north();
        BlockPos ub = pos.up();
        BlockPos db = pos.down();
        boolean west = this.isBlockConnecting(worldIn.getBlockState(wb));
        boolean east = this.isBlockConnecting(worldIn.getBlockState(eb));
        boolean south = this.isBlockConnecting(worldIn.getBlockState(sb));
        boolean north = this.isBlockConnecting(worldIn.getBlockState(nb));
        boolean up = this.isBlockConnecting(worldIn.getBlockState(ub));
        boolean down = this.isBlockConnecting(worldIn.getBlockState(db));
        return state.withProperty(WEST, west).withProperty(EAST, east).withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(UPPER, up).withProperty(DOWN, down);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return this.getAABB(blockState);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return this.getAABB(this.getActualState(state, source, pos));
    }

    public AxisAlignedBB getAABB(IBlockState state) {
        int index = 0;

        if (state.getValue(NORTH))
            index |= BIT_NORTH;
        if (state.getValue(EAST))
            index |= BIT_EAST;
        if (state.getValue(SOUTH))
            index |= BIT_SOUTH;
        if (state.getValue(WEST))
            index |= BIT_WEST;
        if (state.getValue(UPPER))
            index |= BIT_UPPER;
        if (state.getValue(DOWN))
            index |= BIT_DOWN;

        return AABB_LOOKUP[index];
    }

    @SideOnly(Side.CLIENT)
    @Override
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
        return this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, meta > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(WET) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UPPER, DOWN, LEVEL, WET);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, isInWater(worldIn, pos));

        state = state.withProperty(NORTH, this.isBlockConnecting(worldIn.getBlockState(pos.north())));
        state = state.withProperty(EAST, this.isBlockConnecting(worldIn.getBlockState(pos.east())));
        state = state.withProperty(SOUTH, this.isBlockConnecting(worldIn.getBlockState(pos.south())));
        state = state.withProperty(WEST, this.isBlockConnecting(worldIn.getBlockState(pos.west())));
        state = state.withProperty(UPPER, this.isBlockConnecting(worldIn.getBlockState(pos.up())));
        state = state.withProperty(DOWN, this.isBlockConnecting(worldIn.getBlockState(pos.down())));

        return state;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        return world.provider.getDimension() == 103 ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks) : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
    }

    static {
        AABB_LOOKUP[0] = MID_AABB;
        AABB_LOOKUP[BIT_DOWN] = D_AABB;
        AABB_LOOKUP[BIT_UPPER] = U_AABB;
        AABB_LOOKUP[BIT_UPPER | BIT_DOWN] = VERTICAL_AABB;

        AABB_LOOKUP[BIT_NORTH | BIT_EAST | BIT_SOUTH | BIT_WEST | BIT_UPPER | BIT_DOWN] = FULL_BLOCK_AABB;

        AABB_LOOKUP[BIT_NORTH | BIT_EAST | BIT_SOUTH | BIT_WEST | BIT_UPPER] = U_HALF_AABB;
    }
}
