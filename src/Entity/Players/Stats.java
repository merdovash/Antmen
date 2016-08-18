package Entity.Players;

import Entity.States.Health;
import Main.GamePanel;

import java.awt.*;

public class Stats {

    protected Health health;

    private int level;
    private long experience;
    private long nextLevel;
    private boolean congats;

    private Param str;
    private Param intel;
    private Param dex;
    private Param vit;

    private int strGrow;
    private int intallGrow;
    private int dexGrow;
    private int vitGrow;


    private int freePoints;

    public Stats() {
        health = new Health(3);
        level = 1;
        experience = 0;
        nextLevel = 100;

        freePoints = 0;

        str = new Param(1);
        strGrow = 1;

        intel = new Param(5);
        intallGrow = 2;

        dex = new Param(1);
        dexGrow = 1;

        vit = new Param(2);
        vitGrow = 1;

        calculateModifiers();
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
        str.addAbs(strGrow);
        dex.addAbs(dexGrow);
        intel.addAbs(intallGrow);
        freePoints += 2;
        health.extendAbs(0.5);
        health.heal((int) (health.getMaxHealth()));


        calculateModifiers();
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
        return new int[]{str.getValue(), intel.getValue(), dex.getValue(), vit.getValue()};
    }

    public void update() {

    }


    void drawExp(Graphics2D g) {
        double proc = (experience * GamePanel.WIDTH / nextLevel);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, GamePanel.HEIGHT - 10, GamePanel.WIDTH, 10);
        g.setColor(Color.GREEN);
        g.fillRect(0, GamePanel.HEIGHT - 10, (int) proc, 10);
    }

    public void draw(Graphics2D g) {

    }

    public int getLevel() {
        return level;
    }


    //Modifiers;
    private double spellDamageModifier;
    private double spellSpeedModifier;
    private double manaRefillSpeedModifier;
    private double healthExtendModifier;
    private double energyRefillSpeedModifier;
    private double punchModifier;


    public void calculateModifiers() {
        spellDamageModifier = intel.getValue() / 50d + 1;
        spellSpeedModifier = dex.getValue() / 25d + 1;
        manaRefillSpeedModifier = (intel.getValue() + dex.getValue()) / 100d + 1;
        healthExtendModifier = vit.getValue() / 25d + 1;
        energyRefillSpeedModifier = (vit.getValue() + dex.getValue()) / 100d + 1;
        punchModifier = str.getValue() / 50d + 1;

    }

    public double getSpellDamageModifier() {
        return spellDamageModifier;
    }

    public double getSpellSpeedModifier() {
        return spellSpeedModifier;
    }

    public double getManaRefillSpeedModifier() {
        return manaRefillSpeedModifier;
    }

    public double getHealthExtendModifier() {
        return healthExtendModifier;
    }

    public double getEnergyRefillSpeedModifier() {
        return energyRefillSpeedModifier;
    }

    public double getPunchModifier() {
        return punchModifier;
    }

    public int getFreePoints() {
        return freePoints;
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
            default:
                return null;
        }
    }
}
