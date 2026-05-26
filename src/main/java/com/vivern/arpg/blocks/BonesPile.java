package com.vivern.arpg.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BonesPile extends Block {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.15, 0.0, 0.15, 0.85, 0.45, 0.85);

   public BonesPile() {
      super(Material.GROUND);
      this.setRegistryName("bones_pile");
      this.setTranslationKey("bones_pile");
      this.blockHardness = 0.1F;
      this.blockResistance = 0.1F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundTypeCrunchy.CRUNCHY);
   }

   @Override
   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (worldIn.isAirBlock(pos.down())) {
         worldIn.destroyBlock(pos, true);
      }
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos pos) {
      return world.isSideSolid(pos.down(), EnumFacing.UP) ? super.canPlaceBlockAt(world, pos) : false;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.BONE;
   }

   @Override
   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return fortune > 0 ? 2 : random.nextInt(2) + 1;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return false;
   }

   @Override
   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XZ;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }
}
