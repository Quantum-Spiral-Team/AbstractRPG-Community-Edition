package com.vivern.arpg.biomes;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class FrozenMeadow extends Biome {

    public FrozenMeadow() {
        super(new BiomeProperties("Frozen meadow").setBaseHeight(0.46F).setHeightVariation(0.15F).setTemperature(-0.3F).setWaterColor(10804223));
        this.topBlock = Blocks.SNOW.getDefaultState();
        this.fillerBlock = BlocksRegister.SNOW_ICE.getDefaultState();
        this.decorator = new FrozenMeadowDecorator();
    }

}
