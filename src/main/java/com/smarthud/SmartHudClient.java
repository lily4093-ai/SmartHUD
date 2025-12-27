package com.smarthud;

import com.smarthud.config.HudConfig;
import com.smarthud.hud.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SmartHudClient implements ClientModInitializer {
    public static HudConfig config;

    @Override
    public void onInitializeClient() {
        config = HudConfig.load();

        HudRenderCallback.EVENT.register(new HudRenderer());
    }
}
