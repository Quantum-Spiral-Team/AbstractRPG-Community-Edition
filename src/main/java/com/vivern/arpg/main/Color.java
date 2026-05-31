package com.vivern.arpg.main;

import com.vivern.arpg.Tags;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.items.ItemBullet;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.shader.ShaderMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
public class Color {

    static List<EntityLivingBase> list = new ArrayList<>();
    private static final ResourceLocation textur = new ResourceLocation("arpg:textures/freezing.png");
    private static final ResourceLocation snowflake2 = new ResourceLocation("arpg:textures/snowflake2.png");
    private static final ResourceLocation icecube = new ResourceLocation("arpg:textures/ice_cube.png");
    private static final ResourceLocation snowflake3 = new ResourceLocation("arpg:textures/snowflake3.png");
    private static final ResourceLocation minisand1 = new ResourceLocation("arpg:textures/minisand1.png");
    private static final ResourceLocation minisand2 = new ResourceLocation("arpg:textures/minisand2.png");
    private static final ResourceLocation minisand3 = new ResourceLocation("arpg:textures/minisand3.png");
    private static final ResourceLocation acidbubble = new ResourceLocation("arpg:textures/de_acid_bubble.png");
    private static final ResourceLocation shockbeam = new ResourceLocation("arpg:textures/shock.png");
    private static final ResourceLocation electr = new ResourceLocation("arpg:textures/blueexplode2.png");
    private static final ResourceLocation deelectric = new ResourceLocation("arpg:textures/blueexplode4.png");
    private static final ResourceLocation gore = new ResourceLocation("arpg:textures/gore.png");
    private static final ResourceLocation drop = new ResourceLocation("arpg:textures/normaldrop.png");
    private static ResourceLocation blood = new ResourceLocation("arpg:textures/blood.png");
    private static final Random RANDOM = new Random();

