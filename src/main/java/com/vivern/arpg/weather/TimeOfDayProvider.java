package com.vivern.arpg.weather;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.util.math.Vec4f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;

public class TimeOfDayProvider {

    public ArrayList<TimeOfDay> timesOfDay = new ArrayList<>();
    public int dayLength = 24000;
    boolean firstReset = true;

    public TimeOfDayProvider add4f(int upriseStartTime, int upriseEndTime, int fallStartTime, int fallEndTime, float[] colorR, float[] colorG, float[] colorB, float[] colorA) {
        TimeOfDay timeOfDay = new TimeOfDay(upriseStartTime, upriseEndTime, fallStartTime, fallEndTime, colorR, colorG, colorB, colorA, this);
        this.timesOfDay.add(timeOfDay);
        return this;
    }

    public TimeOfDayProvider add4f(int upriseStartTime, int upriseEndTime, float[] colorR, float[] colorG, float[] colorB, float[] colorA) {
        TimeOfDay timeOfDay = new TimeOfDay(upriseStartTime, upriseEndTime, 0, 0, colorR, colorG, colorB, colorA, this);
        this.timesOfDay.add(timeOfDay);
        return this;
    }

    public TimeOfDayProvider addV(int upriseStartTime, int upriseEndTime, Vec4f... colors) {
        int len = colors.length;
        float[] r = new float[len];
        float[] g = new float[len];
        float[] b = new float[len];
        float[] a = new float[len];

        for (int i = 0; i < len; i++) {
            Vec4f color = colors[i];
            r[i] = color.x;
            g[i] = color.y;
            b[i] = color.z;
            a[i] = color.w;
        }

        return add4f(upriseStartTime, upriseEndTime, r, g, b, a);
    }

    public TimeOfDayProvider addI(int upriseStartTime, int upriseEndTime, int... colors) {
        int len = colors.length;
        float[] r = new float[len];
        float[] g = new float[len];
        float[] b = new float[len];
        float[] a = new float[len];

        for (int i = 0; i < len; i++) {
            int color = colors[i];
            r[i] = ((color >> 24) & 0xFF) / 255.0F;
            g[i] = ((color >> 16) & 0xFF) / 255.0F;
            b[i] = ((color >> 8) & 0xFF) / 255.0F;
            a[i] = (color & 0xFF) / 255.0F;
        }

        return add4f(upriseStartTime, upriseEndTime, r, g, b, a);
    }

    public TimeOfDayProvider cycleAll() {
        for (int i = 0; i < this.timesOfDay.size(); i++) {
            TimeOfDay timeOfDay = this.timesOfDay.get(i);
            TimeOfDay timeOfDayPrev = this.timesOfDay.get(i == 0 ? this.timesOfDay.size() - 1 : i - 1);
            timeOfDayPrev.fallStartTime = timeOfDay.upriseStartTime;
            timeOfDayPrev.fallEndTime = timeOfDay.upriseEndTime;
        }

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            timeOfDay.fixtime(this.dayLength);
        }

