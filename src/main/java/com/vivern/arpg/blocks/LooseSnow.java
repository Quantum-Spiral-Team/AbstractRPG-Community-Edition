package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LooseSnow extends BlockFalling {
   protected static final AxisAlignedBB[] SNOW_AABB = new AxisAlignedBB[]{
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.375, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.625, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0),
      new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
   };
   public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 0, 8);
   public static final PropertyBool ISFALLING = PropertyBool.create("falling");

   public LooseSnow() {
      super(Material.ROCK);
      this.setRegistryName("loose_snow");
      this.setTranslationKey("loose_snow");
      this.blockHardness = 0.05F;
      this.blockResistance = 0.003F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setSoundType(SoundType.SNOW);
      this.setHarvestLevel("shovel", 0);
   }

   @Override
   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      int i = blockState.getValue(LAYERS);
      AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
      return i == 8
         ? SNOW_AABB[8]
         : new AxisAlignedBB(
            axisalignedbb.minX,
            axisalignedbb.minY,
            axisalignedbb.minZ,
            axisalignedbb.maxX,
            i * 0.03F,
            axisalignedbb.maxZ
         );
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return SNOW_AABB[state.getValue(LAYERS)];
   }

   @Override
   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos).getValue(LAYERS) < 5;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.SNOWBALL;
   }

   @Override
   public int quantityDropped(Random random) {
      return 2;
   }

   @Override
   public int damageDropped(IBlockState state) {
      return super.damageDropped(state);
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return state.getValue(LAYERS) == 8;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(LAYERS, meta).withProperty(ISFALLING, false);
   }

   @Override
   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      return state.getValue(LAYERS);
   }

   @Override
   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LAYERS, ISFALLING});
   }

   @Override
   public void onEndFalling(World worldIn, BlockPos pos, IBlockState placed, IBlockState replaced) {
      if (replaced.getBlock() == BlocksRegister.LOOSE_SNOW) {
         worldIn.setBlockState(
            pos,
            placed.withProperty(LAYERS, Math.min(8, replaced.getValue(LAYERS) + placed.getValue(LAYERS)))
               .withProperty(ISFALLING, false)
         );
      } else {
         worldIn.setBlockState(pos, placed.withProperty(ISFALLING, false));
      }
   }

   @Override
   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote) {
         this.checkFallable(worldIn, pos);
      }
   }

   private void checkFallable(World worldIn, BlockPos pos) {
      if (worldIn.getBlockState(pos.down()).getBlock() == BlocksRegister.LOOSE_SNOW
            && worldIn.getBlockState(pos.down()).getValue(LAYERS) < 8
         || (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) {
         int i = 32;
         if (fallInstantly || !worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
            IBlockState state = worldIn.getBlockState(pos);
            worldIn.setBlockToAir(pos);
            BlockPos blockpos = pos.down();

            while ((worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) && blockpos.getY() > 0) {
               blockpos = blockpos.down();
            }

            if (blockpos.getY() > 0) {
               worldIn.setBlockState(blockpos.up(), state);
            }
         } else if (!worldIn.isRemote && worldIn.getBlockState(pos).getBlock() == BlocksRegister.LOOSE_SNOW) {
            EntityFallingBlock entityfallingblock = new EntityFallingBlock(
               worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, worldIn.getBlockState(pos).withProperty(ISFALLING, true)
            );
            this.onStartFalling(entityfallingblock);
            worldIn.spawnEntity(entityfallingblock);
         }
      }
   }

   @Override
   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
      return true;
   }

   @Override
   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(LAYERS, 1).withProperty(ISFALLING, false);
   }

   @Override
   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return side == EnumFacing.DOWN || state.getValue(LAYERS) == 8;
   }

   @Override
   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side) {
      return side != EnumFacing.DOWN && state.getValue(LAYERS) != 8 ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
   }
}
