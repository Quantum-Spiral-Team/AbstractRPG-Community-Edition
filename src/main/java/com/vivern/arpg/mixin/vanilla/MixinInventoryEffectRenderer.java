package com.vivern.arpg.mixin.vanilla;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer {

    private MixinInventoryEffectRenderer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Inject(
            method = "drawActivePotionEffects",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$drawActivePotionEffects(CallbackInfo ci) {
        int i = this.getGuiLeft() - 124;
        int j = this.getGuiTop();
        Collection<PotionEffect> collection = this.mc.player.getActivePotionEffects();
        if (!collection.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;
            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }

            for (PotionEffect potioneffect : Ordering.natural().sortedCopy(collection)) {
                Potion potion = potioneffect.getPotion();
                if (potion.shouldRender(potioneffect)) {
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.getTextureManager().bindTexture(InventoryEffectRenderer.INVENTORY_BACKGROUND);
                    this.drawTexturedModalRect(i, j, 0, 166, 140, 32);
                    if (potion.hasStatusIcon()) {
                        int i1 = potion.getStatusIconIndex();
                        this.drawTexturedModalRect(i + 6, j + 7, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                    }

                    potion.renderInventoryEffect(potioneffect, this, i, j, 0.0F);
                    if (!potion.shouldRenderInvText(potioneffect)) {
                        j += l;
                    } else {
                        String s1 = I18n.format(potion.getName());
                        s1 = s1 + " " + (potioneffect.getAmplifier() + 1);
                        this.mc.fontRenderer.drawStringWithShadow(s1, i + 10 + 18, j + 6, 16777215);
                        String s = Potion.getPotionDurationString(potioneffect, 1.0F);
                        this.mc.fontRenderer.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 8355711);
                        j += l;
                    }
                }
            }
        }
        ci.cancel();
    }

}
