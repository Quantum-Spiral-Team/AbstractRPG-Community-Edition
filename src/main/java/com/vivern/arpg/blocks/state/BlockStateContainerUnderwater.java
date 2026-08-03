package com.vivern.arpg.blocks.state;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BlockStateContainerUnderwater extends BlockStateContainer {

    public BlockStateContainerUnderwater(Block blockIn, IProperty<?>... properties) {
        super(blockIn, properties);
    }

    protected BlockStateContainerUnderwater(Block blockIn, IProperty<?>[] properties,
                                            ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
        super(blockIn, properties, unlistedProperties);
    }

    @Override
    protected StateImplementation createState(Block block,
                                              ImmutableMap<IProperty<?>, Comparable<?>> properties,
                                              @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
        return new StateImplementationUnderwater(block, properties);
    }

    public static class StateImplementationUnderwater extends StateImplementation {

        protected StateImplementationUnderwater(Block blockIn,
                                                ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn) {
            super(blockIn, propertiesIn);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Comparable<T>> T getValue(IProperty<T> property) {
            if ("level".equals(property.getName())) {
                return (T) Blocks.WATER.getDefaultState().getValue(BlockLiquid.LEVEL);
            }
            return super.getValue(property);
        }
    }
}