    private static final int[] GEM_COLORS = {0x8AF2E0, // 0: red 0.54, green 0.95, blue 0.88
            0xE60D1A, // 1: red 0.9,  green 0.05, blue 0.1
            0x0D0DE6, // 2: red 0.05, green 0.05, blue 0.9
            0x0DE633, // 3: red 0.05, green 0.9,  blue 0.2
            0xE6D94D, // 4: red 0.9,  green 0.85, blue 0.3
            0x6614D9, // 5: red 0.4,  green 0.08, blue 0.85
            0xE680A6, // 6: red 0.9,  green 0.5,  blue 0.65
            0xE6E8D9  // 7: red 0.9,  green 0.91, blue 0.85
    };

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {

        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? NBTHelper.GetNBTint(stack, "color") : NBTHelper.GetNBTint(stack, "colorover"), ItemsRegister.GEOMANTIC_CRYSTAL);

        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(stack, "bullet"));
            return tintIndex == 1 && bullet != null ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
        }, ItemsRegister.ADAMANTIUM_ROUNDS);

        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(stack, "bullet"));
            return tintIndex == 1 && bullet != null ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
        }, ItemsRegister.BUCKSHOT);

        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 0)
                return 0xFFFFFF;

            int gem = NBTHelper.GetNBTint(stack, "gem");

            if (gem >= 0 && gem < GEM_COLORS.length) {
                return GEM_COLORS[gem];
            }

            return 0xFFFFFF;
        }, ItemsRegister.ELEMENTS_BOOK);

        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(stack, "bullet"));
            return tintIndex == 1 && bullet != null ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
        }, ItemsRegister.HYDRAULIC_SHOTGUN_CLIP);
    }

    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
        EntityLivingBase entity = event.getEntity();
        int mobDeathTime = entity.deathTime;
        if (mobDeathTime > 0) {
            for (DeathEffect deathEffect : DeathEffect.REGISTRY) {
                if (deathEffect.exist(entity)) {
                    deathEffect.onRenderLivingPre(event, entity);
                }
            }
        } else if (DeathEffects.getlistEFFECTshock().contains(entity)) {
            ShaderMain.InverseShader.start();
        }

        GlStateManager.color((float) entity.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX).getAttributeValue(), (float) entity.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX).getAttributeValue(), (float) entity.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX).getAttributeValue());
    }

    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<EntityLivingBase> event) {
        float partialTicks = event.getPartialRenderTick();
        EntityLivingBase entity = event.getEntity();
        EntityPlayer player = Minecraft.getMinecraft().player;
        //why unused ??
        //      double x = entity.prevPosX
        //         - player.prevPosX
        //         + (entity.posX - player.posX)
        //         - (entity.prevPosX - player.prevPosX) * partialTicks;
        //      double y = entity.prevPosY
        //         - player.prevPosY
        //         + (entity.posY - player.posY)
        //         - (entity.prevPosY - player.prevPosY) * partialTicks;
        //      double z = entity.prevPosZ
        //         - player.prevPosZ
        //         + (entity.posZ - player.posZ)
        //         - (entity.prevPosZ - player.prevPosZ) * partialTicks;
        float entityYaw = entity.rotationYaw;
        if (list.contains(event.getEntity())) {
            ModelBase model = event.getRenderer().getMainModel();
            GlStateManager.pushMatrix();
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            doRender(entity, event.getX(), event.getY(), event.getZ(), entityYaw, partialTicks, model, textur, 90.0F, false);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GlStateManager.popMatrix();
        }

        if (ShaderMain.InverseShader.started) {
            ShaderMain.InverseShader.stop();
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingUpdateEvent event) {
        EntityLivingBase base = event.getEntityLiving();
        double x = base.posX;
        double y = base.posY;
        double z = base.posZ;
        World world = base.getEntityWorld();
        if (DeathEffects.getlistEFFECTshock().contains(base)) {
            GUNParticle bigBoom = new GUNParticle(electr, base.height, 0.0F, 2, 240, world, x, y + base.height / 2.0F, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, RANDOM.nextInt(360));
            world.spawnEntity(bigBoom);
            if (AnimationTimer.normaltick % 2 == 0) {
                DeathEffects.getlistEFFECTshock().remove(base);
            }
        }

        if (base.deathTime > 0) {
            for (DeathEffect deathEffect : DeathEffect.REGISTRY) {
                deathEffect.removeAllUnused();
                if (deathEffect.exist(base)) {
                    deathEffect.onLivingUpdate(world, event, base, x, y, z);
                }

                if (base.deathTime >= 20) {
                    deathEffect.remove(base);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, ModelBase mainModel, ResourceLocation texture, float maxangle, boolean electric) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        mainModel.swingProgress = getSwingProgress(entity, partialTicks);
        boolean shouldSit = entity.isRiding() && entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit();
        mainModel.isRiding = shouldSit;
        mainModel.isChild = entity.isChild();
        if (texture != null) {
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        }

        GlStateManager.glTexCoord2f(0.0F, 3.0F);

        try {
            float f = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;
            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
                f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);
                if (f3 < -85.0F) {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F) {
                    f3 = 85.0F;
                }

                f = f1 - f3;
                if (f3 * f3 > 2500.0F) {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            renderLivingAt(entity, x, y, z);
            float f8 = handleRotationFloat(entity, partialTicks);
            applyRotations(entity, f8, f, partialTicks, maxangle);
            float f4 = prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;
            if (!entity.isRiding()) {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
                if (entity.isChild()) {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F) {
                    f5 = 1.0F;
                }

                f2 = f1 - f;
            }

            GlStateManager.enableAlpha();
            if (electric) {
                f6 += (float) Math.sin(AnimationTimer.tick) * 13.0F;
                f2 += (float) Math.sin(AnimationTimer.tick) * 15.0F;
                f7 = f7 - entity.deathTime * 3.5F + (float) Math.sin(AnimationTimer.tick) * 9.0F;
                f5 *= 2.0F;
                float tr1 = (float) Math.sin(AnimationTimer.tick * 1.3F) / 25.0F;
                float tr2 = (float) Math.sin(AnimationTimer.tick * 1.1F) / 25.0F;
                GlStateManager.translate(tr1, tr2, 0.04F - tr1);
                float sc1 = 1.0F + Math.max(entity.deathTime - 15, 0) / 10.0F;
                GlStateManager.scale(sc1, 1.0F, sc1);
            }

            mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);
            renderModel(entity, f6, f5, f8, f2, f7, f4, mainModel);
            GlStateManager.depthMask(true);
            GlStateManager.disableRescaleNormal();
        } catch (Exception ignored) {
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
        float f = yawOffset - prevYawOffset;

        while (f < -180.0F) {
            f += 360.0F;
        }

        while (f >= 180.0F) {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }

    @SideOnly(Side.CLIENT)
    protected static void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, ModelBase mainModel) {
        mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    }

    @SideOnly(Side.CLIENT)
    protected static void renderLivingAt(EntityLivingBase entityLivingBaseIn, double x, double y, double z) {
        GlStateManager.translate((float) x, (float) y, (float) z);
    }

    protected static float handleRotationFloat(EntityLivingBase livingBase, float partialTicks) {
        return livingBase.ticksExisted + partialTicks;
    }

    protected static float getSwingProgress(EntityLivingBase livingBase, float partialTickTime) {
        return livingBase.getSwingProgress(partialTickTime);
    }

    @SideOnly(Side.CLIENT)
    protected static void applyRotations(EntityLivingBase entityLiving, float ageInTicks, float rotationYaw, float partialTicks, float maxangle) {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        if (entityLiving.deathTime > 0) {
            float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            GlStateManager.rotate(f * maxangle, 0.0F, 0.0F, 1.0F);
        } else {
            String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
            if (("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer) entityLiving).isWearing(EnumPlayerModelParts.CAPE))) {
                GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static float prepareScale(EntityLivingBase entitylivingbaseIn, float partialTicks) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        float f = 0.0625F;
        GlStateManager.translate(0.0F, -1.501F, 0.0F);
        return 0.0625F;
    }

    @SideOnly(Side.CLIENT)
    public static void SpawnParticle(EntityLivingBase base1, EntityLivingBase base2) {
        Vec3d pos1 = new Vec3d(base1.posX, base1.posY, base1.posZ);
        Vec3d pos2 = new Vec3d(base2.posX, base2.posY, base2.posZ);
        System.out.println("pos1    " + pos1);
        System.out.println("pos2    " + pos2);
        World world = base1.world;
        if (pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(world, shockbeam, 0.1F, 240, 1.0F, 1.0F, 1.0F, 0.15F, pos1.distanceTo(pos2), 5, 0.3F, 1.0F, pos1, pos2);
            laser.setPosition(base1.posX, base1.posY, base1.posZ);
            world.spawnEntity(laser);
            System.out.println(laser.getPositionVector());
        }
    }

}
