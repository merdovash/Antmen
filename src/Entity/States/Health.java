package Entity.States;

import javax.imageio.ImageIO;
import java.awt.*;

public class Health {
    private int health;
    private int maxHealth;
    private int deffaultMaxHealth;
    private double x;
    public boolean dead;
    private Image tileset;

    public Health(int size){
        deffaultMaxHealth=size;
        maxHealth=deffaultMaxHealth;
        health=maxHealth;
        dead=false;
        x=1;
        try {
            tileset = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/health.gif"));
        }catch(Exception e){
            e.printStackTrace();
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
            health=maxHealth;
        }
    }

    public void extend(int size){
        maxHealth+=size;
    }

    public void extendBy(double size){
        x+=size;
        maxHealth= (int) (deffaultMaxHealth*x);
    }

    public int getHealth(){return health;}
    public int getMaxHealth(){return maxHealth;}

    public void draw(Graphics2D g){
        for (int i=0;i<health;i++){
            g.drawImage(tileset,10+i*55,10, null);
        }
    }

    public void setDead() {
        dead = true;
        health = 0;
    }

    public void setAlive() {
        dead = false;
        health = maxHealth;
    }
}

