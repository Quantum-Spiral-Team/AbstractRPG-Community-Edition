package com.vivern.arpg.blocks;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.tileentity.TileIndustrialMixer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IndustrialMixer extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public IndustrialMixer() {
        super(Material.IRON);
        this.setRegistryName("industrial_mixer");
        this.setTranslationKey("industrial_mixer");
        this.blockHardness = 9.0F;
        this.blockResistance = 25.0F;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setSoundType(SoundTypeShards.METAL);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileIndustrialMixer tile = this.getTileEntity(worldIn, pos);
            if (tile != null) {
                player.openGui(AbstractRPG.instance, 13, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    public static void trySendPacketUpdate(World world, BlockPos pos, TileIndustrialMixer tile, int range, boolean easy) {
        List<EntityPlayerMP> list = world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(pos.getX() + range, pos.getY() + range, pos.getZ() + range, pos.getX() - range, pos.getY() - range, pos.getZ() - range));
        if (easy) {
            for (EntityPlayerMP playerIn : list) {
                SPacketUpdateTileEntity spacketupdatetileentity = tile.getEasyUpdatePacket();
                if (spacketupdatetileentity != null) {
                    playerIn.connection.sendPacket(spacketupdatetileentity);
                }
            }
        } else {
            for (EntityPlayerMP playerInx : list) {
                SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
                if (spacketupdatetileentity != null) {
                    playerInx.connection.sendPacket(spacketupdatetileentity);
                }
            }
        }
    }

    public Class<TileIndustrialMixer> getTileEntityClass() {
        return TileIndustrialMixer.class;
    }

    public TileIndustrialMixer getTileEntity(IBlockAccess world, BlockPos position) {
        return (TileIndustrialMixer) world.getTileEntity(position);
    }

    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }

    @Override
    @Nullable
    public TileIndustrialMixer createTileEntity(World world, IBlockState blockState) {
        return new TileIndustrialMixer();
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
