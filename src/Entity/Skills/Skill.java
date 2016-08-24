package Entity.Skills;

import Entity.Battle.Attack;
import Entity.Players.Stats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Skill implements SpellManageable {
    //params
    protected int level;
    protected int power;
    protected int animation;
    protected boolean active;
    protected int cooldown;
    protected int cost;

    //ico
    private BufferedImage ico;
    protected String addres;

    public static int SPELL = 0;
    public static int SKILL = 1;

    //attack
    protected Attack attack;
    protected boolean hit;
    protected Stats stats;

    //animation
    protected long angleX;
    protected long angle;


    public abstract void start(Stats s);

    public abstract void update(long delta);

    public abstract void draw(Graphics2D g);

    protected void loadico() {
        try {
            ico = ImageIO.read(getClass().getResourceAsStream(addres));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getAttackAnimation() {
        return animation;
    }

    public boolean getActive() {
        return active;
    }

    public Attack getAttack() {
        return attack;
    }

    public BufferedImage getIco() {
        return ico;
    }

    public int getPower() {
        return power;
    }

    //public int getType(){
    //    return type;
    //}
    public boolean isSkill() {
        return true;
    }

    public long getCooldown() {
        return cooldown;
    }

    public long getAngle() {
        return angle;
    }

}
