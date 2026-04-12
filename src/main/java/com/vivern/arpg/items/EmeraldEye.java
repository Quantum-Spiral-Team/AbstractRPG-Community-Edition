package com.vivern.arpg.items;

import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.tileentity.IMirrorUser;
import com.vivern.arpg.tileentity.TileRunicMirror;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EmeraldEye extends Item {

   private static final String firstClickedName = "firstclicked";

   public EmeraldEye() {
      this.setRegistryName("emerald_eye");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("emerald_eye");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
      if (playerIn.isSneaking()) {
         NBTHelper.SetNBTboolean(itemstack, false, firstClickedName);
      }

       if (raytraceresult.typeOfHit == Type.BLOCK) {
           BlockPos blockpos = raytraceresult.getBlockPos();
           if (!worldIn.isBlockModifiable(playerIn, blockpos)
                   || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
             return new ActionResult<>(EnumActionResult.PASS, itemstack);
           }

           TileEntity tile = worldIn.getTileEntity(blockpos);
           if (tile != null) {
             boolean firstClicked = NBTHelper.GetNBTboolean(itemstack, firstClickedName);
             if (firstClicked && tile instanceof IMirrorUser) {
                 BlockPos from = NBTHelper.GetNBTBlockPos(itemstack, "from");
                 if (from == blockpos) {
                     return new ActionResult<>(EnumActionResult.PASS, itemstack);
                 }

                 if (from != null) {
                     TileEntity tileMirror = worldIn.getTileEntity(from);
                     if (tileMirror instanceof TileRunicMirror
                             && from.getDistance(blockpos.getX(), blockpos.getY(), blockpos.getZ()) < 8.0) {
                         ((IMirrorUser) tile).tryAddMirrorPos(from);
                     }
                 }

                 NBTHelper.SetNBTboolean(itemstack, false, firstClickedName);
                 worldIn.playSound(
                         playerIn,
                         playerIn.posX,
                         playerIn.posY,
                         playerIn.posZ,
                         Sounds.item_misc_d,
                         SoundCategory.BLOCKS,
                         0.5F,
                         0.9F + itemRand.nextFloat() / 5.0F
                 );
             } else if (tile instanceof TileRunicMirror) {
                 NBTHelper.GiveNBTboolean(itemstack, true, firstClickedName);
                 NBTHelper.SetNBTboolean(itemstack, true, firstClickedName);
                 NBTHelper.GiveNBTBlockPos(itemstack, blockpos, "from");
                 NBTHelper.SetNBTBlockPos(itemstack, blockpos, "from");
                 worldIn.playSound(
                         playerIn,
                         playerIn.posX,
                         playerIn.posY,
                         playerIn.posZ,
                         Sounds.item_misc_b,
                         SoundCategory.BLOCKS,
                         0.5F,
                         0.9F + itemRand.nextFloat() / 5.0F
                 );
             }

             return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
           }

           if (playerIn.isSneaking()) {
             NBTHelper.SetNBTboolean(itemstack, false, firstClickedName);
           }
       } else if (playerIn.isSneaking()) {
           NBTHelper.SetNBTboolean(itemstack, false, firstClickedName);
       }

       return new ActionResult<>(EnumActionResult.PASS, itemstack);
   }
}
