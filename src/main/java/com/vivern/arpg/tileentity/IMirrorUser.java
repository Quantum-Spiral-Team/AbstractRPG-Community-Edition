package com.vivern.arpg.tileentity;

import com.vivern.arpg.recipes.Seal;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public interface IMirrorUser {

    ArrayList<BlockPos> getMirrors();

    boolean hasSeals(HashMap<Seal, Integer> var1);

    void spendSeals();

    void tryAddMirrorPos(BlockPos var1);

}
