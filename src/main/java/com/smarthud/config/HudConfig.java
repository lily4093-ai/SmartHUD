package com.smarthud.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HudConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("smarthud.json");

    public HudPosition position = HudPosition.BOTTOM_RIGHT;
    public boolean showCoordinates = true;
    public boolean showDirection = true;
    public boolean showDimensionCoords = true;
    public boolean showLookingAt = true;
    public boolean showBiome = true;
    public boolean showTime = true;
    public int offsetX = 5;
    public int offsetY = 5;
    public int textColor = 0xFFFFFF;
    public boolean textShadow = true;

    public enum HudPosition {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public static HudConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                return GSON.fromJson(json, HudConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HudConfig config = new HudConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
