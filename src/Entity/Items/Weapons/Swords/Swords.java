package Entity.Items.Weapons.Swords;

import Entity.Items.Weapons.Weapons;
import Main.GamePanel;

abstract class Swords extends Weapons {

    public void init() {
        speed = 10000;
        super.init();
        range = (int) (60 * GamePanel.SCALE);
        weaponType = "sword";
    }
}
