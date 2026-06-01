package com.vivern.arpg.renders;

import com.vivern.arpg.items.ItemARPGChest;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class TEISRBlocks extends TileEntityItemStackRenderer {

    public static TileEntityItemStackRenderer instance = new TEISRBlocks();

    @Override
    public void renderByItem(@NotNull ItemStack itemStackIn) {
        this.renderByItem(itemStackIn, 1.0F);
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemstack, float partialTicks) {
        Item item = itemstack.getItem();

        if (item instanceof ItemBlock) {
            if (item instanceof ItemARPGChest) {
                ItemARPGChest chest = (ItemARPGChest) item;
                ARPGChestTESR.reservedChestType = chest.chestType;
                renderTESR(TileARPGChest.class, 0.0, 0.0, partialTicks, -1);
                return;
            }

            Block block = ((ItemBlock) item).getBlock();
            GlStateManager.pushMatrix();

            if (block == BlocksRegister.CRYSTAL_SPHERE) {
                NBTTagCompound tag = NBTHelper.GetNBTtag(itemstack, "BlockEntityTag");
                float stored = (tag != null && tag.hasKey("stored")) ? tag.getFloat("stored") : 0.0F;
                ShardType type = (tag != null && tag.hasKey("type")) ? ShardType.byName(tag.getString("type")) : ShardType.FIRE;
                renderTESR(TileCrystalSphere.class, stored, type.id, partialTicks, -1);
            } else if (block == BlocksRegister.MANA_BOTTLE) {
                NBTTagCompound tag = NBTHelper.GetNBTtag(itemstack, "BlockEntityTag");
                float max = (tag != null && tag.hasKey("max")) ? tag.getFloat("max") : 0.0F;
                float stored = (tag != null && tag.hasKey("manaStored")) ? tag.getFloat("manaStored") : 0.0F;
                renderTESR(TileManaBottle.class, max, stored, partialTicks, -1);
            }

            else if (block == BlocksRegister.TURNING_AUGMENT) renderTESR(TileAssemblyAugment.class, 0.0, 0.0, partialTicks, 1);
            else if (block == BlocksRegister.PRESS_AUGMENT) renderTESR(TileAssemblyAugment.class, 0.0, 0.0, partialTicks, 2);
            else if (block == BlocksRegister.WELD_AUGMENT) renderTESR(TileAssemblyAugment.class, 0.0, 0.0, partialTicks, 3);
            else if (block == BlocksRegister.PLASMA_SPRAY_AUGMENT) renderTESR(TileAssemblyAugment.class, 0.0, 0.0, partialTicks, 4);
            else if (block == BlocksRegister.MOLECULAR_PRINTER_AUGMENT) renderTESR(TileAssemblyAugment.class, 0.0, 0.0, partialTicks, 5);

            else if (block == BlocksRegister.ASSEMBLY_TABLE) renderTESR(TileAssemblyTable.class, partialTicks);
            else if (block == BlocksRegister.SHIMMERING_BEASTBLOOM) renderTESR(TileShimmeringBeastbloom.class, partialTicks);
            else if (block == BlocksRegister.BIO_CELL) renderTESR(TileBioCell.class, partialTicks);
            else if (block == BlocksRegister.GLOSSARY) renderTESR(TileGlossary.class, partialTicks);
            else if (block == BlocksRegister.TIDE_BEACON) renderTESR(TileNexusBeacon.class, partialTicks);
            else if (block == BlocksRegister.TRITON_HEARTH) renderTESR(TileTritonHearth.class, partialTicks); // Дубликат удален
            else if (block == BlocksRegister.VOID_CRYSTAL_BLOCK) renderTESR(TileVoidCrystal.class, partialTicks);
            else if (block == BlocksRegister.RUNIC_MIRROR) renderTESR(TileRunicMirror.class, partialTicks);
            else if (block == BlocksRegister.INDUSTRIAL_MIXER) renderTESR(TileIndustrialMixer.class, partialTicks);
            else if (block == BlocksRegister.SOUL_CATCHER) renderTESR(TileSoulCatcher.class, partialTicks);
            else if (block == BlocksRegister.MANA_PUMP) renderTESR(TileManaPump.class, partialTicks);
            else if (block == BlocksRegister.SIEVE) renderTESR(TileSieve.class, partialTicks);
            else if (block == BlocksRegister.ELECTRIC_SIEVE) renderTESR(TileElectricSieve.class, partialTicks);
            else if (block == BlocksRegister.BLOCK_NIVEOLITE_GAME) renderTESR(TileNexusNiveolite.class, partialTicks);
            else if (block == BlocksRegister.PRESENT_BOX) renderTESR(TilePresentBox.class, partialTicks);
            else if (block == BlocksRegister.ITEM_CHARGER) renderTESR(TileItemCharger.class, partialTicks);
            else if (block == BlocksRegister.MAGIC_GENERATOR) renderTESR(TileMagicGenerator.class, partialTicks);
            else if (block == BlocksRegister.BLOCK_ETHERITE_INVOCATOR) renderTESR(TileEtheriteInvocator.class, partialTicks);
            else if (block == BlocksRegister.TEAM_BANNER) renderTESR(TileTeamBanner.class, partialTicks);

            GlStateManager.popMatrix();
        }
    }

    private void renderTESR(Class<? extends net.minecraft.tileentity.TileEntity> teClass, float partialTicks) {
        this.renderTESR(teClass, 0.0, 0.0, partialTicks, -1);
    }

    private void renderTESR(Class<? extends net.minecraft.tileentity.TileEntity> teClass, double xOffset, double yOffset, float partialTicks, int destroyStage) {
        TileEntitySpecialRenderer<?> renderer = TileEntityRendererDispatcher.instance.renderers.get(teClass);
        if (renderer != null) {
            renderer.render(null, xOffset, yOffset, 0.0, partialTicks, destroyStage, 1.0F);
        }
    }

}
