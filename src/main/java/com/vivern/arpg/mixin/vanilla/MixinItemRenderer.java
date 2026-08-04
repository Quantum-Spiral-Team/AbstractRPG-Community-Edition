package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.PlayerAnimation;
import com.vivern.arpg.renders.PlayerAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Inject(
            method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$renderItemInFirstPerson(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo ci) {
        int id = Weapons.getPlayerAnimationId(player, hand);
        PlayerAnimation animation = Weapons.animationsRegister.getOrDefault((byte) id, PlayerAnimations.DEFAULT);
        if (id != 0) {
            float an = 1.0F - Weapons.getPlayerAnimationValue(player, hand, Minecraft.getMinecraft().getRenderPartialTicks());
            if (an > 0.0F && an < 1.0F) {
                if (animation.transformItemFirstperson()) {
                    animation.render(player, hand, an, stack, 0.0F);
                } else {
                    PlayerAnimations.instance.renderNone(player, hand, an, stack, p_187457_7_);
                }

                ci.cancel();
            }
        }
    }

    @Inject(
            method = "renderItemSide",
            at = @At("HEAD")
    )
    private void arpg$renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
        if (entitylivingbaseIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entitylivingbaseIn;
            EnumHand hand = leftHanded ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            int id = Weapons.getPlayerAnimationId(player, hand);
            PlayerAnimation animation = Weapons.animationsRegister.getOrDefault((byte) id, PlayerAnimations.DEFAULT);
            if (animation.ID != 0 && animation.transformItemThirdperson()) {
                float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
                float anim = 1.0F - Weapons.getPlayerAnimationValue(player, hand, partialTicks);
                if (anim > 0.0F && anim < 1.0F) {
                    animation.transform(anim, null, null, leftHanded ? EnumHandSide.LEFT : EnumHandSide.RIGHT, heldStack, player, partialTicks, hand);
                }
            }
        }
    }

}
