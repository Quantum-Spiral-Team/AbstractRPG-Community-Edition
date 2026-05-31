package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class FrozenCobblestone extends BlockBlockHard {

    public FrozenCobblestone() {
        super(Material.ROCK, "frozen_cobblestone", BlocksRegister.HR_FROZEN_COBBLESTONE.hardness, BlocksRegister.HR_FROZEN_COBBLESTONE.resistance, BlocksRegister.HR_FROZEN_COBBLESTONE.slow, BlocksRegister.HR_FROZEN_COBBLESTONE.fast, BlocksRegister.HR_FROZEN_COBBLESTONE.lvl, "pickaxe", true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }

}
