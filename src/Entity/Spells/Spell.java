package Entity.Spells;

import Entity.MapObject;
import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by MERDovashkinar on 8/2/2016.
 */
public abstract class Spell extends MapObject {
    public int cooldown;
    protected boolean hit;
    protected boolean remove;
    protected BufferedImage[] sprites;
    protected BufferedImage[] hitSprites;
    protected double size;
    protected int level;

    protected Rectangle rectangle;


    public int manacost;
    protected int damage;
    protected int power;

    public Spell(TileMap tm ,boolean right) {
        super(tm);
    }

    protected abstract void loadAnimation();


    //public abstract void setposition();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public boolean checkRemove(){
        return remove;
    }

    public long getCooldown(){return cooldown;}

    public int getDamage(){return damage;}


    public void setPosition(double x, double y, int height) {
        this.x = x;
        this.y = y-height*0.20;
    }

    public void setHit(){
        hit=true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx=0;
    }

    public boolean isHit(){
        return hit;
    }
    public int getPower(){return power;}

}
