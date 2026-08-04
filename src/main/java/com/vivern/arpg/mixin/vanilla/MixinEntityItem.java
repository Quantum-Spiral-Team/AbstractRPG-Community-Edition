package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem {
    
    @Shadow public abstract ItemStack getItem();
    
    @Inject(
            method = "attackEntityFrom",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        World world = ((EntityItem) (Object) this).world;
        if (!world.isRemote && !((EntityItem) (Object) this).isDead) {
            Item item = getItem().getItem();
            BlockPos pos = ((EntityItem) (Object) this).getPosition();
            if (item != Items.ENCHANTED_BOOK || !source.isFireDamage() && !source.isExplosion() && source != DamageSource.LIGHTNING_BOLT) {
                if (item != ItemsRegister.MAGIC_POWDER || !source.isFireDamage() && !source.isExplosion() && source != DamageSource.LIGHTNING_BOLT) {
                    if ((item == ItemsRegister.RHINESTONE || item == ItemsRegister.TOPAZ || item == ItemsRegister.AMETHYST || item == ItemsRegister.CITRINE || item == ItemsRegister.RUBY || item == ItemsRegister.SAPPHIRE || item == Items.DIAMOND || item == Items.EMERALD) && source.isFireDamage()) {
                        Block block = world.getBlockState(pos).getBlock();
                        if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
                            arpg$checkGemsparkIngridients(world, pos);
                        }
                    }
                } else {
                    Block block = world.getBlockState(pos).getBlock();
                    if (block == Blocks.FIRE) {
                        world.setBlockToAir(pos);
                    } else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
                        arpg$checkGemsparkIngridients(world, pos);
                    }

                    cir.setReturnValue(true);
                }
            } else {
                if (world.getBlockState(pos).getBlock() == Blocks.FIRE) {
                    world.setBlockToAir(pos);
                }

                world.playSound(null, pos, Sounds.burn, SoundCategory.BLOCKS, 0.8F, 0.9F + world.rand.nextFloat() / 5.0F);
                ((EntityItem) (Object) this).setDead();
                EntityItem dust = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemsRegister.MAGIC_POWDER));
                dust.setFire(4);
                world.spawnEntity(dust);
            }
        }
    }

    @Unique
    private static void arpg$checkGemsparkIngridients(World world, BlockPos pos) {
        AxisAlignedBB findGems = new AxisAlignedBB(pos);
        List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, findGems);
        boolean rhinestone = true;
        boolean amethyst = true;
        boolean topaz = true;
        boolean ruby = true;
        boolean sapphire = true;
        boolean citrine = true;
        boolean emerald = true;
        boolean diamond = true;
        boolean haspowder = false;
        int count = 0;
        if (!list.isEmpty()) {
            for (EntityItem eitem : list) {
                Item gem = eitem.getItem().getItem();
                if (rhinestone && gem == ItemsRegister.RHINESTONE) {
                    rhinestone = false;
                    count++;
                }

                if (amethyst && gem == ItemsRegister.AMETHYST) {
                    amethyst = false;
                    count++;
                }

                if (topaz && gem == ItemsRegister.TOPAZ) {
                    topaz = false;
                    count++;
                }

                if (ruby && gem == ItemsRegister.RUBY) {
                    ruby = false;
                    count++;
                }

                if (sapphire && gem == ItemsRegister.SAPPHIRE) {
                    sapphire = false;
                    count++;
                }

                if (citrine && gem == ItemsRegister.CITRINE) {
                    citrine = false;
                    count++;
                }

                if (emerald && gem == Items.EMERALD) {
                    emerald = false;
                    count++;
                }

                if (diamond && gem == Items.DIAMOND) {
                    diamond = false;
                    count++;
                }

                if (gem == ItemsRegister.MAGIC_POWDER) {
                    haspowder = true;
                }
            }
        }

        if (count >= 4 && haspowder) {
            for (EntityItem eitem : list) {
                eitem.setDead();
            }

            world.setBlockState(pos, BlocksRegister.GEMSPARK_BLOCK.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.8F, 0.9F + world.rand.nextFloat() / 5.0F);
        }
    }
    
}
