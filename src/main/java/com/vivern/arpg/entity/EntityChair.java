package com.vivern.arpg.entity;

import com.vivern.arpg.blocks.Chair;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityChair extends Entity {

    public EntityChair(World world) {
        super(world);
    }

    @Override
    public void entityInit() {
        this.setSize(0.0F, 0.0F);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!this.isBeingRidden()) {
            this.setDead();
        }

        if (!(this.world.getBlockState(this.getPosition()).getBlock() instanceof Chair)) {
            this.setDead();
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
    }

}
