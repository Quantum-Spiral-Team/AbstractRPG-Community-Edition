package com.vivern.arpg.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

@SuppressWarnings({"deprecation", "ConstantConditions"})
public class ToolWand extends Item {
   // B: если будет нужна какое-то из этих полей, напишите в issues, я создам метод аксессор
   private boolean enchanted = false;
   private ItemStack consume;
   private final boolean ignoreMetaConsume;
   private final String placeName;
   private IBlockState place = null;
   private String consumeName = "";
   private boolean canReplaceOnSneak = false;
   private IBlockState replace = null;
   private IBlockState toReplace = null;
   private String replaceName;
   private String toReplaceName;
   private int placeMeta = -1;
   private int replaceMeta = -1;
   private int toReplaceMeta = -1;

   public ToolWand(String name, CreativeTabs tab, int maxDamage, ItemStack consume, boolean ignoreMetaConsume, String placeblock) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxDamage);
      this.setMaxStackSize(1);
      this.consume = consume;
      this.placeName = placeblock;
      this.ignoreMetaConsume = ignoreMetaConsume;
   }

   public ToolWand(String name, CreativeTabs tab, int maxDamage, String consume, boolean ignoreMetaConsume, String placeblock) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxDamage);
      this.setMaxStackSize(1);
      this.consumeName = consume;
      this.placeName = placeblock;
      this.ignoreMetaConsume = ignoreMetaConsume;
   }

   public ToolWand setReplaceLogic(String replaceBlock, String toBlock) {
      this.canReplaceOnSneak = true;
      this.replaceName = replaceBlock;
      this.toReplaceName = toBlock;
      return this;
   }

   public ToolWand setPlaceMeta(int metadata) {
      this.placeMeta = metadata;
      return this;
   }

   public ToolWand setReplaceMeta(int metadata) {
      this.replaceMeta = metadata;
      return this;
   }

   public ToolWand setToReplaceMeta(int metadata) {
      this.toReplaceMeta = metadata;
      return this;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      if (this.consume == null) {
         Item it = Item.getByNameOrId(this.consumeName);
         if (it == null) {
            Block bl = Block.getBlockFromName(this.consumeName);
            if (bl == null) {
               return new ActionResult<>(EnumActionResult.FAIL, itemstack);
            }

            this.consume = new ItemStack(bl);
         } else {
            this.consume = new ItemStack(it);
         }
      }

      if (this.place == null) {
         this.place = this.placeMeta < 0
            ? Block.getBlockFromName(this.placeName).getDefaultState()
            : Block.getBlockFromName(this.placeName).getStateFromMeta(this.placeMeta);
      }

      if (this.canReplaceOnSneak) {
         if (this.replace == null) {
            this.replace = this.replaceMeta < 0
               ? Block.getBlockFromName(this.replaceName).getDefaultState()
               : Block.getBlockFromName(this.replaceName).getStateFromMeta(this.replaceMeta);
         }

         if (this.toReplace == null) {
            this.toReplace = this.toReplaceMeta < 0
               ? Block.getBlockFromName(this.toReplaceName).getDefaultState()
               : Block.getBlockFromName(this.toReplaceName).getStateFromMeta(this.toReplaceMeta);
         }
      }

      RayTraceResult result = this.rayTrace(world, player, false);
      if (player.isSneaking() && this.canReplaceOnSneak) {

          if (result.typeOfHit != Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
         }

         player.swingArm(hand);
         BlockPos pos = result.getBlockPos();
         Block blockFrom = world.getBlockState(pos).getBlock();
         Block blockTo = this.toReplace.getBlock();
         if (blockFrom == this.replace.getBlock()) {
            world.setBlockState(pos, this.toReplace);
            world.playSound(
               null,
               pos,
               blockTo.getSoundType(this.toReplace, world, pos, player).getPlaceSound(),
               SoundCategory.BLOCKS,
               0.8F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
         }
      } else {

          if (result.typeOfHit != Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
         }

         if (result.sideHit != null) {
            player.swingArm(hand);
            BlockPos resultPos = result.getBlockPos();
            BlockPos offsetPos = resultPos.offset(result.sideHit);
            boolean replaceable = world.getBlockState(offsetPos).getBlock().isReplaceable(world, offsetPos);
            Block block = this.place.getBlock();
            if (block.canPlaceBlockOnSide(world, offsetPos, result.sideHit)
               && block.canPlaceBlockAt(world, offsetPos)
               && (replaceable || world.isAirBlock(offsetPos))
               && player.inventory.hasItemStack(this.consume)) {
               player.inventory
                  .clearMatchingItems(this.consume.getItem(), this.ignoreMetaConsume ? -1 : this.consume.getMetadata(), this.consume.getCount(), null);
               if (replaceable) {
                  world.destroyBlock(offsetPos, true);
               }

               world.setBlockState(offsetPos, this.place);
               world.playSound(
                  null,
                  offsetPos,
                  block.getSoundType(this.place, world, offsetPos, player).getPlaceSound(),
                  SoundCategory.BLOCKS,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
            }
         }
      }

      return super.onItemRightClick(world, player, hand);
   }

   public ToolWand setEnchantGlow() {
      this.enchanted = true;
      return this;
   }

   public boolean hasEffect(ItemStack stack) {
      return this.enchanted || super.hasEffect(stack);
   }
}
