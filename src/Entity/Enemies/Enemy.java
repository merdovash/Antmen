package Entity.Enemies;


import Entity.ActiveMapObject;
import TileMap.TileMap;

import java.awt.*;


public abstract class Enemy extends ActiveMapObject {

    protected int damage;

    protected int exp;

    protected boolean agressive;
    public int visionX;
    public int visionY;

    protected boolean enemy;

    //loot
    public DropList loot;

    protected Enemy(TileMap tm) {
        super(tm);
        dx=0;
        dy=0;
        x=0;
        y=0;
    }

    public boolean isDead(){return health.dead;}
    public int getDamage(){return damage;}


    public void punch(double power, int x){
        if (dead) return;
        if (x>this.x && !punched){
            speedsX.set(2, -power * scale);
            speedsY.set(1, 0d);
            punched=true;
        }else{
            speedsX.set(2, power * scale);
            speedsY.set(0, 0d);
            punched=true;
        }
    }

    public void hit(int damage) {
        health.atacked(damage);
    }

    public void setRight(){
        right=true;
        left=false;
    }

    public void setLeft(){
        left=true;
        right=false;
    }

    public int getExp() {
        return exp;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        drawHealth(g);
    }

    public void setAgressive(boolean b) {
        enemy=b;
    }

    private void drawHealth(Graphics2D g) {
        double proc = health.getHealth() / health.getMaxHealth();

        g.fillRect((int) (x + xmap), (int) (y + ymap - (height + 5) * scale), (int) (width * scale * proc), 5);
    }


}
