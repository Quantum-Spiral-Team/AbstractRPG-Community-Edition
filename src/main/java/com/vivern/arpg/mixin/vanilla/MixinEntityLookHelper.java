package com.vivern.arpg.mixin.vanilla;

import net.minecraft.entity.ai.EntityLookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLookHelper.class)
public abstract class MixinEntityLookHelper {

    @Shadow public abstract double getLookPosX();

    // B: Я, честно говоря, без малейшего понятия, зачем это нужно
    @Inject(
            method = "onUpdateLook",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$onUpdateLook(CallbackInfo ci) {
        if (getLookPosX() == Double.MAX_VALUE) ci.cancel();
    }

}
