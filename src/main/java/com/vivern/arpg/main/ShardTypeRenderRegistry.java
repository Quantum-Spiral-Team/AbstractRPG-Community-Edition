package com.Vivern.Arpg.main;

import com.Vivern.Arpg.renders.CrystalSphereTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public final class ShardTypeRenderRegistry {

    private static final Map<ShardType, CrystalSphereTESR.RenderShardsEffect> RENDER = new HashMap<>();
    @SideOnly(Side.CLIENT)
    public static CrystalSphereTESR.RenderShardsEffect ins = new CrystalSphereTESR.RenderShardsEffect();

    public static void register(ShardType type, CrystalSphereTESR.RenderShardsEffect effect) {
        RENDER.put(type, effect);
    }

    public static CrystalSphereTESR.RenderShardsEffect get(ShardType type) {
        return RENDER.get(type);
    }
}