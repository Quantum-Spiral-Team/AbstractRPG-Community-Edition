package com.vivern.arpg.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.items.models.AbstractMobModel;
import com.vivern.arpg.items.models.ModelWings;
import com.vivern.arpg.main.*;
import com.vivern.arpg.network.packet.PacketBaublesNbtToClients;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

//TODO я менял логику onWornTick, нужно будет проверить её работу (скорее всего не работает и придётся что-то придумывать). То же самое для ВСЕХ крыльев
public abstract class AbstractWings extends Item implements IWings, IBauble {

    public int flapPeriod = 8;
    public float flapPeriodFloat = 1.7F;

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IEnergyItem.addRFInformation(stack, worldIn, tooltip, flagIn);
    }

    @SideOnly(Side.CLIENT)
    public void renderDefaultWings(ResourceLocation texture, ModelWings model, ItemStack stack, EntityPlayer player, IRenderBauble.RenderType type, float partialTicks, @Nullable ResourceLocation overlayTexture, int overlayAddLight) {
        if (type == IRenderBauble.RenderType.BODY) {
            float flytime = GetMOP.partial((float) NBTHelper.GetNBTint(stack, "cflytime"), (float) NBTHelper.GetNBTint(stack, "prevcflytime"), partialTicks);
            float flyupStarted = GetMOP.partial((float) NBTHelper.GetNBTint(stack, "flyupStarted"), (float) NBTHelper.GetNBTint(stack, "prevflyupStarted"), partialTicks);
            float glidingRaw = GetMOP.partial(NBTHelper.GetNBTfloat(stack, "gliding"), NBTHelper.GetNBTfloat(stack, "prevgliding"), partialTicks);
            float expand = (-player.rotationPitch + 90.0F) / 180.0F;
            float nofly;
            if (!player.isElytraFlying()) {
                nofly = 1.0F;
            } else {
                float ticksElytraFlying = player.getTicksElytraFlying();
                if (ticksElytraFlying > 0.0F) {
                    ticksElytraFlying += partialTicks;
                }

                nofly = Math.max(0.0F, 1.0F - ticksElytraFlying / 20.0F);
            }

            expand /= 1.0F + nofly * 1.5F;
            float upward = GetMOP.getFromTo(flytime, 0.0F, 5.0F) * GetMOP.getFromTo(flyupStarted, 0.0F, (float) (this.flapPeriod / 2));
            float upwardProgress = flytime / this.flapPeriodFloat;
            float gliding = glidingRaw / 8.0F * nofly;
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            IRenderBauble.Helper.rotateIfSneaking(player);
            if (player.isSneaking()) {
                GlStateManager.translate(0.0F, -0.4F, 0.2F);
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GlStateManager.scale(0.08F, 0.08F, 0.08F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.5F, -0.5F);
            float livingAnimation = MathHelper.sin((player.ticksExisted + partialTicks) / 20.0F) * 0.02F;
            model.renderWings(player, Math.max(expand, gliding * 0.65F) + livingAnimation, nofly, upward * nofly, upwardProgress, gliding, player.limbSwing, GetMOP.partial(player.limbSwingAmount, player.prevLimbSwingAmount, partialTicks), 1.0F);
            if (overlayTexture != null) {
                GlStateManager.pushMatrix();
                GL11.glDisable(2896);
                GlStateManager.depthMask(false);
                Minecraft.getMinecraft().renderEngine.bindTexture(overlayTexture);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
                AbstractMobModel.light(overlayAddLight, true);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
                model.renderWings(player, Math.max(expand, gliding * 0.65F) + livingAnimation, nofly, upward * nofly, upwardProgress, gliding, player.limbSwing, GetMOP.partial(player.limbSwingAmount, player.prevLimbSwingAmount, partialTicks), 1.0F);
                Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
                AbstractMobModel.returnlight();
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.depthMask(true);
                GL11.glEnable(2896);
                GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
                GlStateManager.popMatrix();
            }

            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return player instanceof EntityPlayer && IWings.isEquippableWithChestplate(((EntityPlayer) player).inventory.armorInventory.get(2).getItem());
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            int bitstate = 0;
            boolean flying = player.getDataManager().get(PropertiesRegistry.FLYING);
            boolean click = Keys.isPressedDoubleJump(player);
            if (click && !flying && this.canUseWings(itemstack, player)) {
                if (player.isSneaking()) {
                    if (!entity.world.isRemote) {
                        player.getDataManager().set(PropertiesRegistry.FLYING, true);
                    } else {
                        AbstractRPG.proxy.playWingsSound(player, this.getWingsSound(player));
                    }
                } else if (!entity.world.isRemote) {
                    NBTHelper.GiveNBTint(itemstack, 0, "flytime");
                    NBTHelper.GiveNBTint(itemstack, 0, "lastbitstate");
                    int flytime = NBTHelper.GetNBTint(itemstack, "flytime");
                    double maxUpward = this.getMaxUpwardMotion(itemstack);
                    int maxFlytime = this.getMaxFlyTime(itemstack);
                    if (flytime < maxFlytime) {
                        if (player.motionY < maxUpward) {
                            double motionAddVariant = player.motionY < 0.0 ? this.getFallingMotionAdd(itemstack) : this.getUpwardMotionAdd(itemstack);
                            player.motionY = player.motionY + Math.min(motionAddVariant, maxUpward - player.motionY);
                            player.fallDistance = 0.0F;
                            player.velocityChanged = true;
                            NBTHelper.AddNBTint(itemstack, 1, "flytime");
                            bitstate |= 1;
                            this.onFlyingTick(itemstack, player, false);
                        }
                    } else if (player.motionY < 0.0) {
                        player.motionY = player.motionY * this.getFallingMotionSlowdown(itemstack);
                        player.velocityChanged = true;
                        player.fallDistance = 0.0F;
                        bitstate |= 2;
                    }
                }
            }

            if (flying) {
                this.onFlyingTick(itemstack, player, true);
                if (player.moveForward < 0.0F) {
                    player.getDataManager().set(PropertiesRegistry.FLYING, false);
                    bitstate |= 4;
                }
            }

            if (player.onGround || !player.isEntityAlive() || player.isPlayerSleeping() || player.isRiding()) {
                if (!entity.world.isRemote) {
                    player.getDataManager().set(PropertiesRegistry.FLYING, false);
                }

                NBTHelper.SetNBTint(itemstack, 0, "flytime");
                bitstate |= 4;
            }

            if (player.isInWater() && player.ticksExisted % 25 == 0) {
                NBTHelper.SetNBTint(itemstack, 0, "flytime");
            }

            if (!entity.world.isRemote) {
                ItemStack chestplate = player.inventory.armorInventory.get(2);
                if (!chestplate.isEmpty() && !IWings.isEquippableWithChestplate(chestplate.getItem())) {
                    IWings.chestplateReturnToInv(player);
                }

                int lastBitState = NBTHelper.GetNBTint(itemstack, "lastbitstate");
                if (bitstate != lastBitState) {
                    PacketBaublesNbtToClients.send(player, 64.0, 3, 's', bitstate, Weapons.EnumMathOperation.NONE, 5);
                    NBTHelper.SetNBTint(itemstack, bitstate, "lastbitstate");
                }
            } else {
                if (player.motionY >= 0.0) {
                    player.fallDistance = 0.0F;
                }

                int state = NBTHelper.GetNBTint(itemstack, "s");
                boolean isUpwarded = (state & 1) != 0;
                boolean isGliding = (state & 2) != 0;
                boolean isResetted = (state & 4) != 0;
                NBTHelper.GiveNBTint(itemstack, 0, "cflytime");
                NBTHelper.GiveNBTfloat(itemstack, 0.0F, "gliding");
                NBTHelper.GiveNBTint(itemstack, 0, "flyupStarted");
                NBTHelper.GiveNBTint(itemstack, 0, "prevcflytime");
                NBTHelper.GiveNBTfloat(itemstack, 0.0F, "prevgliding");
                NBTHelper.GiveNBTint(itemstack, 0, "prevflyupStarted");
                int cflytime = NBTHelper.GetNBTint(itemstack, "cflytime");
                float gliding = NBTHelper.GetNBTfloat(itemstack, "gliding");
                int flyupStarted = NBTHelper.GetNBTint(itemstack, "flyupStarted");
                NBTHelper.SetNBTint(itemstack, cflytime, "prevcflytime");
                NBTHelper.SetNBTfloat(itemstack, gliding, "prevgliding");
                NBTHelper.SetNBTint(itemstack, flyupStarted, "prevflyupStarted");
                if (flyupStarted > 0) {
                    NBTHelper.AddNBTint(itemstack, 1, "cflytime");
                    NBTHelper.AddNBTint(itemstack, -1, "flyupStarted");
                    if (flyupStarted == 1) {
                        NBTHelper.SetNBTint(itemstack, 0, "cflytime");
                    }
                }

                if (isGliding) {
                    if (gliding < 6.0F) {
                        NBTHelper.AddNBTfloat(itemstack, 1.0F, "gliding");
                    } else if (gliding < 7.0F) {
                        NBTHelper.AddNBTfloat(itemstack, 0.05F, "gliding");
                    } else if (gliding < 8.0F) {
                        NBTHelper.AddNBTfloat(itemstack, 0.025F, "gliding");
                    }
                } else if (gliding > 0.0F) {
                    NBTHelper.SetNBTfloat(itemstack, Math.max(gliding - 1.0F, 0.0F), "gliding");
                }

                if (!isResetted && isUpwarded) {
                    NBTHelper.SetNBTint(itemstack, this.flapPeriod, "flyupStarted");
                }

                this.tryPlayFlapSound(player, itemstack, cflytime);
            }
        }
    }

    public void onFlyingTick(ItemStack itemstack, EntityPlayer player, boolean likeElytra) {
        if (player.ticksExisted % 20 == 0) {
            itemstack.damageItem(1, player);
            if (itemstack.getMaxDamage() - itemstack.getItemDamage() < 20 && Minecraft.getMinecraft().ingameGUI != null) {
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("\u00A74Your wings are almost broken", false);
            }
        }
    }

    public boolean canUseWings(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    public abstract double getMaxUpwardMotion(ItemStack stack);

    public abstract double getUpwardMotionAdd(ItemStack stack);

    public abstract double getFallingMotionAdd(ItemStack stack);

    @Override
    public abstract int getMaxFlyTime(ItemStack stack);

    public abstract double getFallingMotionSlowdown(ItemStack stack);

    @SideOnly(Side.CLIENT)
    protected abstract @Nullable MovingSound getWingsSound(EntityPlayer player);

    public void tryPlayFlapSound(EntityPlayer player, ItemStack itemstack, int clientFlyTime) {
        NBTHelper.giveNBTboolean(itemstack, false, "soundPlayed");
        boolean soundPlayed = NBTHelper.GetNBTboolean(itemstack, "soundPlayed");
        double soundCycle = clientFlyTime / this.flapPeriodFloat % (Math.PI * 2);
        if (soundCycle >= 1.8) {
            if (!soundPlayed) {
                this.playFlapSound(player);
                NBTHelper.SetNBTboolean(itemstack, true, "soundPlayed");
            }
        } else {
            NBTHelper.SetNBTboolean(itemstack, false, "soundPlayed");
        }
    }

    public void playFlapSound(EntityPlayer player) {
        player.world.playSound(player.posX, player.posY, player.posZ, Sounds.wings, SoundCategory.PLAYERS, 0.5F, 0.95F + itemRand.nextFloat() * 0.1F, false);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BODY;
    }

    @SideOnly(Side.CLIENT)
    public static class WingsSound extends MovingSound {

        private final EntityPlayer player;
        private int time;
        Random rand = new Random();

        public WingsSound(EntityPlayer player, SoundEvent sound) {
            super(sound, SoundCategory.PLAYERS);
            this.player = player;
            this.repeat = true;
            this.repeatDelay = 0;
            this.volume = 0.1F;
        }

        @Override
        public void update() {
            this.time++;
            if (!this.player.isDead && (this.time <= 20 || this.player.isElytraFlying())) {
                this.xPosF = (float) this.player.posX;
                this.yPosF = (float) this.player.posY;
                this.zPosF = (float) this.player.posZ;
                float f = MathHelper.sqrt(this.player.motionX * this.player.motionX + this.player.motionZ * this.player.motionZ + this.player.motionY * this.player.motionY);
                float f1 = f / 2.0F;
                if (f1 > 0.7F && Boom.lastTick == 0) {
                    Boom.lastTick = 8;
                    Boom.frequency = 4.0F;
                    Boom.x = (float) this.rand.nextGaussian();
                    Boom.y = (float) this.rand.nextGaussian();
                    Boom.z = (float) this.rand.nextGaussian();
                    Boom.power = f1 / 25.0F;
                }

                if (f >= 0.01) {
                    this.volume = MathHelper.clamp(f1 * f1, 0.0F, 1.0F);
                } else {
                    this.volume = 0.0F;
                }

                if (this.time < 20) {
                    this.volume = 0.0F;
                } else if (this.time < 40) {
                    this.volume = (float) (this.volume * ((this.time - 20) / 20.0));
                }

                if (this.volume > 0.8F) {
                    this.pitch = 1.0F + (this.volume - 0.8F);
                } else {
                    this.pitch = 1.0F;
                }
            } else {
                this.donePlaying = true;
            }
        }

    }

}
