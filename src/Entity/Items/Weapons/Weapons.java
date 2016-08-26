package Entity.Items.Weapons;

import Entity.Buffs.Buff;
import Entity.Items.Item;

public abstract class Weapons extends Item {
    public static int HEAVY = 0;
    public static int KNIFE = 1;

    protected int range;
    protected int speed;

    protected double power;

    public void init() {
        super.init();
        type = "weapon";
        buffType = Buff.WEAPON;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return (int) wp.getDamage()[0];
    }

    public double getPower() {
        return power;
    }

    public double[] getElement() {
        return new double[]{wp.getDamage()[1], wp.getDamage()[2]};
    }

}
