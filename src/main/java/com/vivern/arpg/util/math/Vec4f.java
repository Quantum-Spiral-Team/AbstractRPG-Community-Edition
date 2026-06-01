package com.vivern.arpg.util.math;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector4f;

public class Vec4f {
    public float x, y, z, w;

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @SideOnly(Side.CLIENT)
    public Vector4f toVector4f() {
        return new Vector4f(x, y, z, w);
    }
}
