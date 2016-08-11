package Entity;

import Entity.Players.Player;
import TileMap.TileMap;

/**
 * Created by MERDovashkinar on 8/1/2016.
 */
public abstract class Enemy extends ActiveMapObject {

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;

    protected boolean agressive;
    protected int visionX;
    protected int visionY;

    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tm) {
        super(tm);
        dx = 0;
        dy = 0;
    }

    public boolean isDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) return;
        health -= damage;
        if (health <= 0) {

            health = 0;
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();

    }

    public void checkEnemy(Player player) {
        if (player.getx() < x + visionX && player.gety() < y - visionY && player.gety() > y + visionY) {
            facingRight = true;
            right = true;
        } else if (player.getx() > x - visionX && player.gety() < y - visionY && player.gety() > y + visionY) {
            facingRight = false;
            right = false;
        }
    }

    public abstract void update(TileMap tm);

}
