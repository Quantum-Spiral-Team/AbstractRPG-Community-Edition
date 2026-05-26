package com.vivern.arpg.dimensions.mortuorus;

import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.DimensionsRegister;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionMortuorus extends WorldProvider {
   @Override
   public DimensionType getDimensionType() {
      return DimensionsRegister.MORTUORUS;
   }

   @Override
   public IChunkGenerator createChunkGenerator() {
      return new MortuorusChunkGenerator(this.world, this.world.getSeed());
   }

   @Override
   protected void init() {
      this.hasSkyLight = true;
      this.biomeProvider = new BiomeProviderSingle(BiomesRegister.DEAD_LANDS);
   }

   @Override
   public boolean canRespawnHere() {
      return true;
   }
}
