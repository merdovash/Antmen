package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import Entity.Health;
import Main.GamePanel;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by MERDovashkinar on 8/1/2016.
 */
public class Ant extends Enemy {


    BufferedImage[] sprites;


    public Ant(TileMap tm) {

        super(tm);

        x=0;
        y=0;

        moveSpeed = 15*GamePanel.SCALE;

        width = 71;
        height=135;

        health = new Health(6);
        damage = 1;

        agressive=true;
        visionX=400;
        visionY=100;

        //load sprites
        try{
            BufferedImage spritesheets = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/ant.gif"));

            sprites = new BufferedImage[3];

            for (int i=0;i<sprites.length;i++){
                sprites[i]=spritesheets.getSubimage(i*width,0,width, height);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right= true;
        left = false;
        lastTime=System.nanoTime();

        fallable=true;
    }

    public void update(TileMap tm){
        delta=System.nanoTime()-lastTime;
        lastTime=System.nanoTime();

        this.tileMap=tm;
        //update position

        getNextPosition();
        checkTileMapCollision();

        if(dx==0){
            if (!enemy){
                if (right){
                    setLeft();
                }else{
                    setRight();
                }
            }

        }

        setPosition(xtemp,ytemp);

        //move();

        //check flinching
        if (flinching){
            long elapsed=(System.nanoTime()-flinchTimer)/1000000;
            if (elapsed>400){
                flinching=false;
            }
        }

        //change direction



        animation.update();
    }

    public void draw(Graphics2D g){
        setMapPosition();

        //if (notOnScreen())return;
        super.draw(g);
    }


}
