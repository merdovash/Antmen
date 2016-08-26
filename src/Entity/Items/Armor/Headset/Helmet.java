package Entity.Items.Armor.Headset;


import Entity.Buffs.Buff;

public class Helmet extends Headset {

    public Helmet() {
        adress = "/Items/Armory/Headset/helmet.gif";
        ID = 2;
        super.init();
    }

    @Override
    public Object[][] getBuff() {
        return new Object[][]{
                {true, Buff.HEADSET, Buff.HEALTH, -1, 2},
                {false, Buff.HEADSET, Buff.ATTACK_SPEED, -1, 1d}
        };
    }

    @Override
    public long[] trajectory(long X) {
        return new long[0];
    }
}
