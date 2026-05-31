package com.vivern.arpg.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IWrenchUser {

    void onUseWrench(World var1, Entity var2, ItemStack var3, float var4, @Nullable BlockPos var5);

}
