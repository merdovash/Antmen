package Entity.Players;

import Entity.States.Energy;
import Entity.States.Health;
import Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Stats {


    public static final int SPEED = 0;
    public static final int BOOST_SPEED = 1;
    public static final int SPELL_DAMAGE = 2;
    public static final int SPELL_SPEED = 3;
    public static final int SPELL_POWER = 4;
    public static final int ATTACK = 5;
    public static final int ENERGY_CONSUMP = 6;
    public static final int STR = 7;
    public static final int INT = 8;
    public static final int DEX = 9;
    public static final int VIT = 10;
    public static final int AGI = 11;
    public static final int SPK = 12;
    public static final int HEALTH = 13;
    public static final int HEALTH_REGEN = 14;
    public static final int MANA = 15;
    public static final int MANA_REGEN = 16;
    public static final int MANA_CONSUMP = 17;
    public static final int ENERGY = 18;
    public static final int ENERGY_REFILL = 19;

    //elements
    public static final int WEAPON = 0;
    public static final int ARMOR = 1;

    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;



    //health
    protected Health health;

    //energy
    protected Energy mana;
    protected Energy energy;

    //level
    private int level;
    private long experience;
    private long nextLevel;
    private boolean congats;


    //stats
    private Param str;
    private Param intel;
    private Param dex;
    private Param vit;
    private Param agi;
    private Param spk;

    private final int[] grow;
    private int freePoints;

    private double boostSpeed;

    private double speed;

    private Inventory inventory;

    public Stats() {
        health = new Health(3);
        level = 1;
        experience = 0;
        nextLevel = 100;

        freePoints = 0;

        str = new Param(1);
        intel = new Param(5);
        dex = new Param(1);
        vit = new Param(2);
        agi = new Param(1);
        spk = new Param(3);

        grow = new int[]{3, 2, 1, 1, 1, 1};


        energy = new Energy(100);
        energy.setConsumption(4);
        energy.setRefillSpeed(1);

        mana = new Energy(100);
        mana.setRefillSpeed(1);

        speed = 20 * GamePanel.SCALE;
        boostSpeed = speed * 2.5;

        modifier = new double[20];
        Arrays.fill(modifier, 1d);
        calculateModifier();

        elementModifier = new double[2][7];
    }

    public void setInventory(Inventory i) {
        inventory = i;
    }

    public void addExp(int exp) {
        experience += exp;
        if (experience >= nextLevel) {
            congats = true;
            levelUp();
            experience = (experience - nextLevel);
            nextLevel += Math.pow((level * 5 + 2), 2);
        }
    }



    private void levelUp() {
        level++;
        freePoints += 4 + level / 20;
        health.extendAbs(0.5);
        health.heal((int) (health.getMaxHealth()));
    }

    public long getExp() {
        return experience;
    }

    public boolean getCongats() {
        if (congats) {
            congats = false;
            return true;
        }
        return false;
    }

    public int[] getStats() {
        return new int[]{str.getValue(), intel.getValue(), dex.getValue(), vit.getValue(), agi.getValue(), spk.getValue()};
    }

    public void update(long delta) {
        mana.refill((long) (delta * modifier[MANA_REGEN]));
        calculateElementModifier();
    }


    void drawExp(Graphics2D g) {
        double proc = (experience * GamePanel.WIDTH / nextLevel);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, GamePanel.HEIGHT - 10, GamePanel.WIDTH, 10);
        g.setColor(Color.GREEN);
        g.fillRect(0, GamePanel.HEIGHT - 10, (int) proc, 10);
    }

    public void draw(Graphics2D g) {
        mana.draw(g, 1, new Color(50, 50, 100));
        energy.draw(g, 0, new Color(50, 100, 50));
        health.draw(g);

        drawExp(g);
    }


    //Modifiers;
    private double[] modifier;

    public void calculateModifier() {
        modifier[SPELL_DAMAGE] = 1 + (double) intel.getValue() / 100d;
        modifier[SPELL_SPEED] = 1 + (double) dex.getValue() / 150d;
        modifier[MANA_REGEN] = 1 + (double) intel.getValue() / 100d;
        modifier[MANA] = 1 + (double) intel.getValue() / 50d + (double) vit.getValue() / 100d;
        modifier[ENERGY] = 1 + (double) vit.getValue() / 50d;
        modifier[HEALTH] = 1 + (double) vit.getValue() / 50d + (double) str.getValue() / 200d;
        modifier[ENERGY_REFILL] = 1 + (double) dex.getValue() / 100d;
        modifier[ATTACK] = 1 + (double) str.getValue() / 100d + (double) dex.getValue() / 500d;
        modifier[SPEED] = 1 + (double) agi.getValue() / 100d + (double) dex.getValue() / 700d;
        modifier[BOOST_SPEED] = 1 + (double) agi.getValue() / 250d;
        modifier[ENERGY_CONSUMP] = 1 + (double) vit.getValue() / 100d + (double) dex.getValue() / 500d;
        System.out.println(Arrays.toString(modifier));
    }

    public double getModifier(int modifier) {
        return this.modifier[modifier];
    }

    private double[][] elementModifier;

    public void calculateElementModifier() {

    }

    public double getElementModifier(int equipment, int element) {
        return elementModifier[equipment][element];
    }

    private ArrayList<Double> boostSpeedIncrease = new ArrayList<>();

    public void extendBoostSpeed(double boostSpeed) {
        boostSpeedIncrease.add(boostSpeed);
    }

    public void removeBoostSpeed(double boostSpeed) {
        boostSpeedIncrease.remove(boostSpeedIncrease.indexOf(boostSpeed));
    }

    private ArrayList<Double> SpeedIncrease = new ArrayList<>();

    public void extendSpeed(double Speed) {
        SpeedIncrease.add(Speed);
    }

    public void removeSpeed(double Speed) {
        SpeedIncrease.remove(SpeedIncrease.indexOf(Speed));
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getFreePoints() {
        return freePoints;
    }

    public int getLevel() {
        return level;
    }

    public void useFreePoints(int i) {
        this.freePoints += i;
    }

    public Param getStat(int place) {
        switch (place) {
            case 0:
                return str;
            case 1:
                return intel;
            case 2:
                return dex;
            case 3:
                return vit;
            case 4:
                return agi;
            case 5:
                return spk;
            default:
                return null;
        }
    }

    public double getBoostSpeed() {
        double temp = 0;
        for (int i = 0; i < boostSpeedIncrease.size(); i++) {
            temp += boostSpeedIncrease.get(i);
        }
        return boostSpeed * (1 + temp);
    }

    public double getSpeed() {
        return speed;
    }

}
