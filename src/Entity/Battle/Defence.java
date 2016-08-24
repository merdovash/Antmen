package Entity.Battle;

import Entity.Buffs.Buff;
import Entity.Buffs.Buffable;
import Entity.Items.Item;

import java.io.Serializable;

public class Defence implements Serializable, Buffable {

    private int def;
    private double defaultDef;
    private int basicDef;
    private int armorDef;

    private int element;
    private double elementPower;
    private double[] elementalDef;

    public Defence() {
        defaultDef = 0;
        init();
    }

    public Defence(int d) {
        defaultDef = d;
        init();
    }

    private void init() {
        elementalDef = new double[7];
        element = Battle.NORMAL;
    }

    public void addArmor(Item i) {
        if (i.getType().equals("armor")) {
            element = (int) i.getElement()[0];
            elementPower = i.getElement()[1];
        }
        for (int j = 0; j < 7; j++) {
            elementalDef[j] += i.getElementDef()[j];
            armorDef += i.getDef();
        }
    }

    public void removeArmor(Item i) {
        if (i.getType().equals("armor")) {
            element = Battle.NORMAL;
            elementPower = 1;
        }
        for (int j = 0; j < 7; j++) {
            elementalDef[j] -= i.getElementDef()[j];
            armorDef -= i.getDef();
        }
    }

    double[] getResistance() {
        return elementalDef;
    }

    int getElement() {
        return element;
    }

    double getElementPower() {
        return elementPower;
    }

    int getDefence() {
        return def;
    }

    @Override
    public void addBuff(Buff b) {
        basicDef = (int) (defaultDef + b.getBuff(Buff.AGI) / 7);
        def = (int) newValue(armorDef + basicDef, Buff.DEF, b);
    }
}
