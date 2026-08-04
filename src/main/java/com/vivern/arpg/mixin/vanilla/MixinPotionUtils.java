package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.potions.PotionEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PotionUtils.class)
public abstract class MixinPotionUtils {

    // TODO переписать на изменение кода, а не полную замену
    @Inject(
            method = "addPotionTooltip",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void arpg$addPotionTooltip(ItemStack itemIn, List<String> lores, float durationFactor, CallbackInfo ci) {
        PotionEffects.addPotionTooltip(itemIn, lores, durationFactor);
        ci.cancel();
    }

}
