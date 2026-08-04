package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.potions.Freezing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    private MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "isMovementBlocked",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$isMovementBlocked(CallbackInfoReturnable<Boolean> cir) {
        if (Freezing.isEntityFreezing((EntityLivingBase) (Object) this)) cir.setReturnValue(true);
    }

    @Inject(
            method = "isElytraFlying",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$isElytraFlying(CallbackInfoReturnable<Boolean> cir) {
        if (((EntityLivingBase) (Object) this) instanceof EntityPlayer && this.getDataManager().get(PropertiesRegistry.FLYING))
            cir.setReturnValue(true);
    }

}
