package Entity.Battle;

import Entity.Items.Item;

public class Defence {
    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;

    private int deffence;

    private int element;
    private double elementPower;

    public Defence() {
        elementalDef = new double[7];
        element = NORMAL;
        deffence = 0;
    }

    private double[] elementalDef;

    public void addArmor(Item i) {
        if (i.getType().equals("armor")) {
            element = (int) i.getElement()[0];
            elementPower = i.getElement()[1];
        }
        for (int j = 0; j < 7; j++) {
            elementalDef[j] += i.getElementDef()[j];
            deffence += i.getDef();
        }
    }

    public void removeArmor(Item i) {
        if (i.getType().equals("armor")) {
            element = NORMAL;
            elementPower = 1;
        }
        for (int j = 0; j < 7; j++) {
            elementalDef[j] -= i.getElementDef()[j];
            deffence -= i.getDef();
        }
    }

    public double[] getResistance() {
        return elementalDef;
    }

    public int getElement() {
        return element;
    }

    public double getElementPower() {
        return elementPower;
    }

    public int getDefence() {
        return deffence;
    }
}
