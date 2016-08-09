package Entity.Items;

import Entity.MapObject;
import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Item {

    protected String adress;
    protected BufferedImage sprite;
    protected int ID;

    public Item() {

    }

    public int getID() {
        return ID;
    }
}
