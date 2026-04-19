package com.vivern.arpg.renders.mobrender;

import org.jetbrains.annotations.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMultitexture {
   @SideOnly(Side.CLIENT)
   @Nullable
   ResourceLocation getMultitexture();
}
