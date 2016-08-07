package Entity;

import TileMap.TileMap;

import java.awt.*;

/**
 * Created by MERDovashkinar on 8/4/2016.
 */
public class SpawnArea extends MapObject {
    private TileMap tileMap;
    private boolean active =true;

    private long cooldown;
    private Rectangle rectangle;
    private long lastTime;

    public SpawnArea(TileMap tm, int x, int y, int width, int height){
        super(tm);
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
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
    public void setActive(boolean b){
        active =b;
    }
    public void setCooldown(long c){cooldown = c;}
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

    public void draw(Graphics2D g){
        setMapPosition();
        rectangle = new Rectangle((int)(x+xmap),(int)(y+ymap),width,height);
        g.setColor(Color.GREEN);
        g.draw(rectangle);
    }
}
