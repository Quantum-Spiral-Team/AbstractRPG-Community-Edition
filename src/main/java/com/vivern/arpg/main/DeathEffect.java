package com.vivern.arpg.main;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public abstract class DeathEffect {

    public static final List<DeathEffect> REGISTRY = new ArrayList<>();
    public List<EntityLivingBase> listMobs = new ArrayList<>();
    public List<EntityLivingBase> toRemove = new ArrayList<>();
    public final int id;

    public DeathEffect() {
        REGISTRY.add(this);
        this.id = REGISTRY.size();
    }

    @SideOnly(Side.CLIENT)
    public abstract void onRenderLivingPre(RenderLivingEvent.Pre<? extends EntityLivingBase> event, EntityLivingBase living);

    @SideOnly(Side.CLIENT)
    public abstract void onLivingUpdate(World world, LivingEvent.LivingUpdateEvent event, EntityLivingBase living, double var4, double var6, double var8);

    public boolean exist(EntityLivingBase entity) {
        return this.listMobs.contains(entity);
    }

    public void add(EntityLivingBase entity) {
        this.listMobs.add(entity);
    }

    public void remove(EntityLivingBase entity) {
        this.toRemove.add(entity);
    }

    public void removeAllUnused() {
        if (!this.toRemove.isEmpty()) {
            this.listMobs.removeAll(this.toRemove);
            this.toRemove.clear();
        }
    }

}
