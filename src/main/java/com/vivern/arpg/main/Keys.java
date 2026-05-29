package com.vivern.arpg.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

//TODO довести до ума настройки клавиш
@SideOnly(Side.CLIENT)
public class Keys implements IKeyConflictContext {
   //private static final String catergory = "Arpg keys"; TODO UNUSED
   private static final GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
   public static final KeyBinding FORWARD = gameSettings.keyBindForward;
   public static final KeyBinding RIGHT = gameSettings.keyBindRight;
   public static final KeyBinding LEFT = gameSettings.keyBindLeft;
   public static final KeyBinding BACK = gameSettings.keyBindBack;
   public static final KeyBinding JUMP = gameSettings.keyBindJump;
   public static final KeyBinding SPRINT = gameSettings.keyBindSprint;
   public static final KeyBinding PRIMARYATTACK = gameSettings.keyBindAttack;
   public static final KeyBinding SECONDARYATTACK = gameSettings.keyBindUseItem;
   public static final KeyBinding HEADABILITY = new KeyBinding("keys.headability", 34, "Arpg keys");
   public static final KeyBinding GRENADE = new KeyBinding("keys.grenade", 34, "Arpg keys");
   public static final KeyBinding SCOPE = new KeyBinding("keys.scope", 3, "Arpg keys");
   public static final KeyBinding GRAPLINGHOOK = new KeyBinding("keys.hook", 19, "Arpg keys");
   public static final KeyBinding USE = gameSettings.keyBindUseItem;
   public static final Keys KEY_CONFLICT_CONTEXT = new Keys();

   public static void register() {
      setRegister(HEADABILITY);
      setRegister(SCOPE);
      setRegister(GRAPLINGHOOK);
      setRegister(GRENADE);
      HEADABILITY.setKeyConflictContext(KEY_CONFLICT_CONTEXT);
      SCOPE.setKeyConflictContext(KEY_CONFLICT_CONTEXT);
      GRAPLINGHOOK.setKeyConflictContext(KEY_CONFLICT_CONTEXT);
      USE.setKeyConflictContext(KEY_CONFLICT_CONTEXT);
      GRENADE.setKeyConflictContext(KEY_CONFLICT_CONTEXT);
   }

   private static void setRegister(KeyBinding binding) {
      ClientRegistry.registerKeyBinding(binding);
   }

   /**
    * @deprecated use {@link ServerKeyTracker#isKeyDown} on server and {@link KeyBinding#isKeyDown()} on client
    */
   @Deprecated
   public static boolean isKeyDown(KeyBinding key) {
      int i = key == PRIMARYATTACK ? ((KeyBindFixed)key).getKeyIndex() : key.getKeyCode();
      if (i != 0 && i < 256) {
         return i < 0 ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i);
      } else {
         return false;
      }
   }

   public static boolean unpackCheckKey(int allKeysPacked, KeyBinding key) {
      if (key == FORWARD) {
         return (allKeysPacked & 1) != 0;
      } else if (key == RIGHT) {
         return (allKeysPacked & 2) != 0;
      } else if (key == LEFT) {
         return (allKeysPacked & 4) != 0;
      } else if (key == BACK) {
         return (allKeysPacked & 8) != 0;
      } else if (key == JUMP) {
         return (allKeysPacked & 16) != 0;
      } else if (key == SPRINT) {
         return (allKeysPacked & 32) != 0;
      } else if (key == PRIMARYATTACK) {
         return (allKeysPacked & 64) != 0;
      } else if (key == SECONDARYATTACK) {
         return (allKeysPacked & 128) != 0;
      } else if (key == HEADABILITY) {
         return (allKeysPacked & 256) != 0;
      } else if (key == SCOPE) {
         return (allKeysPacked & 512) != 0;
      } else if (key == GRAPLINGHOOK) {
         return (allKeysPacked & 1024) != 0;
      } else if (key == USE) {
         return (allKeysPacked & 8192) != 0;
      } else {
         return key == GRENADE && (allKeysPacked & 16384) != 0;
      }
   }

   /**
    * @deprecated use {@link ServerKeyTracker#isKeyPressed} on server and {@link KeyBinding#isPressed()} on client
    */
   @Deprecated
   public static boolean isKeyPressed(EntityPlayer player, KeyBinding key) {
      if (key == PRIMARYATTACK || key == SECONDARYATTACK) {
         if (player.isHandActive()) {
            return false;
         }

         if (key == SECONDARYATTACK) {
            if (isKeyBlockedByItem(player.getHeldItemMainhand(), player, key)) {
               return false;
            }

            if (isKeyBlockedByItem(player.getHeldItemOffhand(), player, key)) {
               return false;
            }
         }
      }

      int keys = player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
      return unpackCheckKey(keys, key);
   }

   public static boolean isKeyBlockedByItem(ItemStack itemstack, EntityPlayer player, KeyBinding key) {
      return itemstack.getItem() instanceof ItemFood || itemstack.getItem() instanceof ItemBlock;
   }

   public static boolean isPressedDoubleJump(EntityPlayer player) {
      int keys = player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
      return (keys & 2048) != 0;
   }

   public static boolean isScopeActived(EntityPlayer player) {
      int keys = player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
      return (keys & 4096) != 0;
   }

   @Override
   public boolean isActive() {
      return true;
   }

   @Override
   public boolean conflicts(IKeyConflictContext other) {
      return false;
   }
}
