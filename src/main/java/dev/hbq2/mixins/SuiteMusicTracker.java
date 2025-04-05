package dev.hbq2.mixins;

import dev.hbq2.hud.SuiteData;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = MusicTracker.class, priority = 1001)
public class SuiteMusicTracker {

    @Shadow
    private SoundInstance current;

    @Inject(method = "tick()V", at = @At(value = "HEAD"))
    public void tick(CallbackInfo ci) {
        if (current != null) {
            Sound sound = current.getSound();
            if (sound != null) {

                Identifier currentMusicRl = sound.getLocation();

                if (!Objects.equals(SuiteData.INSTANCE.getCurrentMusicIdentifier(), current.getSound().getIdentifier())) {
                    SuiteData.INSTANCE.setCurrentMusicIdentifier(currentMusicRl);
                }
            }
        }
        else if (SuiteData.INSTANCE.getCurrentMusicIdentifier() != null) {
            SuiteData.INSTANCE.setCurrentMusicIdentifier(null);
        }
    }
}
