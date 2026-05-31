package com.vivern.arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Waving extends Potion {

    protected Waving(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.setRegistryName("arpg:waving");
        this.setPotionName("Waving");
        this.setIconIndex(3, 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
        return true;
    }

}
