package Interactive;

import Entity.MapObject;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

abstract class Place extends MapObject {

    Place(TileMap tm, int x, int y) {
        super(tm);
        this.x = x * GamePanel.SCALE;
        this.y = y * GamePanel.SCALE;
        width = (int) (82 * GamePanel.SCALE);
        height = (int) (136 * GamePanel.SCALE);
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        rectangle = getRectangle();
        g.draw(rectangle);
    }
}
