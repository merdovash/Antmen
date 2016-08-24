package Entity.Battle;

import Entity.Buffs.Buff;
import Entity.Buffs.Buffable;
import Entity.Items.Item;

import java.io.Serializable;

public class Attack implements Serializable, Buffable {


    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;

    //Attack type
    private int type;
    public static final int TYPE_PHISICAL = 0;
    public static final int TYPE_MAGIC = 1;
    public static final int TYPE_ENCHANCED = 2;

    private double basicDamage;
    private double weaponDamage;
    private double buffDamage;
    private double statsDamage;
    private double bonusDamage;
    private double increaseDamage;
    private int element;

    private static final int WEAPON_HEAVY = 0;
    private int weaponType;

    private double[] elementalAttack;
    private double elementPower;

    public Attack(int type) {
        init();
        this.type = type;
    }

    public Attack(int damage, int element, double elementPower) {
        init();
        type = TYPE_MAGIC;
        this.basicDamage = damage;
        this.element = element;
        this.elementPower = elementPower;
    }

    private void init() {
        elementalAttack = new double[7];
    }

    public void setWeapon(Item weapon) {
        elementalAttack[(int) weapon.getElement()[0]] = weapon.getElement()[1];
        weaponDamage = weapon.getDamage();
        element = (int) weapon.getElement()[0];
        elementPower = weapon.getElement()[1];
        if (weapon.getWeaponType().equals("sword")) {
            weaponType = WEAPON_HEAVY;
        }
    }

    int getElement() {
        return element;
    }

    double getElementPower() {
        return elementPower;
    }

    public double getDamage() {
        System.out.println(basicDamage + " " + weaponDamage + " " + bonusDamage + " " + increaseDamage);
        return (basicDamage + weaponDamage + statsDamage + bonusDamage) * increaseDamage;
    }

    @Override
    public void addBuff(Buff b) {
        if (type == TYPE_MAGIC) {
            increaseDamage = b.getBuff(Buff.SPELL_DMG, 1);
            bonusDamage = b.getBuff(Buff.SPELL_DMG, 0);
            statsDamage = b.getBuff(Buff.INT) + (int) (b.getBuff(Buff.INT) / 7);
        } else if (type == TYPE_PHISICAL || type == TYPE_ENCHANCED) {
            increaseDamage = b.getBuff(Buff.ATTACK_DMG, 1);
            bonusDamage = b.getBuff(Buff.ATTACK_DMG, 0);
            if (weaponType == WEAPON_HEAVY) {
                statsDamage = b.getBuff(Buff.STR) / 1 + b.getBuff(Buff.DEX) / 7;
            }
        }
    }
}
