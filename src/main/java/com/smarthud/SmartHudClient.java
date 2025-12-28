package com.smarthud;

import com.smarthud.config.HudConfig;
import com.smarthud.hud.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.util.Identifier;

public class SmartHudClient implements ClientModInitializer {
    public static HudConfig config;

    @Override
    public void onInitializeClient() {
        System.out.println("[SmartHUD] Initializing SmartHUD...");

        config = HudConfig.load();
        System.out.println("[SmartHUD] Config loaded: " + (config != null ? "OK" : "FAILED"));

        HudRenderer hudRenderer = new HudRenderer();

        // Try new API first (1.21.6+), fallback to old API (1.21-1.21.5)
        try {
            HudElementRegistry.addLast(
                Identifier.of("smarthud", "main_hud"),
                hudRenderer
            );
            System.out.println("[SmartHUD] Registered HUD using NEW API (1.21.6+)");
        } catch (NoClassDefFoundError | NoSuchMethodError e) {
            // Fallback to old API
            net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback.EVENT.register(hudRenderer);
            System.out.println("[SmartHUD] Registered HUD using OLD API (1.21-1.21.5)");
        }

        System.out.println("[SmartHUD] Initialization complete!");
    }

    public static HudConfig getConfig() {
        return config;
    }
}
