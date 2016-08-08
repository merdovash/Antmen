package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Item extends MapObject {


    protected String adress = "/Items/";
    protected BufferedImage sprite;
    protected int ID;

    public Item(TileMap tm) {
        super(tm);
    }
    public abstract void init();
    public abstract void update();
    public void draw(Graphics2D g){

    }

    int getID() {
        return ID;
    }
}
