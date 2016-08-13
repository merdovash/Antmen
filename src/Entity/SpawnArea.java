package Entity;

import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

/**
 * Created by MERDovashkinar on 8/4/2016.
 */
public class SpawnArea extends MapObject {
    private TileMap tileMap;
    private boolean active =true;
    private int enemyID;

    private long cooldown;
    private Rectangle rectangle;
    private long lastTime;

    public SpawnArea(TileMap tm, int x, int y, int width, int height, int enemyID){
        super(tm);
        this.x = x * GamePanel.SCALE;
        this.y = y * GamePanel.SCALE;
        this.width = (int) (width * GamePanel.SCALE);
        this.height = (int) (height * GamePanel.SCALE);
        this.enemyID = enemyID;
        cooldown = 1000;

        rectangle = new Rectangle(x,y,width,height);

        facingRight=true;
    }

    public boolean contains(MapObject o){
        return rectangle.contains(o.getRectangle());
    }

    public int getx(){return (int)(x)+5;}
    public int gety(){return (int)(y)+height-5;}
    public boolean isActive(){return active;}
    public boolean isReady(){
        if (lastTime==-1){
            lastTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis()-lastTime > cooldown){
            lastTime=-1;
            return true;
        }else{
            return  false;
        }
    }
    public int getID(){return enemyID;}

    public void setActive(boolean b){
        active =b;
    }
    public void setCooldown(long c){cooldown = c;}


    public void draw(Graphics2D g){
        setMapPosition();
        rectangle = new Rectangle((int)(x+xmap),(int)(y+ymap),width,height);
        g.setColor(Color.GREEN);
        g.draw(rectangle);
    }
}
