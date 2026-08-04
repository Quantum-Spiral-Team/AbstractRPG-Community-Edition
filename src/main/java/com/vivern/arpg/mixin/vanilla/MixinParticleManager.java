package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.items.IWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {

    @Inject(
            method = "addBlockHitEffects(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$addBlockHitEffects(BlockPos pos, EnumFacing side, CallbackInfo ci) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null && player.getHeldItemMainhand().getItem() instanceof IWeapon) ci.cancel();
    }

}
