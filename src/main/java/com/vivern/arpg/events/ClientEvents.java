package com.vivern.arpg.events;

import com.vivern.arpg.Tags;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.ServerKeyTracker;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.packet.keys.PacketKeyPressed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
@SideOnly(Side.CLIENT)
public class ClientEvents {

    private static final Map<KeyBinding, Byte> KEY_TO_ID = new HashMap<>();
    private static final Map<KeyBinding, Boolean> LAST_STATES = new HashMap<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null) {
            boolean inGui = Minecraft.getMinecraft().currentScreen != null;

            for (Map.Entry<KeyBinding, Byte> entry : KEY_TO_ID.entrySet()) {
                KeyBinding key = entry.getKey();
                byte id = entry.getValue();

                boolean isDown = !inGui && key.isKeyDown();
                boolean wasDown = LAST_STATES.get(key);

                if (isDown != wasDown) {
                    LAST_STATES.put(key, isDown);
                    PacketHandler.NETWORK.sendToServer(new PacketKeyPressed(id, isDown));
                }
            }
        }
    }

    private static void registerKey(KeyBinding key, ServerKeyTracker.Keys modKey) {
        KEY_TO_ID.put(key, modKey.getId());
    }

    static {
        registerKey(Keys.USE,             ServerKeyTracker.Keys.USE);
        registerKey(Keys.PRIMARYATTACK,   ServerKeyTracker.Keys.PRIMARY);
        registerKey(Keys.SECONDARYATTACK, ServerKeyTracker.Keys.SECONDARY);
        registerKey(Keys.SCOPE,           ServerKeyTracker.Keys.SCOPE);
        registerKey(Keys.GRENADE,         ServerKeyTracker.Keys.GRENADE);
        registerKey(Keys.GRAPLINGHOOK,    ServerKeyTracker.Keys.HOOK);
        registerKey(Keys.HEADABILITY,     ServerKeyTracker.Keys.ABILITY);

        for (KeyBinding key : KEY_TO_ID.keySet()) {
            LAST_STATES.put(key, false);
        }
    }
}
