package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.main.DeathEffects;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Mixin(RenderLivingBase.class)
public class MixinRenderLivingBase {

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void arpg$mobModelReg(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn, CallbackInfo ci) {
        Class<?> entityClass = arpg$getGenericParameterClass(((RenderLivingBase) (Object) this).getClass(), 0);

        if (entityClass != null && modelBaseIn != null) {
            DeathEffects.tryAddtoMainModels(entityClass, modelBaseIn);
        }
    }

    @Unique
    private static Class<?> arpg$getGenericParameterClass(Class<?> clazz, int index) {
        Type type = clazz.getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) type).getActualTypeArguments();
            if (index >= 0 && index < args.length && args[index] instanceof Class) {
                return (Class<?>) args[index];
            }
        }
        return null;
    }

}
