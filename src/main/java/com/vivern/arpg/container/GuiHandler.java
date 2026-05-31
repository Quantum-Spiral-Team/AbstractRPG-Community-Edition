package com.vivern.arpg.container;

import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.tileentity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int NETHER_MELTER_GUI_ID = 0;
    public static final int INFERNUM_FURNACE_GUI_ID = 1;
    public static final int ALCHEMIC_LAB_GUI_ID = 2;
    public static final int ALCHEMIC_VAPORIZER_GUI_ID = 3;
    public static final int PUZZLE_GUI_ID = 4;
    public static final int SEALOCK_GUI_ID = 5;
    public static final int TRADER_GUI_ID = 6;
    public static final int CRYSTALLIZER_GUI_ID = 7;
    public static final int PYROCRYSTALLINE_CONV_GUI_ID = 8;
    public static final int ASSEMBLY_TABLE_GUI_ID = 9;
    public static final int ELECTROMAGNET_GUI_ID = 10;
    public static final int SUMMON_GUI_ID = 11;
    public static final int ITEM_CHARGER_GUI_ID = 12;
    public static final int INDUSTRIAL_MIXER_GUI_ID = 13;
    public static final int RESEARCH_TABLE_GUI_ID = 14;
    public static final int CREATIVE_ELEMENT_DISTRIBUTOR_GUI_ID = 15;
    public static final int DISENCHANTMENT_TABLE_GUI_ID = 16;
    public static final int MECHANIC_GUI_ID = 17;
    public static final int BANK_GUI_ID = 18;
    public static final int BOOK_OF_ELEMENTS_GUI_ID = 19;
    public static final int DEBUG_COLOR_GUI_ID = 20;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case NETHER_MELTER_GUI_ID:
                return new ContainerNetherMelter(player.inventory, (TileNetherMelter) world.getTileEntity(new BlockPos(x, y, z)));
            case INFERNUM_FURNACE_GUI_ID:
                return new ContainerInfernumFurnace(player.inventory, (TileInfernumFurnace) world.getTileEntity(new BlockPos(x, y, z)));
            case ALCHEMIC_LAB_GUI_ID:
                return new ContainerAlchemicLab(player.inventory, (TileAlchemicLab) world.getTileEntity(new BlockPos(x, y, z)));
            case ALCHEMIC_VAPORIZER_GUI_ID:
                return new ContainerAlchemicVaporizer(player.inventory, (TileAlchemicVaporizer) world.getTileEntity(new BlockPos(x, y, z)));
            case TRADER_GUI_ID:
                return new ContainerTrader(player.inventory);
            case CRYSTALLIZER_GUI_ID:
                return new ContainerCrystallizer(player.inventory, (TileCrystallizer) world.getTileEntity(new BlockPos(x, y, z)));
            case PYROCRYSTALLINE_CONV_GUI_ID:
                return new ContainerPyrocrystalline(player.inventory, (TilePyrocrystallineConverter) world.getTileEntity(new BlockPos(x, y, z)));
            case ASSEMBLY_TABLE_GUI_ID:
                return new ContainerAssemblyTable(player.inventory, (TileAssemblyTable) world.getTileEntity(new BlockPos(x, y, z)));
            case ITEM_CHARGER_GUI_ID:
                return new ContainerCharger(player.inventory, (TileItemCharger) world.getTileEntity(new BlockPos(x, y, z)));
            case INDUSTRIAL_MIXER_GUI_ID:
                return new ContainerIndustrialMixer(player.inventory, (TileIndustrialMixer) world.getTileEntity(new BlockPos(x, y, z)));
            case RESEARCH_TABLE_GUI_ID:
                return new ContainerResearchTable(player.inventory, (TileResearchTable) world.getTileEntity(new BlockPos(x, y, z)));
            case CREATIVE_ELEMENT_DISTRIBUTOR_GUI_ID:
                return new ContainerElementDistributor(player.inventory, (TileElementDistributor) world.getTileEntity(new BlockPos(x, y, z)));
            case DISENCHANTMENT_TABLE_GUI_ID:
                return new Container1(player.inventory, (TileDisenchantmentTable) world.getTileEntity(new BlockPos(x, y, z)));
            case MECHANIC_GUI_ID:
                Entity e = world.getEntityByID(x);
                if (e instanceof NPCMobsPack.NpcMechanic) {
                    return new ContainerMechanic(player.inventory, (NPCMobsPack.NpcMechanic) e);
                }
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(x);

        switch (ID) {
            case NETHER_MELTER_GUI_ID:
                return new GUINetherMelter(player.inventory, (TileNetherMelter) world.getTileEntity(new BlockPos(x, y, z)));
            case INFERNUM_FURNACE_GUI_ID:
                return new GUIInfernumFurnace(player.inventory, (TileInfernumFurnace) world.getTileEntity(new BlockPos(x, y, z)));
            case ALCHEMIC_LAB_GUI_ID:
                return new GUIAlchemicLab(player.inventory, (TileAlchemicLab) world.getTileEntity(new BlockPos(x, y, z)));
            case ALCHEMIC_VAPORIZER_GUI_ID:
                return new GUIAlchemicVaporizer(player.inventory, (TileAlchemicVaporizer) world.getTileEntity(new BlockPos(x, y, z)));
            case PUZZLE_GUI_ID:
                return new GUIFrozenPuzzle((TilePuzzle) world.getTileEntity(new BlockPos(x, y, z)));
            case TRADER_GUI_ID:
                if (entity instanceof NPCMobsPack.NpcTrader) {
                    return new GUITrader((NPCMobsPack.NpcTrader) entity, player);
                }
            case CRYSTALLIZER_GUI_ID:
                return new GUICrystallizer(player.inventory, (TileCrystallizer) world.getTileEntity(new BlockPos(x, y, z)));
            case PYROCRYSTALLINE_CONV_GUI_ID:
                return new GUIPyrocrystallineConverter(player.inventory, (TilePyrocrystallineConverter) world.getTileEntity(new BlockPos(x, y, z)));
            case ASSEMBLY_TABLE_GUI_ID:
                return new GUIAssemblyTable(player.inventory, (TileAssemblyTable) world.getTileEntity(new BlockPos(x, y, z)));
            case ITEM_CHARGER_GUI_ID:
                return new GUICharger(player.inventory, (TileItemCharger) world.getTileEntity(new BlockPos(x, y, z)));
            case INDUSTRIAL_MIXER_GUI_ID:
                return new GUIIndustrialMixer(player.inventory, (TileIndustrialMixer) world.getTileEntity(new BlockPos(x, y, z)));
            case RESEARCH_TABLE_GUI_ID:
                return new GUIResearchTable(player.inventory, (TileResearchTable) world.getTileEntity(new BlockPos(x, y, z)));
            case CREATIVE_ELEMENT_DISTRIBUTOR_GUI_ID:
                return new GUIElementDistributor(player.inventory, (TileElementDistributor) world.getTileEntity(new BlockPos(x, y, z)));
            case DISENCHANTMENT_TABLE_GUI_ID:
                return new GUIDisenchantmentTable(player.inventory, (TileDisenchantmentTable) world.getTileEntity(new BlockPos(x, y, z)));
            case MECHANIC_GUI_ID:
                if (entity instanceof NPCMobsPack.NpcMechanic) {
                    return new GUIMechanic((NPCMobsPack.NpcMechanic) entity, player);
                }
            case BANK_GUI_ID:
                return new GUIBank((TileBank) world.getTileEntity(new BlockPos(x, y, z)), player);
            case BOOK_OF_ELEMENTS_GUI_ID:
                return new GUIBookOfElements(((TileBookcase) world.getTileEntity(new BlockPos(x, y, z))).stacks);
            case DEBUG_COLOR_GUI_ID:
                return new GUIDebugColorBlock(new BlockPos(x, y, z));
        }

        return null;
    }

    public static void displayGui(EntityPlayer player, @Nullable GuiScreen gui) {
        if (player instanceof EntityPlayerSP) {
            Minecraft.getMinecraft().displayGuiScreen(gui);
        }
    }

}
