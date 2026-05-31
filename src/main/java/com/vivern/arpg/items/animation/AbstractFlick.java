package com.vivern.arpg.items.animation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
public abstract class AbstractFlick {

    public abstract float get(float var1);

    public abstract float getRaw(float var1);

    public abstract void update(@Nullable EntityPlayer var1);

    public abstract boolean canRemove();

}
