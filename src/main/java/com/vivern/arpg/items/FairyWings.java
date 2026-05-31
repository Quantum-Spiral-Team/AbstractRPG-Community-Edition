package com.vivern.arpg.items;

import baubles.api.render.IRenderBauble;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.vivern.arpg.Tags;
import com.vivern.arpg.items.models.FairyWingsModel;
import com.vivern.arpg.main.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.UUID;

public class FairyWings extends AbstractWings implements IAttributedBauble, IRenderBauble {

    @SideOnly(Side.CLIENT)
    public static FairyWingsModel model;
    public static final ResourceLocation texture = new ResourceLocation(Tags.MOD_ID, "textures/fairy_wings_model_tex.png");
    private static final String NAME = "fairy_wings";

    public FairyWings() {
        this.setRegistryName(NAME);
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey(NAME);
        this.setMaxDamage(2500);
        this.setMaxStackSize(1);

        this.flapPeriod = 8;
        this.flapPeriodFloat = 1.7F;
    }

    @SideOnly(Side.CLIENT)
    private static FairyWingsModel getModel() {
        if (model == null) {
            model = new FairyWingsModel();
        }
        return model;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        if (type == RenderType.BODY) {
            float sweep = (-player.rotationPitch + 90.0F) / 180.0F;
            float animationFlap = NBTHelper.GetNBTint(stack, "animation_flap");
            float normalFlap = (float) Math.sin(animationFlap * 0.4F) * animationFlap;
            float animationForward = NBTHelper.GetNBTint(stack, "animation_forward");
            int flyingTicks = NBTHelper.GetNBTint(stack, "animation_in_air");
            float notFlying = 1.0F - flyingTicks / 10.0F;
            GlStateManager.pushMatrix();
            GL11.glEnable(3042);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            if (player.isSneaking()) {
                GlStateManager.translate(0.0F, -0.4F, 0.2F);
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0, -1.2F, -1.0);
            getModel().render(player, sweep, normalFlap, animationForward, notFlying, player.isSneaking() ? 0.7F : 0.0F, 1.0F);
            GL11.glDisable(3042);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
        super.onWornTick(itemstack, entityIn);

        if (!(entityIn instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entityIn;
        Item itemIn = itemstack.getItem();

        int maxBoost = 4000;
        int boostsPerOne = 40;
        float velocity = 0.2F;

        NBTHelper.GiveNBTint(itemstack, 0, "boost");
        NBTHelper.GiveNBTint(itemstack, 0, "animation_flap");
        NBTHelper.GiveNBTint(itemstack, 0, "animation_forward");
        NBTHelper.GiveNBTint(itemstack, 0, "animation_in_air");

        int animationFlap = NBTHelper.GetNBTint(itemstack, "animation_flap");
        int animationForward = NBTHelper.GetNBTint(itemstack, "animation_forward");

        if (animationFlap > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "animation_flap");
        }
        if (animationForward > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "animation_forward");
        }

        if (NBTHelper.GetNBTint(itemstack, "animation_in_air") > 0 && player.onGround) {
            NBTHelper.AddNBTint(itemstack, -1, "animation_in_air");
        }

        if (NBTHelper.GetNBTint(itemstack, "animation_in_air") < 10 && !player.onGround) {
            NBTHelper.AddNBTint(itemstack, 1, "animation_in_air");
        }

        int boost = NBTHelper.GetNBTint(itemstack, "boost");
        if (boost < maxBoost) {
            NBTHelper.AddNBTint(itemstack, 1, "boost");
        }

        int damage = itemstack.getItemDamage();
        boolean flying = player.getDataManager().get(PropertiesRegistry.FLYING);

        if (damage < this.getMaxDamage(itemstack)) {
            if (player.ticksExisted % 100 == 0 && flying && !player.world.isRemote) {
                itemstack.damageItem(1, player);
            }

            if (flying) {
                float f = MathHelper.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ + player.motionY * player.motionY);
                float f1 = f / 2.0F;
                if (f1 > 0.9F) {
                    multipleMotion(player, 0.95F);
                }
            }
        } else {
            NBTHelper.SetNBTint(itemstack, 0, "boost");
            if (!player.world.isRemote) {
                player.getDataManager().set(PropertiesRegistry.FLYING, false);
            }
        }

        if (player.onGround) {
            if (boost < maxBoost) {
                NBTHelper.AddNBTint(itemstack, 49, "boost");
            }
            if (!player.world.isRemote) {
                player.getDataManager().set(PropertiesRegistry.FLYING, false);
            }
        }

        if (player.world.isRemote) {
            this.handleClientMovementTick(itemstack, player, damage, boostsPerOne, velocity, flying, itemIn);
        }
    }

