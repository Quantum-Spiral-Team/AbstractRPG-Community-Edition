package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.main.ItemsElements;
import com.vivern.arpg.renders.ManaBar;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Inject(
            method = "renderToolTip",
            at = @At("HEAD")
    )
    private void arpg$renderToolTip(ItemStack stack, int x, int y, CallbackInfo ci) {
        ItemsElements.ElementsPack pack = ItemsElements.getAllElements(stack);
        GlStateManager.disableDepth();
        ManaBar.renderElementsVision(x + 40, y + 60, pack);
        GlStateManager.enableDepth();
    }

}
