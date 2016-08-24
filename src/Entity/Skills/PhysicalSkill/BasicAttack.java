package Entity.Skills.PhysicalSkill;

import Entity.Battle.Attack;
import Entity.Players.Stats;
import Entity.Skills.Attacking;
import Entity.Skills.Skill;

import java.awt.*;

public class BasicAttack extends Skill implements Attacking {

    public BasicAttack() {
        cooldown = 0;
        cost = 5;
        power = 25;
    }

    @Override
    public void start(Stats s) {
        this.stats = s;
        active = true;
        attack = s.attack;
    }

    @Override
    public void update(long delta) {
        if (active) {
            if (stats.inventory.getWeapon() != null) {
                angleX += delta / (stats.getAttackSpeed());
                angle = (long) ((angleX > 50 ? -100 : -50) * Math.sin(angleX / 16d));

                //attackPlace = new Rectangle((weaponPoint.getX() - (int)()))

                if (angleX >= 100) {
                    angleX = 0;
                    angle = 0;
                    active = false;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {

    }

    public void setHit() {
        hit = true;
    }

    public Attack getAttack() {
        if (!hit) {
            return attack;
        } else {
            return null;
        }
    }
}