    @SideOnly(Side.CLIENT)
    private void handleClientMovementTick(ItemStack itemstack, EntityPlayer player, int damage, int boostsPerOne, float velocity, boolean flying, Item itemIn) {
        boolean cool = player.getCooldownTracker().hasCooldown(itemIn);
        boolean clickForward = GameSettings.isKeyDown(Keys.FORWARD);
        boolean clickSprint = GameSettings.isKeyDown(Keys.SPRINT);
        boolean clickBack = GameSettings.isKeyDown(Keys.BACK);
        boolean jump = GameSettings.isKeyDown(Keys.JUMP);
        int boost = NBTHelper.GetNBTint(itemstack, "boost");

        if (!player.onGround && clickSprint && damage < this.getMaxDamage(itemstack) && !flying && !cool) {
            player.getDataManager().set(PropertiesRegistry.FLYING, true);
            flying = true;
            if (player instanceof EntityPlayerSP) {
                Minecraft.getMinecraft().getSoundHandler().playSound(new FairyWingsSound((EntityPlayerSP) player));
            }
        }

        if (damage < this.getMaxDamage(itemstack) && boost > boostsPerOne) {
            boolean sucess = false;
            if (!clickBack || !flying && !jump) {
                if (clickForward && flying) {
                    if (!cool) {
                        float rotationYawIn = player.rotationYaw;
                        float rotationPitchIn = player.rotationPitch;
                        float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                        float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
                        float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                        float f = MathHelper.sqrt(x * x + y * y + z * z);
                        x /= f; y /= f; z /= f;
                        x *= velocity; y *= velocity; z *= velocity;
                        multipleMotion(player, 0.9F);
                        player.motionX += x; player.motionY += y; player.motionZ += z;
                        player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 0.5F, 0.9F + itemRand.nextFloat() / 5.0F, false);
                        sucess = true;
                    }

                    if (NBTHelper.GetNBTint(itemstack, "animation_forward") == 0) {
                        NBTHelper.SetNBTint(itemstack, 8, "animation_forward");
                    }
                } else if (jump) {
                    if (!cool) {
                        player.motionY *= 0.8;
                        player.motionY += velocity + 0.15;
                        player.fallDistance = 0.0F;
                        player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 0.5F, 0.9F + itemRand.nextFloat() / 5.0F, false);
                        sucess = true;
                    }

                    if (flying) {
                        if (NBTHelper.GetNBTint(itemstack, "animation_flap") == 0) {
                            NBTHelper.SetNBTint(itemstack, 8, "animation_flap");
                        }
                    } else if (NBTHelper.GetNBTint(itemstack, "animation_forward") == 0) {
                        NBTHelper.SetNBTint(itemstack, 8, "animation_forward");
                    }
                }
            } else {
                if (!cool) {
                    float rotationYawIn = player.rotationYaw;
                    float rotationPitchIn = player.rotationPitch;
                    float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                    float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
                    float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                    float f = MathHelper.sqrt(x * x + y * y + z * z);
                    x /= f; y /= f; z /= f;
                    x *= -velocity; y *= -velocity; z *= -velocity;
                    multipleMotion(player, 0.9F);
                    player.motionX += x; player.motionY += y; player.motionZ += z;
                    player.world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 0.5F, 0.9F + itemRand.nextFloat() / 5.0F, false);
                    sucess = true;
                }

                if (NBTHelper.GetNBTint(itemstack, "animation_flap") == 0) {
                    NBTHelper.SetNBTint(itemstack, 8, "animation_flap");
                }
            }

            if (sucess) {
                NBTHelper.AddNBTint(itemstack, -boostsPerOne, "boost");
                player.getCooldownTracker().setCooldown(itemIn, 4);
            }

            if (player.isSneaking() && flying) {
                multipleMotion(player, 0.95F);
                player.motionY += 0.05;
                player.fallDistance = 0.0F;
                NBTHelper.AddNBTint(itemstack, -5, "boost");
                if (NBTHelper.GetNBTint(itemstack, "animation_flap") == 0) {
                    NBTHelper.SetNBTint(itemstack, 8, "animation_flap");
                }
            }
        }
    }

    public static void multipleMotion(EntityLivingBase entity, float mult) {
        entity.motionX *= mult;
        entity.motionY *= mult;
        entity.motionZ *= mult;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        NBTHelper.SetNBTint(stack, 0, "boost");
    }

    @Override
    public void onFlyingTick(ItemStack itemstack, EntityPlayer player, boolean likeElytra) {}

    @Override
    public IAttribute getAttribute() {
        return PropertiesRegistry.JUMP_HEIGHT;
    }

    @Override
    public double value() {
        return 0.1;
    }

    @Override
    public int operation() {
        return 0;
    }

    @Override
    public String itemName() {
        return NAME;
    }

    @Override
    public boolean useMultimap() {
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
        multimap.put(PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(uuid, "jump height modifier", 0.1, 0));
        multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(uuid, "magic power modifier", 0.1, 0));
        return multimap;
    }

    public int getChargesCount(ItemStack stack) {
        return NBTHelper.GetNBTint(stack, "boost") / 1000;
    }

    @Override
    public boolean canUseWings(ItemStack itemstack, EntityPlayer player) {
        return false;
    }

    @Override
    public double getMaxUpwardMotion(ItemStack stack) {
        return 0.0;
    }

    @Override
    public double getUpwardMotionAdd(ItemStack stack) {
        return 0.0;
    }

    @Override
    public double getFallingMotionAdd(ItemStack stack) {
        return 0.0;
    }

    @Override
    public int getMaxFlyTime(ItemStack stack) {
        return 0;
    }

    @Override
    public double getFallingMotionSlowdown(ItemStack stack) {
        return 1.0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected @Nullable MovingSound getWingsSound(EntityPlayer player) {
        if (player instanceof EntityPlayerSP) {
            return new FairyWingsSound(player);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static class FairyWingsSound extends MovingSound {

        private final EntityPlayer player;
        private int time;
        Random rand = new Random();

        public FairyWingsSound(EntityPlayer player) {
            super(Sounds.toxic_wings_flying, SoundCategory.PLAYERS);
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

                float f2 = 0.8F;
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
