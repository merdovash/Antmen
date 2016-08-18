package Entity.Items.Loot;

import Entity.Items.Item;

/**
 * Created by vlad on 09.08.16.
 */
abstract class Loot extends Item {

    public void init() {
        type = "loot";

        super.init();
    }
}
