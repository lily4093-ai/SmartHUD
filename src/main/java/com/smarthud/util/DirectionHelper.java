package com.smarthud.util;

public class DirectionHelper {
    public static String getCardinalDirection(float yaw) {
        // Normalize yaw to 0-360 range
        float normalizedYaw = ((yaw % 360) + 360) % 360;

        if (normalizedYaw >= 337.5 || normalizedYaw < 22.5) {
            return "south";
        } else if (normalizedYaw >= 22.5 && normalizedYaw < 67.5) {
            return "southwest";
        } else if (normalizedYaw >= 67.5 && normalizedYaw < 112.5) {
            return "west";
        } else if (normalizedYaw >= 112.5 && normalizedYaw < 157.5) {
            return "northwest";
        } else if (normalizedYaw >= 157.5 && normalizedYaw < 202.5) {
            return "north";
        } else if (normalizedYaw >= 202.5 && normalizedYaw < 247.5) {
            return "northeast";
        } else if (normalizedYaw >= 247.5 && normalizedYaw < 292.5) {
            return "east";
        } else {
            return "southeast";
        }
    }
}
