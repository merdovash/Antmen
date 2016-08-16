package Entity.Enemies;

import Entity.Animation;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

public class Spider extends Enemy {

    public Spider(TileMap tm) {
        super(tm);

        adressImage = "/Sprites/Enemies/spider.gif";
        width = 300;
        height=150;

        //loadSprites(1);

        moveSpeed = 25* GamePanel.SCALE;



        health = new Health(10);
        damage = 2;
        weight=15;

        agressive=true;
        visionX=600;
        visionY=200;


        animation = new Animation();
        animation.setFrames(sprites.get(0));
        animation.setDelay(300);

        right= true;
        left = false;
        lastTime=System.nanoTime();

        fallable=false;

        //add loot
        loot = new DropList();
        loot.add(1d,1);
    }

    public void update(TileMap tm){
        super.update();

        tileMap=tm;

    }




}
