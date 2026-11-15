package com.vivern.arpg.dimensions.generationutils;

import com.vivern.arpg.weather.TimeOfDayProvider;
import com.vivern.arpg.weather.WorldEvent;
import com.vivern.arpg.weather.WorldEventsHandler;
import com.vivern.arpg.weather.render.WeatherRenderers;
import com.vivern.arpg.weather.render.WorldEventsClientHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractWorldProvider extends WorldProvider {

    private IRenderHandler clientWeatherRenderer;

    @SideOnly(Side.CLIENT)
    @Override
    public IRenderHandler getCloudRenderer() {
        return WeatherRenderers.EMPTY_CLOUDS;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IRenderHandler getWeatherRenderer() {
        if (this.clientWeatherRenderer == null && this.getWorldEventsHandler() != null) {
            this.clientWeatherRenderer = new WorldEventsClientHandler(this);
        }

        return this.clientWeatherRenderer;
    }

    @SideOnly(Side.CLIENT)
    public void onUpdatePlayerOnClient(EntityPlayer player) {
        if (this.clientWeatherRenderer instanceof WorldEventsClientHandler) {
            ((WorldEventsClientHandler) this.clientWeatherRenderer).onUpdateClient(player);
        }
    }

    @Override
    public void updateWeather() {
        WorldEventsHandler handler = this.getWorldEventsHandler();
        if (handler != null) {
            handler.onUpdate();
        }
    }

    @Override
    public void onWorldSave() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        WorldEventsHandler handler = this.getWorldEventsHandler();
        if (handler != null) {
            NBTTagCompound worldEventsTag = new NBTTagCompound();
            handler.writeToNbt(worldEventsTag);
            nbttagcompound.setTag("worldEventsTag", worldEventsTag);
        }

        this.world.getWorldInfo().setDimensionData(this.world.provider.getDimension(), nbttagcompound);
    }

    @Override
    public void init() {
        WorldEventsHandler handler = this.getWorldEventsHandler();
        if (handler != null && this.world instanceof WorldServer) {
            NBTTagCompound data = this.world.getWorldInfo().getDimensionData(this.world.provider.getDimension());
            handler.readFromNbt(data.getCompoundTag("worldEventsTag"));
        }
    }

    public void onPlayerJoin(EntityPlayerMP player) {
        WorldEventsHandler handler = this.getWorldEventsHandler();
        if (handler != null) {
            handler.syncTo(player);
        }
    }

    public static void onEntityJoinWorld(EntityJoinWorldEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof EntityPlayerMP) {
            World world = e.getWorld();
            if (world.provider instanceof AbstractWorldProvider) {
                ((AbstractWorldProvider) world.provider).onPlayerJoin((EntityPlayerMP) entity);
            }
        }
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

    @Override
    public void calculateInitialWeather() {
    }

    public World getWorld() {
        return this.world;
    }

    @Nullable
    public abstract WorldEventsHandler getWorldEventsHandler();

    @Nullable
    public abstract TimeOfDayProvider getTimeOfDayProvider();

    public boolean isWorldEventStarted(int index) {
        WorldEventsHandler handler = this.getWorldEventsHandler();
        return handler != null && index >= 0 && index < handler.events.length && handler.events[index].isStarted;
    }

    public static int rgba(int decimalColorRgb, float alpha) {
        int a = (int) (alpha * 255.0F);
        return decimalColorRgb | a << 24;
    }

}
