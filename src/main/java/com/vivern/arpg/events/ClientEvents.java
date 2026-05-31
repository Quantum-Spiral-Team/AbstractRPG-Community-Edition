package com.vivern.arpg.events;

import com.vivern.arpg.Tags;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.ServerKeyTracker;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.packet.keys.PacketKeysState;
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

    private static final Map<KeyBinding, ServerKeyTracker.Keys> KEY_MAP = new HashMap<>();
    private static byte lastStateMask = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null) {
            boolean inGui = Minecraft.getMinecraft().currentScreen != null;
            byte currentStateMask = 0;

            if (!inGui) {
                for (Map.Entry<KeyBinding, ServerKeyTracker.Keys> entry : KEY_MAP.entrySet()) {
                    if (entry.getKey().isKeyDown()) {
                        currentStateMask |= entry.getValue().getMask();
                    }
                }
            }

            if (currentStateMask != lastStateMask) {
                lastStateMask = currentStateMask;
                PacketHandler.NETWORK.sendToServer(new PacketKeysState(currentStateMask));
            }
        }
    }

    private static void registerKey(KeyBinding keyBind, ServerKeyTracker.Keys key) {
        KEY_MAP.put(keyBind, key);
    }

    static {
        registerKey(Keys.USE, ServerKeyTracker.Keys.USE);
        registerKey(Keys.PRIMARYATTACK, ServerKeyTracker.Keys.PRIMARY);
        registerKey(Keys.SECONDARYATTACK, ServerKeyTracker.Keys.SECONDARY);
        registerKey(Keys.SCOPE, ServerKeyTracker.Keys.SCOPE);
        registerKey(Keys.GRENADE, ServerKeyTracker.Keys.GRENADE);
        registerKey(Keys.GRAPLINGHOOK, ServerKeyTracker.Keys.HOOK);
        registerKey(Keys.HEADABILITY, ServerKeyTracker.Keys.ABILITY);
    }
}
