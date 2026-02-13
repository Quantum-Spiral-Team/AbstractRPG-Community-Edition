//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.arpgfix.AbstractClientFieldsContainer;
import com.Vivern.Arpg.arpgfix.IFieldInit;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;

import java.util.List;

import com.Vivern.Arpg.renders.ParticleTracker;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChlorineCloud extends EntityThrowable implements IFieldInit {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   public int ticks = 0;
   public double moveX = 0.0;
   public double moveY = 0.0;
   public double moveZ = 0.0;

//   public static ParticleTracker.TrackerSmoothShowHide trssh = new ParticleTracker.TrackerSmoothShowHide(
//      new Vec3d[]{new Vec3d(0.0, 15.0, 0.0666), new Vec3d(20.0, 45.0, -0.04)}, null
//   );
   public static Object trssh; // ParticleTracker.TrackerSmoothShowHide

   public static class ClientFieldsContainer extends AbstractClientFieldsContainer {
      @Override
      @SideOnly(Side.CLIENT)
      public void initFields() {
         if (trssh == null) {
            trssh = new ParticleTracker.TrackerSmoothShowHide(
                    new Vec3d[]{new Vec3d(0.0, 15.0, 0.0666), new Vec3d(20.0, 45.0, -0.04)}, null
            );
         }
      }
      @SideOnly(Side.CLIENT)
      public ParticleTracker.TrackerSmoothShowHide getTrash() {
         return (ParticleTracker.TrackerSmoothShowHide) trssh;
      }
   }

   public static ClientFieldsContainer clientFields;

   @Override
   @SideOnly(Side.CLIENT)
   public void initFields() {
      if (clientFields == null) {
         clientFields = new ClientFieldsContainer();
      }
   }

   public ChlorineCloud(World world) {
      super(world);
      this.setSize(0.1F, 0.1F);
      this.initFields();
   }

   public ChlorineCloud(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.setSize(0.1F, 0.1F);
   }

   public ChlorineCloud(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.setSize(0.1F, 0.1F);
   }

   public boolean isInRangeToRenderDist(double distance) {
      return false;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted % 5 == 0) {
         if (!this.world.isRemote) {
            this.ticks += 5;
            if (this.ticks > 100) {
               this.setDead();
            }

            double damageRadius = 3.5;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  PotionEffect eff = entitylivingbase.getActivePotionEffect(PotionEffects.CHLORITE);
                  int amp = 0;
                  if (eff != null) {
                     amp = eff.getAmplifier();
                  }

                  entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.CHLORITE, amp * 300 + 500, amp + 1));
               }
            }
         }

         this.motionX *= 0.75;
         this.motionY *= 0.75;
         this.motionZ *= 0.75;
         this.motionX = this.motionX + this.moveX;
         this.motionY = this.motionY + this.moveY;
         this.motionZ = this.motionZ + this.moveZ;
         this.moveY -= 0.005;
         this.velocityChanged = true;
      }
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setInteger("ticks", this.ticks);
      super.writeEntityToNBT(compound);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("ticks")) {
         this.ticks = compound.getInteger("ticks");
      }

      super.readEntityFromNBT(compound);
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      if (this.world.isRemote && this.rand.nextFloat() < 0.2F) {
         this.onEntityUpdate_Client_1();
      }
   }

   @SideOnly(Side.CLIENT)
   private void onEntityUpdate_Client_1() {
      GUNParticle bigsmoke = new GUNParticle(
              this.largesmoke,
              1.5F + (float)this.rand.nextGaussian() / 3.0F,
              3.0E-4F,
              45,
              110,
              this.world,
              this.posX,
              this.posY,
              this.posZ,
              (float)this.rand.nextGaussian() / 17.0F,
              (float)this.rand.nextGaussian() / 17.0F,
              (float)this.rand.nextGaussian() / 17.0F,
              0.8F + this.rand.nextFloat() * 0.2F,
              0.8F + this.rand.nextFloat() * 0.2F,
              0.4F,
              true,
              this.rand.nextInt(360),
              true,
              1.0F
      );
//      bigsmoke.tracker = trssh;
      bigsmoke.tracker = clientFields.getTrash();
      bigsmoke.alpha = 0.0F;
      this.world.spawnEntity(bigsmoke);
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit == null) {
         Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
         if (result != null
            && result.getBlockPos() != null
            && block.getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos()) != null
            && block != BlocksRegister.CHLORINEBELCHER) {
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = -this.motionY;
               this.moveY = -this.moveY;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = -this.motionZ;
               this.moveZ = -this.moveZ;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = -this.motionX;
               this.moveX = -this.moveX;
            }

            this.velocityChanged = true;
         }
      }
   }
}
