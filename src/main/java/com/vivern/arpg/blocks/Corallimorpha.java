package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class Corallimorpha extends Block implements IBlockHardBreak {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool WET = PropertyBool.create("wet");
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);

   public Corallimorpha(String name) {
      super(Material.WATER);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = BlocksRegister.HR_CORALS.hardness;
      this.blockResistance = BlocksRegister.HR_CORALS.resistance;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_CORALS.lvl);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
   }

   @Override
   public BlocksRegister.HardRes getHardRes() {
      return BlocksRegister.HR_CORALS;
   }

   @Override
   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      int flag = world.isRemote ? 10 : 2;
      return this.isAroundWater(world, pos)
         ? world.setBlockState(pos, Blocks.WATER.getDefaultState(), flag)
         : world.setBlockState(pos, Blocks.AIR.getDefaultState(), flag);
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
      for (EnumFacing facing : EnumFacing.VALUES) {
         IBlockState state = worldIn.getBlockState(pos.offset(facing));
         if (state.getMaterial() != Material.WATER && !state.isOpaqueCube()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      return this.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.offset(side.getOpposite())).isOpaqueCube();
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && state2.getValue(BlockStaticLiquid.LEVEL) == 0 && count++ >= 2) {
            return true;
         }
      }

      return false;
   }

   @Override
   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   @Override
   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      EnumFacing.Axis axis = state.getValue(FACING).getAxis();
      long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());

      double dx = ((i >> 16 & 15L) / 15.0F - 0.5);
      double dy = ((i >> 20 & 15L) / 15.0F - 1.0);
      double dz = ((i >> 24 & 15L) / 15.0F - 0.5);

      return new Vec3d(
         dx * (axis == Axis.X ? 0.1 : 0.5),
         dy * (axis == Axis.Y ? 0.1 : 0.5),
         dz * (axis == Axis.Z ? 0.1 : 0.5)
      );
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch (state.getValue(FACING)) {
         case EAST:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
         case DOWN:
            return UPPER_AABB;
         default:
            return STANDING_AABB;
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      switch (blockState.getValue(FACING)) {
         case EAST:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
         case DOWN:
            return UPPER_AABB;
         default:
            return STANDING_AABB;
      }
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
      boolean isWet = (meta & 8) != 0;
      int facingIndex = meta & 7;
      if (facingIndex > 5) facingIndex = 1;

      return this.getDefaultState()
              .withProperty(FACING, EnumFacing.byIndex(facingIndex))
              .withProperty(WET, isWet);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      int i = state.getValue(FACING).getIndex();
      if (state.getValue(WET)) {
         i |= 8;
      }
      return i;
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
      return new BlockStateContainer(this, FACING, LEVEL, WET);
   }

   @Override
   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this, 1, 0));
   }

   @Override
   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, facing).withProperty(LEVEL, 0).withProperty(WET, this.isInWater(worldIn, pos));
   }

   @SideOnly(Side.CLIENT)
   @Override
   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}
