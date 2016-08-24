package Entity.States;

import Entity.Buffs.Buff;
import Entity.Buffs.Buffable;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Health implements Serializable, Buffable {

    //health
    private double base = 2;
    private int health;
    private int maxHealth;
    private double basicMaxHealth;

    //health regen
    private double basicRegen;
    private double regen;
    private double regened;

    public boolean dead;
    transient private BufferedImage tileset;

    //image
    private int width;
    private int height;

    public Health(int size){
        basicMaxHealth = size;
        maxHealth = (int) basicMaxHealth;
        health = maxHealth;
        dead=false;
        try {
            tileset = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/health.gif"));
        }catch(Exception e){
            e.printStackTrace();
        }
        width = (int) (tileset.getWidth() * GamePanel.SCALE);
        height = (int) (tileset.getHeight() * GamePanel.SCALE);
        basicRegen = 0;
    }

    public void update(long delta) {
        if (health < maxHealth) {
            regened += regen * (double) delta / 1000000000d;
            if (regened >= 1) {
                regened -= 1;
                heal(1);
            }
        }
    }

    public void atacked(int power){
        health-=power;
        if (health<=0){
            setDead();
        }
    }

    public void heal(int size){
        if (health<maxHealth){
            health+=size;
        }
        if (health>maxHealth){
            health = maxHealth;
        }
    }

    public int getHealth(){return health;}

    public double getMaxHealth() {
        return maxHealth;
    }

    public void draw(Graphics2D g){
        if (maxHealth < 10) {
            for (int i = 0; i < health; i++) {
                g.drawImage(tileset, (int) ((10 + i * 55) * GamePanel.SCALE), (int) (10 * GamePanel.SCALE), width, height, null);
            }
        } else {
            g.setColor(new Color(200, 50, 50));
            double percent = (double) health / maxHealth;
            g.fillRect((int) (25 * GamePanel.SCALE), (int) (25 * GamePanel.SCALE), (int) (300 * percent * GamePanel.SCALE), (int) (30 * GamePanel.SCALE));
            g.setColor(new Color(200, 200, 200));
            g.setFont(new Font("Courier New", Font.PLAIN, (int) (14 * GamePanel.SCALE)));
            g.drawString(String.format("%d / %4d", health, maxHealth), (int) (60 * GamePanel.SCALE), (int) (44 * GamePanel.SCALE));
        }
    }


    private void setDead() {
        dead = true;
        health = 0;
    }

    public void setAlive() {
        dead = false;
        health = maxHealth;
    }

    public void levelUp(double i) {
        base += i;
    }

    @Override
    public void addBuff(Buff b) {
        basicMaxHealth = (base + b.getBuff(Buff.VIT));
        maxHealth = (int) newValue(basicMaxHealth, Buff.HEALTH, b);
        basicRegen = b.getBuff(Buff.VIT) / 20d;
        regen = newValue(basicRegen, Buff.HEALTH_REGEN, b);
    }
}

