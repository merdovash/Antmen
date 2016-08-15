package Interactive;

import TileMap.TileMap;

import java.awt.*;

public class SavePoint extends Place {

    public SavePoint(TileMap tm, int x, int y) {
        super(tm, x, y);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        super.draw(g);
    }
}
