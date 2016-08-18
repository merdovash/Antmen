package Entity.Items.Armor.Headset;

import Entity.Items.Armor.Boosts.HealthRegen.HealthRegen;

public class Helmet extends Headset implements HealthRegen {

    static {

    }

    double heal;

    public Helmet() {
        adress = "/Items/Armory/Headset/helmet.gif";
        ID = 2;
        setSpeed(10000);
        super.init();
    }

    @Override
    public void getBuffs() {
        heal = getHealthRegen();
    }
}
