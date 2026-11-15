package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.Sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Rainstorm extends Storm {

    public Rainstorm(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
    }

}
