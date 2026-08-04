package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.PlayerAnimation;
import com.vivern.arpg.renders.PlayerAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public abstract class MixinModelBiped {

    @Inject(
            method = "setRotationAngles",
            at = @At("RETURN")
    )
    private void arpg$setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            EnumHandSide enumhandside = arpg$getMainHand(entityIn);
            float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
            float animMain = 1.0F - Weapons.getPlayerAnimationValue(player, EnumHand.MAIN_HAND, partialTicks);
            float animOff = 1.0F - Weapons.getPlayerAnimationValue(player, EnumHand.OFF_HAND, partialTicks);
            int idMain = Weapons.getPlayerAnimationId(player, EnumHand.MAIN_HAND);
            int idOff = Weapons.getPlayerAnimationId(player, EnumHand.OFF_HAND);
            PlayerAnimation animationMain = Weapons.animationsRegister.getOrDefault((byte) idMain, PlayerAnimations.DEFAULT);
            if (animationMain.ID != 0 && animationMain.transformHandThirdperson() && animMain < 1.0F) {
                animationMain.transform(animMain, ((ModelBiped) (Object) this), arpg$getArmForSide(enumhandside, ((ModelBiped) (Object) this)), enumhandside, null, player, partialTicks, EnumHand.MAIN_HAND);
            }

            PlayerAnimation animationOff = Weapons.animationsRegister.getOrDefault((byte) idOff, PlayerAnimations.DEFAULT);
            if (animationOff.ID != 0 && animationOff.transformHandThirdperson() && animOff < 1.0F) {
                animationOff.transform(animOff, ((ModelBiped) (Object) this), arpg$getArmForSide(enumhandside.opposite(), ((ModelBiped) (Object) this)), enumhandside.opposite(), null, player, partialTicks, EnumHand.OFF_HAND);
            }
        }
    }

    @Unique
    private static ModelRenderer arpg$getArmForSide(EnumHandSide side, ModelBiped biped) {
        return side == EnumHandSide.LEFT ? biped.bipedLeftArm : biped.bipedRightArm;
    }

    @Unique
    private static EnumHandSide arpg$getMainHand(Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) entityIn;
            return entitylivingbase.getPrimaryHand();
        } else {
            return EnumHandSide.RIGHT;
        }
    }

}
