package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.entity.BigLightningStrike;
import com.vivern.arpg.main.GetMOP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class Storm extends WorldEvent {

    public Random rand = new Random();
    static List<int[]> lightningPosesList = new ArrayList<>();

    public Storm(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
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
        if (this.ticksExisted % 80 == 0) {
            this.updateAllLoaded(this.rand, 0.005F, 1);
        }
    }

    @Override
    public void updateLoadedXZ(World world, int posX, int posZ) {
        if (this.rand.nextFloat() < 0.005) {
            this.spawnLightning(world, posX, posZ);
        }
    }

    public void spawnLightning(World world, int posX, int posZ) {
        if (world.isAnyPlayerWithinRangeAt(posX, 256.0, posZ, 256.0)) {
            if (lightningPosesList.isEmpty()) {
                for (int xx = -3; xx <= 3; xx++) {
                    for (int zz = -3; zz <= 3; zz++) {
                        lightningPosesList.add(new int[]{xx, zz});
                    }
                }
            }

            Collections.shuffle(lightningPosesList);
            GetMOP.BlockTraceResult result = null;

            for (int[] ints : lightningPosesList) {
                result = GetMOP.blockTrace(world, new BlockPos(posX, 256, posZ), EnumFacing.DOWN, 180, null);
                if (result != null) {
                    break;
                }
            }

//            if (result == null) {
//                new Vec3d(posX + 0.5 + this.rand.nextGaussian() * 2.0, 76 + this.rand.nextInt(100), posZ + 0.5 + this.rand.nextGaussian() * 2.0);
//            } else {
//                new Vec3d(result.pos.getX() + 0.5, result.pos.getY() + 0.5, result.pos.getZ() + 0.5);
//            }

            BigLightningStrike bolt = new BigLightningStrike(world);
            bolt.setPosition(posX, 254.0, posZ);
            bolt.setSegmentParams(16.0F, 2.0F);
            world.spawnEntity(bolt);
        }
    }

}
