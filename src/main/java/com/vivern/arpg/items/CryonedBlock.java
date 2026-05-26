package com.vivern.arpg.items;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CryonedBlock extends Entity {
   public AxisAlignedBB hitboxToRender;
   public boolean shouldresize = true;
   public int timeLast;

   public CryonedBlock(World worldIn) {
      super(worldIn);
      this.setSize(1.02F, 1.02F);
   }

   public CryonedBlock(World worldIn, BlockPos pos) {
      super(worldIn);
      this.setSize(1.02F, 1.02F);
      this.setPosition(pos.getX() + 0.5 - 0.005, pos.getY() - 0.005, pos.getZ() + 0.5 - 0.005);
   }

   @Override
   protected void entityInit() {
   }

   @SideOnly(Side.CLIENT)
   @Override
   public AxisAlignedBB getRenderBoundingBox() {
      return this.hitboxToRender == null ? super.getRenderBoundingBox() : this.hitboxToRender;
   }

   public void organizeSize(World worldIn, BlockPos pos) {
      AxisAlignedBB aabb = worldIn.getBlockState(pos).getBoundingBox(worldIn, pos);
      if (aabb == null) {
         aabb = new AxisAlignedBB(pos);
      } else {
         aabb = aabb.offset(pos);
      }

      aabb = aabb.grow(0.02);
      this.hitboxToRender = aabb;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         this.timeLast--;
         if (this.timeLast == 0) {
            this.setDead();
         }
      }

      if (this.shouldresize) {
         this.organizeSize(this.world, new BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5));
         this.shouldresize = false;
      }
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound compound) {
   }
}
