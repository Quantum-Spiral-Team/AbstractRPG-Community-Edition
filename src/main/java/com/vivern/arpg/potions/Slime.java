package com.vivern.arpg.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Slime extends Potion {

    protected Slime(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
        this.setRegistryName("arpg:slime");
        this.setPotionName("Slime");
        this.setIconIndex(7, 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        int amp = amplifier + 1;
        if (entityLivingBase.collided) {
            entityLivingBase.motionX /= 1.2 * amp;
            entityLivingBase.motionY /= 1.2 * amp;
            entityLivingBase.motionZ /= 1.2 * amp;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

}
