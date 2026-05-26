package com.vivern.arpg.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FreezingParticle extends Entity {
   public EntityLivingBase entityON;
   public ModelBase model;

   public FreezingParticle(World worldIn) {
      super(worldIn);
   }

   public FreezingParticle(World worldIn, EntityLivingBase entityON) {
      super(worldIn);
      this.entityON = entityON;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound compound) {
   }

   @Override
   public void onUpdate() {
      if (this.ticksExisted > 1) {
         this.setDead();
      }
   }
}
