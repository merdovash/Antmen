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

    private int strGrow;
    private int intallGrow;
    private int dexGrow;

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
        return new int[]{str.getValue(), intel.getValue(), dex.getValue()};
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
}
