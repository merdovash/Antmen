package Entity.Battle;

import Entity.Buffs.Buff;
import Entity.Buffs.Buffable;
import Entity.Items.Item;

import java.io.Serializable;

public class AttackSpeed implements Serializable, Buffable {

    private int defaultAttackSpeed;
    private int basicAttackSpeed;
    private int attackSpeed;

    private Item weapon;

    public AttackSpeed() {
        defaultAttackSpeed = 1000;
    }

    public AttackSpeed(int speed) {
        defaultAttackSpeed = speed;
    }

    public void setWeapon(Item w) {
        weapon = w;
    }

    public void update(long delta) {

    }

    @Override
    public void addBuff(Buff b) {
        if (weapon == null) {
            basicAttackSpeed = (int) (defaultAttackSpeed + b.getBuff(Buff.AGI) + b.getBuff(Buff.DEX) / 7);
        } else {
            basicAttackSpeed = (int) (weapon.getSpeed() + b.getBuff(Buff.AGI) + b.getBuff(Buff.DEX) / 7);
        }

        attackSpeed = (int) newRValue(basicAttackSpeed, Buff.ATTACK_SPEED, b);
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }
}
