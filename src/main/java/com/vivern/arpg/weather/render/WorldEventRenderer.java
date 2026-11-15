package com.vivern.arpg.weather.render;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.weather.WorldEvent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SideOnly(Side.CLIENT)
public abstract class WorldEventRenderer<T extends WorldEvent> extends IRenderHandler {

    public static final float FADE_PER_SECOND = 0.15F;

    protected final T event;
    protected final AbstractWorldProvider provider;

    public float showness;
    protected int rainSoundCounter;
    @Nullable
    protected SoundEvent soundRain;
    @Nullable
    protected SoundEvent soundRainAbove;

    private long lastFadeNanos;

    protected WorldEventRenderer(T event, AbstractWorldProvider provider) {
        this.event = event;
        this.provider = provider;
    }

    public boolean isVisible() {
        return this.event.isStarted || this.showness > 0.0F || this.event.snapVisual;
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        this.updateShowness();
        this.renderClouds(partialTicks, world, mc);
    }

    private void updateShowness() {
        long now = System.nanoTime();
        float dt = this.lastFadeNanos == 0L ? 0.0F : Math.min((now - this.lastFadeNanos) / 1.0E9F, 0.1F);
        this.lastFadeNanos = now;

        if (this.event.snapVisual) {
            this.event.snapVisual = false;
            this.showness = this.event.isStarted ? 1.0F : 0.0F;
            if (!this.event.isStarted) {
                this.stopRenderClouds();
            }

            return;
        }

        if (this.event.isStarted) {
            if (this.showness < 1.0F) {
                this.showness = Math.min(1.0F, this.showness + FADE_PER_SECOND * dt);
            }
        } else if (this.showness > 0.0F) {
            this.showness = Math.max(0.0F, this.showness - FADE_PER_SECOND * dt);
            if (this.showness <= 0.0F) {
                this.stopRenderClouds();
            }
        }
    }

    public void renderClouds(float partialTicks, WorldClient world, Minecraft mc) {
    }

    public void stopRenderClouds() {
    }

    public void spawnSnowRainParticle(BlockPos pos, int allUsedPosesCount, float dist) {
    }

    public void onUpdateClient(EntityPlayer player) {
        if (this.soundRain != null) {
            this.addRainSoundEffect(GetMOP.rand, 3, this.soundRain, this.soundRainAbove);
        }
    }

    /** Перенесено из старого WorldEvent без изменений логики. */
    public void addRainSoundEffect(Random random, int rarity, SoundEvent sound, SoundEvent soundAbove) {
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        World world = Minecraft.getMinecraft().world;
        BlockPos blockpos = new BlockPos(entity);
        double d0 = 0.0;
        double d1 = 0.0;
        double d2 = 0.0;
        int j = 0;
        int k = 20;
        if (Minecraft.getMinecraft().gameSettings.particleSetting == 1) {
            k >>= 1;
        } else if (Minecraft.getMinecraft().gameSettings.particleSetting == 2) {
            k = 0;
        }

        if (random.nextInt(rarity) < this.rainSoundCounter++) {
            for (int l = 0; l < k; l++) {
                BlockPos blockpos1 = world.getPrecipitationHeight(blockpos.add(random.nextInt(10) - random.nextInt(10), 0, random.nextInt(10) - random.nextInt(10)));
                BlockPos blockpos2 = blockpos1.down();
                IBlockState iblockstate = world.getBlockState(blockpos2);
                if (blockpos1.getY() <= blockpos.getY() + 10 && blockpos1.getY() >= blockpos.getY() - 10) {
                    double d3 = random.nextDouble();
                    double d4 = random.nextDouble();
                    AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(world, blockpos2);
                    if (iblockstate.getMaterial() != Material.LAVA && iblockstate.getBlock() != Blocks.MAGMA && iblockstate.getMaterial() != Material.AIR) {
                        random.nextInt(++j);
                        d0 = blockpos2.getX() + d3;
                        d1 = blockpos2.getY() + 0.1F + axisalignedbb.maxY - 1.0;
                        d2 = blockpos2.getZ() + d4;
                        break;
                    }
                }
            }

            if (j > 0) {
                this.rainSoundCounter = 0;
                if (d1 > blockpos.getY() + 1 && world.getPrecipitationHeight(blockpos).getY() > MathHelper.floor(blockpos.getY())) {
                    world.playSound(d0, d1, d2, soundAbove, SoundCategory.WEATHER, 0.1F, 0.5F, false);
                } else {
                    world.playSound(d0, d1, d2, sound, SoundCategory.WEATHER, 0.2F, 1.0F, false);
                }
            }
        }
    }

}
