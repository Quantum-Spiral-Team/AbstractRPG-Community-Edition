package com.vivern.arpg.blocks;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.container.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DebugColorBlock extends Block {

    public DebugColorBlock() {
        super(Material.ROCK);
        this.setRegistryName("debug_color_block");
        this.setTranslationKey("debug_color_block");
        this.blockHardness = 10.0F;
        this.blockResistance = 10.0F;
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            player.openGui(AbstractRPG.instance, GuiHandler.DEBUG_COLOR_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

}
