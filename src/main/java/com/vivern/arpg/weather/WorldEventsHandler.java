package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WorldEventsHandler {

    public static final byte SYNC_STOP = 0;
    public static final byte SYNC_START = 1;
    public static final byte SYNC_STOP_INSTANT = 2;
    public static final byte SYNC_START_INSTANT = 3;

    public static float timeOverclock = 1.0F;
    public static int startCheckDelay = (int) (1200.0F * timeOverclock);

    public final AbstractWorldProvider worldProvider;
    public final WorldEvent[] events;

    public WorldEventsHandler(AbstractWorldProvider worldProvider, WorldEvent... events) {
        for (int i = 0; i < events.length; i++) {
            if (events[i].index != i) {
                throw new IllegalArgumentException("WorldEvent " + events[i].getClass().getSimpleName()
                        + " has index " + events[i].index + " but stands at position " + i);
            }
        }

        this.worldProvider = worldProvider;
        this.events = events;
    }

    public void writeToNbt(NBTTagCompound compound) {
        for (WorldEvent event : this.events) {
            NBTTagCompound eventTag = new NBTTagCompound();
            event.writeToNbt(eventTag);
            compound.setTag("" + event.index, eventTag);
        }
    }

    public void readFromNbt(NBTTagCompound compound) {
        for (WorldEvent event : this.events) {
            if (compound.hasKey("" + event.index)) {
                event.readFromNbt(compound.getCompoundTag("" + event.index));
            }
        }
    }

    public void applySync(int index, byte state) {
        if (index >= 0 && index < this.events.length) {
            this.events[index].applyClientState((state & 1) != 0, (state & 2) != 0);
        }
    }

    public void syncTo(EntityPlayerMP player) {
        for (WorldEvent event : this.events) {
            if (event.isStarted) {
                event.sendSyncTo(player, SYNC_START_INSTANT);
            }
        }
    }

    public void onUpdate() {
        World world = this.worldProvider.getWorld();
        if (world == null || world.isRemote) {
            return;
        }

        for (WorldEvent event : this.events) {
            if (event.isStarted) {
                event.onUpdate();
            } else if (event.currentCooldown > 0) {
                event.currentCooldown--;
            }
        }

        int n = this.events.length;
        if (n == 0) {
            return;
        }

        if (world.getTotalWorldTime() % Math.max(1, startCheckDelay) != 0L) {
            return;
        }

        Random rand = world.rand;
        int first = rand.nextInt(n);
        for (int k = 0; k < n; k++) {
            WorldEvent event = this.events[(first + k) % n];
            if (event.isStarted || event.currentCooldown > 0 || !event.canStart()) {
                continue;
            }

            if (rand.nextFloat() < event.chanceToStart && this.canStartNow(event)) {
                event.start();
            }
        }
    }

    private boolean canStartNow(WorldEvent candidate) {
        for (WorldEvent other : this.events) {
            if (other.isStarted && !candidate.canOverlapWith(other)) {
                return false;
            }
        }

        return true;
    }

    @Nullable
    public <T extends WorldEvent> T find(Class<T> type) {
        for (WorldEvent event : this.events) {
            if (type.isInstance(event)) {
                return type.cast(event);
            }
        }

        return null;
    }

}
