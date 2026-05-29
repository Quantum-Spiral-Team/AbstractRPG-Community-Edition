package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.google.common.base.Predicate;
import java.util.Random;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Seagrass extends Block {
   protected static final AxisAlignedBB TALL_GRASS_AABB = new AxisAlignedBB(0.099999994F, 0.0, 0.099999994F, 0.9F, 1.0, 0.9F);

   public Seagrass() {
      super(Material.WATER);
      this.setRegistryName("seagrass");
      this.setTranslationKey("seagrass");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.5F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
      this.setSoundType(SoundType.PLANT);
   }

   @Override
   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   @Override
   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
      return false;
   }

   @Override
   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      if (!player.isCreative()) {
         this.harvestBlock(world, player, pos, state, null, player.getHeldItemMainhand());
      }

      return world.setBlockState(pos, Blocks.WATER.getDefaultState(), world.isRemote ? 10 : 2);
   }

   @Override
   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   @Override
   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.canStayAt(world, pos)) {
         world.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
      }

      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public boolean canStayAt(World worldIn, BlockPos pos) {
      BlockPos[] horizontals = {pos.up(), pos.east(), pos.south(), pos.west(), pos.north()};

      for (BlockPos neighborPos : horizontals) {
         IBlockState state = worldIn.getBlockState(neighborPos);
         if (!(state.getMaterial() == Material.WATER || state.isOpaqueCube())) {
            return false;
         }
      }

      IBlockState below = worldIn.getBlockState(pos.down());
      return below.getBlock() == this || below.isTopSolid();
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      IBlockState state0 = worldIn.getBlockState(pos);
      return state0.getBlock().isReplaceable(worldIn, pos) && this.canStayAt(worldIn, pos);
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (rand.nextFloat() < 0.99) {
         int weeds = 0;

         for (int i = -1; i <= 1; i++) {
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
               BlockPos poss = pos.offset(facing).add(0, i, 0);
               IBlockState state2 = world.getBlockState(poss);
               if (state2.getBlock() == this) {
                  weeds++;
               }
            }
         }

         if (weeds < 2) {
            BlockPos pos2 = pos.add(rand.nextInt(2) - rand.nextInt(2), rand.nextInt(2) - rand.nextInt(2), rand.nextInt(2) - rand.nextInt(2));
            if (this.canPlaceBlockAt(world, pos2)) {
               world.setBlockState(pos2, this.getDefaultState(), 2);
            }
         }
      }
   }

   @Override
   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      if (this.isAroundWater(world, pos)) {
         world.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
      }

      super.breakBlock(world, pos, state);
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && state2.getValue(BlockStaticLiquid.LEVEL) == 0
            || state2.getBlock() == this) {
            if (++count >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return TALL_GRASS_AABB;
   }

   @Override
   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   @Override
   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   @Override
   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
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
      return this.getDefaultState().withProperty(BlockLiquid.LEVEL, 0);
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      return 0;
   }

   @Override
   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, BlockLiquid.LEVEL);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}
