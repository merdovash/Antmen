package Entity.Enemies;


import Entity.ActiveMapObject;
import Entity.Players.Inventory;
import TileMap.TileMap;



public abstract class Enemy extends ActiveMapObject {

    protected int damage;

    protected int exp;

    protected boolean agressive;
    public int visionX;
    public int visionY;

    protected boolean enemy;

    //loot
    public DropList loot;
    protected Inventory inventory;

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

    public void setAgressive(boolean b) {
        enemy=b;
    }

    public abstract void update(TileMap tilemap);

}
