package com.vivern.arpg.mixin.mods.enderio;

import com.vivern.arpg.mobs.AbstractMob;
import crazypants.enderio.base.item.soulvial.ItemSoulVial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSoulVial.class)
public abstract class MixinItemSoulVial {

    @Inject(
            method = "itemInteractionForEntity",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void itemInteractionForEntity(ItemStack item, EntityPlayer player, EntityLivingBase entity, EnumHand hand, CallbackInfoReturnable<Boolean> cir) {
        if (entity.world.isRemote || (entity instanceof AbstractMob && !((AbstractMob) entity).canBeCaptured(player))) cir.setReturnValue(false);
    }

}
