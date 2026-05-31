package com.vivern.arpg.dimensions.generationutils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSkyPlatforms extends WorldGenAdvanced {

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return false;
    }

}
