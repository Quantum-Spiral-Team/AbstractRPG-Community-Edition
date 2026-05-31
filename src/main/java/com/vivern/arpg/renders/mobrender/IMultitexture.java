package com.vivern.arpg.renders.mobrender;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public interface IMultitexture {

    @SideOnly(Side.CLIENT)
    @Nullable ResourceLocation getMultitexture();

}
