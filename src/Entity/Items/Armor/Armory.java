package Entity.Items.Armor;

import Entity.Items.Item;

public abstract class Armory extends Item {

    protected static int def;


    public void init() {
        super.init();
    }

    public int getDef() {
        return def;
    }


}
