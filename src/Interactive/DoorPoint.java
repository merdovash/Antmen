package Interactive;

import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class DoorPoint extends Place {

    private int px;
    private int py;
    private String nextLevel;

    public DoorPoint(TileMap tm, int x, int y, int px, int py, String nextLevel) {
        super(tm, x, y);
        this.px = (int) (px * GamePanel.SCALE);
        this.py = (int) (py * GamePanel.SCALE);
        this.nextLevel = nextLevel;
        width = 90;
        height = 150;
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        super.draw(g);
    }

}
