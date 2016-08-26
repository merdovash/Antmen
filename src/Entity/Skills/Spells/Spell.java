package Entity.Skills.Spells;

import Entity.Battle.Attack;
import Entity.MapObject;
import Entity.Skills.SpellManageable;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.image.BufferedImage;

public abstract class Spell extends MapObject implements SpellManageable {

    protected final static int FLY = 0;
    protected final static int HIT = 1;

    protected boolean hit;
    protected boolean remove;
    private boolean active;
    protected int type;

    //parameters
    protected int level;
    protected double size;
    protected int cooldown = 0;
    public int manacost;
    protected int damage;
    protected int power;

    //ico
    protected BufferedImage ico;

    protected Attack attack;

    public Spell() {
        super(null);
        active = true;
        this.facingRight = right;
    }

    public void start(TileMap tm, boolean right) {
        super.init(tm, right);
    }

    public abstract void update();

    public boolean checkRemove(){
        return remove;
    }

    public void setPosition(double x, double y, int height) {
        this.x = x;
        this.y = y - height * 0.20 * GamePanel.SCALE;
    }

    public void setHit(){
        hit=true;
        animation.setFrames(sprites.get(HIT));
        animation.setDelay(70);
        dx=0;
        active = false;
    }

    public long getCooldown() {
        return cooldown;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isActive() {
        return active;
    }

    public int getPower() {
        return power;
    }

    public Attack getAttack() {
        return attack;
    }

    public int getType() {
        return type;
    }

    public int getManacost() {
        return manacost;
    }

    public BufferedImage getIco() {
        return ico;
    }

    public boolean isSkill() {
        return false;
    }

    public abstract Object[][] getBuff();

    public void setCooldown(double value) {
        cooldown = (int) Math.ceil(value);
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public void setManacost(double manacost) {
        this.manacost = (int) Math.ceil(manacost);
    }
}
