package com.vivern.arpg.main;

import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerKeyTracker {

    private static final Map<UUID, EnumSet<Keys>> CURRENT_KEYS = new HashMap<>();
    private static final Map<UUID, EnumSet<Keys>> PREVIOUS_KEYS = new HashMap<>();

    public static void updatePlayerKeys(EntityPlayer player, byte mask) {
        if (player == null) return;
        UUID uuid = player.getUniqueID();

        EnumSet<Keys> oldState = CURRENT_KEYS.getOrDefault(uuid, EnumSet.noneOf(Keys.class));
        PREVIOUS_KEYS.put(uuid, oldState.clone());

        EnumSet<Keys> newState = EnumSet.noneOf(Keys.class);
        for (Keys key : Keys.values()) {
            if ((mask & key.mask) != 0) newState.add(key);
        }
        CURRENT_KEYS.put(uuid, newState);
    }

    public static boolean isKeyPressed(EntityPlayer player, Keys key) {
        UUID uuid = player.getUniqueID();
        EnumSet<Keys> current = CURRENT_KEYS.get(uuid);
        EnumSet<Keys> previous = PREVIOUS_KEYS.get(uuid);

        boolean isNowDown = current != null && current.contains(key);
        boolean wasDown = previous != null && previous.contains(key);

        return isNowDown && !wasDown;
    }

    public static boolean isKeyDown(EntityPlayer player, Keys key) {
        EnumSet<Keys> keys = CURRENT_KEYS.get(player.getUniqueID());
        return keys != null && keys.contains(key);
    }

    public static void clearPlayer(EntityPlayer player) {
        if (player != null) {
            UUID uuid = player.getUniqueID();
            CURRENT_KEYS.remove(uuid);
            PREVIOUS_KEYS.remove(uuid);
        }
    }

    public static void resetTick() {
        for (Map.Entry<UUID, EnumSet<Keys>> entry : CURRENT_KEYS.entrySet()) {
            PREVIOUS_KEYS.put(entry.getKey(), entry.getValue().clone());
        }
    }

    /**
     * Если потребуется увеличить количество доступных кнопок, просто поменяйте byte на short.
     * Если будет необходимо сильно больше клавиш (хотя я сомневаюсь, что подобная необходимость возникнет), используйте {@link java.util.BitSet}.
      */
    public enum Keys {
        USE(1),             // 00000001 (1)
        PRIMARY(1 << 1),    // 00000010 (2)
        SECONDARY(1 << 2),  // 00000100 (4)
        SCOPE(1 << 3),      // 00001000 (8)
        GRENADE(1 << 4),    // 00010000 (16)
        HOOK(1 << 5),       // 00100000 (32)
        ABILITY(1 << 6),    // 01000000 (64)
        ;

        private final byte mask;

        Keys(int mask) {
            this.mask = (byte) mask;
        }

        public byte getMask() {
            return mask;
        }
    }

}