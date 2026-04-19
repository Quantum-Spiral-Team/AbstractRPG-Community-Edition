package com.vivern.arpg.biomes;

import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class FrozenMountain extends Biome {
   public FrozenMountain(float height) {
      super(new BiomeProperties("Frozen mountain").setBaseHeight(height).setHeightVariation(0.2F).setTemperature(-1.1F).setWaterColor(10804223));
      this.topBlock = Blocks.SNOW.getDefaultState();
      this.fillerBlock = BlocksRegister.SNOW_ICE.getDefaultState();
      this.decorator = new FrozenMountainDecorator();
   }
}
