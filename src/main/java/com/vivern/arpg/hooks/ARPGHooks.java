package com.vivern.arpg.hooks;

import com.vivern.arpg.hooks.coloredlightning.ColoredLightning;
import com.vivern.arpg.main.*;
import com.vivern.arpg.renders.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Deprecated
@SuppressWarnings({"unused"})
public class ARPGHooks {

    public static BlockColors blockColors = new BlockColors();
    private static final float BLOCK_COLOR_INTENSITY = 0.75F;
    //TODO глянуть, не проще ли просто биндить текстуру, ибо зачем здвесь вообще миксин
    public static ResourceLocation bindAnotherTexture = null;
    public static int moveSlot = 0;

    //FIXME unused
    public static void armsToDefaults(ModelBiped biped, float ageInTicks, float limbSwing, float limbSwingAmount) {
        biped.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        biped.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        biped.bipedRightArm.rotateAngleY = 0.0F;
        biped.bipedRightArm.rotateAngleZ = 0.0F;
        biped.bipedLeftArm.rotateAngleY = 0.0F;
        biped.bipedLeftArm.rotateAngleZ = 0.0F;
        switch (biped.leftArmPose) {
            case EMPTY:
                biped.bipedLeftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
                biped.bipedLeftArm.rotateAngleY = (float) (Math.PI / 6);
                break;
            case ITEM:
                biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX * 0.5F - (float) (Math.PI / 10);
                biped.bipedLeftArm.rotateAngleY = 0.0F;
        }

        switch (biped.rightArmPose) {
            case EMPTY:
                biped.bipedRightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                biped.bipedRightArm.rotateAngleX = biped.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
                biped.bipedRightArm.rotateAngleY = (float) (-Math.PI / 6);
                break;
            case ITEM:
                biped.bipedRightArm.rotateAngleX = biped.bipedRightArm.rotateAngleX * 0.5F - (float) (Math.PI / 10);
                biped.bipedRightArm.rotateAngleY = 0.0F;
        }

        biped.bipedRightArm.rotateAngleZ = biped.bipedRightArm.rotateAngleZ + (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
        biped.bipedLeftArm.rotateAngleZ = biped.bipedLeftArm.rotateAngleZ - (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
        biped.bipedRightArm.rotateAngleX = biped.bipedRightArm.rotateAngleX + MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX - MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        if (biped.isRiding) {
            biped.bipedRightArm.rotateAngleX += (float) (-Math.PI / 5);
            biped.bipedLeftArm.rotateAngleX += (float) (-Math.PI / 5);
        }
    }

    // B: зачем здесь меняется овещённость? Теперь все модели максимально тёмные :/
    // B: Да, это ломает рендер освещения, к тому же в самом моде не используется
    //   @SideOnly(Side.CLIENT)
    //   @Hook
    //   @OnBegin
    //   public static float getBrightness(Entity entity) {
    //      return 0.0F;
      /* Old code:

         int oldValue = 0;
         int j = (oldValue >> 20 & 15) / 2;
         int k = (oldValue >> 4 & 15) / 2;
         return j << 20 | k << 4;

       */
    //   }

    // B: изначально выключенный хук (хотя является таковым по структуре). ломает систему освещения майна, переводя её на кастомную (многопоточную?). мне лень это дебажить. если кто-то хочет - вперёд
    @SideOnly(Side.CLIENT)
    //   @Hook
    //   @OnBegin
    public static boolean renderModel(BlockModelRenderer renderer, IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
        boolean flag = Minecraft.isAmbientOcclusionEnabled() && stateIn.getLightValue(worldIn, posIn) == 0 && modelIn.isAmbientOcclusion(stateIn);
        boolean flag2 = flag && Minecraft.getMinecraft().gameSettings.ambientOcclusion == 2;

        try {
            if (flag2) {
                return renderModelMaxSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand);
            } else {
                return flag ? renderModelSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand) : renderModelFlat(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand);
            }
        } catch (Exception e) {
            CrashReport crashreport = CrashReport.makeCrashReport(e, "Tessellating block model");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tessellated");
            CrashReportCategory.addBlockInfo(crashreportcategory, posIn, stateIn);
            crashreportcategory.addCrashSection("Using AO", flag);
            throw new ReportedException(crashreport);
        }
    }

