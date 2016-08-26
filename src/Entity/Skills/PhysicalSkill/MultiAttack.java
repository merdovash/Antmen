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
        dmg = 2 * level - 4;
        defaultPower = 15 + level;
        active = false;
        cooldown = 0;
        addres = "/Skills/MultiAttack/ico.gif";
        cost = 20;
        ico = loadIco(addres, 50, 50);
    }

    @Override
    public void start(Stats s) {
        start = System.currentTimeMillis();
        active = true;
        attack = new Attack(dmg, Attack.NORMAL, 1);
        attack.addBuff(s.buff);
        hits = 1 + level;
        hit = true;
        positionY = new long[]{0, 0, 0};
    }

    @Override
    public void update(long delta) {
        if (active) {
            try {
                if (positionY[0] < 80 && hits > 0) {
                    System.out.println("positionY add " + (double) delta / 2000000 + " so " + positionY[0]);
                    positionY[0] += (double) delta / 2000000;
                    power = defaultPower / 10;
                } else if (positionY[0] >= 80) {
                    positionY[0] = 40;
                    hits -= 1;
                    hit = true;
                } else if (hits == 0) {
                    active = false;
                    positionY[0] = 0;
                }
                if (hits == 1) {
                    power = defaultPower;
                }
                animation = (int) positionY[0];
            } catch (NullPointerException e) {
                System.out.println("da ebaaaat");
            }
        }
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
