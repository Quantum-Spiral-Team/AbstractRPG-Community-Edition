package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.hooks.ARPGHooks;
import com.vivern.arpg.items.IWeapon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer {

    @Shadow public EntityPlayer player;

    @Shadow public abstract ItemStack getCurrentItem();

    @Inject(
            method = "changeCurrentItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$changeCurrentItem(int direction, CallbackInfo ci) {
        if (direction > 0) {
            direction = 1;
        }

        if (direction < 0) {
            direction = -1;
        }

        ARPGHooks.moveSlot -= direction;

        while (ARPGHooks.moveSlot < 0) {
            ARPGHooks.moveSlot += 9;
        }

        while (ARPGHooks.moveSlot >= 9) {
            ARPGHooks.moveSlot -= 9;
        }

        ItemStack stack = getCurrentItem();
        Item item = stack.getItem();
        boolean condition = item instanceof IWeapon && !((IWeapon) item).canChangeItem(stack, player);
        if (condition) ci.cancel();
    }

}
