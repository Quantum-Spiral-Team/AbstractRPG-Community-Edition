package com.vivern.arpg.util.math;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector4f;

@FunctionalInterface
public interface ColorConsumer {
    void write(float[] r, float[] g, float[] b, float[] a, int index);

    static ColorConsumer of(int color) {
        return (r, g, b, a, i) -> {
            r[i] = ((color >> 24) & 0xFF) / 255.0F;
            g[i] = ((color >> 16) & 0xFF) / 255.0F;
            b[i] = ((color >> 8) & 0xFF) / 255.0F;
            a[i] = (color & 0xFF) / 255.0F;
        };
    }

    @SideOnly(Side.CLIENT)
    static ColorConsumer of(Vector4f vector) {
        return (r, g, b, a, i) -> {
            r[i] = vector.x;
            g[i] = vector.y;
            b[i] = vector.z;
            a[i] = vector.w;
        };
    }
}