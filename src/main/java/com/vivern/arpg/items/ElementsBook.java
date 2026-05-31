package com.vivern.arpg.items;

import com.vivern.arpg.container.GUIBookOfElements;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.tileentity.TileBookcase;
import com.vivern.arpg.tileentity.TileSplitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ElementsBook extends Item {

    public ElementsBook() {
        this.setRegistryName("book_of_elements");
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setTranslationKey("book_of_elements");
        this.setMaxStackSize(1);
    }

    public static int getMaxPagesCount() {
        return 8;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.getCooldownTracker().hasCooldown(ItemsRegister.ELEMENTS_BOOK)) {
            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
            if (raytraceresult != null && raytraceresult.typeOfHit == Type.BLOCK) {
                BlockPos blockpos = raytraceresult.getBlockPos();
                TileEntity tentity = worldIn.getTileEntity(blockpos);
                if (tentity != null) {
                    if (tentity instanceof TileSplitter) {
                        TileSplitter splitter = (TileSplitter) tentity;
                        if (!worldIn.isRemote && splitter.lastDissolvedItem != null) {
                            NBTTagList tagList = NBTHelper.GetNbtTagList(itemstack, "pages", 10);
                            if (tagList.tagCount() >= getMaxPagesCount()) {
                                playerIn.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
                                return new ActionResult(EnumActionResult.FAIL, itemstack);
                            }

                            NBTTagCompound itemInBook = new NBTTagCompound();
                            itemInBook.setString("item", splitter.lastDissolvedItem.getRegistryName().toString());
                            itemInBook.setInteger("metadata", splitter.lastDissolvedMetadata);
                            tagList.appendTag(itemInBook);
                            playerIn.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
                            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                        }

                        playerIn.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
                        return new ActionResult(EnumActionResult.PASS, itemstack);
                    }

                    if (tentity instanceof TileBookcase) {
                        TileBookcase bookcase = (TileBookcase) tentity;
                        if (bookcase.addBook(itemstack)) {
                            itemstack.shrink(1);
                            playerIn.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
                            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                        }

                        playerIn.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
                        return new ActionResult(EnumActionResult.PASS, itemstack);
                    }
                }
            }

            this.openGui(playerIn, itemstack);
            playerIn.addStat(StatList.getObjectUseStats(this));
            playerIn.getCooldownTracker().setCooldown(ItemsRegister.ELEMENTS_BOOK, 3);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult(EnumActionResult.FAIL, itemstack);
        }
    }

    public void openGui(EntityPlayer player, ItemStack book) {
        if (player instanceof EntityPlayerSP) {
            Minecraft.getMinecraft().displayGuiScreen(new GUIBookOfElements(book));
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i <= 7; i++) {
                ItemStack itemStack = new ItemStack(this);
                NBTHelper.GiveNBTint(itemStack, i, "gem");
                NBTHelper.SetNBTint(itemStack, i, "gem");
                items.add(itemStack);
            }
        }
    }

}
