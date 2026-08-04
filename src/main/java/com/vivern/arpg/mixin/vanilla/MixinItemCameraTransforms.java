package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.CreateItemFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("deprecation")
@Mixin(ItemCameraTransforms.class)
public abstract class MixinItemCameraTransforms {

    @Unique private static ItemCameraTransforms arpg$cameraTransforms;

    @Inject(
            method = "getTransform",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$getTransform(ItemCameraTransforms.TransformType type, CallbackInfoReturnable<ItemTransformVec3f> cir) {
        if (Debugger.itemTransformHookEnabled) cir.setReturnValue(arpg$getTransformsVec3f(type));
    }

    @Unique
    private static ItemTransformVec3f arpg$getTransformsVec3f(ItemCameraTransforms.TransformType type) {
        if (arpg$cameraTransforms == null || AnimationTimer.normaltick % 10 == 0) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            Item item = player.getHeldItemMainhand().isEmpty() ? player.getHeldItemOffhand().getItem() : player.getHeldItemMainhand().getItem();
            arpg$cameraTransforms = CreateItemFile.readJsonItemCameraTransforms(item.getRegistryName().getPath());
        }

        boolean hookSave = Debugger.itemTransformHookEnabled;
        Debugger.itemTransformHookEnabled = false;
        ItemTransformVec3f tr = arpg$cameraTransforms != null ? arpg$cameraTransforms.getTransform(type) : ItemTransformVec3f.DEFAULT;
        Debugger.itemTransformHookEnabled = hookSave;
        return tr;
    }

}
