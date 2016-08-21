package Entity.Items.Weapons.Swords;

import Entity.Items.Weapons.Weapons;
import Main.GamePanel;

public class Swords extends Weapons {

    public void init() {
        speed = 3000000;
        super.init();
        range = (int) (60 * GamePanel.SCALE);
    }
}