    public static boolean renderModelFlat(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
        boolean flag = false;
        BitSet bitset = new BitSet(3);
        World world;
        if (worldIn instanceof World) {
            world = (World) worldIn;
        } else {
            world = Minecraft.getMinecraft().world;
        }

        long lig = world.getWorldTime() % 24000L;

        for (EnumFacing enumfacing : EnumFacing.values()) {
            List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
            if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
                int i = stateIn.getPackedLightmapCoords(worldIn, posIn.offset(enumfacing));
                BlockPos posoff = posIn.offset(enumfacing);
                LoadedRGBChunk loadedrgb = StaticRGBLight.getActualLoadedRGBChunk(posoff.getX(), posoff.getZ());
                if (loadedrgb != null) {
                    long reds = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordRed(posoff.getX(), posoff.getY(), posoff.getZ()));
                    long greens = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordGreen(posoff.getX(), posoff.getY(), posoff.getZ()));
                    long blues = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordBlue(posoff.getX(), posoff.getY(), posoff.getZ()));
                    renderQuadsFlat(worldIn, lig, reds, greens, blues, stateIn, posIn, i, false, buffer, list, bitset);
                }

                flag = true;
            }
        }

        List<BakedQuad> list1 = modelIn.getQuads(stateIn, null, rand);
        if (!list1.isEmpty()) {
            LoadedRGBChunk loadedrgb = StaticRGBLight.getActualLoadedRGBChunk(posIn.getX(), posIn.getZ());
            if (loadedrgb != null) {
                long reds = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordRed(posIn.getX(), posIn.getY(), posIn.getZ()));
                long greens = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordGreen(posIn.getX(), posIn.getY(), posIn.getZ()));
                long blues = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordBlue(posIn.getX(), posIn.getY(), posIn.getZ()));
                renderQuadsFlat(worldIn, lig, reds, greens, blues, stateIn, posIn, -1, true, buffer, list1, bitset);
            }

            flag = true;
        }

        return flag;
    }

    public static void renderQuadsFlat(IBlockAccess blockAccessIn, long dayNightLight, long bakColR, long bakColG, long bakColB, IBlockState stateIn, BlockPos posIn, int brightnessIn, boolean ownBrightness, BufferBuilder buffer, List<BakedQuad> list, BitSet bitSet) {
        Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
        double d0 = posIn.getX() + vec3d.x;
        double d1 = posIn.getY() + vec3d.y;
        double d2 = posIn.getZ() + vec3d.z;
        int i = 0;
        int brightnessX = 0;
        int brightnessZ = 0;
        int brighZadding = 0;
        int brighXadding = 0;
        float red = LoadedRGBChunk.finalColorAdditive(bakColR);
        float green = LoadedRGBChunk.finalColorAdditive(bakColG);
        float blue = LoadedRGBChunk.finalColorAdditive(bakColB);
        if (!ownBrightness) {
            brightnessX = ColorConverters.UnpackLightmapCoordsX(brightnessIn);
            brightnessZ = ColorConverters.UnpackLightmapCoordsZ(brightnessIn);
            brighZadding = Math.min(brightnessZ + (int) Math.round((red + green + blue) / 1000.0 * 220.0), 240);
            brightnessIn = ColorConverters.RGBtoDecimal255(brightnessX, 0, brighZadding);
        }

        for (int j = list.size(); i < j; i++) {
            BakedQuad bakedquad = list.get(i);
            if (ownBrightness) {
                fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), null, bitSet);
                BlockPos blockpos = bitSet.get(0) ? posIn.offset(bakedquad.getFace()) : posIn;
                brightnessIn = stateIn.getPackedLightmapCoords(blockAccessIn, blockpos);
                brightnessX = ColorConverters.UnpackLightmapCoordsX(brightnessIn);
                brightnessZ = ColorConverters.UnpackLightmapCoordsZ(brightnessIn);
                brighZadding = Math.min(brightnessZ + (int) Math.round((red + green + blue) / 1000.0 * 220.0), 240);
                brightnessIn = ColorConverters.RGBtoDecimal255(brightnessX, 0, brighZadding + (brightnessIn == -1 ? (int) (red + green + blue) * 70 : 0));
            }

            buffer.addVertexData(bakedquad.getVertexData());
            buffer.putBrightness4(brightnessIn, brightnessIn, brightnessIn, brightnessIn);
            if (bakedquad.hasTintIndex()) {
                int k = blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
                if (EntityRenderer.anaglyphEnable) {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f = (k >> 16 & 0xFF) / 255.0F;
                float f1 = (k >> 8 & 0xFF) / 255.0F;
                float f2 = (k & 0xFF) / 255.0F;
                if (bakedquad.shouldApplyDiffuseLighting()) {
                    float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
                    f *= diffuse;
                    f1 *= diffuse;
                    f2 *= diffuse;
                }

                float dayadd = 0.0F;
                float minimalbrightness = 0.3F;
                if (dayNightLight >= 0L && dayNightLight < 1500L) {
                    dayadd = ((float) dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
                } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
                    dayadd = 1.0F * brightnessX / 240.0F;
                } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
                    dayadd = (minimalbrightness + (float) (1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
                } else {
                    dayadd = minimalbrightness * brightnessX / 240.0F;
                }

                float brpow = (float) Math.pow(f, BLOCK_COLOR_INTENSITY);
                float brpow1 = (float) Math.pow(f1, BLOCK_COLOR_INTENSITY);
                float brpow2 = (float) Math.pow(f2, BLOCK_COLOR_INTENSITY);
                float cmR = MathHelper.clamp(dayadd * f + red * brpow, 0.0F, brpow);
                float cmG = MathHelper.clamp(dayadd * f1 + green * brpow1, 0.0F, brpow1);
                float cmB = MathHelper.clamp(dayadd * f2 + blue * brpow2, 0.0F, brpow2);
                buffer.putColorMultiplier(cmR, cmG, cmB, 4);
                buffer.putColorMultiplier(cmR, cmG, cmB, 3);
                buffer.putColorMultiplier(cmR, cmG, cmB, 2);
                buffer.putColorMultiplier(cmR, cmG, cmB, 1);
            } else if (bakedquad.shouldApplyDiffuseLighting()) {
                float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
                float dayadd = 0.0F;
                float minimalbrightness = 0.3F;
                if (dayNightLight >= 0L && dayNightLight < 1500L) {
                    dayadd = ((float) dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
                } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
                    dayadd = 1.0F * brightnessX / 240.0F;
                } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
                    dayadd = (minimalbrightness + (float) (1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
                } else {
                    dayadd = minimalbrightness * brightnessX / 240.0F;
                }

                float bound = Math.min(diffuse + Math.max(red + green + blue - 0.5F, 0.0F), 1.0F);
                float cmR = MathHelper.clamp(red + dayadd, 0.0F, bound);
                float cmG = MathHelper.clamp(green + dayadd, 0.0F, bound);
                float cmB = MathHelper.clamp(blue + dayadd, 0.0F, bound);
                buffer.putColorMultiplier(cmR, cmG, cmB, 4);
                buffer.putColorMultiplier(cmR, cmG, cmB, 3);
                buffer.putColorMultiplier(cmR, cmG, cmB, 2);
                buffer.putColorMultiplier(cmR, cmG, cmB, 1);
            }

            buffer.putPosition(d0, d1, d2);
        }
    }

    public static boolean renderModelSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
        boolean flag = false;
        float[] afloat = new float[EnumFacing.values().length * 2];
        World world;
        if (worldIn instanceof World) {
            world = (World) worldIn;
        } else {
            world = Minecraft.getMinecraft().world;
        }

        long lig = world.getWorldTime() % 24000L;
        BitSet bitset = new BitSet(3);
        AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = new AmbientOcclusionFace();
        Vec3d[] nbColors = new Vec3d[]{ColoredLightning.getAdditiveColorInPos(posIn.add(1, 0, 1)), ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 0, 1)), ColoredLightning.getAdditiveColorInPos(posIn.add(1, 0, -1)), ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 0, -1)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, 1, 1)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, 1, -1)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, -1, 1)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, -1, -1)), ColoredLightning.getAdditiveColorInPos(posIn.add(1, 1, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 1, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(1, -1, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(-1, -1, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, -1, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, 1, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, 0, -1)), ColoredLightning.getAdditiveColorInPos(posIn.add(0, 0, 1)), ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 0, 0)), ColoredLightning.getAdditiveColorInPos(posIn.add(1, 0, 0))};

        for (EnumFacing enumfacing : EnumFacing.values()) {
            List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
            if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
                renderQuadsSmooth(worldIn, lig, nbColors, stateIn, posIn, buffer, list, afloat, bitset, blockmodelrenderer$ambientocclusionface);
                flag = true;
            }
        }

        List<BakedQuad> list1 = modelIn.getQuads(stateIn, null, rand);
        if (!list1.isEmpty()) {
            renderQuadsSmooth(worldIn, lig, nbColors, stateIn, posIn, buffer, list1, afloat, bitset, blockmodelrenderer$ambientocclusionface);
            flag = true;
        }

        return flag;
    }

    public static void renderQuadsSmooth(IBlockAccess blockAccessIn, long dayNightLight, Vec3d[] nbColors, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, List<BakedQuad> list, float[] quadBounds, BitSet bitSet, AmbientOcclusionFace aoFace) {
        Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
        double d0 = posIn.getX() + vec3d.x;
        double d1 = posIn.getY() + vec3d.y;
        double d2 = posIn.getZ() + vec3d.z;
        int i = 0;
        int brightnessX = 0;

        for (int j = list.size(); i < j; i++) {
            BakedQuad bakedquad = list.get(i);
            fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), quadBounds, bitSet);
            aoFace.updateVertexBrightness(blockAccessIn, stateIn, posIn, bakedquad.getFace(), quadBounds, bitSet);
            buffer.addVertexData(bakedquad.getVertexData());
            buffer.putBrightness4(aoFace.vertexBrightness[0], aoFace.vertexBrightness[1], aoFace.vertexBrightness[2], aoFace.vertexBrightness[3]);
            brightnessX = (int) ((ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[0]) + ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[1]) + ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[2]) + ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[3])) * 0.25);
            if (bakedquad.shouldApplyDiffuseLighting()) {
                float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
                aoFace.vertexColorMultiplier[0] = aoFace.vertexColorMultiplier[0] * diffuse;
                aoFace.vertexColorMultiplier[1] = aoFace.vertexColorMultiplier[1] * diffuse;
                aoFace.vertexColorMultiplier[2] = aoFace.vertexColorMultiplier[2] * diffuse;
                aoFace.vertexColorMultiplier[3] = aoFace.vertexColorMultiplier[3] * diffuse;
            }

            if (bakedquad.hasTintIndex()) {
                int k = blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
                if (EntityRenderer.anaglyphEnable) {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f = (k >> 16 & 0xFF) / 255.0F;
                float f1 = (k >> 8 & 0xFF) / 255.0F;
                float f2 = (k & 0xFF) / 255.0F;
                float brpow = (float) Math.pow(f, BLOCK_COLOR_INTENSITY);
                float brpow1 = (float) Math.pow(f1, BLOCK_COLOR_INTENSITY);
                float brpow2 = (float) Math.pow(f2, BLOCK_COLOR_INTENSITY);
                float diffuse = 1.0F;
                float dayadd = 0.0F;
                float minimalbrightness = 0.3F;
                if (dayNightLight >= 0L && dayNightLight < 1500L) {
                    dayadd = ((float) dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
                } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
                    dayadd = 1.0F * brightnessX / 240.0F;
                } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
                    dayadd = (minimalbrightness + (float) (1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
                } else {
                    dayadd = minimalbrightness * brightnessX / 240.0F;
                }

                for (int cfi = 4; cfi >= 1; cfi--) {
                    int cfn = 4 - cfi;
                    Vec3d colort = getNbColor(bakedquad, cfi, nbColors);
                    float cf = (float) colort.x;
                    float cf1 = (float) colort.y;
                    float cf2 = (float) colort.z;
                    float vertColorm = aoFace.vertexColorMultiplier[cfn];
                    float cmR = MathHelper.clamp((dayadd * f + cf * brpow) * vertColorm, 0.0F, brpow);
                    float cmG = MathHelper.clamp((dayadd * f1 + cf1 * brpow1) * vertColorm, 0.0F, brpow1);
                    float cmB = MathHelper.clamp((dayadd * f2 + cf2 * brpow2) * vertColorm, 0.0F, brpow2);
                    buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
                }
            } else {
                float diffuse = 1.0F;
                float dayadd = 0.0F;
                float minimalbrightness = 0.3F;
                if (dayNightLight >= 0L && dayNightLight < 1500L) {
                    dayadd = ((float) dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
                } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
                    dayadd = 1.0F * brightnessX / 240.0F;
                } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
                    dayadd = (minimalbrightness + (float) (1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
                } else {
                    dayadd = minimalbrightness * brightnessX / 240.0F;
                }

                for (int cfi = 4; cfi >= 1; cfi--) {
                    int cfn = 4 - cfi;
                    Vec3d colort = getNbColor(bakedquad, cfi, nbColors);
                    float cf = (float) colort.x;
                    float cf1 = (float) colort.y;
                    float cf2 = (float) colort.z;
                    float bound = Math.min(diffuse + Math.max(cf + cf1 + cf2 - 1.5F, 0.0F), 1.0F);
                    float vertColorm = aoFace.vertexColorMultiplier[cfn];
                    float cmR = MathHelper.clamp((cf + dayadd) * vertColorm, 0.0F, bound);
                    float cmG = MathHelper.clamp((cf1 + dayadd) * vertColorm, 0.0F, bound);
                    float cmB = MathHelper.clamp((cf2 + dayadd) * vertColorm, 0.0F, bound);
                    buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
                }
            }

            buffer.putPosition(d0, d1, d2);
        }
    }

    public static boolean renderModelMaxSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand) {
        boolean flag = false;
        float[] afloat = new float[EnumFacing.values().length * 2];
        World world;
        if (worldIn instanceof World) {
            world = (World) worldIn;
        } else {
            world = Minecraft.getMinecraft().world;
        }

        long lig = world.getWorldTime() % 24000L;
        BitSet bitset = new BitSet(3);
        AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = new AmbientOcclusionFace();
        BlockPos down = posIn.add(0, -1, 0);
        BlockPos up = posIn.add(0, 1, 0);
        BlockPos north = posIn.add(0, 0, -1);
        BlockPos south = posIn.add(0, 0, 1);
        BlockPos west = posIn.add(-1, 0, 0);
        BlockPos east = posIn.add(1, 0, 0);
        BlockPos p0 = posIn.add(1, 0, 1);
        BlockPos p1 = posIn.add(-1, 0, 1);
        BlockPos p2 = posIn.add(1, 0, -1);
        BlockPos p3 = posIn.add(-1, 0, -1);
        BlockPos p4 = posIn.add(0, 1, 1);
        BlockPos p5 = posIn.add(0, 1, -1);
        BlockPos p6 = posIn.add(0, -1, 1);
        BlockPos p7 = posIn.add(0, -1, -1);
        BlockPos p8 = posIn.add(1, 1, 0);
        BlockPos p9 = posIn.add(-1, 1, 0);
        BlockPos p10 = posIn.add(1, -1, 0);
        BlockPos p11 = posIn.add(-1, -1, 0);
        BlockPos p12 = posIn.add(1, 1, 1);
        BlockPos p13 = posIn.add(1, 1, -1);
        BlockPos p14 = posIn.add(-1, 1, 1);
        BlockPos p15 = posIn.add(-1, 1, -1);
        BlockPos p16 = posIn.add(1, -1, 1);
        BlockPos p17 = posIn.add(1, -1, -1);
        BlockPos p18 = posIn.add(-1, -1, 1);
        BlockPos p19 = posIn.add(-1, -1, -1);
        Vec3d[] nbColors = new Vec3d[]{ColoredLightning.getAdditiveColorInPos(p0), ColoredLightning.getAdditiveColorInPos(p1), ColoredLightning.getAdditiveColorInPos(p2), ColoredLightning.getAdditiveColorInPos(p3), ColoredLightning.getAdditiveColorInPos(p4), ColoredLightning.getAdditiveColorInPos(p5), ColoredLightning.getAdditiveColorInPos(p6), ColoredLightning.getAdditiveColorInPos(p7), ColoredLightning.getAdditiveColorInPos(p8), ColoredLightning.getAdditiveColorInPos(p9), ColoredLightning.getAdditiveColorInPos(p10), ColoredLightning.getAdditiveColorInPos(p11), ColoredLightning.getAdditiveColorInPos(p12), ColoredLightning.getAdditiveColorInPos(p13), ColoredLightning.getAdditiveColorInPos(p14), ColoredLightning.getAdditiveColorInPos(p15), ColoredLightning.getAdditiveColorInPos(p16), ColoredLightning.getAdditiveColorInPos(p17), ColoredLightning.getAdditiveColorInPos(p18), ColoredLightning.getAdditiveColorInPos(p19), ColoredLightning.getAdditiveColorInPos(down), ColoredLightning.getAdditiveColorInPos(up), ColoredLightning.getAdditiveColorInPos(north), ColoredLightning.getAdditiveColorInPos(south), ColoredLightning.getAdditiveColorInPos(west), ColoredLightning.getAdditiveColorInPos(east)};
        boolean[] nbOpacity = new boolean[]{worldIn.getBlockState(p0).getLightOpacity(worldIn, p0) == 0, worldIn.getBlockState(p1).getLightOpacity(worldIn, p1) == 0, worldIn.getBlockState(p2).getLightOpacity(worldIn, p2) == 0, worldIn.getBlockState(p3).getLightOpacity(worldIn, p3) == 0, worldIn.getBlockState(p4).getLightOpacity(worldIn, p4) == 0, worldIn.getBlockState(p5).getLightOpacity(worldIn, p5) == 0, worldIn.getBlockState(p6).getLightOpacity(worldIn, p6) == 0, worldIn.getBlockState(p7).getLightOpacity(worldIn, p7) == 0, worldIn.getBlockState(p8).getLightOpacity(worldIn, p8) == 0, worldIn.getBlockState(p9).getLightOpacity(worldIn, p9) == 0, worldIn.getBlockState(p10).getLightOpacity(worldIn, p10) == 0, worldIn.getBlockState(p11).getLightOpacity(worldIn, p11) == 0, worldIn.getBlockState(p12).getLightOpacity(worldIn, p12) == 0, worldIn.getBlockState(p13).getLightOpacity(worldIn, p13) == 0, worldIn.getBlockState(p14).getLightOpacity(worldIn, p14) == 0, worldIn.getBlockState(p15).getLightOpacity(worldIn, p15) == 0, worldIn.getBlockState(p16).getLightOpacity(worldIn, p16) == 0, worldIn.getBlockState(p17).getLightOpacity(worldIn, p17) == 0, worldIn.getBlockState(p18).getLightOpacity(worldIn, p18) == 0, worldIn.getBlockState(p19).getLightOpacity(worldIn, p19) == 0};

        for (EnumFacing enumfacing : EnumFacing.values()) {
            List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
            if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
                renderQuadsMaxSmooth(worldIn, lig, nbColors, nbOpacity, stateIn, posIn, buffer, list, afloat, bitset, blockmodelrenderer$ambientocclusionface);
                flag = true;
            }
        }

        List<BakedQuad> list1 = modelIn.getQuads(stateIn, null, rand);
        if (!list1.isEmpty()) {
            renderQuadsMaxSmooth(worldIn, lig, nbColors, nbOpacity, stateIn, posIn, buffer, list1, afloat, bitset, blockmodelrenderer$ambientocclusionface);
            flag = true;
        }

        return flag;
    }

    public static void renderQuadsMaxSmooth(IBlockAccess blockAccessIn, long dayNightLight, Vec3d[] nbColors, boolean[] nbOpacity, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, List<BakedQuad> list, float[] quadBounds, BitSet bitSet, AmbientOcclusionFace aoFace) {
        Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
        double d0 = posIn.getX() + vec3d.x;
        double d1 = posIn.getY() + vec3d.y;
        double d2 = posIn.getZ() + vec3d.z;
        int i = 0;

        for (int j = list.size(); i < j; i++) {
            BakedQuad bakedquad = list.get(i);
            fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), quadBounds, bitSet);
            aoFace.updateVertexBrightness(blockAccessIn, stateIn, posIn, bakedquad.getFace(), quadBounds, bitSet);
            buffer.addVertexData(bakedquad.getVertexData());
            buffer.putBrightness4(aoFace.vertexBrightness[0], aoFace.vertexBrightness[1], aoFace.vertexBrightness[2], aoFace.vertexBrightness[3]);
            if (bakedquad.shouldApplyDiffuseLighting()) {
                float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
                aoFace.vertexColorMultiplier[0] = aoFace.vertexColorMultiplier[0] * diffuse;
                aoFace.vertexColorMultiplier[1] = aoFace.vertexColorMultiplier[1] * diffuse;
                aoFace.vertexColorMultiplier[2] = aoFace.vertexColorMultiplier[2] * diffuse;
                aoFace.vertexColorMultiplier[3] = aoFace.vertexColorMultiplier[3] * diffuse;
            }

            if (bakedquad.hasTintIndex()) {
                int k = blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
                if (EntityRenderer.anaglyphEnable) {
                    k = TextureUtil.anaglyphColor(k);
                }

                float f = (k >> 16 & 0xFF) / 255.0F;
                float f1 = (k >> 8 & 0xFF) / 255.0F;
                float f2 = (k & 0xFF) / 255.0F;
                float brpow = (float) Math.pow(f, BLOCK_COLOR_INTENSITY);
                float brpow1 = (float) Math.pow(f1, BLOCK_COLOR_INTENSITY);
                float brpow2 = (float) Math.pow(f2, BLOCK_COLOR_INTENSITY);
                float diffuse = 1.0F;

                for (int cfi = 4; cfi >= 1; cfi--) {
                    int cfn = 4 - cfi;
                    float dayadd = 0.0F;
                    int brightnessX = ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[cfn]);
                    float minimalbrightness = 0.3F;
                    if (dayNightLight >= 0L && dayNightLight < 1500L) {
                        dayadd = ((float) dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
                    } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
                        dayadd = 1.0F * brightnessX / 240.0F;
                    } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
                        dayadd = (minimalbrightness + (float) (1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
                    } else {
                        dayadd = minimalbrightness * brightnessX / 240.0F;
                    }

                    Vec3d colort = getNbColor(bakedquad, cfi, nbColors);
                    float cf = (float) colort.x;
                    float cf1 = (float) colort.y;
                    float cf2 = (float) colort.z;
                    float vertColorm = aoFace.vertexColorMultiplier[cfn];
                    float cmR = MathHelper.clamp((dayadd * f + cf * brpow) * vertColorm, 0.0F, brpow);
                    float cmG = MathHelper.clamp((dayadd * f1 + cf1 * brpow1) * vertColorm, 0.0F, brpow1);
                    float cmB = MathHelper.clamp((dayadd * f2 + cf2 * brpow2) * vertColorm, 0.0F, brpow2);
                    buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
                }
            } else {
                float diffuse = 1.0F;

                for (int cfi = 4; cfi >= 1; cfi--) {
                    int cfn = 4 - cfi;
                    float dayadd = 0.0F;
                    int brightnessX = ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[cfn]);
                    float minimalbrightness = 0.3F;
                    if (dayNightLight >= 0L && dayNightLight < 1500L) {
                        dayadd = (dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
                    } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
                        dayadd = 1.0F * brightnessX / 240.0F;
                    } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
                        dayadd = (minimalbrightness + (1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
                    } else {
                        dayadd = minimalbrightness * brightnessX / 240.0F;
                    }

                    Vec3d colort = getNbColorMaxSmooth(bakedquad, cfi, nbColors, nbOpacity);
                    float cf = (float) colort.x;
                    float cf1 = (float) colort.y;
                    float cf2 = (float) colort.z;
                    float bound = Math.min(diffuse + Math.max(cf + cf1 + cf2 - 1.5F, 0.0F), 1.0F);
                    float vertColorm = aoFace.vertexColorMultiplier[cfn];
                    float cmR = MathHelper.clamp((cf + dayadd) * vertColorm, 0.0F, bound);
                    float cmG = MathHelper.clamp((cf1 + dayadd) * vertColorm, 0.0F, bound);
                    float cmB = MathHelper.clamp((cf2 + dayadd) * vertColorm, 0.0F, bound);
                    buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
                }
            }

            buffer.putPosition(d0, d1, d2);
        }
    }

    public static Vec3d fixedNoDark(Vec3d check, Vec3d[] nbColors, EnumFacing facing) {
        return isVec3dNull(check) ? nbColors[facing.getIndex() + 12] : check;
    }

    public static Vec3d getNbColor(BakedQuad bakedquad, int buffervertex, Vec3d[] nbColors) {
        switch (bakedquad.getFace()) {
            case UP:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(fixedNoDark(nbColors[8], nbColors, EnumFacing.UP), fixedNoDark(nbColors[5], nbColors, EnumFacing.UP));
                    case 2:
                        return ColorConverters.mix(fixedNoDark(nbColors[4], nbColors, EnumFacing.UP), fixedNoDark(nbColors[8], nbColors, EnumFacing.UP));
                    case 3:
                        return ColorConverters.mix(fixedNoDark(nbColors[4], nbColors, EnumFacing.UP), fixedNoDark(nbColors[9], nbColors, EnumFacing.UP));
                    case 4:
                        return ColorConverters.mix(fixedNoDark(nbColors[5], nbColors, EnumFacing.UP), fixedNoDark(nbColors[9], nbColors, EnumFacing.UP));
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case DOWN:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(fixedNoDark(nbColors[10], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[6], nbColors, EnumFacing.DOWN));
                    case 2:
                        return ColorConverters.mix(fixedNoDark(nbColors[7], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[10], nbColors, EnumFacing.DOWN));
                    case 3:
                        return ColorConverters.mix(fixedNoDark(nbColors[11], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[7], nbColors, EnumFacing.DOWN));
                    case 4:
                        return ColorConverters.mix(fixedNoDark(nbColors[6], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[11], nbColors, EnumFacing.DOWN));
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case EAST:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(fixedNoDark(nbColors[8], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[2], nbColors, EnumFacing.EAST));
                    case 2:
                        return ColorConverters.mix(fixedNoDark(nbColors[10], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[2], nbColors, EnumFacing.EAST));
                    case 3:
                        return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[10], nbColors, EnumFacing.EAST));
                    case 4:
                        return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[8], nbColors, EnumFacing.EAST));
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case NORTH:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(fixedNoDark(nbColors[5], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[3], nbColors, EnumFacing.NORTH));
                    case 2:
                        return ColorConverters.mix(fixedNoDark(nbColors[7], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[3], nbColors, EnumFacing.NORTH));
                    case 3:
                        return ColorConverters.mix(fixedNoDark(nbColors[2], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[7], nbColors, EnumFacing.NORTH));
                    case 4:
                        return ColorConverters.mix(fixedNoDark(nbColors[2], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[5], nbColors, EnumFacing.NORTH));
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case SOUTH:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[4], nbColors, EnumFacing.SOUTH));
                    case 2:
                        return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[6], nbColors, EnumFacing.SOUTH));
                    case 3:
                        return ColorConverters.mix(fixedNoDark(nbColors[1], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[6], nbColors, EnumFacing.SOUTH));
                    case 4:
                        return ColorConverters.mix(fixedNoDark(nbColors[4], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[1], nbColors, EnumFacing.SOUTH));
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case WEST:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(fixedNoDark(nbColors[1], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[9], nbColors, EnumFacing.WEST));
                    case 2:
                        return ColorConverters.mix(fixedNoDark(nbColors[11], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[1], nbColors, EnumFacing.WEST));
                    case 3:
                        return ColorConverters.mix(fixedNoDark(nbColors[3], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[11], nbColors, EnumFacing.WEST));
                    case 4:
                        return ColorConverters.mix(fixedNoDark(nbColors[9], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[3], nbColors, EnumFacing.WEST));
                }
        }

        return new Vec3d(0.0, 0.0, 0.0);
    }

    // TODO unused ???
    public static boolean isVec3dNull(Vec3d vec) {
        return vec.x == 0.0 && vec.y == 0.0 && vec.z == 0.0;
    }

    public static Vec3d fixedNoDarkMxSm(int chec, Vec3d[] nbColors, EnumFacing facing, boolean[] nbOpacity) {
        Vec3d check = nbColors[chec];
        return nbOpacity[chec] ? check : nbColors[facing.getIndex() + 20];
    }

    public static Vec3d getNbColorMaxSmooth(BakedQuad bakedquad, int buffervertex, Vec3d[] nbColors, boolean[] nbOpacity) {
        switch (bakedquad.getFace()) {
            case UP:
                switch (buffervertex) {
                    case 1:
                        if (!nbOpacity[5] && !nbOpacity[8]) {
                            return nbColors[21];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(8, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(5, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(13, nbColors, EnumFacing.UP, nbOpacity), nbColors[21]);
                    case 2:
                        if (!nbOpacity[4] && !nbOpacity[8]) {
                            return nbColors[21];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(4, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(8, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(12, nbColors, EnumFacing.UP, nbOpacity), nbColors[21]);
                    case 3:
                        if (!nbOpacity[4] && !nbOpacity[9]) {
                            return nbColors[21];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(4, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(9, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(14, nbColors, EnumFacing.UP, nbOpacity), nbColors[21]);
                    case 4:
                        if (!nbOpacity[5] && !nbOpacity[9]) {
                            return nbColors[21];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(5, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(9, nbColors, EnumFacing.UP, nbOpacity), fixedNoDarkMxSm(15, nbColors, EnumFacing.UP, nbOpacity), nbColors[21]);
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case DOWN:
                switch (buffervertex) {
                    case 1:
                        if (!nbOpacity[10] && !nbOpacity[6]) {
                            return nbColors[20];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(6, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(10, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(16, nbColors, EnumFacing.DOWN, nbOpacity), nbColors[20]);
                    case 2:
                        if (!nbOpacity[7] && !nbOpacity[10]) {
                            return nbColors[20];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(7, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(10, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(17, nbColors, EnumFacing.DOWN, nbOpacity), nbColors[20]);
                    case 3:
                        if (!nbOpacity[11] && !nbOpacity[7]) {
                            return nbColors[20];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(7, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(11, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(19, nbColors, EnumFacing.DOWN, nbOpacity), nbColors[20]);
                    case 4:
                        if (!nbOpacity[6] && !nbOpacity[11]) {
                            return nbColors[20];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(6, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(11, nbColors, EnumFacing.DOWN, nbOpacity), fixedNoDarkMxSm(18, nbColors, EnumFacing.DOWN, nbOpacity), nbColors[20]);
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case EAST:
                switch (buffervertex) {
                    case 1:
                        if (!nbOpacity[8] && !nbOpacity[2]) {
                            return nbColors[25];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(8, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(2, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(13, nbColors, EnumFacing.EAST, nbOpacity), nbColors[25]);
                    case 2:
                        if (!nbOpacity[10] && !nbOpacity[2]) {
                            return nbColors[25];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(10, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(2, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(17, nbColors, EnumFacing.EAST, nbOpacity), nbColors[25]);
                    case 3:
                        if (!nbOpacity[0] && !nbOpacity[10]) {
                            return nbColors[25];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(0, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(10, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(16, nbColors, EnumFacing.EAST, nbOpacity), nbColors[25]);
                    case 4:
                        if (!nbOpacity[0] && !nbOpacity[8]) {
                            return nbColors[25];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(0, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(8, nbColors, EnumFacing.EAST, nbOpacity), fixedNoDarkMxSm(12, nbColors, EnumFacing.EAST, nbOpacity), nbColors[25]);
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case NORTH:
                switch (buffervertex) {
                    case 1:
                        if (!nbOpacity[5] && !nbOpacity[3]) {
                            return nbColors[22];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(5, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(3, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(15, nbColors, EnumFacing.NORTH, nbOpacity), nbColors[22]);
                    case 2:
                        if (!nbOpacity[7] && !nbOpacity[3]) {
                            return nbColors[22];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(7, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(3, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(19, nbColors, EnumFacing.NORTH, nbOpacity), nbColors[22]);
                    case 3:
                        if (!nbOpacity[2] && !nbOpacity[7]) {
                            return nbColors[22];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(2, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(7, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(17, nbColors, EnumFacing.NORTH, nbOpacity), nbColors[22]);
                    case 4:
                        if (!nbOpacity[2] && !nbOpacity[5]) {
                            return nbColors[22];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(2, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(5, nbColors, EnumFacing.NORTH, nbOpacity), fixedNoDarkMxSm(13, nbColors, EnumFacing.NORTH, nbOpacity), nbColors[22]);
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case SOUTH:
                switch (buffervertex) {
                    case 1:
                        if (!nbOpacity[0] && !nbOpacity[4]) {
                            return nbColors[23];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(0, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(4, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(12, nbColors, EnumFacing.SOUTH, nbOpacity), nbColors[23]);
                    case 2:
                        if (!nbOpacity[0] && !nbOpacity[6]) {
                            return nbColors[23];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(0, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(6, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(16, nbColors, EnumFacing.SOUTH, nbOpacity), nbColors[23]);
                    case 3:
                        if (!nbOpacity[1] && !nbOpacity[6]) {
                            return nbColors[23];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(1, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(6, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(18, nbColors, EnumFacing.SOUTH, nbOpacity), nbColors[23]);
                    case 4:
                        if (!nbOpacity[4] && !nbOpacity[1]) {
                            return nbColors[23];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(4, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(1, nbColors, EnumFacing.SOUTH, nbOpacity), fixedNoDarkMxSm(14, nbColors, EnumFacing.SOUTH, nbOpacity), nbColors[23]);
                    default:
                        return new Vec3d(0.0, 0.0, 0.0);
                }
            case WEST:
                switch (buffervertex) {
                    case 1:
                        if (!nbOpacity[9] && !nbOpacity[1]) {
                            return nbColors[24];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(9, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(1, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(14, nbColors, EnumFacing.WEST, nbOpacity), nbColors[24]);
                    case 2:
                        if (!nbOpacity[11] && !nbOpacity[1]) {
                            return nbColors[24];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(11, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(1, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(18, nbColors, EnumFacing.WEST, nbOpacity), nbColors[24]);
                    case 3:
                        if (!nbOpacity[3] && !nbOpacity[11]) {
                            return nbColors[24];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(3, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(11, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(19, nbColors, EnumFacing.WEST, nbOpacity), nbColors[24]);
                    case 4:
                        if (!nbOpacity[9] && !nbOpacity[3]) {
                            return nbColors[24];
                        }

                        return ColorConverters.mix(fixedNoDarkMxSm(9, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(3, nbColors, EnumFacing.WEST, nbOpacity), fixedNoDarkMxSm(15, nbColors, EnumFacing.WEST, nbOpacity), nbColors[24]);
                }
        }

        return new Vec3d(0.0, 0.0, 0.0);
    }

    //TODO unused ???
    public static long getNbColorMaxSmooth1(BakedQuad bakedquad, int buffervertex, long[] nbColors) {
        switch (bakedquad.getFace()) {
            case UP:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(nbColors[8], nbColors[5], nbColors[21]);
                    case 2:
                        return ColorConverters.mix(nbColors[4], nbColors[8], nbColors[21]);
                    case 3:
                        return ColorConverters.mix(nbColors[4], nbColors[9], nbColors[21]);
                    case 4:
                        return ColorConverters.mix(nbColors[5], nbColors[9], nbColors[21]);
                    default:
                        return 0L;
                }
            case DOWN:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(nbColors[10], nbColors[6], nbColors[24]);
                    case 2:
                        return ColorConverters.mix(nbColors[7], nbColors[10], nbColors[24]);
                    case 3:
                        return ColorConverters.mix(nbColors[11], nbColors[7], nbColors[24]);
                    case 4:
                        return ColorConverters.mix(nbColors[6], nbColors[11], nbColors[24]);
                    default:
                        return 0L;
                }
            case EAST:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(nbColors[8], nbColors[2], nbColors[20]);
                    case 2:
                        return ColorConverters.mix(nbColors[10], nbColors[2], nbColors[20]);
                    case 3:
                        return ColorConverters.mix(nbColors[0], nbColors[10], nbColors[20]);
                    case 4:
                        return ColorConverters.mix(nbColors[0], nbColors[8], nbColors[20]);
                    default:
                        return 0L;
                }
            case NORTH:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(nbColors[5], nbColors[3], nbColors[25]);
                    case 2:
                        return ColorConverters.mix(nbColors[7], nbColors[3], nbColors[25]);
                    case 3:
                        return ColorConverters.mix(nbColors[2], nbColors[7], nbColors[25]);
                    case 4:
                        return ColorConverters.mix(nbColors[2], nbColors[5], nbColors[25]);
                    default:
                        return 0L;
                }
            case SOUTH:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(nbColors[0], nbColors[4], nbColors[22]);
                    case 2:
                        return ColorConverters.mix(nbColors[0], nbColors[6], nbColors[22]);
                    case 3:
                        return ColorConverters.mix(nbColors[1], nbColors[6], nbColors[22]);
                    case 4:
                        return ColorConverters.mix(nbColors[4], nbColors[1], nbColors[22]);
                    default:
                        return 0L;
                }
            case WEST:
                switch (buffervertex) {
                    case 1:
                        return ColorConverters.mix(nbColors[1], nbColors[9], nbColors[23]);
                    case 2:
                        return ColorConverters.mix(nbColors[11], nbColors[1], nbColors[23]);
                    case 3:
                        return ColorConverters.mix(nbColors[3], nbColors[11], nbColors[23]);
                    case 4:
                        return ColorConverters.mix(nbColors[9], nbColors[3], nbColors[23]);
                }
        }

        return 0L;
    }

    public static void fillQuadBounds(IBlockState stateIn, int[] vertexData, EnumFacing face, @Nullable float[] quadBounds, BitSet boundsFlags) {
        float f = 32.0F;
        float f1 = 32.0F;
        float f2 = 32.0F;
        float f3 = -32.0F;
        float f4 = -32.0F;
        float f5 = -32.0F;

        for (int i = 0; i < 4; i++) {
            float f6 = Float.intBitsToFloat(vertexData[i * 7]);
            float f7 = Float.intBitsToFloat(vertexData[i * 7 + 1]);
            float f8 = Float.intBitsToFloat(vertexData[i * 7 + 2]);
            f = Math.min(f, f6);
            f1 = Math.min(f1, f7);
            f2 = Math.min(f2, f8);
            f3 = Math.max(f3, f6);
            f4 = Math.max(f4, f7);
            f5 = Math.max(f5, f8);
        }

        if (quadBounds != null) {
            quadBounds[EnumFacing.WEST.getIndex()] = f;
            quadBounds[EnumFacing.EAST.getIndex()] = f3;
            quadBounds[EnumFacing.DOWN.getIndex()] = f1;
            quadBounds[EnumFacing.UP.getIndex()] = f4;
            quadBounds[EnumFacing.NORTH.getIndex()] = f2;
            quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
            int j = EnumFacing.values().length;
            quadBounds[EnumFacing.WEST.getIndex() + j] = 1.0F - f;
            quadBounds[EnumFacing.EAST.getIndex() + j] = 1.0F - f3;
            quadBounds[EnumFacing.DOWN.getIndex() + j] = 1.0F - f1;
            quadBounds[EnumFacing.UP.getIndex() + j] = 1.0F - f4;
            quadBounds[EnumFacing.NORTH.getIndex() + j] = 1.0F - f2;
            quadBounds[EnumFacing.SOUTH.getIndex() + j] = 1.0F - f5;
        }

        float f9 = 1.0E-4F;
        float f10 = 0.9999F;
        switch (face) {
            case UP:
                boundsFlags.set(1, f >= f9 || f2 >= f9 || f3 <= f10 || f5 <= f10);
                boundsFlags.set(0, (f4 > f10 || stateIn.isFullCube()) && f1 == f4);
                break;
            case DOWN:
                boundsFlags.set(1, f >= f9 || f2 >= f9 || f3 <= f10 || f5 <= f10);
                boundsFlags.set(0, (f1 < f9 || stateIn.isFullCube()) && f1 == f4);
                break;
            case EAST:
                boundsFlags.set(1, f1 >= f9 || f2 >= f9 || f4 <= f10 || f5 <= f10);
                boundsFlags.set(0, (f3 > f10 || stateIn.isFullCube()) && f == f3);
                break;
            case NORTH:
                boundsFlags.set(1, f >= f9 || f1 >= f9 || f3 <= f10 || f4 <= f10);
                boundsFlags.set(0, (f2 < f10 || stateIn.isFullCube()) && f2 == f5);
                break;
            case SOUTH:
                boundsFlags.set(1, f >= f9 || f1 >= f9 || f3 <= f10 || f4 <= f10);
                boundsFlags.set(0, (f5 > f10 || stateIn.isFullCube()) && f2 == f5);
                break;
            case WEST:
                boundsFlags.set(1, f1 >= f9 || f2 >= f9 || f4 <= f10 || f5 <= f10);
                boundsFlags.set(0, (f < f9 || stateIn.isFullCube()) && f == f3);
        }
    }

}
