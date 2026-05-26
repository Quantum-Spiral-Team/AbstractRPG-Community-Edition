package com.vivern.arpg.items;

import baubles.api.render.IRenderBauble;
import com.vivern.arpg.items.models.ThunderbirdWingsModel;
import com.vivern.arpg.main.IAttributedBauble;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThunderbirdWings extends AbstractWings implements IAttributedBauble, IRenderBauble, IEnergyItem {
   public static ThunderbirdWingsModel model = new ThunderbirdWingsModel();
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/thunderbird_wings_model_tex.png");

   public ThunderbirdWings() {
      this.setRegistryName("thunderbird_wings");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("thunderbird_wings");
      this.setMaxDamage(10000);
      this.setMaxStackSize(1);
      this.flapPeriod = 12;
      this.flapPeriodFloat = 2.0F;
   }

   @Override
   public boolean canUseWings(ItemStack itemstack, EntityPlayer player) {
      return this.extractEnergyFromItem(itemstack, 100, true) >= 100;
   }

   @Override
   public void onFlyingTick(ItemStack itemstack, EntityPlayer player, boolean likeElytra) {
      super.onFlyingTick(itemstack, player, likeElytra);
      this.extractEnergyFromItem(itemstack, likeElytra ? 50 : 100, false);
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.TOPAZITRON_CAPACITY;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.TOPAZITRON_THROUGHPUT;
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      this.renderDefaultWings(texture, model, stack, player, type, partialTicks, null, 0);
   }

   @Override
   public double getMaxUpwardMotion(ItemStack stack) {
      return 0.7;
   }

   @Override
   public double getUpwardMotionAdd(ItemStack stack) {
      return 0.11;
   }

   @Override
   public double getFallingMotionAdd(ItemStack stack) {
      return 0.35;
   }

   @Override
   public int getMaxFlyTime(ItemStack stack) {
      return Integer.MAX_VALUE;
   }

   @Override
   public double getFallingMotionSlowdown(ItemStack stack) {
      return 0.65;
   }

   @SideOnly(Side.CLIENT)
   @Override
   protected MovingSound getWingsSound(EntityPlayer player) {
      return new WingsSound(player, Sounds.toxic_wings_flying);
   }

   @Override
   public void playFlapSound(EntityPlayer player) {
      player.world
         .playSound(
            player.posX,
            player.posY,
            player.posZ,
            Sounds.wings,
            SoundCategory.PLAYERS,
            0.5F,
            0.85F + itemRand.nextFloat() * 0.1F,
            false
         );
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.JUMP_HEIGHT;
   }

   @Override
   public double value() {
      return 0.05;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "thunderbird_wings";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.AIRBORNE_MOBILITY.getName(), new AttributeModifier(uuid, "airborn mobility modifier", 0.06, 0));
      return multimap;
   }
}
