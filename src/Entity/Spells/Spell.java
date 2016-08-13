package Entity.Spells;

import Entity.MapObject;
import TileMap.TileMap;

/**
 * Created by MERDovashkinar on 8/2/2016.
 */
public abstract class Spell extends MapObject {

    protected final static int FLY = 0;
    protected final static int HIT = 1;

    protected boolean hit;
    protected boolean remove;


    //parameters
    protected int level;
    protected double size;
    public int cooldown;
    public int manacost;
    protected int damage;
    protected int power;

    public Spell(TileMap tm ,boolean right) {
        super(tm);
    }

    public abstract void update();
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
        animation.setFrames(sprites.get(HIT));
        animation.setDelay(70);
        dx=0;
    }

    public boolean isHit(){
        return hit;
    }

    public int getPower(){return power;}

}
