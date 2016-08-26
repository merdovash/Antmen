package Entity.Items.Weapons.Knifes;

import Entity.Battle.Attack;
import Entity.Buffs.WeaponModifier;
import Main.GamePanel;

public class Knife extends Knifes {

    public Knife() {
        adress = "/Items/Weapons/Knifes/knife.gif";
        init();
        ID = 4;
        wp = new WeaponModifier(2, Attack.NORMAL, 1);
        width = (int) (13 * GamePanel.SCALE);
        height = (int) (40 * GamePanel.SCALE);
        power = 15;
    }


    @Override
    public Object[][] getBuff() {
        return new Object[0][];
    }
}
