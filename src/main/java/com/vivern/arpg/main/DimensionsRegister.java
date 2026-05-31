package com.vivern.arpg.main;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.dimensions.dungeon.DimensionDungeon;
import com.vivern.arpg.dimensions.ethernalfrost.DimensionEthernalFrost;
import com.vivern.arpg.dimensions.generationutils.BlockAtPos;
import com.vivern.arpg.dimensions.mortuorus.DimensionMortuorus;
import com.vivern.arpg.dimensions.stormledge.DimensionStormledge;
import com.vivern.arpg.dimensions.toxicomania.ARPGTeleporter;
import com.vivern.arpg.dimensions.toxicomania.DimensionToxicomania;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//TODO упростить логику, добавить конфигурируемый id измерений
public class DimensionsRegister {

    public static final int ETHERNAL_FROST_ID = 100; //TODO config
    public static final int TOXICOMANIA_ID = 101; //TODO config
    public static final int DUNGEON_ID = 102; //TODO config
    public static final int AQUATICA_ID = 103; //TODO config
    public static final int STORMLEDGE_ID = 104; //TODO config
    public static final int MORTUORUS_ID = 106; //TODO config
    public static final DimensionType ETHERNAL_FROST = DimensionType.register("Ethernal_Frost", "_ethernal_frost", ETHERNAL_FROST_ID, DimensionEthernalFrost.class, false);
    public static final DimensionType DUNGEON = DimensionType.register("Dungeon", "_dungeon", DUNGEON_ID, DimensionDungeon.class, false);
    public static final DimensionType TOXICOMANIA = DimensionType.register("Toxicomania", "_toxicomania", TOXICOMANIA_ID, DimensionToxicomania.class, false);
    public static final DimensionType AQUATICA = DimensionType.register("Aquatica", "_aquatica", AQUATICA_ID, DimensionAquatica.class, false);
    public static final DimensionType STORMLEDGE = DimensionType.register("Stormledge", "_stormledge", STORMLEDGE_ID, DimensionStormledge.class, false);
    public static final DimensionType MORTUORUS = DimensionType.register("Mortuorus", "_mortuorus", MORTUORUS_ID, DimensionMortuorus.class, false);
    public static boolean canPortalsBreak = true;
    public static ARPGTeleporter teleporterTOXICOMANIA;
    public static ARPGTeleporter teleporterEVERFROST;
    public static ARPGTeleporter teleporterDUNGEON;
    public static ARPGTeleporter teleporterAQUATICA;
    public static ARPGTeleporter teleporterSTORMLEDGE;

    public static void registerDimensions() {
        DimensionManager.registerDimension(ETHERNAL_FROST_ID, ETHERNAL_FROST);
        DimensionManager.registerDimension(DUNGEON_ID, DUNGEON);
        DimensionManager.registerDimension(TOXICOMANIA_ID, TOXICOMANIA);
        DimensionManager.registerDimension(AQUATICA_ID, AQUATICA);
        DimensionManager.registerDimension(STORMLEDGE_ID, STORMLEDGE);
        DimensionManager.registerDimension(MORTUORUS_ID, MORTUORUS);
    }

    public static boolean isAbstractRPGDimension(int dimension) {
        return dimension == ETHERNAL_FROST_ID || dimension == DUNGEON_ID || dimension == TOXICOMANIA_ID || dimension == AQUATICA_ID || dimension == STORMLEDGE_ID || dimension == MORTUORUS_ID;
    }

