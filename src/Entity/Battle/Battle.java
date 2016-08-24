package Entity.Battle;

import java.io.Serializable;

public class Battle implements Serializable {

    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;

    public Battle() {

    }

    public int calculateDmg(Attack a, Defence d) {
        double dmg = a.getDamage();
        double elementalResistance = 1 - d.getResistance()[a.getElement()];
        double elementalWar = getElementResistance(new double[]{a.getElement(), a.getElementPower()}, new double[]{d.getElement(), d.getElementPower()});
        //double bonusDmg = 1 + a.getBuffs(d.getElement());
        int def = d.getDefence();

        System.out.println(dmg + " " + elementalResistance + " " + elementalWar + " ");
        dmg = (dmg * (1 - (double) def / 150) * elementalWar * elementalResistance);
        System.out.println(dmg);
        return (int) dmg;
    }


    private static double getElementResistance(double[] weapon, double[] armor) {
        if (weapon[0] == NORMAL) {
            if (armor[0] == NORMAL) return 1;
            if (armor[0] == FIRE) return 0.75;
            if (armor[0] == WATER) return 0.75;
            if (armor[0] == WIND) return 0.75;
            if (armor[0] == EARTH) return 0.75;
            if (armor[0] == HOLY) return 0.5;
            if (armor[0] == SHADOW) return 0.5;
        } else if (weapon[0] == FIRE) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == WATER) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == WIND) return 1;
            if (armor[0] == EARTH) return 1.5 * (1 + weapon[1]);
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == WATER) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 1.5 * (1 + weapon[1]);
            if (armor[0] == WATER) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == WIND) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == EARTH) return 1;
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == WIND) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 1;
            if (armor[0] == WATER) return 1.5 * (1 + weapon[1]);
            if (armor[0] == WIND) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == EARTH) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == EARTH) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == WATER) return 1;
            if (armor[0] == WIND) return 1.5 * (1 + weapon[1]);
            if (armor[0] == EARTH) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == HOLY) {
            if (armor[0] == NORMAL) return 1;
            if (armor[0] == FIRE) return 1;
            if (armor[0] == WATER) return 1;
            if (armor[0] == WIND) return 1;
            if (armor[0] == EARTH) return 1;
            if (armor[0] == HOLY) return 0.5 * (weapon[1] - armor[1]);
            if (armor[0] == SHADOW) return 1 * (1 * weapon[1]);
        } else if (weapon[0] == SHADOW) {
            if (armor[0] == NORMAL) return 1 * (weapon[1] - armor[1]);
            if (armor[0] == FIRE) return 1.5;
            if (armor[0] == WATER) return 1.5;
            if (armor[0] == WIND) return 1.5;
            if (armor[0] == EARTH) return 1.5;
            if (armor[0] == HOLY) return 0;
            if (armor[0] == SHADOW) return 0;
        }
        return 1;
    }
}
