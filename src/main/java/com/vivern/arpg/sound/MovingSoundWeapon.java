package com.vivern.arpg.sound;

import com.vivern.arpg.main.GetMOP;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundWeapon extends MovingSound {
    public final EntityLivingBase entity;
    public Item itemInHand;
    public int startTime;
    public int ticksExisted;
    public SoundEvent soundEvent;
    public int endTime;
    public float startPitch;
    public float endPitch;
    public float initVolume;
    public float initPitch;
    public long endDate;

    public MovingSoundWeapon(
            EntityLivingBase entity,
            Item itemInHand,
            SoundEvent soundEvent,
            SoundCategory category,
            float initVolume,
            float initPitch,
            int playtime,
            int startTime,
            int endTime,
            float startPitch,
            float endPitch
    ) {
        super(soundEvent, category);
        this.entity = entity;
        this.repeat = true;
        this.initVolume = initVolume;
        this.initPitch = initPitch;
        this.itemInHand = itemInHand;
        this.startTime = startTime;
        this.soundEvent = soundEvent;
        this.endTime = endTime;
        this.startPitch = startPitch;
        this.endPitch = endPitch;
        this.endDate = entity.world.getTotalWorldTime() + playtime;
    }

    @Override
    public void update() {
        if (!this.entity.isDead && this.entity.world != null) {
            ItemStack stack = this.entity.getHeldItemMainhand();
            if (!stack.isEmpty() && stack.getItem() == this.itemInHand) {
                long playtime = this.endDate - this.entity.world.getTotalWorldTime();
                float ft1 = GetMOP.getFromTo((float)this.ticksExisted, 0.0F, (float)this.startTime);
                float ft2 = 1.0F - GetMOP.getFromTo((float)playtime, 0.0F, (float)this.endTime);
                this.ticksExisted++;
                this.volume = (ft1 - ft2) * this.initVolume;
                this.pitch = this.startPitch * (1.0F - ft1) + this.initPitch * (ft1 - ft2) + this.endPitch * ft2;
                this.xPosF = (float)this.entity.posX;
                this.yPosF = (float)this.entity.posY;
                this.zPosF = (float)this.entity.posZ;
            } else {
                this.donePlaying = true;
            }
        } else {
            this.donePlaying = true;
        }
    }

    @Override
    public boolean isDonePlaying() {
        boolean b1 = super.isDonePlaying()
                || this.entity.isDead
                || this.entity.world == null
                || this.entity.world.getTotalWorldTime() >= this.endDate;
        if (!b1) {
            ItemStack stack = this.entity.getHeldItemMainhand();
            if (stack.isEmpty() || stack.getItem() != this.itemInHand) {
                b1 = true;
            }
        }

        return b1;
    }
}
