package Entity.Enemies;


import Entity.ActiveMapObject;
import Entity.Player.Player;
import TileMap.TileMap;



public abstract class Enemy extends ActiveMapObject {

    private boolean dead;
    int damage;

    boolean agressive;
    public int visionX;
    public int visionY;

    protected boolean flinching;
    protected long flinchTimer;

    boolean enemy;

    //loot
    public DropList loot;

    Enemy(TileMap tm) {
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

    public void setAgressive(boolean b) {
        enemy=b;
    }

    public abstract void update(TileMap tilemap);

}
