package com.Vivern.Arpg.renders;

import net.minecraft.entity.EntityLiving;

//@SideOnly(Side.CLIENT) // duh
public interface IRender {
   void renderPost(EntityLiving var1, double var2, double var4, double var6, float var8, float var9);
}
