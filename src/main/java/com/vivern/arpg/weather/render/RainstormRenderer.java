package com.vivern.arpg.weather.render;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.weather.Storm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RainstormRenderer extends StormRenderer {

    public static ResourceLocation tex = new ResourceLocation("arpg:textures/stormledge_rainstorm.png");

    public RainstormRenderer(Storm event, AbstractWorldProvider provider) {
        super(event, provider);
        this.soundRain = Sounds.weather_rainstorm;
        this.soundRainAbove = Sounds.weather_rainstorm;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        super.render(partialTicks, world, mc);
        WorldEventsClientHandler.renderRainSnow(partialTicks, this.event.rand, 1.0F, tex, this.showness, 0.012F, -0.2F, 0.02F, 1.0F, null, null);
    }
}
