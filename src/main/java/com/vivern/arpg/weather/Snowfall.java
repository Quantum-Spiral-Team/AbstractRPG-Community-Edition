package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class Snowfall extends WorldEvent {

    public final Random rand = new Random();

    public Snowfall(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
    }

    @Override
    public boolean canOverlapWith(WorldEvent other) {
        return false;
    }

    @Override
    public int getCooldown() {
        return 5000 + this.worldProvider.getWorld().rand.nextInt(5000);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % 10 == 0) {
            this.updateAllLoaded(this.rand, 0.01F, 1);
        }
    }

    @Override
    public void updateLoadedXZ(World world, int posX, int posZ) {
        BlockPos pos = new BlockPos(posX, 0, posZ);
        pos = world.getPrecipitationHeight(pos);
        if (Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos) && world.isAirBlock(pos)) {
            world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState(), 2);
        }
    }

}