package Entity.Players;

import Entity.MapObject;
import Entity.SpawnArea;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Place extends MapObject {

    private int id;
    private static String nextLevel;

    public Place(TileMap tm, int x, int y) {
        super(tm);
        this.x = x * GamePanel.SCALE;
        this.y = y * GamePanel.SCALE;
        width = 82;
        height = 136;
    }

    public Place(TileMap tm, int x, int y, String NL) {

        super(tm);
        this.x = x * GamePanel.SCALE;
        this.y = y * GamePanel.SCALE;
        width = 75;
        height = 136;
        nextLevel =NL;
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        rectangle = getRectangle();
        g.draw(rectangle);
    }

    public static String getNextLevel() {
        return nextLevel;
    }
}
