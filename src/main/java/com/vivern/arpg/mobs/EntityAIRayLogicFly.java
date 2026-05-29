package com.vivern.arpg.mobs;

import com.vivern.arpg.main.SuperKnockback;
import com.google.common.base.Predicate;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class EntityAIRayLogicFly extends EntityAIBase {
   public EntityCreature entity;
   public LogicRay logicRay;
   public Predicate raysuccess = (Predicate<Entity>) en -> en instanceof EntityPlayer;
   public Predicate rayignore;

   public EntityAIRayLogicFly(EntityCreature entity) {
      this.entity = entity;
      this.rayignore = (Predicate<Entity>) en -> !(en instanceof EntityPlayer);
   }

   @Override
   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   @Override
   public void updateTask() {
      if (this.logicRay == null) {
         this.logicRay = new LogicRay(
            this.entity.posX,
            this.entity.posY + this.entity.height / 2.0F,
            this.entity.posZ,
            this.entity.getAttackTarget(),
            null
         );
      } else {
         this.logicRay.posX = this.entity.posX;
         this.logicRay.posY = this.entity.posY + this.entity.height / 2.0F;
         this.logicRay.posZ = this.entity.posZ;
         boolean shouldTravel = false;
         if (this.entity.getAttackTarget() != null) {
            shouldTravel = true;
            if (this.logicRay.nextRay == null) {
               this.logicRay.targetEntity = this.entity.getAttackTarget();
            }

            if (this.entity.ticksExisted % 15 == 0) {
               this.raysuccess = (Predicate<Entity>) en -> en != null && en == EntityAIRayLogicFly.this.entity.getAttackTarget();
               this.rayignore = (Predicate<Entity>) en -> en == null || en != EntityAIRayLogicFly.this.entity.getAttackTarget();
               this.logicRay.checkShortRay(this.entity.world, this.entity.getAttackTarget(), this.raysuccess, this.rayignore, false);
            }

            if (this.entity.ticksExisted % 10 == 0) {
               this.entity.motionX = this.entity.motionX + (this.entity.getRNG().nextFloat() - 0.5F) / 10.0F;
               this.entity.motionY = this.entity.motionY + (this.entity.getRNG().nextFloat() - 0.5F) / 10.0F;
               this.entity.motionZ = this.entity.motionZ + (this.entity.getRNG().nextFloat() - 0.5F) / 10.0F;
               this.entity.faceEntity(this.entity.getAttackTarget(), 30.0F, 30.0F);
            }
         }

         if (shouldTravel
            && this.entity.getDistance(this.entity.getAttackTarget()) > 3.0F
            && this.logicRay.exist(this.entity.world, this.raysuccess, this.rayignore, false)) {
            Vec3d forwPos = this.logicRay.getForwardPosition();
            if (forwPos != null) {
               float flyspeed = (float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
               SuperKnockback.applyMove(
                  this.entity, -flyspeed, forwPos.x, forwPos.y - this.entity.height / 2.0F, forwPos.z
               );
            }
         }

         this.logicRay.checkOwnerWalkedOn(this.entity);
      }
   }
}
