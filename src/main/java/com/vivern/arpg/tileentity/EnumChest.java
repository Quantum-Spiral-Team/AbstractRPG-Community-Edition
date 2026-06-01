package com.vivern.arpg.tileentity;

import com.vivern.arpg.Tags;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.ARPGChestTESR;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum EnumChest {
    FROZEN("frozen", 180, () -> BlocksRegister.CHEST_FROZEN, SoundEvents.BLOCK_CHEST_OPEN, SoundEvents.BLOCK_CHEST_CLOSE),
    TOXIC("toxic", 200, () -> BlocksRegister.CHEST_TOXIC, SoundEvents.BLOCK_CHEST_OPEN, SoundEvents.BLOCK_CHEST_CLOSE),
    RUSTED("rusted", 0,   () -> BlocksRegister.CHEST_RUSTED, Sounds.METAL_CHEST_OPEN, Sounds.METAL_CHEST_CLOSE),
    CRYSTAL("crystal", 230, () -> BlocksRegister.CHEST_CRYSTAL, Sounds.STONE_CHEST_OPEN, Sounds.STONE_CHEST_CLOSE),
    ROTTEN("rotten", 0,   () -> BlocksRegister.CHEST_ROTTEN, SoundEvents.BLOCK_CHEST_OPEN, SoundEvents.BLOCK_CHEST_CLOSE),
    SUNKEN("sunken", 210, () -> BlocksRegister.CHEST_SUNKEN, SoundEvents.BLOCK_CHEST_OPEN, SoundEvents.BLOCK_CHEST_CLOSE),
    CORAL("coral", 190, () -> BlocksRegister.CHEST_CORAL, Sounds.STONE_CHEST_OPEN, Sounds.STONE_CHEST_CLOSE),

    STORM("storm", 240, () -> BlocksRegister.CHEST_STORM, Sounds.PLASMA_CHEST_OPEN, Sounds.PLASMA_CHEST_CLOSE),

    DEFAULT,
    ;

    private final ResourceLocation texture;
    private final int light;
    private final Supplier<Block> blockSupplier;
    private final SoundEvent soundOpen;
    private final SoundEvent soundClose;

    private static final EnumChest[] VALUES = values();
    @SideOnly(Side.CLIENT)
    private static List<ModelEntry> modelEntries;

    EnumChest() {
        this.texture = new ResourceLocation(Tags.MOD_ID, "textures/arpg_chest_tex.png");
        this.light = 0;
        this.soundOpen = SoundEvents.BLOCK_CHEST_OPEN;
        this.soundClose = SoundEvents.BLOCK_CHEST_CLOSE;
        this.blockSupplier = () -> Blocks.CHEST;
    }

    EnumChest(String name, int light, Supplier<Block> blockSupplier, SoundEvent soundOpen, SoundEvent soundClose) {
        this.texture = new ResourceLocation(Tags.MOD_ID, "textures/chest_" + name + ".png");
        this.light = light;
        this.blockSupplier = blockSupplier;
        this.soundOpen = soundOpen;
        this.soundClose = soundClose;
    }

    public Block getBlock() {
        Block block = this.blockSupplier.get();
        return block != null ? block : Blocks.CHEST;
    }

    public static EnumChest byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return VALUES[0];
        }
        return VALUES[id];
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public int getLight() {
        return this.light;
    }

    public SoundEvent getSoundOpen() {
        return this.soundOpen;
    }

    public SoundEvent getSoundClose() {
        return this.soundClose;
    }

    @SideOnly(Side.CLIENT)
    public ModelEntry getModelEntry() {
        if (modelEntries == null) {
            modelEntries = new ArrayList<>();
            for (EnumChest value : VALUES) {
                modelEntries.add(value == STORM ?
                        new ModelEntry(ARPGChestTESR.modelStorm, ARPGChestTESR.modelBigStorm)
                        : new ModelEntry(ARPGChestTESR.model, ARPGChestTESR.modelBig));
            }
        }
        return modelEntries.get(this.ordinal());
    }

    @SideOnly(Side.CLIENT)
    public static class ModelEntry {
        private final ModelBase model;
        private final ModelBase modelLarge;

        public ModelEntry(ModelBase model, ModelBase modelLarge) {
            this.model = model;
            this.modelLarge = modelLarge;
        }

        public ModelEntry(ModelBase model) {
            this.model = model;
            this.modelLarge = model;
        }

        public ModelBase getModel() {
            return this.model;
        }

        public ModelBase getModelLarge() {
            return this.modelLarge;
        }
    }
}
