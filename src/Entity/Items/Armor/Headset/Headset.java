package Entity.Items.Armor.Headset;

import Entity.Items.Armor.Armory;

abstract class Headset extends Armory {


    public void init() {
        type = "headset";
        weight = 64;
        super.init();
    }

}
