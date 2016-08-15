package Interactive;

import Entity.Enemies.Enemy;
import TileMap.TileMap;

import java.awt.*;

public class SpawnPoint extends Place {

    private int id;
    private boolean empty;
    private Enemy enemy;

    public SpawnPoint(TileMap tm, int x, int y, int width, int height, int id) {
        super(tm, x, y);
        this.id = id;
        this.width = width;
        this.height = height;
        empty = true;
    }

    public int getId() {
        return id;
    }

    public void setEnemy(Enemy e) {
        enemy = e;
        empty = false;
    }

    public boolean isEmpty() {
        return empty || enemy.isDead();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        super.draw(g);
    }

}
