package com.vivern.arpg.main;

import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

public class ServerKeyTracker {
    private static final Map<UUID, Set<Byte>> PRESSED_KEYS = new HashMap<>();
    private static final Map<UUID, Set<Byte>> JUST_PRESSED_KEYS = new HashMap<>();

    public static void setKeyPressed(EntityPlayer player, byte keyId, boolean isPressed) {
        if (player == null) return;

        UUID uuid = player.getUniqueID();

        if (isPressed) {
            PRESSED_KEYS.computeIfAbsent(uuid, k -> new HashSet<>()).add(keyId);
            JUST_PRESSED_KEYS.computeIfAbsent(uuid, k -> new HashSet<>()).add(keyId);
        } else {
           Set<Byte> keys = PRESSED_KEYS.get(uuid);
            if (keys != null) {
                keys.remove(keyId);

                if (keys.isEmpty()) {
                    PRESSED_KEYS.remove(uuid);
                }
            }
        }
    }

    public static boolean isKeyPressed(EntityPlayer player, byte keyId) {
        if (player == null) return false;

        Set<Byte> keys = PRESSED_KEYS.get(player.getUniqueID());
        return keys != null && keys.contains(keyId);
    }

    public static boolean isKeyPressed(EntityPlayer player, Keys key) {
        return isKeyPressed(player, key.getId());
    }

    public static boolean isKeyDown(EntityPlayer player, byte id) {
        if (player == null) return false;
        Set<Byte> keys = PRESSED_KEYS.get(player.getUniqueID());
        return keys != null && keys.contains(id);
    }

    public static boolean isKeyDown(EntityPlayer player, Keys key) {
        return isKeyDown(player, key.getId());
    }

    public static void clearPlayer(EntityPlayer player) {
        if (player != null) {
            UUID uuid = player.getUniqueID();
            PRESSED_KEYS.remove(uuid);
            JUST_PRESSED_KEYS.remove(uuid);
        }
    }

    public static void resetTick() {
        JUST_PRESSED_KEYS.clear();
    }

    public enum Keys {
        USE((byte) 0),
        PRIMARY((byte) 1),
        SECONDARY((byte) 2),
        SCOPE((byte) 3),
        GRENADE((byte) 4),
        HOOK((byte) 5),
        ABILITY((byte) 6),
        ;

        private final byte id;

        Keys(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }
    }
}
