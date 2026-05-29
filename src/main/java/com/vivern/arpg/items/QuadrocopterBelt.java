package com.vivern.arpg.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.vivern.arpg.items.armor.Armors;
import com.vivern.arpg.items.models.QuadrocopterBeltModel;
import com.vivern.arpg.items.models.QuadroBeltModel2;
import com.vivern.arpg.main.FindAmmo;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

//TODO по возможности упростить логику
@SuppressWarnings("deprecation")
public class QuadrocopterBelt extends ItemArmor implements IBauble, IRenderBauble {

   private static final ResourceLocation TEXTURE_PARTICLE = new ResourceLocation("arpg:textures/air2.png");
   private static final ResourceLocation TEXTURE_BELT = new ResourceLocation("arpg:textures/quadro_belt_model_tex.png");
   private final QuadroBeltModel2 model = new QuadroBeltModel2();
   public static ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial(
      "arpg:armor", "arpg:armor", 9, new int[]{2, 4, 6, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F
   );

   public QuadrocopterBelt() {
      super(armorMaterial, 2, EntityEquipmentSlot.LEGS);
      this.setRegistryName("quadrocopter_belt");
      this.setTranslationKey("quadrocopter_belt");
      this.setMaxDamage(20000);
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.COMBAT);
      BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         this.model.setRotateAngle(this.model.propeller1, 0.5462881F, player.ticksExisted, 0.0F);
         this.model.setRotateAngle(this.model.Bpropeller1, 0.5462881F, -player.ticksExisted, 0.0F);
         this.model.setRotateAngle(this.model.back, (float)(-player.motionY / 2.0 - 0.5462881F), 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.Aforward1, (float)(player.motionY / 2.0 + 0.5462881F), 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.AAforward2, (float)(player.motionY / 2.0 + 0.5462881F), 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.Apropeller1, 0.5462881F, player.ticksExisted, 0.0F);
         this.model.setRotateAngle(this.model.AApropeller1, 0.5462881F, -player.ticksExisted, 0.0F);
         GlStateManager.pushMatrix();
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_BELT);
         this.model.render(player, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.065F);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.BELT;
   }

   //TODO упростить
   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
      Item itemIn = itemstack.getItem();
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer) entityIn;
         int damage = itemstack.getItemDamage();
         InventoryPlayer inventory = player.inventory;
         NonNullList<ItemStack> armorInventory = inventory.armorInventory;
         if (armorInventory.get(1).getItem() == ItemsRegister.QUADROCOPTER_BELT) {
            ItemStack quadrocopter = armorInventory.get(1);
            int empty = inventory.getFirstEmptyStack();
            armorInventory.set(1, ItemStack.EMPTY);
            World world = player.world;
            if (empty != -1) {
               inventory.setInventorySlotContents(empty, quadrocopter);
            } else if (!world.isRemote) {
               world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, quadrocopter));
            }
         }

         if (damage <= 19999) {
            if (!player.getCooldownTracker().hasCooldown(itemIn) && Keyboard.isKeyDown(57)) {
               player.motionY = player.motionY * 0.3 + 1.2;
               player.getCooldownTracker().setCooldown(this, 40);
            }

            player.fallDistance = 0.0F;
            this.Effects(player.world, player);
            itemstack.damageItem(1, player);
            Vec3d end = GetMOP.rotatedPosRayTrace(3.5, 1.0F, player, 0.01, 0.01, 90.0F, 0.0F);
            double dist = end.distanceTo(player.getPositionEyes(1.0F));
            float actualVelocity = damage <= 19900 ? 0.05F : 0.05F - (damage - 19900) * 5.0E-4F;
            float velocity = 0.0F;
            int angle = 0;
            if (GameSettings.isKeyDown(Keys.FORWARD)) {
               velocity = actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.BACK)) {
               velocity = -actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.RIGHT)) {
               angle = 90;
               velocity = actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.LEFT)) {
               angle = -90;
               velocity = actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.LEFT) && GameSettings.isKeyDown(Keys.RIGHT)) {
               angle = 0;
               velocity = 0.0F;
            }

            if (GameSettings.isKeyDown(Keys.FORWARD) && GameSettings.isKeyDown(Keys.BACK)) {
               angle = 0;
               velocity = 0.0F;
            }

            if (GameSettings.isKeyDown(Keys.RIGHT) && GameSettings.isKeyDown(Keys.FORWARD)) {
               angle = 45;
               velocity = actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.LEFT) && GameSettings.isKeyDown(Keys.FORWARD)) {
               angle = -45;
               velocity = actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.RIGHT) && GameSettings.isKeyDown(Keys.BACK)) {
               angle = 135;
               velocity = actualVelocity;
            }

            if (GameSettings.isKeyDown(Keys.LEFT) && GameSettings.isKeyDown(Keys.BACK)) {
               angle = -135;
               velocity = actualVelocity;
            }

            float rotationYawIn = player.rotationYaw + angle;
            float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0));
            float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0));
            float f = MathHelper.sqrt(x * x + z * z);
            x /= f;
            z /= f;
            x *= velocity;
            z *= velocity;
            player.motionX += x;
            player.motionZ += z;
            if (dist < 3.4) {
               if (!player.isSneaking()) {
                  player.motionY += actualVelocity * 4.0F;
               } else {
                  player.motionY += actualVelocity;
               }
            }
         }

         if (itemstack.getItemDamage() > 19999 && FindAmmo.find(player.inventory, ItemsRegister.ION_BATTERY) >= 6) {
            player.inventory.clearMatchingItems(new ItemStack(ItemsRegister.ION_BATTERY, 6).getItem(), -1, 6, null);
            itemstack.setItemDamage(0);
            player.getCooldownTracker().setCooldown(this, 45);
            player.world.playSound(
                null, player.posX, player.posY, player.posZ, Sounds.lasergun_rel, SoundCategory.NEUTRAL, 0.7F, 1.0F
            );
         }
      }
   }

   @Override
   public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
      player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.75F, 1.9F);
   }

   @Override
   public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
      player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.75F, 1.9F);
   }

   @Override
   public boolean canEquip(ItemStack itemstack, EntityLivingBase living) {
      if (living instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)living;
         return player.inventory.armorInventory.get(1).getItem() != ItemsRegister.QUADROCOPTER_BELT;
      }

      return true;
   }

   @Override
   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
      return true;
   }

   @Override
   public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void Effects(World world, EntityPlayer player) {
      Entity bigBoom = new GUNParticle(
              TEXTURE_PARTICLE,
         0.1F + (float)itemRand.nextGaussian() / 20.0F,
         -0.07F,
         15,
         240,
         world,
         player.posX + (float)itemRand.nextGaussian() / 2.0F,
         player.posY,
         player.posZ + (float)itemRand.nextGaussian() / 2.0F,
         (float)itemRand.nextGaussian() / 22.0F,
         (float)itemRand.nextGaussian() / 22.0F - 0.38F,
         (float)itemRand.nextGaussian() / 22.0F,
         1.0F,
         1.0F,
         1.0F,
         false,
         (int)Math.round(itemRand.nextGaussian() * 20.0)
      );
      world.spawnEntity(bigBoom);
      if (player.ticksExisted % 4 == 0) {
         float sss = (float)(Math.abs(player.motionX) + Math.abs(player.motionY) + Math.abs(player.motionZ)) / 20.0F;
         world.playSound(
                 null, player.posX, player.posY, player.posZ, Sounds.quadromotor, SoundCategory.NEUTRAL, 0.7F, 0.8F + sss
         );
      }
   }

   @Override
   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      Item itemIn = itemstack.getItem();
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         boolean click = Keyboard.isKeyDown(57);
         int damage = itemstack.getItemDamage();
         if (player.inventory.armorInventory.get(1) == itemstack) {
            if (damage <= 19999) {
               if (!player.getCooldownTracker().hasCooldown(itemIn) && click) {
                  player.motionY = player.motionY * 0.3 + 1.2;
                  player.getCooldownTracker().setCooldown(this, 40);
               }

               player.fallDistance = 0.0F;
               this.Effects(worldIn, player);
               itemstack.damageItem(1, player);
               Vec3d end = GetMOP.rotatedPosRayTrace(3.5, 1.0F, player, 0.01, 0.01, 90.0F, 0.0F);
               double dist = end.distanceTo(player.getPositionEyes(1.0F));
               float actualVelocity = damage <= 19900 ? 0.05F : 0.05F - (damage - 19900) * 5.0E-4F;
               float velocity = 0.0F;
               int angle = 0;
               if (GameSettings.isKeyDown(Keys.FORWARD)) {
                  velocity = actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.BACK)) {
                  velocity = -actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.RIGHT)) {
                  angle = 90;
                  velocity = actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.LEFT)) {
                  angle = -90;
                  velocity = actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.LEFT) && GameSettings.isKeyDown(Keys.RIGHT)) {
                  angle = 0;
                  velocity = 0.0F;
               }

               if (GameSettings.isKeyDown(Keys.FORWARD) && GameSettings.isKeyDown(Keys.BACK)) {
                  angle = 0;
                  velocity = 0.0F;
               }

               if (GameSettings.isKeyDown(Keys.RIGHT) && GameSettings.isKeyDown(Keys.FORWARD)) {
                  angle = 45;
                  velocity = actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.LEFT) && GameSettings.isKeyDown(Keys.FORWARD)) {
                  angle = -45;
                  velocity = actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.RIGHT) && GameSettings.isKeyDown(Keys.BACK)) {
                  angle = 135;
                  velocity = actualVelocity;
               }

               if (GameSettings.isKeyDown(Keys.LEFT) && GameSettings.isKeyDown(Keys.BACK)) {
                  angle = -135;
                  velocity = actualVelocity;
               }

               float rotationYawIn = player.rotationYaw + angle;
               float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0));
               float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0));
               float f = MathHelper.sqrt(x * x + z * z);
               x /= f;
               z /= f;
               x *= velocity;
               z *= velocity;
               player.motionX += x;
               player.motionZ += z;
               if (dist < 3.4) {
                  if (!player.isSneaking()) {
                     player.motionY += actualVelocity * 4.0F;
                  } else {
                     player.motionY += actualVelocity;
                  }
               }
            }

            if (itemstack.getItemDamage() > 19999 && FindAmmo.find(player.inventory, ItemsRegister.ION_BATTERY) >= 6) {
               player.inventory.clearMatchingItems(new ItemStack(ItemsRegister.ION_BATTERY, 6).getItem(), -1, 6, null);
               itemstack.setItemDamage(0);
               player.getCooldownTracker().setCooldown(this, 45);
               worldIn.playSound(
                   null, player.posX, player.posY, player.posZ, Sounds.lasergun_rel, SoundCategory.NEUTRAL, 0.7F, 1.0F
               );
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public @Nullable ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (itemStack != ItemStack.EMPTY) {
         QuadrocopterBeltModel qmodel = (QuadrocopterBeltModel) Armors.ArmorModels.QUADROCOPTER_BELT.getModel();
         qmodel.bipedBody.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         qmodel.isSneak = entityLiving.isSneaking();
         qmodel.isRiding = entityLiving.isRiding();
         qmodel.isChild = entityLiving.isChild();
         qmodel.setRotateAngle(qmodel.propeller1, 0.5462881F, entityLiving.ticksExisted, 0.0F);
         qmodel.setRotateAngle(qmodel.Bpropeller1, 0.5462881F, -entityLiving.ticksExisted, 0.0F);
         qmodel.setRotateAngle(qmodel.back, (float)(-entityLiving.motionY / 2.0 - 0.5462881F), 0.0F, 0.0F);
         qmodel.setRotateAngle(qmodel.Aforward1, (float)(entityLiving.motionY / 2.0 + 0.5462881F), 0.0F, 0.0F);
         qmodel.setRotateAngle(qmodel.AAforward2, (float)(entityLiving.motionY / 2.0 + 0.5462881F), 0.0F, 0.0F);
         qmodel.setRotateAngle(qmodel.Apropeller1, 0.5462881F, entityLiving.ticksExisted, 0.0F);
         qmodel.setRotateAngle(qmodel.AApropeller1, 0.5462881F, -entityLiving.ticksExisted, 0.0F);
         return qmodel;
      } else {
         return null;
      }
   }

   @Override
   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "arpg:textures/quadro_belt_model_tex.png";
   }

}
