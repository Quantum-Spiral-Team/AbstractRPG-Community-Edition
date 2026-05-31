package com.vivern.arpg.dimensions.ethernalfrost;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.mobs.SpawnerTuners;
import com.vivern.arpg.renders.RenderFrozenSky;
import com.vivern.arpg.tileentity.TileMonsterSpawner;
import com.vivern.arpg.weather.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.util.vector.Vector4f;

import java.util.Random;

public class DimensionEthernalFrost extends AbstractWorldProvider {

    public static TimeOfDayProvider timeOfDayProvider = new TimeOfDayProvider().addO(21700, 23000, new Vector4f(0.02745098F, 0.1254902F, 0.4117647F, 0.9882353F), new Vector4f(0.0F, 0.05490196F, 0.07058824F, 0.99215686F), new Vector4f(0.007843138F, 0.84705883F, 1.0F, 0.99607843F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.06666667F, 0.0627451F, 0.19607843F, 0.27058825F), new Vector4f(0.003921569F, 0.49411765F, 1.0F, 0.47843137F)).addO(23500, 25000, new Vector4f(0.011764706F, 0.21176471F, 0.6431373F, 0.0F), new Vector4f(0.7058824F, 1.0F, 1.0F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.6039216F, 0.6862745F, 0.77254903F, 0.16862746F), new Vector4f(0.8745098F, 0.9372549F, 1.0F, 0.5921569F)).addO(11500, 12500, new Vector4f(0.015686275F, 0.039215688F, 0.3254902F, 0.0F), new Vector4f(0.89411765F, 0.69411767F, 0.80784315F, 0.0F), new Vector4f(0.8235294F, 0.27058825F, 0.16078432F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.09411765F, 0.12941177F, 0.4117647F, 0.34509805F), new Vector4f(0.7137255F, 0.29411766F, 0.5137255F, 0.6862745F)).addO(12500, 13500, new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.08627451F, 0.14901961F, 0.2627451F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.0F, 0.0F, 0.0F, 0.0F), new Vector4f(0.0F, 0.05490196F, 0.105882354F, 0.0F), new Vector4f(0.21960784F, 0.28627452F, 0.4F, 0.21176471F), new Vector4f(0.07450981F, 0.105882354F, 0.17254902F, 0.015686275F)).cycleAll();
    public static RenderFrozenSky skyRender = new RenderFrozenSky(timeOfDayProvider);
    public Snowfall snowfall = new Snowfall(this, 0, 6000, 24000, 0.045F);
    public Hail hail = new Hail(this, 1, 8000, 12000, 0.035F);
    public Aurora aurora = new Aurora(this, 2, 8000, 12000, 0.025F);
    public WorldEventsHandler worldEventsHandler = new WorldEventsHandler(this, this.snowfall, this.hail, this.aurora);

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        timeOfDayProvider.setLightmapColors(6, this.getWorldTime(), partialTicks, sunBrightness, skyLight, blockLight, colors);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IRenderHandler getSkyRenderer() {
        return skyRender;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IRenderHandler getWeatherRenderer() {
        return this.worldEventsHandler;
    }

    @Override
    public void updateWeather() {
        this.worldEventsHandler.onUpdate();
    }

    @Override
    public WorldEventsHandler getWorldEventsHandler() {
        return this.worldEventsHandler;
    }

    @Override
    public TimeOfDayProvider getTimeOfDayProvider() {
        return timeOfDayProvider;
    }

    public static boolean isAuroraNow(World world) {
        if (world.provider instanceof AbstractWorldProvider) {
            AbstractWorldProvider provider = (AbstractWorldProvider) world.provider;
            if (provider.getWorldEventsHandler() != null) {
                for (WorldEvent worldEvent : provider.getWorldEventsHandler().events) {
                    if (worldEvent instanceof Aurora) {
                        return worldEvent.isStarted;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isSnowfallNow(World world) {
        if (world.provider instanceof AbstractWorldProvider) {
            AbstractWorldProvider worldProvider = (AbstractWorldProvider) world.provider;
            if (worldProvider.getWorldEventsHandler() != null) {
                for (WorldEvent worldEvent : worldProvider.getWorldEventsHandler().events) {
                    if (worldEvent instanceof Snowfall) {
                        return worldEvent.isStarted;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionsRegister.ETHERNAL_FROST;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new EthernalFrostChunkGenerator(this.world, this.world.getSeed());
    }

    @Override
    public void init() {
        super.init();
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderFrost(this.world.getSeed());
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
        Biome biome = this.world.getBiome(pos);
        float f = biome.getTemperature(pos);
        if (f >= 0.15F && pos.getY() >= 0 && pos.getY() < 256 && this.world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();
            return block == Blocks.WATER || block == Blocks.FLOWING_WATER;
        }
        return false;
    }

    // B: Random??
    public static void setupRandomSpawner(@Nullable World world, @Nullable TileEntity tile, EnumEverfrostSpawner type, Random rand) {
        if (world == null && tile != null) {
            world = tile.getWorld();
        }

        if (tile instanceof TileMonsterSpawner) {
            switch (type) {
                case ICE_CASTLE_PARAPET:
                    SpawnerTuners.ICECASTLEPARAPET.setupSpawner(world, (TileMonsterSpawner) tile, world.rand);
                case ICE_CASTLE:
                    SpawnerTuners.ICECASTLE.setupSpawner(world, (TileMonsterSpawner) tile, world.rand);
                case MOUND:
                    SpawnerTuners.MOUND.setupSpawner(world, (TileMonsterSpawner) tile, world.rand);
                case STRUCTURES:
                    SpawnerTuners.EVERFROST_STRUCTURES.setupSpawner(world, (TileMonsterSpawner) tile, world.rand);
                case GRAVE:
                    SpawnerTuners.EVERFROST_GRAVE.setupSpawner(world, (TileMonsterSpawner) tile, world.rand);
                case NIVEOUS_HALL:
                    SpawnerTuners.NIVEOUSHALL.setupSpawner(world, (TileMonsterSpawner) tile, world.rand);
            }
        }
    }

    public enum EnumEverfrostSpawner {
        ICE_CASTLE,
        ICE_CASTLE_PARAPET,
        MOUND,
        STRUCTURES,
        GRAVE,
        NIVEOUS_HALL;
    }

}
