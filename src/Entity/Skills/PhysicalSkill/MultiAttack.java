package Entity.Skills.PhysicalSkill;

import Entity.Battle.Attack;
import Entity.Players.Stats;
import Entity.Skills.Skill;

import java.awt.*;

public class MultiAttack extends Skill {
    private int defaultPower;
    private int dmg;
    private int hits;
    private boolean hit;

    //animation
    private long start;


    public MultiAttack(int level) {
        this.level = level;
        hits = 3 + level;
        dmg = 2 * level - 4;
        defaultPower = 15 + level;
        active = false;
        cooldown = 5 + level;
        addres = "/Skills/MultiAttack/ico.gif";
        loadico();
    }

    @Override
    public void start(Stats s) {
        start = System.currentTimeMillis();
        active = true;
        attack = new Attack(dmg, Attack.NORMAL, 1);
        attack.addBuff(s.buff);
        hit = true;
    }

    @Override
    public void update(long delta) {
        if (active) {
            if (angle < 80 && hits > 0) {
                System.out.println("angle add " + (double) delta / 10000000 + " so " + angle);
                angle += (double) delta / 10000000;
                power = defaultPower / 10;
            } else if (angle >= 80) {
                angle = 40;
                hits -= 1;
                hit = true;
            } else if (hits == 0) {
                active = false;
                angle = 0;
            }
            if (hits == 1) {
                power = defaultPower;
            }
        }
        animation = (int) angle;
    }

    @Override
    public void draw(Graphics2D g) {

    }

    public Attack getAttack() {
        if (hit) {
            hit = false;
            return attack;
        } else {
            return null;
        }
    }
}
