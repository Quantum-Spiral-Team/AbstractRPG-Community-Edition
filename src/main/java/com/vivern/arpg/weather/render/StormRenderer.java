package com.vivern.arpg.weather.render;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.weather.Storm;
import com.vivern.arpg.weather.WorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StormRenderer extends WorldEventRenderer<Storm> implements IVoxelCloudInfo {

    public VoxelCloudField voxelCloudField;
    public double sunX;
    public double sunY;
    public float[] colorR = new float[2];
    public float[] colorG = new float[2];
    public float[] colorB = new float[2];
    public float[] colorA = new float[2];
    public float nightpower;
    public float daypower;

    protected StormRenderer(Storm event, AbstractWorldProvider provider) {
        super(event, provider);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        super.render(partialTicks, world, mc);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
        if (this.voxelCloudField == null) {
            this.voxelCloudField = new VoxelCloudField(32, 1024, 96, 256.0F);
            this.voxelCloudField.voxelCloudInfo = this;
        }

        this.voxelCloudField.render(partialTicks, world, mc);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void stopRenderClouds() {
        this.voxelCloudField = null;
    }

    @Override
    public boolean hasVoxelAt(VoxelCloudField field, double x, double y, double z, int inArrayX, int inArrayY, int inArrayZ) {
        double d1 = 40.0F + Debugger.floats[0];
        double d2 = 20.0F + Debugger.floats[1];
        double perlin = field.noiseGeneratorPerlin1.getValue(x / d1, z / d1) + Debugger.floats[2] + (2.0F - GetMOP.getFromTo((float) inArrayY, 0.0F, 7.0F) * 3.0F);
        return (field.noiseGenerator3D.getValue(x / d2, y / d2, z / d2) + 1.2F + Debugger.floats[3]) * perlin > 11.0F + Debugger.floats[4];
    }

    @Override
    public void onPreRender() {
        AbstractWorldProvider provider = this.event.worldProvider;
        float celestialAngle = provider.calculateCelestialAngle(provider.getWorldTime(), 0.0F);
        this.sunX = WorldEvent.calcCelestialX(celestialAngle);
        this.sunY = WorldEvent.calcCelestialY(celestialAngle);
        provider.getTimeOfDayProvider().getColors4fa(5, 2, provider.getWorld().getWorldTime(), this.colorR, this.colorG, this.colorB, this.colorA);
        this.nightpower = provider.getTimeOfDayProvider().getPowerOf(3, provider.getWorldTime());
        this.daypower = 1.0F - this.nightpower;
    }

    @Override
    public float getLightInFace(VoxelCloudField field, VoxelCloud voxelCloud, int arrayY, EnumFacing facing) {
        if (facing.getAxis() == EnumFacing.Axis.Z) {
            return 0.0F;
        } else {
            float normal2dX = facing.getXOffset();
            float normal2dY = facing.getYOffset();
            double scalar = normal2dX * this.sunX + normal2dY * this.sunY;
            double anglebetweenSun = Math.acos(scalar) * 57.2958;
            double anglebetweenMoon = 180.0 - anglebetweenSun;
            double possibilityAngle = 110.0;
            float dark = (float) (Math.min(anglebetweenSun * this.daypower + anglebetweenMoon * this.nightpower, possibilityAngle) / possibilityAngle);
            return 1.0F - dark;
        }
    }

    @Override
    public void writeColorInPoint(VoxelCloudField field, VoxelCloud voxelCloud, float[] colorRGBA, double x, double y, double z, int arrayX, int arrayY, int arrayZ, VoxelCloud.EnumCubeVertex vertex) {
        float light = voxelCloud.getMostSurroundingsLightning(field, vertex, arrayX, arrayY, arrayZ);
        float dark = 1.0F - light;
        colorRGBA[0] = this.colorA[1] * light + this.colorA[0] * dark;
        colorRGBA[1] = this.colorG[1] * light + this.colorG[0] * dark;
        colorRGBA[2] = this.colorB[1] * light + this.colorB[0] * dark;
        colorRGBA[3] = (this.colorA[1] * light + this.colorA[0] * dark) * this.showness;
    }

}
