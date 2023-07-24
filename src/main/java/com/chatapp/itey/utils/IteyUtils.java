package com.chatapp.itey.utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class IteyUtils {

    public static String newUUID(String username) {
        return generateUUID(username).toString();
    }
    public static UUID generateUUID(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        byte[] inputBytes = username.getBytes();
        UUID timeBasedUUID = Generators.timeBasedGenerator().generate();
        long timestamp = (timeBasedUUID.timestamp() - 0x01b21dd213814000L) / 10000L;

        // Combine the timestamp with the input string bytes
        byte[] combinedBytes = new byte[inputBytes.length + 8];
        System.arraycopy(inputBytes, 0, combinedBytes, 0, inputBytes.length);
        for (int i = 0; i < 8; i++) {
            combinedBytes[inputBytes.length + i] = (byte) ((timestamp >> 8 * i) & 0xFF);
        }

        // Create a new UUID from the combined bytes
        UUID finalUUID = UUID.nameUUIDFromBytes(combinedBytes);

        return finalUUID;
    }
}
