package com.vivern.arpg.weather.render;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.weather.Hail;
import com.vivern.arpg.weather.WorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class HailRenderer extends WorldEventRenderer<Hail> implements IVoxelCloudInfo {

    public static final ResourceLocation hail = new ResourceLocation("arpg:textures/hail.png");
    public static final ResourceLocation tex = new ResourceLocation("arpg:textures/everfrost_hail.png");

    public final Random rand1 = new Random();
    public final Random rand2 = new Random();
    private long timeSaved;

    public VoxelCloudField voxelCloudField;
    public double sunX;
    public double sunY;
    public float[] colorR = new float[2];
    public float[] colorG = new float[2];
    public float[] colorB = new float[2];
    public float nightpower;
    public float daypower;

    public HailRenderer(Hail event, AbstractWorldProvider provider) {
        super(event, provider);
    }

    @Override
    public void spawnSnowRainParticle(BlockPos pos, int allUsedPosesCount, float dist) {
        if (this.rand2.nextFloat() < (2.0F - dist / 4.0F) / allUsedPosesCount) {
            World world = Minecraft.getMinecraft().world;
            if (world != null && this.timeSaved < world.getTotalWorldTime()) {
                this.timeSaved = world.getTotalWorldTime();
                float scl = (0.25F + 0.125F * this.rand2.nextFloat()) * 0.125F;
                int lt = 20 + this.rand2.nextInt(40);
                GUNParticle part = new GUNParticle(hail, scl, 0.04F, lt, -1, world, pos.getX() + this.rand2.nextFloat(), pos.getY() + 0.0625, pos.getZ() + this.rand2.nextFloat(), (float) this.rand2.nextGaussian() / 20.0F, this.rand2.nextFloat() * 0.05F + 0.07F, (float) this.rand2.nextGaussian() / 20.0F, 1.0F, 1.0F, 1.0F, false, this.rand2.nextInt(360), true, 1.5F);
                part.scaleTickAdding = -scl / lt * 0.9F;
                world.spawnEntity(part);
            }
        }
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        super.render(partialTicks, world, mc);
        WorldEventsClientHandler.renderRainSnow(partialTicks, this.rand1, 0.4F * this.showness, tex, this.showness, 0.016F, -0.2F, 0.0F, 0.0F, null, this);
    }

    @Override
    public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
        if (this.voxelCloudField == null) {
            this.voxelCloudField = new VoxelCloudField(16, 384, 112, 230.0F);
            this.voxelCloudField.voxelCloudInfo = this;
        }

        this.voxelCloudField.render(partialTicks, world, mc);
    }

    @Override
    public void stopRenderClouds() {
        this.voxelCloudField = null;
    }

    @Override
    public boolean hasVoxelAt(VoxelCloudField field, double x, double y, double z, int inArrayX, int inArrayY, int inArrayZ) {
        double d1 = 40.0F + Debugger.floats[0];
        double d2 = 20.0F + Debugger.floats[1];
        double perlin = field.noiseGeneratorPerlin1.getValue(x / d1, z / d1) + Debugger.floats[2] + (2.0F - GetMOP.getFromTo((float) inArrayY, 0.0F, 7.0F) * 3.0F);
        return (field.noiseGenerator3D.getValue(x / d2, y / d2, z / d2) + 1.2F + Debugger.floats[3]) * perlin > 4.0F + Debugger.floats[4];
    }

    @Override
    public void onPreRender() {
        long worldTime = this.provider.getWorldTime();
        float celestialAngle = this.provider.calculateCelestialAngle(worldTime, 0.0F);
        this.sunX = WorldEvent.calcCelestialX(celestialAngle);
        this.sunY = WorldEvent.calcCelestialY(celestialAngle);
        this.provider.getTimeOfDayProvider().getColors3fa(5, 2, this.provider.getWorld().getWorldTime(), this.colorR, this.colorG, this.colorB);
        this.nightpower = this.provider.getTimeOfDayProvider().getPowerOf(3, worldTime);
        this.daypower = 1.0F - this.nightpower;
    }

    @Override
    public float getLightInFace(VoxelCloudField field, VoxelCloud voxelCloud, int arrayY, EnumFacing facing) {
        if (facing.getAxis() == Axis.Z) {
            return 0.0F;
        } else {
            float normal2dX = facing.getXOffset();
            float normal2dY = facing.getYOffset();
            double scalar = normal2dX * this.sunX + normal2dY * this.sunY;
            double angleBetweenSun = Math.acos(scalar) * 57.2958;
            double angleBetweenMoon = 180.0 - angleBetweenSun;
            double possibilityAngle = 120.0;
            float dark = (float) (Math.min(angleBetweenSun * this.daypower + angleBetweenMoon * this.nightpower, possibilityAngle) / possibilityAngle);
            return 1.0F - dark;
        }
    }

    @Override
    public void writeColorInPoint(VoxelCloudField field, VoxelCloud voxelCloud, float[] colorRGBA, double x, double y, double z, int arrayX, int arrayY, int arrayZ, VoxelCloud.EnumCubeVertex vertex) {
        float light = voxelCloud.getMostSurroundingsLightning(field, vertex, arrayX, arrayY, arrayZ);
        float dark = 1.0F - light;
        colorRGBA[0] = this.colorR[1] * light + this.colorR[0] * dark;
        colorRGBA[1] = this.colorG[1] * light + this.colorG[0] * dark;
        colorRGBA[2] = this.colorB[1] * light + this.colorB[0] * dark;
        colorRGBA[3] = 0.7F * this.showness;
    }

}