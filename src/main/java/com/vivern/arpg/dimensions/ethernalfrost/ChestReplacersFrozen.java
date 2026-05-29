package com.vivern.arpg.dimensions.ethernalfrost;

import com.vivern.arpg.blocks.PresentBox;
import com.vivern.arpg.blocks.StarLantern;
import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.loot.ListLootTable;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.tileentity.EnumChest;
import com.vivern.arpg.tileentity.TilePresentBox;
import com.vivern.arpg.tileentity.TilePuzzle;
import com.vivern.arpg.tileentity.TileStarLantern;
import java.util.ArrayList;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class ChestReplacersFrozen {
   public static ArrayList<BlockPos> posesToSetPuzzle = new ArrayList<>();
   public static ITemplateProcessor replacerMound = (world, pos, blockInfoIn) -> {
      if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
         GenerationHelper.setChestWithLoot(
            world, pos, EnumChest.FROZEN, ListLootTable.CHESTS_MOUND, blockInfoIn.blockState.getValue(BlockChest.FACING)
         );
         if (world.rand.nextFloat() < 0.3) {
            ChestReplacersFrozen.posesToSetPuzzle.add(pos);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
         world.setBlockState(pos, BlocksRegister.FROZEN_SPAWNER.getDefaultState());
         DimensionEthernalFrost.setupRandomSpawner(world, world.getTileEntity(pos), DimensionEthernalFrost.EnumEverfrostSpawner.MOUND, world.rand);
         return null;
      } else if (blockInfoIn.blockState.getBlock() == BlocksRegister.PUZZLE) {
         world.setBlockState(pos, BlocksRegister.PUZZLE.getDefaultState());
         TilePuzzle puzzle = (TilePuzzle)world.getTileEntity(pos);
         puzzle.setupPuzzle(5);
         return null;
      } else {
         return blockInfoIn;
      }
   };
   public static ITemplateProcessor replacerIceCastle = (world, pos, blockInfoIn) -> {
      if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
         if (world.rand.nextFloat() < 0.6) {
            world.setBlockToAir(pos);
         } else {
            IceCastle.placeSpawner(world, pos, false, world.rand);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
         if (world.rand.nextFloat() < 0.25) {
            world.setBlockToAir(pos);
         } else {
            IceCastle.placeChest(world, pos, world.rand, blockInfoIn.blockState, false);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.TRAPPED_CHEST) {
         if (world.rand.nextFloat() < 0.05) {
            world.setBlockToAir(pos);
         } else {
            IceCastle.placeChest(world, pos, world.rand, blockInfoIn.blockState, true);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_PILLAR) {
         if (world.rand.nextFloat() < 0.05) {
            world.setBlockToAir(pos);
         } else {
            IceCastle.placeSpawner(world, pos, false, world.rand);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_STAIRS) {
         IceCastle.placeSpawner(world, pos, true, world.rand);
         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.DIAMOND_ORE) {
         if (world.rand.nextFloat() < 0.15) {
            world.setBlockToAir(pos);
         } else if (world.rand.nextFloat() < 0.35) {
            IceCastle.placeChest(world, pos, world.rand, null, world.rand.nextFloat() < 0.15);
         } else {
            IceCastle.placeSpawner(world, pos, false, world.rand);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.GOLD_ORE) {
         if (world.rand.nextFloat() < 0.3) {
            world.setBlockToAir(pos);
         } else if (world.rand.nextFloat() < 0.5) {
            IceCastle.placeChest(world, pos, world.rand, null, world.rand.nextFloat() < 0.25);
         } else {
            IceCastle.placeSpawner(world, pos, false, world.rand);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.IRON_ORE) {
         if (world.rand.nextFloat() < 0.7) {
            world.setBlockToAir(pos);
         } else if (world.rand.nextFloat() < 0.35) {
            IceCastle.placeChest(world, pos, world.rand, null, false);
         } else {
            IceCastle.placeSpawner(world, pos, false, world.rand);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_SLAB) {
         if (world.rand.nextFloat() < 0.35) {
            world.setBlockToAir(pos);
         } else if (world.rand.nextFloat() < 0.3) {
            world.setBlockState(pos, BlocksRegister.FROZEN_TREASURE_BARREL.getDefaultState());
         } else if (world.rand.nextFloat() < 0.5) {
            world.setBlockState(pos, BlocksRegister.ICE_SPIKES.getDefaultState());
         } else {
            world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState());
         }

         return null;
      } else {
         return blockInfoIn;
      }
   };
   public static ITemplateProcessor replacerStructures = (world, pos, blockInfoIn) -> {
      if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_SLAB) {
         if (world.rand.nextFloat() < 0.35) {
            world.setBlockToAir(pos);
         } else if (world.rand.nextFloat() < 0.3) {
            world.setBlockState(pos, BlocksRegister.FROZEN_TREASURE_BARREL.getDefaultState());
         } else if (world.rand.nextFloat() < 0.5) {
            world.setBlockState(pos, BlocksRegister.ICE_SPIKES.getDefaultState());
         } else {
            world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState());
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
         GenerationHelper.setChestWithLoot(
            world,
            pos,
            EnumChest.FROZEN,
            ListLootTable.CHESTS_FROZEN_STRUCTURES,
                 blockInfoIn.blockState.getValue(BlockChest.FACING)
         );
         if (world.rand.nextFloat() < 0.2) {
            ChestReplacersFrozen.posesToSetPuzzle.add(pos);
         }

         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
         world.setBlockState(pos, BlocksRegister.FROZEN_SPAWNER.getDefaultState());
         DimensionEthernalFrost.setupRandomSpawner(
            world, world.getTileEntity(pos), DimensionEthernalFrost.EnumEverfrostSpawner.STRUCTURES, world.rand
         );
         return null;
      } else if (blockInfoIn.blockState.getBlock() == BlocksRegister.PUZZLE) {
         world.setBlockState(pos, BlocksRegister.PUZZLE.getDefaultState());
         TilePuzzle puzzle = (TilePuzzle)world.getTileEntity(pos);
         puzzle.setupPuzzle(5);
         return null;
      } else {
         return blockInfoIn;
      }
   };
   public static ITemplateProcessor replacerGrave = (world, pos, blockInfoIn) -> {
      if (blockInfoIn.blockState.getBlock() == Blocks.CHEST) {
         GenerationHelper.setChestWithLoot(
            world, pos, EnumChest.FROZEN, ListLootTable.CHESTS_FROZEN_GRAVE, blockInfoIn.blockState.getValue(BlockChest.FACING)
         );
         return null;
      } else if (blockInfoIn.blockState.getBlock() == Blocks.PURPUR_BLOCK) {
         world.setBlockState(pos, BlocksRegister.FROZEN_SPAWNER.getDefaultState());
         DimensionEthernalFrost.setupRandomSpawner(world, world.getTileEntity(pos), DimensionEthernalFrost.EnumEverfrostSpawner.GRAVE, world.rand);
         return null;
      } else {
         return blockInfoIn;
      }
   };
   public static PresentBoxReplacer replacerPresentBox = new PresentBoxReplacer();
   public static IBlockState replacerPresentBoxLampState = BlocksRegister.RUBY_LAMP.getDefaultState();
   public static IBlockState replacerPresentBoxLanternState = BlocksRegister.RUBY_LANTERN.getDefaultState();

   public static class PresentBoxReplacer implements ITemplateProcessor {
      @Override
      public BlockInfo processBlock(World world, BlockPos pos, BlockInfo blockInfoIn) {
         if (blockInfoIn.blockState.getBlock() == BlocksRegister.CITRINE_LANTERN) {
            world.setBlockState(pos, ChestReplacersFrozen.replacerPresentBoxLanternState);
            return null;
         } else if (blockInfoIn.blockState.getBlock() == BlocksRegister.CITRINE_LAMP) {
            world.setBlockState(pos, ChestReplacersFrozen.replacerPresentBoxLampState);
            return null;
         } else if (blockInfoIn.blockState.getBlock() == Blocks.MOSSY_COBBLESTONE) {
            if (world.rand.nextFloat() < 0.7) {
               IBlockState state = BlocksRegister.PRESENT_BOX.getDefaultState().withProperty(PresentBox.TEXTYPE, world.rand.nextInt(6));
               world.setBlockState(pos, state);
               TileEntity tileentity = world.getTileEntity(pos);
               if (tileentity instanceof TilePresentBox) {
                  ((TilePresentBox)tileentity).setLootTable(ListLootTable.PRESENT_BOX, world.rand.nextLong());
               }
            } else {
               world.setBlockToAir(pos);
            }

            return null;
         } else if (blockInfoIn.blockState.getBlock() != BlocksRegister.STAR_LANTERN) {
            return blockInfoIn;
         } else {
            IBlockState state = BlocksRegister.STAR_LANTERN.getDefaultState();
            world.setBlockState(pos, state);
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileStarLantern) {
               float r = world.rand.nextFloat();
               float g = world.rand.nextFloat();
               float b = world.rand.nextFloat();
               if (r > g && r > b) {
                  if (world.rand.nextFloat() < 0.5) {
                     if (world.rand.nextFloat() > g) {
                        g = world.rand.nextFloat();
                     }

                     if (world.rand.nextFloat() > g) {
                        g = world.rand.nextFloat();
                     }

                     b /= 2.0F;
                  } else {
                     if (world.rand.nextFloat() > b) {
                        b = world.rand.nextFloat();
                     }

                     if (world.rand.nextFloat() > b) {
                        b = world.rand.nextFloat();
                     }

                     g /= 2.0F;
                  }
               } else if (g > r && g > b) {
                  if (world.rand.nextFloat() < 0.5) {
                     if (world.rand.nextFloat() > r) {
                        r = world.rand.nextFloat();
                     }

                     if (world.rand.nextFloat() > r) {
                        r = world.rand.nextFloat();
                     }

                     b /= 2.0F;
                  } else {
                     if (world.rand.nextFloat() > b) {
                        b = world.rand.nextFloat();
                     }

                     if (world.rand.nextFloat() > b) {
                        b = world.rand.nextFloat();
                     }

                     r /= 2.0F;
                  }
               } else if (b > g && b > r) {
                  if (world.rand.nextFloat() < 0.5) {
                     if (world.rand.nextFloat() > g) {
                        g = world.rand.nextFloat();
                     }

                     if (world.rand.nextFloat() > g) {
                        g = world.rand.nextFloat();
                     }

                     r /= 2.0F;
                  } else {
                     if (world.rand.nextFloat() > r) {
                        r = world.rand.nextFloat();
                     }

                     if (world.rand.nextFloat() > r) {
                        r = world.rand.nextFloat();
                     }

                     g /= 2.0F;
                  }
               }

               ((TileStarLantern)tileentity).red = r;
               ((TileStarLantern)tileentity).green = g;
               ((TileStarLantern)tileentity).blue = b;
               StarLantern.trySendPacketUpdate(world, pos, (TileStarLantern)tileentity);
            }

            return null;
         }
      }
   }
}
