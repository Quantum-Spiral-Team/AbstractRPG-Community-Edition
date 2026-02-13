package com.Vivern.Arpg.main;

import com.Vivern.Arpg.arpgfix.Actions;
import com.Vivern.Arpg.arpgfix.KeyBitset;
import com.Vivern.Arpg.network.*;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = "arpg", value = Side.CLIENT)
public class ClientInputHandler {

    private static final long[] LAST_KEYS = new long[KeyBitset.LONG_COUNT];
    private static int LAST_PACKED = 0;
    private static boolean LAST_LMB = false;
    private static boolean LAST_RMB = false;

    // custom -> default
    private static final Int2IntMap INDIVIDUAL_BINDS = new Int2IntOpenHashMap();

    public static void updateIndividualBinds(int[] defaultBinds, int[] customBinds) {
        if (defaultBinds == null || customBinds == null) return;
        if (defaultBinds.length != customBinds.length) return;
        if (defaultBinds.length == 0) return;

        INDIVIDUAL_BINDS.clear();
        for (int i = 0; i < defaultBinds.length; i++) {
            INDIVIDUAL_BINDS.put(customBinds[i], defaultBinds[i]);
        }
    }

    private static int resolveKeyCode(int code) {
        return INDIVIDUAL_BINDS.getOrDefault(code, code);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player == null) return;

        if (mc.currentScreen != null) {
            // как в оригинале: при GUI ничего не читаем/не шлём
            return;
        }

        // 1) raw keyboard snapshot (опционально, но ты это хотел)
        long[] nowKeys = new long[KeyBitset.LONG_COUNT];
        for (KeyBinding kb : mc.gameSettings.keyBindings) {
            if (!kb.isKeyDown()) continue;

            int keyCode = resolveKeyCode(kb.getKeyCode());
            if (keyCode >= 0 && keyCode < Keyboard.KEYBOARD_SIZE) {
                KeyBitset.set(nowKeys, keyCode, true);
            }
        }

        // 2) mouse
        boolean lmb = Mouse.isButtonDown(0);
        boolean rmb = Mouse.isButtonDown(1);

        // 3) packed actions (как оригинал, но с учётом реальных KeyBinding’ов)
        int packed = 0;

        // ВАЖНО: здесь должны быть KeyBinding'и из мода (они учитывают кастомные настройки игрока)
        if (Keys.isKeyDown(Keys.FORWARD))        packed |= Actions.FORWARD;
        if (Keys.isKeyDown(Keys.RIGHT))          packed |= Actions.RIGHT;
        if (Keys.isKeyDown(Keys.LEFT))           packed |= Actions.LEFT;
        if (Keys.isKeyDown(Keys.BACK))           packed |= Actions.BACK;
        if (Keys.isKeyDown(Keys.JUMP))           packed |= Actions.JUMP;
        if (Keys.isKeyDown(Keys.SPRINT))         packed |= Actions.SPRINT;

        if (lmb) packed |= Actions.PRIMARYATTACK;
        if (rmb) packed |= Actions.SECONDARYATTACK;

//        if (Keys.isKeyDown(Keys.HEADABILITY))    packed |= Actions.HEADABILITY;
        if (Keys.isKeyDown(Keys.SCOPE))          packed |= Actions.SCOPE;
        if (Keys.isKeyDown(Keys.GRAPLINGHOOK))   packed |= Actions.GRAPLINGHOOK;
//        if (Keys.isKeyDown(Keys.USE))            packed |= Actions.USE;
//        if (Keys.isKeyDown(Keys.GRENADE))        packed |= Actions.GRENADE;

        // 4) отправка только при изменениях
        boolean changed =
                !KeyBitset.equals(nowKeys, LAST_KEYS) ||
                        packed != LAST_PACKED ||
                        lmb != LAST_LMB ||
                        rmb != LAST_RMB;

        if (changed) {
            PacketHandler.NETWORK.sendToServer(new PacketInputState(nowKeys, packed, lmb, rmb));

            System.arraycopy(nowKeys, 0, LAST_KEYS, 0, LAST_KEYS.length);
            LAST_PACKED = packed;
            LAST_LMB = lmb;
            LAST_RMB = rmb;
        }
    }
}