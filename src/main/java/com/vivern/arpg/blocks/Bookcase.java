package com.vivern.arpg.blocks;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.container.GuiHandler;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.tileentity.IManaBuffer;
import com.vivern.arpg.tileentity.TileBookcase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Bookcase extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public Bookcase(String name, SoundType sound, String toolClass, int harvestLvl) {
        super(IManaBuffer.MAGIC_BLOCK);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.blockHardness = 2.5F;
        this.blockResistance = 10.0F;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setSoundType(sound);
        this.setHarvestLevel(toolClass, harvestLvl);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.MAIN_HAND && !player.getCooldownTracker().hasCooldown(ItemsRegister.ELEMENTS_BOOK)) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileBookcase) {
                TileBookcase tileBookcase = (TileBookcase) tile;
                ItemStack handStack = player.getHeldItem(hand);
                if (handStack.isEmpty()) {
                    if (!player.isSneaking()) {
                        if (worldIn.isRemote && tileBookcase.hasBooks()) {
                            player.openGui(AbstractRPG.instance, GuiHandler.BOOK_OF_ELEMENTS_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                        }

                        return true;
                    }

                    if (!worldIn.isRemote) {
                        for (int i = 2; i >= 0; i--) {
                            if (tileBookcase.booksGems[i] != 50) {
                                ItemStack itemStack = tileBookcase.stacks.get(i).copy();
                                EntityItem entityItem = new EntityItem(worldIn, player.posX, player.posY, player.posZ, itemStack);
                                entityItem.setNoPickupDelay();
                                worldIn.spawnEntity(entityItem);
                                tileBookcase.stacks.set(i, ItemStack.EMPTY);
                                tileBookcase.booksGems[i] = 50;
                                PacketHandler.trySendPacketUpdate(worldIn, pos, tileBookcase, 64.0);
                                player.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
                                return true;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileBookcase) {
            System.out.println("instanceof");
            TileBookcase tileBookcase = (TileBookcase) tile;

            for (int i = 0; i < 3; i++) {
                System.out.println("for");
                if (tileBookcase.booksGems[i] != 50) {
                    System.out.println("add");
                    drops.add(tileBookcase.stacks.get(i).copy());
                }
            }
        }
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        IBlockState state = world.getBlockState(pos);
        return state.getValue(FACING).getAxis() != side.getAxis();
    }

    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }

    @Override
    @Nullable
    public TileBookcase createTileEntity(World world, IBlockState blockState) {
        TileBookcase tileBookcase = new TileBookcase();
        tileBookcase.rotation = blockState.getValue(FACING).getHorizontalIndex();
        return tileBookcase;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);
        if (enumfacing.getAxis() == Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

}
