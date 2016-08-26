package Entity.Items.Weapons.Knifes;

import Entity.Items.Weapons.Weapons;
import Main.GamePanel;

public abstract class Knifes extends Weapons {

    public void init() {
        speed = 7000;
        super.init();
        range = (int) (30 * GamePanel.SCALE);
        weaponType = KNIFE;
    }

    public long[] trajectory(long X) {
        return new long[]{(long) (X / 2 + 25 + Math.sqrt(X * 3 + 3)), (long) (power * Math.sin((double) X / 31)), 0};
    }
}
