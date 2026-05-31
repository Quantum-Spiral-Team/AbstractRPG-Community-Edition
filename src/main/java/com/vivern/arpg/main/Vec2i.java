package com.vivern.arpg.main;

import com.google.common.base.MoreObjects;
import net.minecraft.util.math.MathHelper;

import javax.annotation.concurrent.Immutable;

@Immutable
public class Vec2i implements Comparable<Vec2i> {

    public static final Vec2i NULL_VECTOR = new Vec2i(0, 0);
    private final int x;
    private final int y;

    public Vec2i(int xIn, int yIn) {
        this.x = xIn;
        this.y = yIn;
    }

    public Vec2i(double xIn, double yIn) {
        this(MathHelper.floor(xIn), MathHelper.floor(yIn));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Vec2i)) {
            return false;
        } else {
            Vec2i vec2i = (Vec2i) object;
            return this.getX() == vec2i.getX() && this.getY() == vec2i.getY();
        }
    }

    @Override
    public int hashCode() {
        return this.getY() * 31 + this.getX();
    }

    @Override
    public int compareTo(Vec2i comparedVec) {
        return this.getY() == comparedVec.getY() ? this.getX() - comparedVec.getX() : this.getY() - comparedVec.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public double getDistance(int xIn, int yIn) {
        double d0 = this.getX() - xIn;
        double d1 = this.getY() - yIn;
        return Math.sqrt(d0 * d0 + d1 * d1);
    }

    public double distanceSq(double toX, double toY) {
        double d0 = this.getX() - toX;
        double d1 = this.getY() - toY;
        return d0 * d0 + d1 * d1;
    }

    public double distanceSqToCenter(double xIn, double yIn) {
        double d0 = this.getX() + 0.5 - xIn;
        double d1 = this.getY() + 0.5 - yIn;
        return d0 * d0 + d1 * d1;
    }

    public double distanceSq(Vec2i to) {
        return this.distanceSq(to.getX(), to.getY());
    }

    public double distance(Vec2i to) {
        return this.getDistance(to.getX(), to.getY());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).toString();
    }

}