    public static void registerTeleporters() {
        List<BlockPos> membraneConfiguration = new ArrayList<>();
        List<BlockAtPos> frameConfiguration = new ArrayList<>();
        List<BlockPos> groundCheck = new ArrayList<>();
        List<BlockPos> spawnPoints = new ArrayList<>();
        IBlockState framestate = Blocks.SNOW.getDefaultState();
        membraneConfiguration.add(new BlockPos(0, 0, 0));
        membraneConfiguration.add(new BlockPos(1, 0, 0));
        membraneConfiguration.add(new BlockPos(-1, 0, 0));
        membraneConfiguration.add(new BlockPos(0, 1, 0));
        membraneConfiguration.add(new BlockPos(1, 1, 0));
        membraneConfiguration.add(new BlockPos(-1, 1, 0));
        membraneConfiguration.add(new BlockPos(0, -1, 0));
        membraneConfiguration.add(new BlockPos(1, -1, 0));
        membraneConfiguration.add(new BlockPos(-1, -1, 0));
        frameConfiguration.add(new BlockAtPos(-2, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, 2, 0, framestate));
        groundCheck.add(new BlockPos(0, -3, 0));
        groundCheck.add(new BlockPos(-1, -3, 0));
        groundCheck.add(new BlockPos(1, -3, 0));
        groundCheck.add(new BlockPos(-2, -3, 0));
        groundCheck.add(new BlockPos(2, -3, 0));
        add8Poses(spawnPoints, new BlockPos(0, 1, 0));
        add8Poses(spawnPoints, new BlockPos(0, 0, 0));
        add8Poses(spawnPoints, new BlockPos(0, -1, 0));
        add8Poses(spawnPoints, new BlockPos(0, -2, 0));
        teleporterEVERFROST = new ARPGTeleporter(membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, ETHERNAL_FROST_ID, BlocksRegister.ETHERNAL_FROST_PORTAL.getDefaultState(), 5, false);
        membraneConfiguration = new ArrayList<>();
        frameConfiguration = new ArrayList<>();
        groundCheck = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        framestate = BlocksRegister.TOXIC_PORTAL_FRAME.getDefaultState();
        membraneConfiguration.add(new BlockPos(0, 0, 0));
        membraneConfiguration.add(new BlockPos(1, 0, 0));
        membraneConfiguration.add(new BlockPos(-1, 0, 0));
        membraneConfiguration.add(new BlockPos(0, 1, 0));
        membraneConfiguration.add(new BlockPos(1, 1, 0));
        membraneConfiguration.add(new BlockPos(-1, 1, 0));
        frameConfiguration.add(new BlockAtPos(-2, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, -1, 1, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -1, 1, framestate));
        frameConfiguration.add(new BlockAtPos(1, -1, 1, framestate));
        frameConfiguration.add(new BlockAtPos(-2, -1, 1, framestate));
        frameConfiguration.add(new BlockAtPos(2, -1, 1, framestate));
        frameConfiguration.add(new BlockAtPos(0, -1, -1, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -1, -1, framestate));
        frameConfiguration.add(new BlockAtPos(1, -1, -1, framestate));
        frameConfiguration.add(new BlockAtPos(-2, -1, -1, framestate));
        frameConfiguration.add(new BlockAtPos(2, -1, -1, framestate));
        groundCheck.add(new BlockPos(0, -2, 0));
        groundCheck.add(new BlockPos(-1, -2, 0));
        groundCheck.add(new BlockPos(1, -2, 0));
        groundCheck.add(new BlockPos(-2, -2, 0));
        groundCheck.add(new BlockPos(2, -2, 0));
        groundCheck.add(new BlockPos(0, -2, 1));
        groundCheck.add(new BlockPos(-1, -2, 1));
        groundCheck.add(new BlockPos(1, -2, 1));
        groundCheck.add(new BlockPos(-2, -2, 1));
        groundCheck.add(new BlockPos(2, -2, 1));
        groundCheck.add(new BlockPos(0, -2, -1));
        groundCheck.add(new BlockPos(-1, -2, -1));
        groundCheck.add(new BlockPos(1, -2, -1));
        groundCheck.add(new BlockPos(-2, -2, -1));
        groundCheck.add(new BlockPos(2, -2, -1));
        add8Poses(spawnPoints, new BlockPos(0, 0, 0));
        add8Poses(spawnPoints, new BlockPos(0, 1, 0));
        teleporterTOXICOMANIA = new ARPGTeleporter(membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, TOXICOMANIA_ID, BlocksRegister.TOXICOMANIA_PORTAL.getDefaultState(), 4, true);
        teleporterTOXICOMANIA.prototypePortalBlock = Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.GREEN);
        membraneConfiguration = new ArrayList<>();
        frameConfiguration = new ArrayList<>();
        groundCheck = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        framestate = BlocksRegister.DUNGEON_PORTAL_FRAME.getDefaultState();
        membraneConfiguration.add(new BlockPos(0, 0, 0));
        membraneConfiguration.add(new BlockPos(-1, 0, 0));
        membraneConfiguration.add(new BlockPos(1, 0, 0));
        membraneConfiguration.add(new BlockPos(-2, 0, 0));
        membraneConfiguration.add(new BlockPos(2, 0, 0));
        membraneConfiguration.add(new BlockPos(0, 0, 1));
        membraneConfiguration.add(new BlockPos(-1, 0, 1));
        membraneConfiguration.add(new BlockPos(1, 0, 1));
        membraneConfiguration.add(new BlockPos(-2, 0, 1));
        membraneConfiguration.add(new BlockPos(2, 0, 1));
        membraneConfiguration.add(new BlockPos(0, 0, -1));
        membraneConfiguration.add(new BlockPos(-1, 0, -1));
        membraneConfiguration.add(new BlockPos(1, 0, -1));
        membraneConfiguration.add(new BlockPos(-2, 0, -1));
        membraneConfiguration.add(new BlockPos(2, 0, -1));
        membraneConfiguration.add(new BlockPos(0, 0, -2));
        membraneConfiguration.add(new BlockPos(-1, 0, -2));
        membraneConfiguration.add(new BlockPos(1, 0, -2));
        membraneConfiguration.add(new BlockPos(0, 0, 2));
        membraneConfiguration.add(new BlockPos(-1, 0, 2));
        membraneConfiguration.add(new BlockPos(1, 0, 2));
        frameConfiguration.add(new BlockAtPos(0, 0, -3, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 0, -3, framestate));
        frameConfiguration.add(new BlockAtPos(1, 0, -3, framestate));
        frameConfiguration.add(new BlockAtPos(0, 0, 3, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 0, 3, framestate));
        frameConfiguration.add(new BlockAtPos(1, 0, 3, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 0, -2, framestate));
        frameConfiguration.add(new BlockAtPos(2, 0, -2, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 0, 2, framestate));
        frameConfiguration.add(new BlockAtPos(2, 0, 2, framestate));
        frameConfiguration.add(new BlockAtPos(-3, 0, -1, framestate));
        frameConfiguration.add(new BlockAtPos(3, 0, -1, framestate));
        frameConfiguration.add(new BlockAtPos(-3, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(3, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-3, 0, 1, framestate));
        frameConfiguration.add(new BlockAtPos(3, 0, 1, framestate));

        for (BlockPos pos : frameConfiguration) {
            groundCheck.add(pos.down());
        }

        for (BlockPos pos : membraneConfiguration) {
            groundCheck.add(pos.down());
        }

        add8Poses(spawnPoints, new BlockPos(0, -2, 0));
        add8Poses(spawnPoints, new BlockPos(0, -3, 0));
        add8Poses(spawnPoints, new BlockPos(0, -4, 0));
        add8Poses(spawnPoints, new BlockPos(0, -5, 0));
        spawnPoints.add(new BlockPos(3, 1, 0));
        spawnPoints.add(new BlockPos(-3, 1, 0));
        spawnPoints.add(new BlockPos(0, 1, 3));
        spawnPoints.add(new BlockPos(0, 1, -3));
        spawnPoints.add(new BlockPos(2, 1, 2));
        spawnPoints.add(new BlockPos(-2, 1, 2));
        spawnPoints.add(new BlockPos(2, 1, -2));
        spawnPoints.add(new BlockPos(-2, 1, -2));
        teleporterDUNGEON = new ARPGTeleporter(membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, DUNGEON_ID, BlocksRegister.DUNGEON_PORTAL.getDefaultState(), 1, false);
        teleporterDUNGEON.canPlaceUnderground = true;
        membraneConfiguration = new ArrayList<>();
        frameConfiguration = new ArrayList<>();
        groundCheck = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        framestate = BlocksRegister.AQUATICA_PORTAL_FRAME.getDefaultState();
        membraneConfiguration.add(new BlockPos(0, 0, 0));
        membraneConfiguration.add(new BlockPos(1, 0, 0));
        membraneConfiguration.add(new BlockPos(-1, 0, 0));
        membraneConfiguration.add(new BlockPos(0, 1, 0));
        membraneConfiguration.add(new BlockPos(1, 1, 0));
        membraneConfiguration.add(new BlockPos(-1, 1, 0));
        membraneConfiguration.add(new BlockPos(0, -1, 0));
        membraneConfiguration.add(new BlockPos(1, -1, 0));
        membraneConfiguration.add(new BlockPos(-1, -1, 0));
        membraneConfiguration.add(new BlockPos(0, 2, 0));
        membraneConfiguration.add(new BlockPos(2, 0, 0));
        membraneConfiguration.add(new BlockPos(0, -2, 0));
        membraneConfiguration.add(new BlockPos(-2, 0, 0));
        frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-3, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(3, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, -3, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, 3, 0, framestate));
        groundCheck.add(new BlockPos(0, -4, 0));
        groundCheck.add(new BlockPos(-1, -4, 0));
        groundCheck.add(new BlockPos(1, -4, 0));
        groundCheck.add(new BlockPos(-2, -4, 0));
        groundCheck.add(new BlockPos(2, -4, 0));
        groundCheck.add(new BlockPos(-3, -4, 0));
        groundCheck.add(new BlockPos(3, -4, 0));
        add8Poses(spawnPoints, new BlockPos(0, 1, 0));
        add8Poses(spawnPoints, new BlockPos(0, 0, 0));
        add8Poses(spawnPoints, new BlockPos(0, -1, 0));
        add8Poses(spawnPoints, new BlockPos(0, -2, 0));
        add8Poses(spawnPoints, new BlockPos(0, -3, 0));
        add8Poses(spawnPoints, new BlockPos(0, -4, 0));
        teleporterAQUATICA = new ARPGTeleporter(membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, AQUATICA_ID, BlocksRegister.AQUATICA_PORTAL.getDefaultState(), 7, false);
        membraneConfiguration = new ArrayList<>();
        frameConfiguration = new ArrayList<>();
        groundCheck = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        framestate = BlocksRegister.STORMLEDGE_PORTAL_FRAME.getDefaultState();
        membraneConfiguration.add(new BlockPos(0, 0, 0));
        membraneConfiguration.add(new BlockPos(1, 0, 0));
        membraneConfiguration.add(new BlockPos(-1, 0, 0));
        membraneConfiguration.add(new BlockPos(0, 1, 0));
        membraneConfiguration.add(new BlockPos(1, 1, 0));
        membraneConfiguration.add(new BlockPos(-1, 1, 0));
        membraneConfiguration.add(new BlockPos(0, -1, 0));
        membraneConfiguration.add(new BlockPos(1, -1, 0));
        membraneConfiguration.add(new BlockPos(-1, -1, 0));
        membraneConfiguration.add(new BlockPos(0, 3, 0));
        membraneConfiguration.add(new BlockPos(0, -3, 0));
        membraneConfiguration.add(new BlockPos(3, 0, 0));
        membraneConfiguration.add(new BlockPos(-3, 0, 0));
        frameConfiguration.add(new BlockAtPos(-2, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-2, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(2, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, -2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, 2, 0, framestate));
        frameConfiguration.add(new BlockAtPos(3, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(4, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-3, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-4, 1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(3, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(4, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-3, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-4, -1, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-4, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(4, 0, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 3, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, 4, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, 3, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, 4, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -3, 0, framestate));
        frameConfiguration.add(new BlockAtPos(-1, -4, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, -3, 0, framestate));
        frameConfiguration.add(new BlockAtPos(1, -4, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, -4, 0, framestate));
        frameConfiguration.add(new BlockAtPos(0, 4, 0, framestate));
        groundCheck.add(new BlockPos(0, -6, 0));
        groundCheck.add(new BlockPos(-1, -6, 0));
        groundCheck.add(new BlockPos(1, -6, 0));
        groundCheck.add(new BlockPos(-2, -6, 0));
        groundCheck.add(new BlockPos(2, -6, 0));
        groundCheck.add(new BlockPos(-3, -6, 0));
        groundCheck.add(new BlockPos(3, -6, 0));
        groundCheck.add(new BlockPos(-4, -6, 0));
        groundCheck.add(new BlockPos(4, -6, 0));
        add8Poses(spawnPoints, new BlockPos(0, 3, 0));
        add8Poses(spawnPoints, new BlockPos(0, 2, 0));
        add8Poses(spawnPoints, new BlockPos(0, 1, 0));
        add8Poses(spawnPoints, new BlockPos(0, 0, 0));
        teleporterSTORMLEDGE = new ARPGTeleporter(membraneConfiguration, frameConfiguration, groundCheck, spawnPoints, STORMLEDGE_ID, BlocksRegister.STORMLEDGE_PORTAL.getDefaultState(), 9, false);
    }

    public static @Nullable ARPGTeleporter getTeleporterToDimension(int dimensionId) {
        if (dimensionId == ETHERNAL_FROST_ID) {
            return teleporterEVERFROST;
        } else if (dimensionId == TOXICOMANIA_ID) {
            return teleporterTOXICOMANIA;
        } else if (dimensionId == DUNGEON_ID) {
            return teleporterDUNGEON;
        } else {
            return dimensionId == AQUATICA_ID ? teleporterAQUATICA : null;
        }
    }

    private static void add8Poses(List<BlockPos> list, BlockPos mainPos) {
        list.add(mainPos.add(1, 0, 1));
        list.add(mainPos.add(-1, 0, 1));
        list.add(mainPos.add(1, 0, -1));
        list.add(mainPos.add(-1, 0, -1));
        list.add(mainPos.add(-1, 0, 0));
        list.add(mainPos.add(1, 0, 0));
        list.add(mainPos.add(0, 0, 1));
        list.add(mainPos.add(0, 0, -1));
    }

}
