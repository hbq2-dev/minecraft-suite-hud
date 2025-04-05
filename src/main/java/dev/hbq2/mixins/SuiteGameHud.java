package dev.hbq2.mixins;

import dev.hbq2.hud.SuiteLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(InGameHud.class)
public class SuiteGameHud {
	@Inject(method = "render", at = @At("HEAD"))
	private void onDraw(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		CompletableFuture.runAsync(() -> SuiteLoader.drawAsync(context), MinecraftClient.getInstance()::executeTask);
	}
}
