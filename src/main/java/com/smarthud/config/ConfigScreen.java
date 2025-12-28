package com.smarthud.config;

import com.smarthud.SmartHudClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private final HudConfig config;

    public ConfigScreen(Screen parent) {
        super(Text.literal("SmartHUD Configuration"));
        this.parent = parent;
        this.config = SmartHudClient.getConfig();
    }

    @Override
    protected void init() {
        int y = 40;
        int spacing = 25;

        // Show Coordinates
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.showCoordinates)
                .build(width / 2 - 155, y, 150, 20, Text.literal("Coordinates"),
                        (button, value) -> config.showCoordinates = value));

        // Show Direction
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.showDirection)
                .build(width / 2 + 5, y, 150, 20, Text.literal("Direction"),
                        (button, value) -> config.showDirection = value));

        y += spacing;

        // Show Dimension Coords
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.showDimensionCoords)
                .build(width / 2 - 155, y, 150, 20, Text.literal("Dimension Coords"),
                        (button, value) -> config.showDimensionCoords = value));

        // Show Looking At
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.showLookingAt)
                .build(width / 2 + 5, y, 150, 20, Text.literal("Looking At"),
                        (button, value) -> config.showLookingAt = value));

        y += spacing;

        // Show Biome
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.showBiome)
                .build(width / 2 - 155, y, 150, 20, Text.literal("Biome"),
                        (button, value) -> config.showBiome = value));

        // Show Time
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.showTime)
                .build(width / 2 + 5, y, 150, 20, Text.literal("Time"),
                        (button, value) -> config.showTime = value));

        y += spacing;

        // Text Shadow
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(config.textShadow)
                .build(width / 2 - 155, y, 150, 20, Text.literal("Text Shadow"),
                        (button, value) -> config.textShadow = value));

        // Position
        this.addDrawableChild(CyclingButtonWidget.<HudConfig.HudPosition>builder(
                pos -> Text.literal(pos.name()),
                () -> config.position)
                .values(HudConfig.HudPosition.values())
                .build(width / 2 + 5, y, 150, 20, Text.literal("Position"),
                        (button, value) -> config.position = value));

        y += spacing + 20;

        // Done button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> {
            config.save();
            client.setScreen(parent);
        }).dimensions(width / 2 - 100, y, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, 0xFFFFFF);
    }

    @Override
    public void close() {
        config.save();
        client.setScreen(parent);
    }
}
