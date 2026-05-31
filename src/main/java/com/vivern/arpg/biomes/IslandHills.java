package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class IslandHills extends Biome {

    public IslandHills() {
        super(new BiomeProperties("Island hills").setBaseHeight(7.0F).setHeightVariation(0.15F).setTemperature(0.95F).setWaterColor(11921407).setRainfall(0.0F).setRainDisabled());
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.SANDSTONE.getDefaultState();
        this.decorator = new IslandsDecorator();
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
        AquaticaChunkGenerator.generateBiomeTerrain(this, worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return 11001929;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return 11001929;
    }

}
