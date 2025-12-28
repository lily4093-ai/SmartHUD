package com.smarthud.hud;

import com.smarthud.SmartHudClient;
import com.smarthud.config.HudConfig;
import com.smarthud.util.CoordinateConverter;
import com.smarthud.util.DirectionHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

// Implements both old (1.21-1.21.5) and new (1.21.6+) HUD APIs
public class HudRenderer implements HudRenderCallback, HudElement {

    private static boolean firstRender = true;

    // New API method (1.21.6+)
    @Override
    public void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (firstRender) {
            System.out.println("[SmartHUD] render() called (NEW API)");
            firstRender = false;
        }
        renderHudContent(drawContext, tickCounter);
    }

    // Old API method (1.21-1.21.5)
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (firstRender) {
            System.out.println("[SmartHUD] onHudRender() called (OLD API)");
            firstRender = false;
        }
        renderHudContent(drawContext, tickCounter);
    }

    private void renderHudContent(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // Debug: Fill a rectangle to see if rendering works at all
        drawContext.fill(10, 10, 200, 50, 0x80FF0000); // Red semi-transparent box

        // Debug: Always show test text - try different draw method
        TextRenderer textRenderer = client.textRenderer;
        drawContext.drawTextWithShadow(textRenderer, "SmartHUD Test", 15, 15, 0xFFFFFF);

        HudConfig config = SmartHudClient.config;
        if (config == null) {
            drawContext.drawTextWithShadow(textRenderer, "Config is NULL!", 10, 30, 0xFF0000);
            return;
        }

        PlayerEntity player = client.player;
        World world = client.world;

        List<String> lines = new ArrayList<>();

        // Current coordinates
        if (config.showCoordinates) {
            BlockPos pos = player.getBlockPos();
            lines.add(String.format("X: %d Y: %d Z: %d", pos.getX(), pos.getY(), pos.getZ()));
        }

        // Direction
        if (config.showDirection) {
            String direction = DirectionHelper.getCardinalDirection(player.getYaw());
            lines.add(String.format("Facing: %s (%.0f°)", direction, ((player.getYaw() % 360 + 360) % 360)));
        }

        // Dimension coordinates (Nether/Overworld conversion)
        if (config.showDimensionCoords) {
            BlockPos converted = CoordinateConverter.convertCoordinates(player.getBlockPos(), world);
            if (converted != null) {
                String dimName = CoordinateConverter.isNether(world) ? "Overworld" : "Nether";
                lines.add(String.format("%s: X: %d Z: %d", dimName, converted.getX(), converted.getZ()));
            }
        }

        // Looking at block
        if (config.showLookingAt) {
            HitResult hit = client.crosshairTarget;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
                lines.add(String.format("Looking at: X: %d Y: %d Z: %d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            }
        }

        // Biome
        if (config.showBiome) {
            RegistryEntry<Biome> biome = world.getBiome(player.getBlockPos());
            String biomeName = biome.getIdAsString();
            if (biomeName != null) {
                String shortName = biomeName.replace("minecraft:", "");
                lines.add("Biome: " + shortName);
            }
        }

        // Time
        if (config.showTime) {
            long time = world.getTimeOfDay() % 24000;
            int hours = (int)((time / 1000 + 6) % 24);
            int minutes = (int)((time % 1000) * 60 / 1000);
            lines.add(String.format("Time: %02d:%02d", hours, minutes));
        }

        // Render the HUD
        renderHud(drawContext, client.textRenderer, lines, config);
    }

    private void renderHud(DrawContext drawContext, TextRenderer textRenderer, List<String> lines, HudConfig config) {
        if (lines.isEmpty()) return;

        int screenWidth = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();

        int lineHeight = textRenderer.fontHeight + 2;
        int totalHeight = lines.size() * lineHeight;
        int maxWidth = 0;

        for (String line : lines) {
            int width = textRenderer.getWidth(line);
            if (width > maxWidth) maxWidth = width;
        }

        int x = config.offsetX;
        int y = config.offsetY;

        switch (config.position) {
            case TOP_RIGHT:
                x = screenWidth - maxWidth - config.offsetX;
                break;
            case BOTTOM_LEFT:
                y = screenHeight - totalHeight - config.offsetY;
                break;
            case BOTTOM_RIGHT:
                x = screenWidth - maxWidth - config.offsetX;
                y = screenHeight - totalHeight - config.offsetY;
                break;
            case TOP_LEFT:
            default:
                break;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int lineY = y + i * lineHeight;

            if (config.textShadow) {
                drawContext.drawTextWithShadow(textRenderer, line, x, lineY, config.textColor);
            } else {
                drawContext.drawText(textRenderer, line, x, lineY, config.textColor, false);
            }
        }
    }
}
