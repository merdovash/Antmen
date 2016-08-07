package Entity;

import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

/**
 * Created by MERDovashkinar on 8/3/2016.
 */
public abstract class ActiveMapObject extends MapObject {

    protected Health health;
    boolean dead;

    // boost
    boolean boost;
    double boostSpeed;
    Energy energy;

    // time
    long delta;

    ActiveMapObject(TileMap tm) {
        super(tm);

        speedX.add(0d); //right
        speedX.add(0d); //left
        speedX.add(0d); //punch

        speedY.add(0d); //grivity
        speedY.add(0d); //jump
        speedY.add(0d); //jump square

        fallSpeed = 24* GamePanel.SCALE;
        health = new Health(10);
    }

    long punchTimer;
    boolean punched=false;
    protected void calculateDX(){
        double ms = delta/100000000d;
        if (!punched){
            if(left) {
                if (boost && !energy.isEmpty()){
                    energy.consump(delta);
                    speedX.set(0,-moveSpeed*boostSpeed);
                }else{
                    speedX.set(0,-moveSpeed);
                }

            }
            else if(right) {
                if(boost && !energy.isEmpty()){
                    energy.consump(delta);
                    speedX.set(1, moveSpeed*boostSpeed) ;

                }else{
                    speedX.set(1, moveSpeed);
                }
            }
            else {
                if(speedX.get(0) != 0) {
                    speedX.set(0,0d);
                }
                else if(speedX.get(1) != 0) {
                    speedX.set(1,0d);
                }
            }
        }else{
            if (System.currentTimeMillis()-punchTimer<1000 || speedX.get(2)>0){
                speedX.set(0,0d);
                speedX.set(1,0d);
                speedX.set(2,speedX.get(2)-1*ms);
            }else{
                punched=false;
            }


        }
        // movement

        int temp=0;
        for (int i=0;i<speedX.size();i++){
            temp+=speedX.get(i);
        }
        dx=temp*ms;
    }

    protected void calculateDY(){
        double ms = delta/100000000d;

        boolean save=jumper;

        // falling
        if(falling) {

            speedY.set(0, speedY.get(0) + fallSpeed*ms);
            if (speedY.get(0)>-speedY.get(1) || speedY.get(0)>-speedY.get(2)){
                pik=true;
            }

        }else{
            gravityDown=speedY.get(0)+speedY.get(2);
            for (int i=0; i<speedY.size();i++){
                speedY.set(i,0d);
                pik=false;
            }
            if (!save){
                health.atacked((int) (gravityDown/130));
            }
        }

        // jumper
        if (jumper && !inAir) {
            speedY.set(2, jumpStart*1.4 - gravityDown/8);
            inAir =true;
        }

        // jumping
        if(jumping && !falling) {
            speedY.set(1, jumpStart);
            inAir = true;
            jumping = false;
        }

        if (inAir){
            falling=true;
            jumping=false;
            jumper=false;
        }

        double temp=0;
        for (int i=0;i<speedY.size();i++){
            temp+=speedY.get(i);
        }
        dy=temp*ms;
    }

    protected void move(){
        checkTileMapCollision();

    }

    protected void respawn(SpawnArea a){
        x=a.getx();
        y=a.gety();
        health.heal(health.getMaxHealth());
        dead=false;
    }

    protected void getNextPosition(){
        // movement
        calculateDX();

        calculateDY();
    }

    public void update(){
        if (!(health.getHealth()>0)){
            dead=true;
        }
    }

    public void draw(Graphics2D g){
            super.draw(g);
    }
}



