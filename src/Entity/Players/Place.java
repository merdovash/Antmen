package Entity.Players;

import Entity.MapObject;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class Place extends MapObject {

    private int id;

    public Place(TileMap tm, int x, int y) {
        super(tm);
        this.x = x * GamePanel.SCALE;
        this.y = y * GamePanel.SCALE;
        width = 82;
        height = 136;
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        rectangle = getRectangle();
        if (id == 0) {
            g.setColor(Color.MAGENTA);
        } else {
            g.setColor(Color.YELLOW);
        }
        g.draw(rectangle);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
