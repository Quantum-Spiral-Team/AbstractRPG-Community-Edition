package com.vivern.arpg.events;

import com.vivern.arpg.Reference;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.ServerKeyTracker;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.packet.PacketKeysState;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)
@SideOnly(Side.CLIENT)
public class ClientEvents {

    private static final Object2ReferenceMap<KeyBinding, ServerKeyTracker.Keys> KEY_MAP = new Object2ReferenceOpenHashMap<>();
    private static short lastStateMask = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player != null && event.phase == TickEvent.Phase.END) {
            boolean inGui = mc.currentScreen != null;
            short currentStateMask = 0;

            if (!inGui) {
                for (Object2ReferenceMap.Entry<KeyBinding, ServerKeyTracker.Keys> entry : KEY_MAP.object2ReferenceEntrySet()) {
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
        registerKey(Keys.FORWARD, ServerKeyTracker.Keys.FORWARD);
        registerKey(Keys.BACK, ServerKeyTracker.Keys.BACKWARD);
        registerKey(Keys.LEFT, ServerKeyTracker.Keys.LEFT);
        registerKey(Keys.RIGHT, ServerKeyTracker.Keys.RIGHT);
        registerKey(Keys.JUMP, ServerKeyTracker.Keys.JUMP);
        registerKey(Keys.SPRINT, ServerKeyTracker.Keys.SPRINT);
    }
}
