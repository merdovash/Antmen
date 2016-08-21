package Entity.Items.Weapons;

import Entity.Buffs.WeaponModifier;
import Entity.Items.Item;

public class Weapons extends Item {

    protected WeaponModifier wp;
    protected int range;
    protected String weaponType;
    protected int speed;

    protected double power;

    public void init() {
        super.init();
        type = "weapon";
    }

    public WeaponModifier getWp() {
        return wp;
    }

    public String getWeaponType() {
        return weaponType;
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
