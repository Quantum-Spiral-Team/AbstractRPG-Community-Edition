package com.vivern.arpg.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindFixed extends KeyBinding {
   int keyIndex;

   public KeyBindFixed(String description, int keyCode, String category) {
      super(description, keyCode == Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode() ? 0 : keyCode, category);
      this.keyIndex = keyCode;
   }

   public int getKeyIndex() {
      if (this.getKeyCode() == 0) {
         this.keyIndex = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode();
      }

      return this.keyIndex;
   }

   @Override
   public void setKeyCode(int keyCode) {
      this.keyIndex = keyCode;
      if (keyCode == Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode()) {
         super.setKeyCode(0);
      } else {
         super.setKeyCode(keyCode);
      }
   }

   @Override
   public String getDisplayName() {
      return this.getKeyCode() == 0
         ? this.getKeyModifier().getLocalizedComboName(this.keyIndex)
         : this.getKeyModifier().getLocalizedComboName(this.getKeyCode());
   }
}
