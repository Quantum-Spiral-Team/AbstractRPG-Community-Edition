package com.vivern.arpg.mobs;

public enum EnumMobRole {
    SWARMER(0, 0.6F, 0.2F),
    WEAK_SOLDIER(3, 1.2F, 0.25F),
    SOLDIER(5, 1.7F, 0.25F),
    STRONG_SOLDIER(6, 2.0F, 0.25F),
    WEAK_TANK(8, 3.5F, 0.75F),
    TANK(12, 6.0F, 1.0F),
    MIDDLE_ENEMY(8, 2.5F, 0.35F),
    STRONG_ENEMY(12, 4.5F, 0.5F),
    ELITE_ENEMY(20, 9.0F, 1.0F),
    MINI_BOSS(50, 18.0F, 1.0F),

    SPECIAL_MOB(0, 0.0F, 0.0F);

    private final int xpValue;
    private final float middleMoneyPerMob;
    private final float dropChance;

    EnumMobRole(int xpValue, float middleMoneyPerMob, float dropChance) {
        this.xpValue = xpValue;
        this.middleMoneyPerMob = middleMoneyPerMob;
        this.dropChance = dropChance;
    }

    public int getXpValue() {
        return this.xpValue;
    }

    public void setMoneyValue(AbstractMob mob, int dimensionNumber) {
        if (this == SPECIAL_MOB) {
            return;
        }

        float dimensionRaise = 0.9F;
        float multiplier = 1.0F + dimensionRaise * dimensionNumber;

        float middleDropped = this.middleMoneyPerMob * multiplier / this.dropChance;
        float randomizeDropped = Math.max(middleDropped * 0.4F, 6.0F);

        int min = (int) (middleDropped - randomizeDropped);
        int max = (int) (middleDropped + randomizeDropped);

        mob.setDropMoney(min, max, this.dropChance);
    }
}