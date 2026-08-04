package com.vivern.arpg.mixin.vanilla;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RangedAttribute.class)
public abstract class MixinRangedAttribute {

    @Inject(
            method = "clampValue",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$clampValue(double value, CallbackInfoReturnable<Double> cir) {
        if (((RangedAttribute) (Object) this).equals(SharedMonsterAttributes.MAX_HEALTH)) {
            cir.setReturnValue(MathHelper.clamp(value, Float.MIN_VALUE, 8000.0));
        }
    }

}
