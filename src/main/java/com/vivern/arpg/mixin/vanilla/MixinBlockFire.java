package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.blocks.AshBlock;
import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = BlockFire.class)
public abstract class MixinBlockFire {

    @Inject(
            method = "tryCatchFire",
            at = @At("HEAD"),
            cancellable = true,
            remap = false // B: почему ремап выключен? Я понятия не имею, оно так рабоатет
    )
    private void arpg$tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face, CallbackInfo ci) {
        int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);
        if (random.nextInt(chance) < i) {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
                int j = Math.min(age + random.nextInt(5) / 4, 15);
                worldIn.setBlockState(pos, ((BlockFire) (Object) this).getDefaultState().withProperty(BlockFire.AGE, j), 3);
            } else {
                IBlockState has = worldIn.getBlockState(pos);
                if ((random.nextFloat() >= 0.75F || has.getMaterial() != Material.WOOD) && (random.nextFloat() >= 0.35F || has.getMaterial() != Material.LEAVES)) {
                    worldIn.setBlockToAir(pos);
                } else {
                    worldIn.setBlockState(pos, BlocksRegister.ASH_BLOCK.getDefaultState().withProperty(AshBlock.LAYERS, 1).withProperty(AshBlock.ISFALLING, false));
                }
            }

            if (iblockstate.getBlock() == Blocks.TNT) {
                Blocks.TNT.onPlayerDestroy(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, true));
            }
        }
        ci.cancel();
    }

}
