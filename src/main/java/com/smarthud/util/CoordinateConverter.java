package com.smarthud.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoordinateConverter {
    private static final int NETHER_SCALE = 8;

    public static boolean isNether(World world) {
        return world.getRegistryKey().getValue().toString().equals("minecraft:the_nether");
    }

    public static boolean isOverworld(World world) {
        return world.getRegistryKey().getValue().toString().equals("minecraft:overworld");
    }

    public static BlockPos convertCoordinates(BlockPos pos, World world) {
        if (isNether(world)) {
            // Nether to Overworld (multiply by 8)
            return new BlockPos(pos.getX() * NETHER_SCALE, pos.getY(), pos.getZ() * NETHER_SCALE);
        } else if (isOverworld(world)) {
            // Overworld to Nether (divide by 8)
            return new BlockPos(pos.getX() / NETHER_SCALE, pos.getY(), pos.getZ() / NETHER_SCALE);
        }
        return null; // End or other dimensions don't have conversion
    }
}
