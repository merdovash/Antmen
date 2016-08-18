package Entity.States;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Health {
    private int health;
    private double maxHealth;
    private double deffaultMaxHealth;
    private double x;
    public boolean dead;
    private BufferedImage tileset;

    //image
    private int width;
    private int height;

    public Health(int size){
        deffaultMaxHealth=size;
        maxHealth=deffaultMaxHealth;
        health = (int) maxHealth;
        dead=false;
        x=1;
        try {
            tileset = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/health.gif"));
        }catch(Exception e){
            e.printStackTrace();
        }
        width = (int) (tileset.getWidth() * GamePanel.SCALE);
        height = (int) (tileset.getHeight() * GamePanel.SCALE);

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
            health = (int) maxHealth;
        }
    }

    double permaHeal;

    public void heal(double healthRegen) {
        if (health == maxHealth) {
        } else if (health < maxHealth) {
            permaHeal += healthRegen;
            if (permaHeal >= 1) {
                health++;
                permaHeal -= 1;
            }
        } else if (health > maxHealth) {
            health = (int) maxHealth;
        }

    }

    public void extend(int size){
        maxHealth+=size;
    }

    public void extendBy(double size){
        x+=size;
        maxHealth= (int) (deffaultMaxHealth*x);
    }

    public void extendAbs(double size) {
        deffaultMaxHealth += size;
        maxHealth = deffaultMaxHealth;
    }

    public int getHealth(){return health;}

    public double getMaxHealth() {
        return maxHealth;
    }

    public void draw(Graphics2D g){
        for (int i=0;i<health;i++){
            g.drawImage(tileset, (int) ((10 + i * 55) * GamePanel.SCALE), (int) (10 * GamePanel.SCALE), width, height, null);
        }
    }

    public void setDead() {
        dead = true;
        health = 0;
    }

    public void setAlive() {
        dead = false;
        health = (int) maxHealth;
    }


}

