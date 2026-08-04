package com.vivern.arpg.mixin.vanilla;

import com.google.common.collect.Multimap;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.SoundCategory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(SoundManager.class)
public abstract class MixinSoundManager {

    @Unique private static boolean arpg$soundManagerUpdatingNow;
    @Unique private ITickableSound[] arpg$tickableSoundsBuffer = new ITickableSound[0];

    @Shadow private int playTime;
    @Shadow private SoundManager.SoundSystemStarterThread sndSystem;
    @Shadow @Final private List<ITickableSound> tickableSounds;
    @Shadow @Final private Map<ISound, String> invPlayingSounds;
    @Shadow @Final private Map<String, ISound> playingSounds;
    @Shadow @Final private Map<String, Integer> playingSoundsStopTime;
    @Shadow @Final private Map<ISound, Integer> delayedSounds;
    @Shadow @Final private Multimap<SoundCategory, String> categorySounds;
    @Shadow @Final private static Logger LOGGER;
    @Shadow @Final private static Marker LOG_MARKER;

    @Shadow public void playSound(ISound sound) {}
    @Shadow public void stopSound(ISound sound) {}
    @Shadow protected abstract float getClampedVolume(ISound sound);
    @Shadow protected abstract float getClampedPitch(ISound soundIn);

    @Inject(
            method = "stopAllSounds",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$stopAllSounds(CallbackInfo ci) {
        if (arpg$soundManagerUpdatingNow) ci.cancel();
    }
    
    @Inject(
            method = "playSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$playSound(ISound sound, CallbackInfo ci) {
        if (arpg$soundManagerUpdatingNow) ci.cancel();
    }

    @Inject(
            method = "playDelayedSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$playDelayedSound(ISound sound, int delay, CallbackInfo ci) {
        if (arpg$soundManagerUpdatingNow) ci.cancel();
    }
    
    @Inject(
            method = "updateAllSounds",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$updateAllSounds(CallbackInfo ci) {
        arpg$soundManagerUpdatingNow = true;
        try {
            playTime += 1;
            SoundSystem soundSystem = (SoundSystem) sndSystem;

            arpg$tickTickableSounds(soundSystem);
            arpg$cleanupFinishedSounds(soundSystem);
            arpg$replayDelayedSounds();
        } finally {
            arpg$soundManagerUpdatingNow = false;
        }
        ci.cancel();
    }

    @Unique
    private void arpg$tickTickableSounds(SoundSystem soundSystem) {
        if (arpg$tickableSoundsBuffer.length != tickableSounds.size()) {
            arpg$tickableSoundsBuffer = new ITickableSound[tickableSounds.size()];
        }
        ITickableSound[] snapshot = tickableSounds.toArray(arpg$tickableSoundsBuffer);

        for (ITickableSound sound : snapshot) {
            sound.update();
            if (sound.isDonePlaying()) {
                stopSound(sound);
                continue;
            }
            String channel = invPlayingSounds.get(sound);
            soundSystem.setVolume(channel, getClampedVolume(sound));
            soundSystem.setPitch(channel, getClampedPitch(sound));
            soundSystem.setPosition(channel, sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
        }
    }

    @Unique
    private void arpg$cleanupFinishedSounds(SoundSystem soundSystem) {
        Iterator<Map.Entry<String, ISound>> iterator = playingSounds.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, ISound> entry = iterator.next();
            String channel = entry.getKey();

            if (soundSystem.playing(channel)) {
                continue;
            }
            if (playingSoundsStopTime.get(channel) > playTime) {
                continue;
            }

            ISound sound = entry.getValue();
            int repeatDelay = sound.getRepeatDelay();
            if (sound.canRepeat() && repeatDelay > 0) {
                delayedSounds.put(sound, playTime + repeatDelay);
            }

            iterator.remove();
            LOGGER.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", channel);
            soundSystem.removeSource(channel);
            playingSoundsStopTime.remove(channel);
            categorySounds.remove(sound.getCategory(), channel);

            if (sound instanceof ITickableSound) {
                tickableSounds.remove(sound);
            }
        }
    }

    @Unique
    private void arpg$replayDelayedSounds() {
        Iterator<Map.Entry<ISound, Integer>> iterator = delayedSounds.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<ISound, Integer> entry = iterator.next();
            if (playTime < entry.getValue()) {
                continue;
            }

            ISound sound = entry.getKey();
            if (sound instanceof ITickableSound) {
                ((ITickableSound) sound).update();
            }
            playSound(sound);
            iterator.remove();
        }
    }
}
