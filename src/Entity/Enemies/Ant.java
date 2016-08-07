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

    private long delta;
    private long lastTime;

    BufferedImage[] sprites;


    public Ant(TileMap tm) {

        super(tm);

        x=0;
        y=0;

        moveSpeed = 20*GamePanel.SCALE;

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
        lastTime=System.nanoTime();
    }




    public void checkTileMapCollision2() {

        currCol = (int)(x / (tileSize* GamePanel.SCALE));
        currRow = (int)(y / (tileSize*GamePanel.SCALE));


        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;

        calculateCorners(x, ydest);
        if(dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
            }
        }
        if(dy > 0) {
            if(bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
            }
        }
        ytemp+=dy;

        calculateCorners(xdest, y);
        if(dx < 0) {
            if(topLeft || bottomLeft) {

                dx = 0;
            }
            else {
                calculateCorners(xdest, y+1);
                if (!bottomLeft){
                    dx=0;
                }
            }
        }
        if(dx > 0) {
            if(topRight || bottomRight) {
                dx = 0;
            }
            else {
                calculateCorners(xdest, y+1);
                if (!bottomRight){
                    dx=0;
                }
            }
        }
        xtemp+=dx;

        if(!falling) {
            calculateCorners(x, ydest + 1);
            if(!bottomLeft && !bottomRight) {
                falling = true;
            }
        }

    }

    public void update(TileMap tm){
        this.tileMap=tm;
        //update position

        getNextPosition();
        System.out.println(speedX.get(0));
        checkTileMapCollision();
        System.out.println(x+ " "+xtemp);
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
        if(dx==0){
            if (!enemy){
                if (right){
                    setLeft();
                }else{
                    setRight();
                }
            }

        }


        animation.update();
    }

    public void draw(Graphics2D g){
        setMapPosition();

        //if (notOnScreen())return;
        super.draw(g);
    }


}
