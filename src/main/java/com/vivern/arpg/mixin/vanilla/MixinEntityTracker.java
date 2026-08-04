package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.network.IFixedTrackerEntity;
import com.vivern.arpg.network.MyEntityTrackerEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTracker.class)
public abstract class MixinEntityTracker {

    @Inject(
            method = "track(Lnet/minecraft/entity/Entity;IIZ)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$track(Entity entityIn, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, CallbackInfo ci) {
        if (entityIn instanceof IFixedTrackerEntity && ((IFixedTrackerEntity) entityIn).canFix()) {
            MyEntityTrackerEntry.track((EntityTracker) (Object) this, entityIn, trackingRange, updateFrequency, sendVelocityUpdates);
            ci.cancel();
        }
    }

}
