package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.potions.Freezing;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

    private MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "isMovementBlocked",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$isMovementBlocked(CallbackInfoReturnable<Boolean> cir) {
        if (Freezing.isEntityFreezing((EntityPlayer) (Object) this)) cir.setReturnValue(true);
    }

    @Inject(
            method = "getPortalCooldown",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$getPortalCooldown(CallbackInfoReturnable<Integer> cir) {
        if (this.dimension == 0) cir.setReturnValue(100);
    }

}
