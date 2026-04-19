package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import java.util.Random;
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
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// B: почистил класс от лишнего и чутка оптимизировал
@SuppressWarnings("deprecation")
public class MiniCoral extends Block implements IBlockHardBreak {

   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool WET = PropertyBool.create("wet");

   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.6, 0.8);
   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.2, 0.2, 0.4, 0.8, 0.8, 1.0);
   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.2, 0.2, 0.0, 0.8, 0.8, 0.6);
   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.4, 0.2, 0.2, 1.0, 0.8, 0.8);
   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0, 0.2, 0.2, 0.6, 0.8, 0.8);
   protected static final AxisAlignedBB UPPER_AABB = new AxisAlignedBB(0.2, 0.4, 0.2, 0.8, 0.95, 0.8);

   private final BlockRenderLayer layer;
   private double offset = 0.4;
   private boolean dropGlowingPearls = false;

   public MiniCoral(String name, BlockRenderLayer layer) {
      super(Material.WATER);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = BlocksRegister.HR_CORALS.hardness;
      this.blockResistance = BlocksRegister.HR_CORALS.resistance;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_CORALS.lvl);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(WET, false));
      this.layer = layer;
   }

   @Override
   public BlocksRegister.HardRes getHardRes() {
      return BlocksRegister.HR_CORALS;
   }

   public MiniCoral setDropGlowingPearls() {
      this.dropGlowingPearls = true;
      return this;
   }

   public MiniCoral setOffset(double offset) {
      this.offset = offset;
      return this;
   }

   @Override
   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      super.getDrops(drops, world, pos, state, fortune);

      if (this.dropGlowingPearls) {
         int pearlsCount = 0;
         if (RANDOM.nextFloat() < 0.5F + fortune * 0.15F) pearlsCount++;
         if (RANDOM.nextFloat() < 0.35F + fortune * 0.15F) pearlsCount++;
         if (RANDOM.nextFloat() < 0.25F) pearlsCount++;

         if (pearlsCount > 0) {
            drops.add(new ItemStack(ItemsRegister.GLOWING_PEARL, pearlsCount));
         }
      }
   }

   @Override
   protected boolean canSilkHarvest() {
      return true;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return ItemsRegister.CORAL;
   }

   @Override
   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 1 + GetMOP.floatToIntWithChance(fortune * 0.25F, random);
   }

   private void destroyFallingBlock(World world, Entity entityIn) {
      if (!world.isRemote && entityIn instanceof EntityFallingBlock) {
         entityIn.setDead();
      }
   }

   @Override
   public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entityIn) {
      destroyFallingBlock(world, entityIn);
   }

   @Override
   public void onFallenUpon(World world, BlockPos pos, Entity entityIn, float fallDistance) {
      destroyFallingBlock(world, entityIn);
      super.onFallenUpon(world, pos, entityIn, fallDistance);
   }

   @Override
   public void onLanded(World world, Entity entityIn) {
      destroyFallingBlock(world, entityIn);
   }

   @Override
   public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
      int light = super.getLightValue(state, world, pos);
      return state.getValue(WET) ? light : light / 2;
   }

   @Override
   public Material getMaterial(IBlockState state) {
      return state.getValue(WET) ? Material.WATER : Material.ROCK;
   }

   @Override
   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean shouldBeWet = this.isInWater(world, pos);
      if (state.getValue(WET) != shouldBeWet) {
         world.setBlockState(pos, state.withProperty(WET, shouldBeWet), 2);
      }
      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public boolean isInWater(World worldIn, BlockPos pos) {
      for (EnumFacing facing : EnumFacing.VALUES) {
         IBlockState offsetState = worldIn.getBlockState(pos.offset(facing));
         if (offsetState.getMaterial() != Material.WATER && !offsetState.isOpaqueCube()) {
            return false;
         }
      }
      return true;
   }

   @Override
   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      return this.canPlaceBlockAt(worldIn, pos) && worldIn.isSideSolid(pos.offset(side.getOpposite()), side);
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;
      for (EnumFacing facing : EnumFacing.VALUES) {
         IBlockState offsetState = world.getBlockState(pos.offset(facing));
         if (offsetState.getBlock() == Blocks.WATER && offsetState.getValue(BlockStaticLiquid.LEVEL) == 0 && ++count >= 2) {
             return true;
         }
      }
      return false;
   }

   @Override
   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      IBlockState replaceState = this.isAroundWater(world, pos) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
      return world.setBlockState(pos, replaceState, world.isRemote ? 10 : 2);
   }

   @Override
   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   @Override
   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      EnumFacing face = state.getValue(FACING);
      long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());

      switch (face.getAxis()) {
         case X:
            return new Vec3d(0.0, ((i >> 20 & 15L) / 15.0F - 0.5) * this.offset, ((i >> 24 & 15L) / 15.0F - 0.5) * this.offset);
         case Y:
            return new Vec3d(((i >> 16 & 15L) / 15.0F - 0.5) * this.offset, 0.0, ((i >> 24 & 15L) / 15.0F - 0.5) * this.offset);
         case Z:
            return new Vec3d(((i >> 16 & 15L) / 15.0F - 0.5) * this.offset, ((i >> 20 & 15L) / 15.0F - 0.5) * this.offset, 0.0);
         default:
            return Vec3d.ZERO;
      }
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      switch (state.getValue(FACING)) {
         case EAST: return EAST_AABB;
         case WEST: return WEST_AABB;
         case SOUTH: return SOUTH_AABB;
         case NORTH: return NORTH_AABB;
         case DOWN: return UPPER_AABB;
         default: return STANDING_AABB;
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return getBoundingBox(blockState, worldIn, pos);
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return this.layer;
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
      boolean isWet = meta >= 8;
      int faceIndex = isWet ? meta - 8 : meta;
      if (faceIndex < 0 || faceIndex > 5) faceIndex = 1;

      return this.getDefaultState()
              .withProperty(FACING, EnumFacing.byIndex(faceIndex))
              .withProperty(WET, isWet)
              .withProperty(LEVEL, 0);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      int i = state.getValue(FACING).getIndex();
      return state.getValue(WET) ? i + 8 : i;
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

   @Override
   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      if (world.provider.getDimension() == DimensionsRegister.AQUATICA_ID) {
         return DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks);
      }
      return super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }

}