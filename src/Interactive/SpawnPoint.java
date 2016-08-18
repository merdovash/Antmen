package Interactive;

import Entity.Enemies.Enemy;
import TileMap.TileMap;

import java.awt.*;

public class SpawnPoint extends Place {

    private int id;
    private boolean empty;
    private Enemy enemy;
    private long cooldown;



    public SpawnPoint(TileMap tm, int x, int y, int width, int height, int id) {
        super(tm, x, y);
        this.id = id;
        this.width = width;
        this.height = height;
        empty = true;
        cooldown = 3000;
    }

    public int getId() {
        return id;
    }

    public void setEnemy(Enemy e) {
        enemy = e;
        empty = false;
        checked = false;
    }

    private long dead = System.currentTimeMillis();
    private boolean checked = false;
    public boolean isEmpty() {
        if (enemy != null) {
            if (enemy.isDead() && !checked) {
                checked = true;
                dead = System.currentTimeMillis();
            }
        }
        return empty || (enemy.isDead() && (System.currentTimeMillis() - cooldown > dead));
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        super.draw(g);
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
