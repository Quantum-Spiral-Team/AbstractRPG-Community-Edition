package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.packet.PacketWorldEventToClients;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

import java.util.Random;

public class WorldEvent {

    public final AbstractWorldProvider worldProvider;
    public final byte index;
    public final int livetimeMin;
    public final int livetimeMax;
    public final float chanceToStart;

    public int ticksExisted;
    public int livetime;
    public boolean isStarted;
    public int currentCooldown;

    public boolean snapVisual;

    public WorldEvent(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        this.worldProvider = worldProvider;
        this.index = (byte) index;
        this.livetimeMin = (int) (livetimeMin * WorldEventsHandler.timeOverclock);
        this.livetimeMax = (int) (livetimeMax * WorldEventsHandler.timeOverclock);
        this.chanceToStart = chanceToStart;
    }

    public void onUpdate() {
        this.ticksExisted++;
        if (this.ticksExisted > this.livetime) {
            this.stop();
        }
    }

    public void start() {
        World world = this.worldProvider.getWorld();
        if (world.isRemote) {
            return;
        }

        this.isStarted = true;
        this.ticksExisted = 0;
        this.livetime = this.livetimeMin + world.rand.nextInt(this.livetimeMax - this.livetimeMin + 1);
        this.sendSync(WorldEventsHandler.SYNC_START);
    }

    public void stop() {
        World world = this.worldProvider.getWorld();
        if (world.isRemote) {
            return;
        }

        this.isStarted = false;
        this.currentCooldown = (int) (this.getCooldown() * WorldEventsHandler.timeOverclock);
        this.sendSync(WorldEventsHandler.SYNC_STOP);
    }

    public int getCooldown() {
        return 12000;
    }

    public boolean canOverlapWith(WorldEvent other) {
        return true;
    }

    public boolean canStart() {
        return true;
    }

    protected void sendSync(byte state) {
        int dim = this.worldProvider.getDimension();
        PacketWorldEventToClients packet = new PacketWorldEventToClients();
        packet.writeArgs(this.index, state, dim);
        PacketHandler.sendToDimension(packet, dim);
    }

    public void sendSyncTo(EntityPlayerMP player, byte state) {
        PacketWorldEventToClients packet = new PacketWorldEventToClients();
        packet.writeArgs(this.index, state, this.worldProvider.getDimension());
        PacketHandler.sendTo(packet, player);
    }

    public void updateAllLoaded(Random rand, float chance, int loadCheckRadius) {
        World world = this.worldProvider.getWorld();
        if (world.isRemote || !(world.getChunkProvider() instanceof ChunkProviderServer)) {
            return;
        }

        ChunkProviderServer chunkProvider = (ChunkProviderServer) world.getChunkProvider();
        float perChunk = 256.0F * chance;
        int base = (int) perChunk;
        float fraction = perChunk - base;
        BlockPos.MutableBlockPos min = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos max = new BlockPos.MutableBlockPos();

        for (Chunk chunk : chunkProvider.getLoadedChunks()) {
            int count = base + (rand.nextFloat() < fraction ? 1 : 0);
            for (int i = 0; i < count; i++) {
                int x = chunk.x * 16 + rand.nextInt(16);
                int z = chunk.z * 16 + rand.nextInt(16);
                min.setPos(x - loadCheckRadius, 0, z - loadCheckRadius);
                max.setPos(x + loadCheckRadius, 255, z + loadCheckRadius);
                if (world.isAreaLoaded(min, max)) {
                    this.updateLoadedXZ(world, x, z);
                }
            }
        }
    }

    public void updateLoadedXZ(World world, int posX, int posZ) {
    }

    public void applyClientState(boolean started, boolean instant) {
        this.isStarted = started;
        if (instant) {
            this.snapVisual = true;
        }
    }

    public void writeToNbt(NBTTagCompound compound) {
        compound.setInteger("ticksExisted", this.ticksExisted);
        compound.setBoolean("isStarted", this.isStarted);
        compound.setInteger("live_time", this.livetime);
        compound.setInteger("currentCooldown", this.currentCooldown);
    }

    public void readFromNbt(NBTTagCompound compound) {
        if (compound.hasKey("ticksExisted")) {
            this.ticksExisted = compound.getInteger("ticksExisted");
        }

        if (compound.hasKey("isStarted")) {
            this.isStarted = compound.getBoolean("isStarted");
        }

        if (compound.hasKey("live_time")) {
            this.livetime = compound.getInteger("live_time");
        }

        if (compound.hasKey("currentCooldown")) {
            this.currentCooldown = compound.getInteger("currentCooldown");
        }
    }

    public static double calcCelestialX(float celestialAngle) {
        celestialAngle += 0.75F;
        return -MathHelper.cos(-celestialAngle * (float) (Math.PI * 2));
    }

    public static double calcCelestialY(float celestialAngle) {
        celestialAngle += 0.75F;
        return MathHelper.sin(-celestialAngle * (float) (Math.PI * 2));
    }

    public boolean isRainingAt(Entity position) {
        return this.isRainingAt(position.getPosition());
    }

    public boolean isRainingAt(BlockPos position) {
        return this.worldProvider.getWorld().getPrecipitationHeight(position).getY() <= position.getY();
    }

}
