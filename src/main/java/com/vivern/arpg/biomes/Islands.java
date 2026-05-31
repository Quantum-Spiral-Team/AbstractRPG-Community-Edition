package com.vivern.arpg.biomes;

import com.vivern.arpg.dimensions.aquatica.AquaticaChunkGenerator;
import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class Islands extends Biome {

    public Islands() {
        super(new BiomeProperties("Islands").setBaseHeight(5.75F).setHeightVariation(0.08F).setTemperature(0.95F).setWaterColor(11921407).setRainfall(0.0F).setRainDisabled());
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = BlocksRegister.CHALKROCK.getDefaultState();
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
