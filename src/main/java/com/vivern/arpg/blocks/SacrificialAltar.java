package com.vivern.arpg.blocks;

import com.vivern.arpg.items.SoulStone;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.tileentity.TileSacrificialAltar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SacrificialAltar extends Block {

    public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);
    public static ResourceLocation tex = new ResourceLocation("arpg:textures/clouds3.png");

    public SacrificialAltar() {
        super(Material.ROCK);
        this.setRegistryName("sacrificial_altar");
        this.setTranslationKey("sacrificial_altar");
        this.blockHardness = 7.5F;
        this.blockResistance = 16.0F;
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
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
        if (!worldIn.isRemote) {
            TileSacrificialAltar tile = this.getTileEntity(worldIn, pos);
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == ItemsRegister.SOUL_STONE && SoulStone.getSoul(stack) > 0 && tile.isEmpty()) {
                tile.setInventorySlotContents(0, stack.copy());
                stack.shrink(1);
                trySendPacketUpdate(worldIn, pos, tile);
                worldIn.playSound(null, pos, Sounds.item_misc_d, SoundCategory.PLAYERS, 0.5F, 0.7F + RANDOM.nextFloat() / 5.0F);
                return true;
            }

            ItemStack stackInTile = tile.getStackInSlot(0);
            if (!stackInTile.isEmpty() && stackInTile.getItem() == ItemsRegister.SOUL_STONE && SoulStone.getSoul(stackInTile) == 0) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, stackInTile.copy()));
                tile.setInventorySlotContents(0, ItemStack.EMPTY);
                trySendPacketUpdate(worldIn, pos, tile);
                worldIn.playSound(null, pos, Sounds.item_misc_d, SoundCategory.PLAYERS, 0.5F, 0.8F + RANDOM.nextFloat() / 5.0F);
                return true;
            }
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.5F) {
            TileSacrificialAltar tile = this.getTileEntity(worldIn, pos);
            if (tile.charge > 0.0F) {
                float scale = 0.2F + rand.nextFloat() / 5.0F;
                GUNParticle spelll = new GUNParticle(tex, scale, 0.01F, 15, 220, worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, rand.nextInt(360));
                spelll.alpha = 0.1F;
                spelll.alphaTickAdding = 0.1F;
                spelll.isPushedByLiquids = false;
                spelll.alphaGlowing = true;
                spelll.scaleTickAdding = -scale / 16.0F;
                worldIn.spawnEntity(spelll);
            }
        }
    }

    public static void trySendPacketUpdate(World world, BlockPos pos, TileSacrificialAltar tile) {
        int range = 64;

        for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(pos.getX() + range, pos.getY() + range, pos.getZ() + range, pos.getX() - range, pos.getY() - range, pos.getZ() - range))) {
            SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
            if (spacketupdatetileentity != null) {
                playerIn.connection.sendPacket(spacketupdatetileentity);
            }
        }
    }

    public Class<TileSacrificialAltar> getTileEntityClass() {
        return TileSacrificialAltar.class;
    }

    public TileSacrificialAltar getTileEntity(IBlockAccess world, BlockPos position) {
        return (TileSacrificialAltar) world.getTileEntity(position);
    }

    @Override
    public boolean hasTileEntity(IBlockState blockState) {
        return true;
    }

    @Override
    @Nullable
    public TileSacrificialAltar createTileEntity(World world, IBlockState blockState) {
        return new TileSacrificialAltar();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
