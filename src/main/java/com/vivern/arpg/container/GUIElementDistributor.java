package com.vivern.arpg.container;

import com.vivern.arpg.main.ItemsElements;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.packet.PacketTileClickToServer;
import com.vivern.arpg.tileentity.TileElementDistributor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

public class GUIElementDistributor extends GuiContainer {
   public static final ResourceLocation GUI_TEXTURES = new ResourceLocation("arpg:textures/gui_creative_element_distributor.png");
   private final InventoryPlayer playerInventory;
   private final IInventory tileEntity;
   private GuiTextField[] textFieldElements = new GuiTextField[12];
   private GuiTextField textFieldPurity;
   public Item displayForItem;
   public int displayForMetadata;

   public GUIElementDistributor(InventoryPlayer playerInv, IInventory furnaceInv) {
      super(new ContainerElementDistributor(playerInv, furnaceInv));
      this.playerInventory = playerInv;
      this.tileEntity = furnaceInv;
   }

   @Override
   public void updateScreen() {
      for (GuiTextField textFieldElement : this.textFieldElements) {
          textFieldElement.updateCursorCounter();
      }

      this.textFieldPurity.updateCursorCounter();
      if (this.tileEntity instanceof TileElementDistributor) {
         TileElementDistributor elementDistributor = (TileElementDistributor)this.tileEntity;
         ItemStack stack = elementDistributor.getStackInSlot(0);
         if (!stack.isEmpty() && (stack.getItem() != this.displayForItem || stack.getMetadata() != this.displayForMetadata)) {
            this.displayForItem = stack.getItem();
            this.displayForMetadata = stack.getMetadata();
            ItemsElements.ItemElementsFileEntry elementsFileEntry = findExistedElementsInFiles(this.displayForItem, this.displayForMetadata);
            if (elementsFileEntry != null) {
               this.textFieldPurity.setText(elementsFileEntry.elements.purity + "");

               for (int i = 0; i < this.textFieldElements.length; i++) {
                  float elemAmount = elementsFileEntry.elements.elementsAmount[i];
                  if (elemAmount == 0.0F) {
                     this.textFieldElements[i].setText("");
                  } else {
                     this.textFieldElements[i].setText(elemAmount + "");
                  }
               }

               this.sendPacket(0, 0, elementsFileEntry.useMeta ? 6 : 7, elementDistributor.getPos());
            }
         }
      }
   }

