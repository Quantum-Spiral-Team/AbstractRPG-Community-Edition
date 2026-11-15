package com.vivern.arpg.weather.render;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.weather.Rainfall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RainfallRenderer extends WorldEventRenderer<Rainfall> {

    public static ResourceLocation tex = new ResourceLocation("arpg:textures/aquatica_rainfall.png");

    protected RainfallRenderer(Rainfall event, AbstractWorldProvider provider) {
        super(event, provider);
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {}

}
