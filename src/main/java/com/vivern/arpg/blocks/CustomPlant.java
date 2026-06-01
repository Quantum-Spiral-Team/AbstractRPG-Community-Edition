package com.vivern.arpg.blocks;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.items.CustomPlantSeed;
import com.vivern.arpg.items.CustomPlantSeedEatable;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.CreateItemFile;
import com.vivern.arpg.main.ItemsRegister;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class CustomPlant extends Block implements IGrowable, IShearable {

    public static final PropertyBool GROWED = PropertyBool.create("growed");
    public static final AxisAlignedBB CP_AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.8, 0.8);
    public static final AxisAlignedBB CP_SMALL_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.3, 0.75);
    public static final AxisAlignedBB CP_DOUBLE_AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 1.2, 0.8);
    public final AxisAlignedBB COLLISION_AABB;
    public final Block[] groundBlocks;
    public final boolean canUseBoneMeal;
    public final String drops;
    public int minLightForGrow;
    public int maxLightForGrow;
    public float growChance;
    public Item seed;
    public int modelType;
    public int seedRadiation = 0;

    public CustomPlant(String name,
                       float hardnessResistance,
                       float lightLvl,
                       SoundType soundType,
                       AxisAlignedBB collisionAabb,
                       Block[] groundBlocks,
                       boolean canUseBoneMeal,
                       String drops,
                       int minLightForGrow,
                       int maxLightForGrow,
                       float growChance,
                       int modelType
    ) {
        super(Material.PLANTS);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.blockHardness = hardnessResistance;
        this.blockResistance = hardnessResistance;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setLightLevel(lightLvl);
        this.setSoundType(soundType);
        this.COLLISION_AABB = collisionAabb;
        this.groundBlocks = groundBlocks;
        this.setTickRandomly(true);
        this.canUseBoneMeal = canUseBoneMeal;
        this.drops = drops;
        this.minLightForGrow = minLightForGrow;
        this.maxLightForGrow = maxLightForGrow;
        this.growChance = growChance;
        this.modelType = modelType;
    }

    public static CustomPlant createCustomPlant(String name, float hardnessResistance, float lightLvl, SoundType soundType, AxisAlignedBB collisionAabb, Block[] groundBlocks, boolean canUseBoneMeal, String drops, int minLightForGrow, int maxLightForGrow, float growChance, int seedEatable, Potion potion, int dur, int amp, float effectChance, int modelType) {
        CustomPlant plant = new CustomPlant(name, hardnessResistance, lightLvl, soundType, collisionAabb, groundBlocks, canUseBoneMeal, drops, minLightForGrow, maxLightForGrow, growChance, modelType);
        Item seed = seedEatable > 0 ? new CustomPlantSeedEatable(plant, seedEatable, potion, dur, amp, effectChance) : new CustomPlantSeed(plant);
        plant.seed = seed;
//        CreateItemFile.customPlantResLocationCreate(plant, modelType); DEBUG method
        BlocksRegister.FOR_RENDER.add(plant);
        ItemsRegister.FOR_RENDER.add(seed);
        return plant;
    }

    public CustomPlant setFuelToSeed(int time) {
        if (this.seed instanceof CustomPlantSeed) {
            ((CustomPlantSeed) this.seed).burntime = time;
        } else if (this.seed instanceof CustomPlantSeedEatable) {
            ((CustomPlantSeedEatable) this.seed).burntime = time;
        }

        return this;
    }

    public CustomPlant setSeedRadioactive(int rad) {
        this.seedRadiation = rad;
        return this;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (rand.nextFloat() < this.growChance) {
            int light = Math.max(worldIn.getLightFor(EnumSkyBlock.BLOCK, pos), worldIn.getLightFor(EnumSkyBlock.SKY, pos));
            if (light <= this.maxLightForGrow && light >= this.minLightForGrow) {
                this.grow(worldIn, rand, pos, state);
            }
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return this.drops.isEmpty() ? 1 : 0;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getValue(GROWED);
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XYZ;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> lastDrops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(lastDrops, world, pos, state, fortune);
        if (state.getValue(GROWED)) {
            String[] lootTypes = this.drops.split(" ");

            for (String lootType : lootTypes) {
                String[] words = lootType.split(",");
                Item item = Item.getByNameOrId(words[0]);
                int minCount = Integer.parseInt(words[1]);
                int maxCount = Integer.parseInt(words[2]);
                int meta = Integer.parseInt(words[3]);
                if (item == null) {
                    AbstractRPG.LOGGER.warn("[{}] Item with name ot ID {} not found!", CustomPlant.class.getSimpleName(), words[0]);
                } else {
                    lastDrops.add(new ItemStack(item, minCount + RANDOM.nextInt(maxCount - minCount + 1), meta));
                }
            }
        } else if (RANDOM.nextFloat() < 0.7) {
            lastDrops.add(new ItemStack(this.seed, 1));
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return !(Boolean) state.getValue(GROWED);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return this.canUseBoneMeal;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos, state.withProperty(GROWED, true), 2);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos)) {
            this.dropBlock(worldIn, pos, state);
        }
    }

    private void dropBlock(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        this.dropBlockAsItem(worldIn, pos, state, 0);
    }

    public boolean canBlockStay(World worldIn, BlockPos pos) {
        Block blockd = worldIn.getBlockState(pos.down()).getBlock();

        for (Block block : this.groundBlocks) {
            if (blockd == block) {
                return true;
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
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return this.COLLISION_AABB != null ? this.COLLISION_AABB : Block.FULL_BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (!state.getValue(GROWED)) {
            return CP_SMALL_AABB;
        } else if (this.modelType == 1) {
            return CP_AABB;
        } else if (this.modelType == 2) {
            return CP_DOUBLE_AABB;
        } else {
            return this.modelType == 3 ? CP_AABB : Block.FULL_BLOCK_AABB;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(GROWED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(GROWED) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROWED);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(GROWED, true);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1, 0));
    }

}
