package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.main.ColorConverters;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import java.util.Optional;
import java.util.Random;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer.StateImplementation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUnderwater extends Block {
   public static final PropertyBool WET = PropertyBool.create("wet");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

   public BlockUnderwater(Material materialIn) {
      super(materialIn);
   }

   @Override
   public Material getMaterial(IBlockState state) {
      return state.getValue(WET) ? Material.WATER : Material.ROCK;
   }

   @Override
   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!isInWater(world, pos)) {
         world.setBlockState(pos, state.withProperty(WET, false));
      } else {
         world.setBlockState(pos, state.withProperty(WET, true));
      }

      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public static boolean isInWater(IBlockAccess worldIn, BlockPos pos) {
      for (EnumFacing facing : EnumFacing.values()) {
         IBlockState state = worldIn.getBlockState(pos.offset(facing));
         if (!(state.getMaterial() == Material.WATER || state.isOpaqueCube())) {
            return false;
         }
      }
      return true;
   }

   public static boolean isAroundWater(IBlockAccess world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && (Integer)state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
            if (++count >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      return isAroundWater(world, pos)
         ? world.setBlockState(pos, Blocks.WATER.getDefaultState(), world.isRemote ? 10 : 2)
         : world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 10 : 2);
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
   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return super.getActualState(state, worldIn, pos).withProperty(LEVEL, 0).withProperty(WET, isInWater(worldIn, pos));
   }

   @Override
   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, isInWater(worldIn, pos));
   }

   @SideOnly(Side.CLIENT)
   @Override
   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }

   public static class BlockBlockUnderwater extends BlockUnderwater {
      public boolean replaceableOreGen = true;
      public String dropped = null;
      public BlockRenderLayer layer = BlockRenderLayer.SOLID;
      public boolean opaque = true;
      public boolean fullcub = true;
      public boolean fullbloc = true;
      public AxisAlignedBB aabbSEL = FULL_BLOCK_AABB;
      public AxisAlignedBB aabbCOL = FULL_BLOCK_AABB;
      public EnumOffsetType offsets = EnumOffsetType.NONE;
      public boolean vasePlace = false;
      public int packedLightmapCoords = -1;
      public float blockSlipperiness = 0.6F;

      public BlockBlockUnderwater(Material mater, String name, float hard, float resi) {
         super(mater);
         this.setRegistryName(name);
         this.setTranslationKey(name);
         this.blockHardness = hard;
         this.blockResistance = resi;
         this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
         this.setLightOpacity(255);
      }

      @Override
      protected BlockStateContainer createBlockState() {
         return new BlockStateContainer(this, new IProperty[]{LEVEL, WET});
      }

      @Override
      public IBlockState getStateFromMeta(int meta) {
         return this.getDefaultState();
      }

      @Override
      public int getMetaFromState(IBlockState state) {
         return 0;
      }

      public BlockBlockUnderwater setSlipperiness(float blockSlipperiness) {
         this.blockSlipperiness = blockSlipperiness;
         return this;
      }

      @Override
      public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
         return this.blockSlipperiness;
      }

      @SideOnly(Side.CLIENT)
      @Override
      public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
         return this.packedLightmapCoords == -1 ? super.getPackedLightmapCoords(state, source, pos) : this.packedLightmapCoords;
      }

      public BlockBlockUnderwater setLightOptions(int lightValue, int lightmapCoords) {
         this.lightValue = lightValue;
         this.packedLightmapCoords = ColorConverters.RGBAtoDecimal255(lightmapCoords, 0, lightmapCoords, 0);
         return this;
      }

      @SideOnly(Side.CLIENT)
      @Override
      public BlockRenderLayer getRenderLayer() {
         return this.layer;
      }

      public BlockBlockUnderwater setOffsets(EnumOffsetType offsets) {
         this.offsets = offsets;
         return this;
      }

      public BlockBlockUnderwater setPlaceAsVase(boolean b) {
         this.vasePlace = b;
         return this;
      }

      public BlockBlockUnderwater setOpacity(int i) {
         this.setLightOpacity(i);
         return this;
      }

      @Override
      public EnumOffsetType getOffsetType() {
         return this.offsets;
      }

      @Override
      public boolean isOpaqueCube(IBlockState state) {
         return this.opaque;
      }

      public BlockBlockUnderwater setOpaque(boolean b) {
         this.opaque = b;
         return this;
      }

      public BlockBlockUnderwater setFullCube(boolean b) {
         this.fullcub = b;
         return this;
      }

      public BlockBlockUnderwater setAABB(AxisAlignedBB aabbSELECT, AxisAlignedBB aabbCOLLIDE) {
         this.aabbSEL = aabbSELECT;
         this.aabbCOL = aabbCOLLIDE;
         this.fullbloc = false;
         return this;
      }

      public BlockBlockUnderwater setAABB(AxisAlignedBB aabb) {
         this.aabbSEL = aabb;
         this.aabbCOL = aabb;
         this.fullbloc = false;
         return this;
      }

      @Override
      public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
         return this.aabbSEL;
      }

      @Override
      public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
         return this.aabbCOL;
      }

      @SideOnly(Side.CLIENT)
      public BlockBlockUnderwater setRenderLayer(BlockRenderLayer render) {
         this.layer = render;
         return this;
      }

      public BlockBlockUnderwater setSound(SoundType sound) {
         this.setSoundType(sound);
         return this;
      }

      public BlockBlockUnderwater setIsReplaceableOreGen(boolean b) {
         this.replaceableOreGen = b;
         return this;
      }

      public BlockBlockUnderwater setHarvest(String tool, int lvl) {
         this.setHarvestLevel(tool, lvl);
         return this;
      }

      public BlockBlockUnderwater setItemDropped(String dropped) {
         this.dropped = dropped;
         return this;
      }

      @Override
      public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
         return (!this.vasePlace || !worldIn.isAirBlock(pos.down())) && super.canPlaceBlockAt(worldIn, pos);
      }

      @Override
      public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
         return this.replaceableOreGen;
      }

      @Override
      public Item getItemDropped(IBlockState state, Random rand, int fortune) {
         if (this.dropped != null) {
            Item dr = Item.getByNameOrId(this.dropped);
            return dr != null ? dr : Items.AIR;
         } else {
            return Item.getItemFromBlock(this);
         }
      }

      @Override
      public boolean isFullCube(IBlockState state) {
         return this.fullcub;
      }

      @Override
      public boolean isFullBlock(IBlockState state) {
         return this.fullbloc;
      }
   }

   public static class BlockStateContainerUnderwater extends BlockStateContainer {
      public BlockStateContainerUnderwater(Block blockIn, IProperty<?>[] properties) {
         super(blockIn, properties);
      }

      protected BlockStateContainerUnderwater(Block blockIn, IProperty<?>[] properties, ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
         super(blockIn, properties, unlistedProperties);
      }

      @Override
      protected StateImplementation createState(
         Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties
      ) {
         return new StateImplementationUnderwater(block, properties);
      }
   }

   public static class StateImplementationUnderwater extends StateImplementation {
      protected StateImplementationUnderwater(Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn) {
         super(blockIn, propertiesIn);
      }

      protected StateImplementationUnderwater(
         Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn, ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable
      ) {
         super(blockIn, propertiesIn, propertyValueTable);
      }

      @SuppressWarnings("unchecked")
      @Override
      public <T extends Comparable<T>> T getValue(IProperty<T> property) {
         return (T)("level".equals(property.getName())
            ? Blocks.WATER.getDefaultState().getValue(BlockLiquid.LEVEL)
            : super.getValue(property));
      }
   }
}
