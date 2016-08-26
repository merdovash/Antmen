package Entity.Items.Weapons.Swords;

import Entity.Items.Weapons.Weapons;
import Main.GamePanel;

abstract class Swords extends Weapons {

    public void init() {
        speed = 10000;
        super.init();
        range = (int) (80 * GamePanel.SCALE);
        weaponType = HEAVY;
    }

    public long[] trajectory(long X) {
        return new long[]{(long) ((X > 50 ? -100 : -50) * Math.sin(X / 16d)), 0, 0};
    }
}
