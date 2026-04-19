package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.tileentity.TileEtheriteInvocator;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEtheriteInvocator extends Block implements IBlockHardBreak {
   public BlockEtheriteInvocator() {
      super(Material.ROCK);
      this.setRegistryName("block_etherite_invocator");
      this.setTranslationKey("block_etherite_invocator");
      this.blockHardness = BlocksRegister.HR_ZARPION_ROCKS.hardness;
      this.blockResistance = BlocksRegister.HR_ZARPION_ROCKS.resistance;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_ZARPION_ROCKS.lvl);
      this.setCreativeTab(CreativeTabs.MISC);
   }

   @Override
   public BlocksRegister.HardRes getHardRes() {
      return BlocksRegister.HR_ZARPION_ROCKS;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public TileEtheriteInvocator getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileEtheriteInvocator)world.getTileEntity(position);
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (player.getHeldItemMainhand().getItem() == ItemsRegister.ETHERITE_FUEL_CELL) {
         TileEtheriteInvocator tile = this.getTileEntity(world, pos);
         if (tile.setCell()) {
            player.getHeldItemMainhand().shrink(1);
         }
      }

      return true;
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileEtheriteInvocator createTileEntity(World world, IBlockState blockState) {
      return new TileEtheriteInvocator();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