        return this;
    }

    public Vec4f getColorV4f(int id, long worldTimeLong) {
        int worldTime = (int) (worldTimeLong % this.dayLength);
        float r = 0.0F;
        float g = 0.0F;
        float b = 0.0F;
        float a = 0.0F;

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                r += timeOfDay.colorR[id] * power;
                g += timeOfDay.colorG[id] * power;
                b += timeOfDay.colorB[id] * power;
                a += timeOfDay.colorA[id] * power;
            }
        }

        return new Vec4f(r, g, b, a);
    }

    public int getColorI(int id, long worldTimeLong) {
        int worldTime = (int) (worldTimeLong % this.dayLength);
        float r = 0.0F;
        float g = 0.0F;
        float b = 0.0F;
        float a = 0.0F;

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                r += timeOfDay.colorR[id] * power;
                g += timeOfDay.colorG[id] * power;
                b += timeOfDay.colorB[id] * power;
                a += timeOfDay.colorA[id] * power;
            }
        }

        int ri = (int) (Math.min(1.0F, Math.max(0.0F, r)) * 255.0F);
        int gi = (int) (Math.min(1.0F, Math.max(0.0F, g)) * 255.0F);
        int bi = (int) (Math.min(1.0F, Math.max(0.0F, b)) * 255.0F);
        int ai = (int) (Math.min(1.0F, Math.max(0.0F, a)) * 255.0F);

        return (ri << 24) | (gi << 16) | (bi << 8) | ai;
    }

    public Vec3d[] getColorsV3d(int idFirst, int amount, long worldTimeLong) {
        int worldTime = (int) (worldTimeLong % this.dayLength);
        Vec3d[] vectors = new Vec3d[amount];
        float[] r = new float[amount];
        float[] g = new float[amount];
        float[] b = new float[amount];
        int fpa = idFirst + amount;

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int j = idFirst; j < fpa; j++) {
                    int k = j - idFirst;
                    r[k] += timeOfDay.colorR[j] * power;
                    g[k] += timeOfDay.colorG[j] * power;
                    b[k] += timeOfDay.colorB[j] * power;
                }
            }
        }

        for (int j = 0; j < amount; j++) {
            vectors[j] = new Vec3d(r[j], g[j], b[j]);
        }

        return vectors;
    }

    @SideOnly(Side.CLIENT)
    public Vector4f[] getColors(int idFirst, int amount, long worldTimeLong) {
        int worldTime = (int) (worldTimeLong % this.dayLength);
        Vector4f[] vectors = new Vector4f[amount];
        float[] r = new float[amount];
        float[] g = new float[amount];
        float[] b = new float[amount];
        float[] a = new float[amount];
        int fpa = idFirst + amount;

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int j = idFirst; j < fpa; j++) {
                    int k = j - idFirst;
                    r[k] += timeOfDay.colorR[j] * power;
                    g[k] += timeOfDay.colorG[j] * power;
                    b[k] += timeOfDay.colorB[j] * power;
                    a[k] += timeOfDay.colorA[j] * power;
                }
            }
        }

        for (int j = 0; j < amount; j++) {
            vectors[j] = new Vector4f(r[j], g[j], b[j], a[j]);
        }

        return vectors;
    }

    public float[] getColorsFA(int idFirst, int amount, long worldTimeLong) {
        int worldTime = (int) (worldTimeLong % this.dayLength);
        float[] result = new float[amount * 4];

        int rOffset = 0;
        int gOffset = amount;
        int bOffset = amount * 2;
        int aOffset = amount * 3;

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int i = 0; i < amount; i++) {
                    int id = idFirst + i;
                    result[rOffset + i] += timeOfDay.colorR[id] * power;
                    result[gOffset + i] += timeOfDay.colorG[id] * power;
                    result[bOffset + i] += timeOfDay.colorB[id] * power;
                    result[aOffset + i] += timeOfDay.colorA[id] * power;
                }
            }
        }

        return result;
    }

    public void getColors(int idFirst, int amount, long worldTimeLong, float[] dest) {
        int worldTime = (int) (worldTimeLong % this.dayLength);

        int bOffset = amount * 2;
        int aOffset = amount * 3;

        Arrays.fill(dest, 0, amount * 4, 0.0F);

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int i = 0; i < amount; i++) {
                    int id = idFirst + i;
                    dest[i]           += timeOfDay.colorR[id] * power;
                    dest[amount + i] += timeOfDay.colorG[id] * power;
                    dest[bOffset + i] += timeOfDay.colorB[id] * power;
                    dest[aOffset + i] += timeOfDay.colorA[id] * power;
                }
            }
        }
    }

    public void getColors4fa(int idFirst, int amount, long worldTimeLong, float[] outR, float[] outG, float[] outB, float[] outA) {
        int worldTime = (int) (worldTimeLong % this.dayLength);

        Arrays.fill(outR, 0, amount, 0.0F);
        Arrays.fill(outG, 0, amount, 0.0F);
        Arrays.fill(outB, 0, amount, 0.0F);
        Arrays.fill(outA, 0, amount, 0.0F);

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int i = 0; i < amount; i++) {
                    int id = idFirst + i;
                    outR[i] += timeOfDay.colorR[id] * power;
                    outG[i] += timeOfDay.colorG[id] * power;
                    outB[i] += timeOfDay.colorB[id] * power;
                    outA[i] += timeOfDay.colorA[id] * power;
                }
            }
        }
    }

    public void getColors3fa(int idFirst, int amount, long worldTimeLong, float[] outR, float[] outG, float[] outB) {
        int worldTime = (int) (worldTimeLong % this.dayLength);

        Arrays.fill(outR, 0, amount, 0.0F);
        Arrays.fill(outG, 0, amount, 0.0F);
        Arrays.fill(outB, 0, amount, 0.0F);

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int i = 0; i < amount; i++) {
                    int id = idFirst + i;
                    outR[i] += timeOfDay.colorR[id] * power;
                    outG[i] += timeOfDay.colorG[id] * power;
                    outB[i] += timeOfDay.colorB[id] * power;
                }
            }
        }
    }

    public void getColors(int idFirst, int amount, long worldTimeLong,
                          float[] outR, float[] outG, float[] outB, float[] outA, int outOffset) {
        int worldTime = (int) (worldTimeLong % this.dayLength);

        Arrays.fill(outR, outOffset, outOffset + amount, 0.0F);
        Arrays.fill(outG, outOffset, outOffset + amount, 0.0F);
        Arrays.fill(outB, outOffset, outOffset + amount, 0.0F);
        Arrays.fill(outA, outOffset, outOffset + amount, 0.0F);

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(worldTime, this);
            if (power > 0.0F) {
                for (int i = 0; i < amount; i++) {
                    int id = idFirst + i;
                    int targetIdx = outOffset + i;

                    outR[targetIdx] += timeOfDay.colorR[id] * power;
                    outG[targetIdx] += timeOfDay.colorG[id] * power;
                    outB[targetIdx] += timeOfDay.colorB[id] * power;
                    outA[targetIdx] += timeOfDay.colorA[id] * power;
                }
            }
        }
    }

    public float getPowerOf(int index, long worldTimeLong) {
        int worldTime = (int) (worldTimeLong % this.dayLength);
        return this.timesOfDay.get(index).getPower(worldTime, this);
    }

    public void resetAndDebug() {
        for (int i = 0; i < this.timesOfDay.size(); i++) {
            TimeOfDay timeOfDay = this.timesOfDay.get(i);

            for (int j = 0; j < timeOfDay.colorR.length; j++) {
                if (this.firstReset) {
                    Debugger.debugColors.put(i + " " + j, new Vec4f(timeOfDay.colorR[j], timeOfDay.colorG[j], timeOfDay.colorB[j], timeOfDay.colorA[j]));
                } else {
                    Vec4f colors = Debugger.getDebugColor(i + " " + j);
                    timeOfDay.colorR[j] = colors.x;
                    timeOfDay.colorG[j] = colors.y;
                    timeOfDay.colorB[j] = colors.z;
                    timeOfDay.colorA[j] = colors.w;
                }
            }
        }

        this.firstReset = false;
        if (Debugger.floats[15] == 123.0F) {
            Debugger.floats[15] = 0.0F;
            this.printSourceCode();
        }
    }

    public void printSourceCode() {
        for (TimeOfDay timeOfDay : this.timesOfDay) {
            System.out.print(".addO(" + timeOfDay.upriseStartTime + ", " + timeOfDay.upriseEndTime);

            for (int j = 0; j < timeOfDay.colorR.length; j++) {
                System.out.print(", new Vec4f(" + timeOfDay.colorR[j] + "F, " + timeOfDay.colorG[j] + "F, " + timeOfDay.colorB[j] + "F, " + timeOfDay.colorA[j] + "F)");
            }

            System.out.print(")");
            System.out.print("\n");
        }
    }

    public void setLightmapColors(int providerColorIndex, long worldTime, float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        float gamma = 1.0F + Minecraft.getMinecraft().gameSettings.gammaSetting * 0.3F;
        skyLight *= gamma;
        blockLight *= gamma;

        int time = (int) worldTime % this.dayLength;
        float colorR = 0.0F;
        float colorG = 0.0F;
        float colorB = 0.0F;

        for (TimeOfDay timeOfDay : this.timesOfDay) {
            float power = timeOfDay.getPower(time, this);
            if (power > 0.0F) {
                colorR += timeOfDay.colorR[providerColorIndex] * power;
                colorG += timeOfDay.colorG[providerColorIndex] * power;
                colorB += timeOfDay.colorB[providerColorIndex] * power;
            }
        }

        float limit = 0.2F;
        colors[0] = MathHelper.clamp(colorR * skyLight + colors[0] * blockLight, colors[0] * limit, 1.0F);
        colors[1] = MathHelper.clamp(colorG * skyLight + colors[1] * blockLight, colors[1] * limit, 1.0F);
        colors[2] = MathHelper.clamp(colorB * skyLight + colors[2] * blockLight, colors[2] * limit, 1.0F);
    }

    public static class TimeOfDay {

        public int upriseStartTime;
        public int upriseEndTime;
        public int fallStartTime;
        public int fallEndTime;
        public float[] colorR;
        public float[] colorG;
        public float[] colorB;
        public float[] colorA;

        public TimeOfDay(int upriseStartTime, int upriseEndTime, int fallStartTime, int fallEndTime, float[] colorR, float[] colorG, float[] colorB, float[] colorA, TimeOfDayProvider timeOfDayProvider) {
            this.upriseStartTime = upriseStartTime;
            this.upriseEndTime = upriseEndTime;
            this.fallStartTime = fallStartTime;
            this.fallEndTime = fallEndTime;
            this.colorR = colorR;
            this.colorA = colorA;
            this.colorG = colorG;
            this.colorB = colorB;
            this.fixtime(timeOfDayProvider.dayLength);
        }

        public void fixtime(int daylength) {
            if (this.upriseEndTime < this.upriseStartTime) {
                this.upriseEndTime += daylength;
            }

            if (this.fallStartTime < this.upriseEndTime) {
                this.fallStartTime += daylength;
            }

            if (this.fallEndTime < this.fallStartTime) {
                this.fallEndTime += daylength;
            }
        }

        @Override
        public String toString() {
            return "(upSt: " + this.upriseStartTime + " upEn: " + this.upriseEndTime + " | falSt: " + this.fallStartTime + " falEn: " + this.fallEndTime + ")";
        }

        public float getPower(int clampedWorldTime, TimeOfDayProvider timeOfDayProvider) {
            float selector2 = clampedWorldTime + timeOfDayProvider.dayLength;
            float value1 = GetMOP.getFromTo((float) clampedWorldTime, (float) this.upriseStartTime, (float) this.upriseEndTime) - GetMOP.getFromTo((float) clampedWorldTime, (float) this.fallStartTime, (float) this.fallEndTime);
            float value2 = GetMOP.getFromTo(selector2, (float) this.upriseStartTime, (float) this.upriseEndTime) - GetMOP.getFromTo(selector2, (float) this.fallStartTime, (float) this.fallEndTime);
            return MathHelper.clamp(value1 + value2, 0.0F, 1.0F);
        }

    }

}
