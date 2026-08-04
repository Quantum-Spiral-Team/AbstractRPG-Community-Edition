package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.hooks.ARPGHooks;
import com.vivern.arpg.potions.AdvancedPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(Render.class)
public abstract class MixinRender {

    @Unique private boolean arpgf$dontRecurse;

    @Inject(
            method = "bindTexture",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$bindTexture(ResourceLocation location, CallbackInfo ci) {
        if (ARPGHooks.bindAnotherTexture != null) {
            Minecraft.getMinecraft().renderEngine.bindTexture(ARPGHooks.bindAnotherTexture);
            ci.cancel();
        }
    }

    @Inject(
            method = "doRenderShadowAndFire",
            at = @At("HEAD")
    )
    private void arpg$doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, CallbackInfo ci) {
        if (!arpgf$dontRecurse && entityIn instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
            Collection<PotionEffect> potionsList = entityLiving.getActivePotionEffects();
            if (!potionsList.isEmpty()) {
                for (PotionEffect effect : potionsList) {
                    if (effect.getPotion() instanceof AdvancedPotion) {
                        AdvancedPotion potion = (AdvancedPotion) effect.getPotion();
                        if (potion.shouldRender) {
                            arpgf$dontRecurse = true;
                            potion.render(entityLiving, x, y, z, yaw, partialTicks, effect, (Render<EntityLivingBase>) (Object) this);
                            arpgf$dontRecurse = false;
                        }
                    }
                }
            }
        }
    }

}
