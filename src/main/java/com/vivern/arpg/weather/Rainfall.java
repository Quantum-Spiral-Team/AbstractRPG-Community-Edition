package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.weather.render.IVoxelCloudInfo;
import com.vivern.arpg.weather.render.VoxelCloud;
import com.vivern.arpg.weather.render.VoxelCloudField;
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

public class Rainfall extends WorldEvent {

    public Random rand = new Random();

    public Rainfall(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
    }

    @Override
    public boolean canOverlapWith(WorldEvent other) {
        return false;
    }

    @Override
    public int getCooldown() {
        return 5000 + this.worldProvider.getWorld().rand.nextInt(5000);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % 20 == 0) {
            this.updateAllLoaded(this.rand, 0.01F, 1);
        }
    }

    @Override
    public void updateLoadedXZ(World world, int posX, int posZ) {
        BlockPos pos = new BlockPos(posX, 0, posZ);
        pos = world.getPrecipitationHeight(pos).down();
        world.getBlockState(pos).getBlock().fillWithRain(world, pos);
    }

}
