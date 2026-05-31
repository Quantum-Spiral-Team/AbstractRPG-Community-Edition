package com.vivern.arpg.main;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public enum EnumSurroundings {
    DOWN(new Vec3i(0, -1, 0)),
    UP(new Vec3i(0, 1, 0)),
    NORTH(new Vec3i(0, 0, -1)),
    SOUTH(new Vec3i(0, 0, 1)),
    WEST(new Vec3i(-1, 0, 0)),
    EAST(new Vec3i(1, 0, 0)),
    NORTH_DOWN(new Vec3i(0, -1, -1)),
    SOUTH_DOWN(new Vec3i(0, -1, 1)),
    WEST_DOWN(new Vec3i(-1, -1, 0)),
    EAST_DOWN(new Vec3i(1, -1, 0)),
    NORTH_UP(new Vec3i(0, 1, -1)),
    SOUTH_UP(new Vec3i(0, 1, 1)),
    WEST_UP(new Vec3i(-1, 1, 0)),
    EAST_UP(new Vec3i(1, 1, 0)),
    NORTH_EAST(new Vec3i(1, 0, -1)),
    SOUTH_EAST(new Vec3i(1, 0, 1)),
    NORTH_WEST(new Vec3i(-1, 0, -1)),
    SOUTH_WEST(new Vec3i(-1, 0, 1)),
    NORTH_EAST_UP(new Vec3i(1, 1, -1)),
    SOUTH_EAST_UP(new Vec3i(1, 1, 1)),
    NORTH_WEST_UP(new Vec3i(-1, 1, -1)),
    SOUTH_WEST_UP(new Vec3i(-1, 1, 1)),
    NORTH_EAST_DOWN(new Vec3i(1, -1, -1)),
    SOUTH_EAST_DOWN(new Vec3i(1, -1, 1)),
    NORTH_WEST_DOWN(new Vec3i(-1, -1, -1)),
    SOUTH_WEST_DOWN(new Vec3i(-1, -1, 1));

    private final Vec3i directionVec;

    EnumSurroundings(Vec3i directionVecIn) {
        this.directionVec = directionVecIn;
    }

    public BlockPos offset(BlockPos pos) {
        return pos.add(this.directionVec);
    }
}
