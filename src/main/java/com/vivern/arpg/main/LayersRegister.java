package com.vivern.arpg.main;

import com.vivern.arpg.renders.LayerLaser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayersRegister {
   public static void register() {
      setLayer(new LayerLaser());
   }

   private static void setLayer(LayerRenderer layer) {
      Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default").addLayer(layer);
      Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim").addLayer(layer);
   }
}
