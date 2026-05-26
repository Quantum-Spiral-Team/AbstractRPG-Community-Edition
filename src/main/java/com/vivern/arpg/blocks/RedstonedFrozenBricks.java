package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RedstonedFrozenBricks extends Block implements IBlockHardBreak {
   public RedstonedFrozenBricks() {
      super(Material.ROCK);
      this.setRegistryName("crackable_frozen_bricks");
      this.setTranslationKey("crackable_frozen_bricks");
      this.blockHardness = BlocksRegister.HR_FROZEN_BRICK.hardness;
      this.blockResistance = BlocksRegister.HR_FROZEN_BRICK.resistance;
      this.setHarvestLevel("pickaxe", BlocksRegister.HR_FROZEN_BRICK.lvl);
      this.setCreativeTab(CreativeTabs.REDSTONE);
   }

   @Override
   public BlocksRegister.HardRes getHardRes() {
      return BlocksRegister.HR_FROZEN_BRICK;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      Block blockk = worldIn.getBlockState(pos.down()).getBlock();
      if (worldIn.isBlockPowered(pos.down()) && blockk != BlocksRegister.REDSTONED_FROZEN_BRICKS && blockk != BlocksRegister.BLOCK_BLOCK_HARD) {
         this.crackBlock(worldIn, pos, EnumFacing.DOWN);
      }
   }

   public void crackBlock(World worldIn, BlockPos pos, EnumFacing from) {
      worldIn.setBlockToAir(pos);
      worldIn.playSound(
         null,
         pos.getX(),
         pos.getY(),
         pos.getZ(),
         worldIn.rand.nextFloat() < 0.6 ? SoundEvents.BLOCK_STONE_BREAK : SoundEvents.BLOCK_GRAVEL_BREAK,
         SoundCategory.BLOCKS,
         1.0F,
         0.85F + worldIn.rand.nextFloat() / 4.0F
      );
      if (worldIn instanceof WorldServer) {
         ((WorldServer)worldIn)
            .spawnParticle(
               EnumParticleTypes.BLOCK_DUST,
               pos.getX(),
               pos.getY(),
               pos.getZ(),
               15,
               -0.5,
               -0.5,
               -0.5,
               0.08,
               new int[]{Block.getStateId(BlocksRegister.REDSTONED_FROZEN_BRICKS.getDefaultState())}
            );
      }

      for (EnumFacing f : EnumFacing.VALUES) {
         if (f != from) {
            BlockPos pos2 = pos.offset(f);
            if (worldIn.getBlockState(pos2).getBlock() == BlocksRegister.REDSTONED_FROZEN_BRICKS) {
               this.crackBlock(worldIn, pos2, f.getOpposite());
            }
         }
      }
   }
}
