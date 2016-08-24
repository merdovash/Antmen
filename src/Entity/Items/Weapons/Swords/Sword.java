package Entity.Items.Weapons.Swords;

import Entity.Battle.Attack;
import Entity.Buffs.Buff;
import Entity.Buffs.WeaponModifier;
import Main.GamePanel;

public class Sword extends Swords {

    public Sword() {
        adress = "/Items/Weapons/Swords/sword.gif";
        init();
        ID = 3;
        wp = new WeaponModifier(2, Attack.FIRE, 0.5);
        width = (int) (19 * GamePanel.SCALE);
        height = (int) (96 * GamePanel.SCALE);
        power = 25;
    }

    public Object[][] getBuff() {
        return new Object[][]{
                {true, buffType, Buff.ATTACK_DMG, -1, 1},
                {false, buffType, Buff.ATTACK_DMG, -1, 0.1d},
        };
    }

}