   @Nullable
   public static ItemsElements.ItemElementsFileEntry findExistedElementsInFiles(Item item, int meta) {
      List<File> allFiles = ItemsElements.getAllElementsFiles();
      if (!allFiles.isEmpty()) {
         for (File file : allFiles) {
            try {
               FileReader fr = new FileReader(file);
               BufferedReader reader = new BufferedReader(fr);
               int lineNumber = 0;

               for (String line = reader.readLine(); line != null; lineNumber++) {
                  ItemsElements.ItemElementsFileEntry elementsFileEntry = ItemsElements.lineToElementsFileEntry(line, lineNumber);
                  if (elementsFileEntry != null && elementsFileEntry.item == item && (!elementsFileEntry.useMeta || elementsFileEntry.meta == meta)) {
                     elementsFileEntry.file = file;
                     return elementsFileEntry;
                  }

                  line = reader.readLine();
               }
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      }

      return null;
   }

   public static void addLineToFile(String string, String filename) {
      String basePath = ((File)FMLInjectionData.data()[6]).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "/config/arpg_item_elements/");
      basePath = basePath + filename + ".txt";
      File file = new File(basePath);

      try {
         if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      try {
         FileWriter writer = new FileWriter(file, true);
         writer.write(string);
         writer.flush();
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public static void replaceLineInFile(String string, int line, File file) {
      if (file != null && file.exists()) {
         try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String linereaded = reader.readLine();

            List<String> list;
            for (list = new ArrayList<>(); linereaded != null; linereaded = reader.readLine()) {
               list.add(linereaded);
            }

            FileWriter writerclear = new FileWriter(file, false);
            writerclear.write("");
            writerclear.flush();
            FileWriter writer = new FileWriter(file, true);
            int lineNum = 0;

            for (String lineSaved : list) {
               if (lineNum == line) {
                  writer.write(string);
               } else {
                  writer.write(lineSaved + '\n');
               }

               lineNum++;
            }

            writer.flush();
         } catch (FileNotFoundException var12) {
            var12.printStackTrace();
         } catch (IOException var13) {
            var13.printStackTrace();
         }
      }
   }

   @Override
   public void initGui() {
      super.initGui();

      for (int i = 0; i < this.textFieldElements.length; i++) {
         if (i <= 5) {
            this.textFieldElements[i] = new GuiTextField(i, this.fontRenderer, 33 + 18 * i + this.getGuiLeft(), 27 + this.getGuiTop(), 22, 16);
         } else {
            this.textFieldElements[i] = new GuiTextField(i, this.fontRenderer, 33 + 18 * (i - 6) + this.getGuiLeft(), 61 + this.getGuiTop(), 22, 16);
         }

         this.textFieldElements[i].setMaxStringLength(16);
      }

      this.textFieldPurity = new GuiTextField(12, this.fontRenderer, 7 + this.getGuiLeft(), 61 + this.getGuiTop(), 22, 16);
      this.textFieldPurity.setMaxStringLength(16);
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      this.textFieldPurity.textboxKeyTyped(typedChar, keyCode);

      for (GuiTextField textFieldElement : this.textFieldElements) {
          textFieldElement.textboxKeyTyped(typedChar, keyCode);
      }

      super.keyTyped(typedChar, keyCode);
   }

   public float @Nullable [] getTextFieldData() {
      try {
         float[] floats = new float[13];

         for (int i = 0; i < 12; i++) {
            String text = this.textFieldElements[i].getText();
            text = text.replace(" ", "");
            if (text.isEmpty()) {
               floats[i] = 0.0F;
            } else {
               text = text.replace(',', '.');
               float amount = Float.parseFloat(text);
               floats[i] = amount;
            }
         }

         String text2 = this.textFieldPurity.getText();
         text2 = text2.replace(" ", "");
         if (text2.isEmpty()) {
            floats[12] = 0.0F;
         } else {
            text2 = text2.replace(',', '.');
            floats[12] = Float.parseFloat(text2);
         }

         return floats;
      } catch (NumberFormatException var5) {
         return null;
      }
   }

   public static void writeElementsOfItemToFile(Item item, int meta, float purity, boolean useMetadata, String filename, boolean canRewrite, float[] elements) {
      String name = item.getRegistryName().toString();
      StringBuilder toAdd = new StringBuilder(name + " " + meta + " " + purity + " " + useMetadata);

      for (int i = 0; i < 12; i++) {
         toAdd.append(" ").append(elements[i]);
      }

      ItemsElements.ItemElementsFileEntry elementsFileEntry = findExistedElementsInFiles(item, meta);
      if (elementsFileEntry == null) {
         addLineToFile(toAdd.toString() + '\n', filename);
      } else if (canRewrite) {
         System.out.println("replaced line: " + elementsFileEntry.fileLine);
         replaceLineInFile(toAdd.toString() + '\n', elementsFileEntry.fileLine, elementsFileEntry.file);
      }
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      int absX = mouseX - this.getGuiLeft();
      int absY = mouseY - this.getGuiTop();
      if (absX > 151 && absY > 63 && absX < 169 && absY < 75 && this.tileEntity instanceof TileElementDistributor) {
         TileElementDistributor elementDistributor = (TileElementDistributor)this.tileEntity;
         ItemStack itemStack = elementDistributor.getStackInSlot(0);
         if (!itemStack.isEmpty()) {
            float[] textData = this.getTextFieldData();
            if (textData != null) {
               writeElementsOfItemToFile(
                  itemStack.getItem(), itemStack.getMetadata(), textData[12], elementDistributor.optionUseMetadata, "arpg", true, textData
               );
               this.sendPacket(mouseX, mouseY, 3, elementDistributor.getPos());

               for (GuiTextField textFieldElement : this.textFieldElements) {
                   textFieldElement.setText("");
               }
            } else {
               System.out.println("Text fields data uncorrect!");
            }
         } else {
            System.out.println("Stack is empty!");
         }
      }

      if (absX > 151 && absY > 5 && absX < 169 && absY < 29 && this.tileEntity instanceof TileElementDistributor) {
         TileElementDistributor elementDistributor = (TileElementDistributor)this.tileEntity;
         this.sendPacket(mouseX, mouseY, 4, elementDistributor.getPos());
      }

      if (absX > 7 && absY > 4 && absX < 25 && absY < 26 && this.tileEntity instanceof TileElementDistributor) {
         TileElementDistributor elementDistributor = (TileElementDistributor)this.tileEntity;
         this.sendPacket(mouseX, mouseY, 5, elementDistributor.getPos());
      }

      this.textFieldPurity.mouseClicked(mouseX, mouseY, mouseButton);

      for (GuiTextField textFieldElement : this.textFieldElements) {
          textFieldElement.mouseClicked(mouseX, mouseY, mouseButton);
      }

      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   public void sendPacket(int mouseX, int mouseY, int mouseButton, BlockPos pos) {
      PacketTileClickToServer packet = new PacketTileClickToServer();
      packet.writeInts(pos.getX(), pos.getY(), pos.getZ(), mouseX, mouseY, mouseButton);
      PacketHandler.NETWORK.sendToServer(packet);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.renderHoveredToolTip(mouseX, mouseY);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(GUI_TEXTURES);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
      int useMeta = this.tileEntity.getField(0);
      if (useMeta <= 0) {
         this.drawTexturedModalRect(i + 151, j + 17, 176, 0, 18, 12);
      }

      this.textFieldPurity.drawTextBox();

      for (GuiTextField textFieldElement : this.textFieldElements) {
          textFieldElement.drawTextBox();
      }

      if (TileElementDistributor.nextDisplayed != null && !TileElementDistributor.nextDisplayed.isEmpty()) {
         this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, TileElementDistributor.nextDisplayed, i + 8, j + 9);
      }
   }
}
