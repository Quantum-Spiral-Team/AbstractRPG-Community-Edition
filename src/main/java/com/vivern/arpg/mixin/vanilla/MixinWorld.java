package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.hooks.coloredlightning.ColoredLightning;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(World.class)
public abstract class MixinWorld {

    @Shadow @Final public boolean isRemote;

    @Shadow public abstract void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2);

    @Inject(
            method = "markAndNotifyBlock",
            at = @At("HEAD"),
            remap = false
    )
    private void arpg$markAndNotifyBlock(BlockPos pos, @Nullable Chunk chunk, IBlockState iblockstate, IBlockState newState, int flags, CallbackInfo ci) {
        if ((newState.getLightOpacity(((World) (Object) this), pos) != iblockstate.getLightOpacity(((World) (Object) this), pos) || newState.getLightValue(((World) (Object) this), pos) != iblockstate.getLightValue(((World) (Object) this), pos)) && isRemote) {
            ColoredLightning.doColorUpdate(pos.getX(), pos.getY(), pos.getZ(), false, ((World) (Object) this));
        }
    }

    @Inject(
            method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z",
            at = @At("HEAD")
    )
    private void arpg$setBlockState(BlockPos pos, IBlockState newState, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (newState.getLightValue(((World) (Object) this), pos) > 0 || newState.getLightOpacity(((World) (Object) this), pos) > 0 || newState.getBlock() == Blocks.AIR) {
            markBlockRangeForRenderUpdate(pos.getX() - 15, pos.getY() - 15, pos.getZ() - 15, pos.getX() + 15, pos.getY() + 15, pos.getZ() + 15);
        }
    }

}
