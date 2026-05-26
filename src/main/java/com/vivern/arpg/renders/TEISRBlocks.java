package com.vivern.arpg.renders;

import com.vivern.arpg.items.ItemARPGChest;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.tileentity.TileARPGChest;
import com.vivern.arpg.tileentity.TileAssemblyAugment;
import com.vivern.arpg.tileentity.TileAssemblyTable;
import com.vivern.arpg.tileentity.TileBioCell;
import com.vivern.arpg.tileentity.TileCrystalSphere;
import com.vivern.arpg.tileentity.TileElectricSieve;
import com.vivern.arpg.tileentity.TileEtheriteInvocator;
import com.vivern.arpg.tileentity.TileGlossary;
import com.vivern.arpg.tileentity.TileIndustrialMixer;
import com.vivern.arpg.tileentity.TileItemCharger;
import com.vivern.arpg.tileentity.TileMagicGenerator;
import com.vivern.arpg.tileentity.TileManaBottle;
import com.vivern.arpg.tileentity.TileManaPump;
import com.vivern.arpg.tileentity.TileNexusBeacon;
import com.vivern.arpg.tileentity.TileNexusNiveolite;
import com.vivern.arpg.tileentity.TilePresentBox;
import com.vivern.arpg.tileentity.TileRunicMirror;
import com.vivern.arpg.tileentity.TileShimmeringBeastbloom;
import com.vivern.arpg.tileentity.TileSieve;
import com.vivern.arpg.tileentity.TileSoulCatcher;
import com.vivern.arpg.tileentity.TileTeamBanner;
import com.vivern.arpg.tileentity.TileTritonHearth;
import com.vivern.arpg.tileentity.TileVoidCrystal;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEISRBlocks extends TileEntityItemStackRenderer {
   public static TileEntityItemStackRenderer instance = new TEISRBlocks();

   @Override
   public void renderByItem(ItemStack itemStackIn) {
      this.renderByItem(itemStackIn, 1.0F);
   }

   @Override
   public void renderByItem(ItemStack itemstack, float partialTicks) {
      Item item = itemstack.getItem();
      if (item instanceof ItemBlock) {
         Block block = ((ItemBlock)item).getBlock();
         GlStateManager.pushMatrix();
         if (block == BlocksRegister.ASSEMBLY_TABLE) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileAssemblyTable.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TURNING_AUGMENT
            || block == BlocksRegister.PRESS_AUGMENT
            || block == BlocksRegister.WELD_AUGMENT
            || block == BlocksRegister.PLASMA_SPRAY_AUGMENT
            || block == BlocksRegister.MOLECULAR_PRINTER_AUGMENT) {
            int id = 0;
            if (block == BlocksRegister.TURNING_AUGMENT) {
               id = 1;
            }

            if (block == BlocksRegister.PRESS_AUGMENT) {
               id = 2;
            }

            if (block == BlocksRegister.WELD_AUGMENT) {
               id = 3;
            }

            if (block == BlocksRegister.PLASMA_SPRAY_AUGMENT) {
               id = 4;
            }

            if (block == BlocksRegister.MOLECULAR_PRINTER_AUGMENT) {
               id = 5;
            }

            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileAssemblyAugment.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, id, 1.0F);
         }

         if (block == BlocksRegister.SHIMMERING_BEASTBLOOM) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileShimmeringBeastbloom.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.BIO_CELL) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileBioCell.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.GLOSSARY) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileGlossary.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TIDE_BEACON) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileNexusBeacon.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TRITON_HEARTH) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileTritonHearth.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TRITON_HEARTH) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileTritonHearth.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.VOID_CRYSTAL_BLOCK) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileVoidCrystal.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.RUNIC_MIRROR) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileRunicMirror.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.INDUSTRIAL_MIXER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileIndustrialMixer.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.CRYSTAL_SPHERE) {
            NBTTagCompound blockEntityTag = NBTHelper.GetNBTtag(itemstack, "BlockEntityTag");
            float stored = blockEntityTag != null && blockEntityTag.hasKey("stored") ? blockEntityTag.getFloat("stored") : 0.0F;
            ShardType type = blockEntityTag != null && blockEntityTag.hasKey("type")
               ? ShardType.byName(blockEntityTag.getString("type"))
               : ShardType.FIRE;
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileCrystalSphere.class);
            TESRrenderer.render(null, stored, type.id, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.MANA_BOTTLE) {
            NBTTagCompound blockEntityTag = NBTHelper.GetNBTtag(itemstack, "BlockEntityTag");
            float max = blockEntityTag != null && blockEntityTag.hasKey("max") ? blockEntityTag.getFloat("max") : 0.0F;
            float stored = blockEntityTag != null && blockEntityTag.hasKey("manaStored") ? blockEntityTag.getFloat("manaStored") : 0.0F;
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileManaBottle.class);
            TESRrenderer.render(null, max, stored, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.SOUL_CATCHER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileSoulCatcher.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.MANA_PUMP) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileManaPump.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.SIEVE) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance.renderers.get(TileSieve.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.ELECTRIC_SIEVE) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileElectricSieve.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.BLOCK_NIVEOLITE_GAME) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileNexusNiveolite.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.PRESENT_BOX) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TilePresentBox.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.ITEM_CHARGER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileItemCharger.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (item instanceof ItemARPGChest) {
            ItemARPGChest chest = (ItemARPGChest)item;
            ARPGChestTESR.reservedChestType = chest.chestType;
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileARPGChest.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.MAGIC_GENERATOR) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileMagicGenerator.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.BLOCK_ETHERITE_INVOCATOR) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileEtheriteInvocator.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TEAM_BANNER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileTeamBanner.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         GlStateManager.popMatrix();
      }
   }
}
