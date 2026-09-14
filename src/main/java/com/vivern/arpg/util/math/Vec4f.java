package com.vivern.arpg.util.math;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector4f;

public class Vec4f implements ColorConsumer {
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

    @Override
    public void write(float[] r, float[] g, float[] b, float[] a, int index) {
        r[index] = this.x;
        g[index] = this.y;
        b[index] = this.z;
        a[index] = this.w;
    }
}
