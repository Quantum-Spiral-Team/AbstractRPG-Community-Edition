package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.potions.PotionEffects;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid {

    @Inject(
            method = "getFogColor",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks, CallbackInfoReturnable<Vec3d> cir) {
        if (world.provider.getDimension() == 103) {
            cir.setReturnValue(arpg$getFogColorVector(world, pos, state, entity, originalColor, partialTicks));
        }
    }

    @Unique
    private static Vec3d arpg$getFogColorVector(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        Vec3d viewport = ActiveRenderInfo.projectViewFromEntity(entity, partialTicks);
        if (state.getMaterial().isLiquid()) {
            float height = 0.0F;
            if (state.getBlock() instanceof BlockLiquid) {
                height = BlockLiquid.getLiquidHeightPercent(state.getValue(BlockLiquid.LEVEL)) - 0.11111111F;
            }

            float f1 = pos.getY() + 1 - height;
            if (viewport.y > f1) {
                BlockPos upPos = pos.up();
                IBlockState upState = world.getBlockState(upPos);
                return upState.getBlock().getFogColor(world, upPos, upState, entity, originalColor, partialTicks);
            }
        }

        if (state.getMaterial() == Material.WATER) {
            float r = MathHelper.clamp((-entity.rotationPitch + 90.0F) / 180.0F, 0.0F, 1.0F);
            return new Vec3d(0.1 + 0.1 * r, 0.4 + 0.35 * r, 0.85 + 0.23 * r);
        } else {
            return originalColor;
        }
    }

}
