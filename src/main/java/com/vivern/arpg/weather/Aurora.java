package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.GetMOP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class Aurora extends WorldEvent {

    public Aurora(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
    }

    @Override
    public boolean canOverlapWith(WorldEvent other) {
        return false;
    }

    @Override
    public int getCooldown() {
        return 3000 + this.worldProvider.getWorld().rand.nextInt(3000);
    }

    @Override
    public boolean canStart() {
        int t = (int) this.worldProvider.getWorld().getWorldTime() % 24000;
        return t < 0 || t > 13000;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.isStarted && !this.canStart()) {
            this.stop();
        }
    }

}
