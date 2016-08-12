package Entity.Players;

import Entity.MapObject;
import TileMap.TileMap;

import java.awt.*;

public class SavePoint extends MapObject {

    public SavePoint(TileMap tm, int x, int y) {
        super(tm);
        this.x = x;
        this.y = y;
        width = 82;
        height = 136;
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        rectangle = getRectangle();
        g.setColor(Color.MAGENTA);
        g.draw(rectangle);
    }
}
