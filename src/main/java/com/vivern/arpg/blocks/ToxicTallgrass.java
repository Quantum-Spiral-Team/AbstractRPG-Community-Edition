package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicTallgrass extends Block implements IShearable {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 0.7, 0.9);

   public ToxicTallgrass() {
      super(Material.PLANTS);
      this.setRegistryName("toxic_tallgrass");
      this.setTranslationKey("toxic_tallgrass");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.PLANT);
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return true;
   }

   @Override
   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public int quantityDropped(Random random) {
      return random.nextFloat() < 0.65F ? 1 : 0;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return ItemsRegister.PLANT_FIBER;
   }

   @Override
   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return canStayAtPos(worldIn, pos);
   }

   public static boolean canStayAtPos(World worldIn, BlockPos pos) {
      Block blockd = worldIn.getBlockState(pos.down()).getBlock();
      return blockd == BlocksRegister.TOXIC_DIRT
         || blockd == BlocksRegister.TOXIC_GRASS
         || blockd == BlocksRegister.SLUDGE
         || blockd == BlocksRegister.JUNK
         || blockd == BlocksRegister.NUCLEAR_WASTE;
   }

   @Override
   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!canStayAtPos(worldIn, pos)) {
         worldIn.destroyBlock(pos, true);
      }
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
      return NonNullList.withSize(1, new ItemStack(this, 1));
   }

   @Override
   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
      return true;
   }
}
