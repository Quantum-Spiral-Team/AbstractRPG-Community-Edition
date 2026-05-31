package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.jetbrains.annotations.Nullable;

public abstract class WorldGenAdvanced extends WorldGenerator {

    private World world;
    private ChunkBuilder builder;

    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
        if (this.builder != null) {
            this.builder.setBlockState(pos, newState, flags);
        }

        return this.world != null && this.world.setBlockState(pos, newState, flags);
    }

    public boolean setBlockState(BlockPos pos, IBlockState state) {
        return this.setBlockState(pos, state, 2);
    }

    public IBlockState getBlockState(BlockPos pos) {
        if (this.builder != null) {
            return this.builder.getBlockState(pos);
        } else {
            return this.world != null ? this.world.getBlockState(pos) : null;
        }
    }

    public boolean isAirBlock(BlockPos pos) {
        if (this.builder != null) {
            return this.builder.isAirBlock(pos);
        } else {
            return this.world != null && this.world.isAirBlock(pos);
        }
    }

    public boolean isReplaceable(BlockPos pos) {
        if (this.builder != null) {
            return this.builder.getBlockState(pos).getBlock().isReplaceable(this.builder, pos);
        } else {
            return this.world != null && GenerationHelper.isReplaceable(this.world, pos);
        }
    }

    public WorldGenAdvanced setWorld(World world) {
        this.world = world;
        this.builder = null;
        return this;
    }

    public WorldGenAdvanced setChunkBuilder(ChunkBuilder builder) {
        this.builder = builder;
        this.world = null;
        return this;
    }

    public IBlockAccess getAccess() {
        return this.builder != null ? this.builder : this.world;
    }

    public long getWorldSeed() {
        return this.builder != null ? this.builder.worldSeed : this.world.getSeed();
    }

    @Nullable
    public ChunkBuilder getBuilder() {
        return this.builder;
    }

}
