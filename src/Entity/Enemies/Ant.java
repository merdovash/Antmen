package Entity.Enemies;

import Entity.Animation;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class Ant extends Enemy {

    private Vision v;

    public Ant(TileMap tm) {

        super(tm);

        adress = "/Sprites/Enemies/ant.gif";
        width = 71;
        height=135;

        loadSprites(3);

        moveSpeed = 15*GamePanel.SCALE;



        health = new Health(6);
        damage = 1;
        weight=5;

        agressive=true;
        visionX=400;
        visionY=100;


        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right= true;
        left = false;
        lastTime=System.nanoTime();

        fallable=true;

        //add loot
        loot = new DropList();
        loot.add(1d,1);

        //v=new Vision(400,100);

    }

    public void update(TileMap tm){
        super.update();

        this.tileMap=tm;
        //update position

        if(dx==0){
            if (!enemy){
                if (right){
                    setLeft();
                }else{
                    setRight();
                }
            }
        }

        //change direction
        facingRight = right;

        //v.update((int)x+width/2,(int)y-height+25,tileMap);

    }

    public void draw(Graphics2D g){
        setMapPosition();

        super.draw(g);
        //v.draw(g);
    }


}
