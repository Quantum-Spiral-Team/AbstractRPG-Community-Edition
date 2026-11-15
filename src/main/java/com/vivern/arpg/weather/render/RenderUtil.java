package com.vivern.arpg.weather.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

@SideOnly(Side.CLIENT)
public final class RenderUtil {

    private static Field farPlaneField;
    private static boolean resolved;
    private static boolean failed;

    private RenderUtil() {
    }

    public static void disableFarplane() {
        if (failed) {
            return;
        }

        if (!resolved) {
            resolve();
        }

        if (farPlaneField == null) {
            failed = true;
            System.err.println("RenderUtil: EntityRenderer.farPlaneDistance not found, disableFarplane() is disabled");
            return;
        }

        try {
            farPlaneField.setFloat(Minecraft.getMinecraft().entityRenderer, 100000.0F);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            failed = true;
            e.printStackTrace();
        }
    }

    private static void resolve() {
        resolved = true;
        for (String name : new String[]{"farPlaneDistance", "field_78530_s"}) {
            try {
                Field field = EntityRenderer.class.getDeclaredField(name);
                field.setAccessible(true);
                farPlaneField = field;
                return;
            } catch (NoSuchFieldException ignored) {
            }
        }
    }

}