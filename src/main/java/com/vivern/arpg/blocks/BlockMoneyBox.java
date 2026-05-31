package com.vivern.arpg.blocks;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.container.GuiHandler;
import com.vivern.arpg.main.Coins;
import com.vivern.arpg.tileentity.TileBank;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class BlockMoneyBox extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);

    public BlockMoneyBox() {
        super(Material.WOOD);
        this.setRegistryName("money_box");
        this.setTranslationKey("money_box");
        this.blockHardness = 2.0F;
        this.blockResistance = 10.0F;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!world.isRemote) {
            TileBank tile = this.getTileEntity(world, pos);
            Coins.dropMoneyToWorld(world, tile.coins, 25, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            tile.coins = 0;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return ALL_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return ALL_AABB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            player.openGui(AbstractRPG.instance, GuiHandler.BANK_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    public TileBank getTileEntity(IBlockAccess world, BlockPos position) {
        return (TileBank) world.getTileEntity(position);
    }

    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }

    @Override
    @Nullable
    public TileBank createTileEntity(World world, IBlockState blockState) {
        return new TileBank();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
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
