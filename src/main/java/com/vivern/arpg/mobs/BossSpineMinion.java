package com.vivern.arpg.mobs;

import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.potions.PotionEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class BossSpineMinion extends EntityMob {

    public BossSpine owner;

    public BossSpineMinion(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.6F);
        this.setNoGravity(true);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.owner = null;
    }

    public BossSpineMinion(World worldIn, BossSpine owner) {
        super(worldIn);
        this.setSize(0.6F, 1.6F);
        this.setNoGravity(true);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.owner = owner;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void addPotionEffect(PotionEffect potioneffectIn) {
        if (potioneffectIn.getPotion() != PotionEffects.FREEZING) {
            super.addPotionEffect(potioneffectIn);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source != DamageSource.IN_WALL && source != DamageSource.CRAMMING && source != DamageSource.CACTUS) {
            return !this.isEntityInvulnerable(source) && super.attackEntityFrom(source, amount);
        } else {
            return false;
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        for (BossSpineMinion minion : this.world.getEntitiesWithinAABB(BossSpineMinion.class, this.getEntityBoundingBox().grow(0.5))) {
            if (minion != this) {
                SuperKnockback.applyMove(this, 0.3F, minion.posX, minion.posY, minion.posZ);
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return Sounds.spine_minion_hurt;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.spine_minion_dead;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            PotionEffect baff = new PotionEffect(PotionEffects.INCORPOREITY, 50, 1);
            ((EntityLivingBase) entityIn).addPotionEffect(baff);
        }

        return super.attackEntityAsMob(entityIn);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIFlying(this, 120, 16.0F, 0.015F, false));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
        this.tasks.addTask(2, new EntityAIEasyFlyingAttack(this, 0.045F, 1.07F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

}
