package Entity.Enemies;


import Entity.ActiveMapObject;
import Entity.Player.Player;
import TileMap.TileMap;

import java.awt.image.BufferedImage;



public abstract class Enemy extends ActiveMapObject {

    private boolean dead;
    protected int damage;

    protected boolean agressive;
    public int visionX;
    public int visionY;

    protected boolean flinching;
    protected long flinchTimer;

    protected boolean enemy;

    protected BufferedImage[] sprites;

    //loot
    public DropList loot;

    public Enemy(TileMap tm) {
        super(tm);
        dx=0;
        dy=0;
    }

    public boolean isDead(){return health.dead;}
    public int getDamage(){return damage;}

    public void hit(int damage){
        if (dead) return;
        health.atacked(damage);
        if (health.getHealth()<0) {
            dead=true;
        }
        flinching =true;
        flinchTimer = System.nanoTime();

    }


    public void punch(double power, int x){
        if (dead) return;
        if (x>this.x && !punched){
            speedX.set(2,-power);
            speedY.set(1,0d);
            punched=true;
        }else{
            speedX.set(2,power);
            speedY.set(0,0d);
            punched=true;
        }
    }

    public void checkEnemy(Player player){
        if (player.getx()<x + visionX &&player.gety()<y-visionY &&player.gety()>y+visionY){
            facingRight=true;
            right=true;
        }else if(player.getx()>x - visionX &&player.gety()<y-visionY &&player.gety()>y+visionY){
            facingRight=false;
            right=false;
        }
    }

    private int range(){return (int)(Math.random()*(250-50));}

    private int range;
    private boolean decide;
    protected void direction(){
        switch((int) (Math.random() * (2))){
            case 0:
                left=true;
                right=false;
                decide = true;
                range = range();
        }
    }

    public void setRight(){
        right=true;
        left=false;
    }

    public void setLeft(){
        left=true;
        right=false;
    }

    public void setEnemy(boolean b){
        enemy=b;
    }

    public abstract void update(TileMap tm);

}
