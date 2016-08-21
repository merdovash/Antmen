package Entity.Battle;

import Entity.Items.Item;

import java.util.ArrayList;

public class Attack {


    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;

    private int damage;
    private int element;


    private double[] elementalAttack;
    private double elementPower;

    public Attack() {
        elementalAttack = new double[7];
        buff = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            buff.add(0d);
        }
    }

    public Attack(int damage, int element, double elementPower) {
        elementalAttack = new double[7];
        buff = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            buff.add(0d);
        }
        this.damage = damage;
        this.element = element;
        this.elementPower = elementPower;
    }

    public void setWeapon(Item weapon) {
        elementalAttack[(int) weapon.getElement()[0]] = weapon.getElement()[1];
        damage = weapon.getDamage();
        element = (int) weapon.getElement()[0];
        elementPower = weapon.getElement()[1];
    }

    private ArrayList<Double> buff;

    public void addBuff(ArrayList<Double> e) {
        for (int i = 0; i < 7; i++) {
            buff.set(i, buff.get(i) + e.get(i));
        }
    }

    public void removeBuff(ArrayList<Double> e) {
        for (int i = 0; i < 7; i++) {
            buff.set(i, buff.get(i) - e.get(i));
        }
    }

    public int getElement() {
        return element;
    }

    public double getElementPower() {
        return elementPower;
    }

    public int getDamage() {
        return damage;
    }

    public double getBuffs(int element) {
        return buff.get(element);
    }
}
