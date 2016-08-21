package Entity.Items.Weapons.Swords;

import Entity.Buffs.WeaponModifier;
import Main.GamePanel;

public class Sword extends Swords {

    public Sword() {
        adress = "/Items/Weapons/Swords/sword.gif";
        init();
        ID = 3;
        wp = new WeaponModifier(4, NORMAL, 1);
        width = (int) (19 * GamePanel.SCALE);
        height = (int) (96 * GamePanel.SCALE);
        power = 25;
    }


}
