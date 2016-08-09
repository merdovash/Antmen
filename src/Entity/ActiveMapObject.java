package Entity;

import Entity.States.Energy;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;


public abstract class ActiveMapObject extends MapObject {

    protected Health health;
    protected boolean dead;
    protected int weight;
    protected boolean flinching;
    protected long flinchTimer;

    // boost
    protected boolean boost;
    protected double boostSpeed;
    protected Energy energy;

    // time
    protected long delta;

    protected ActiveMapObject(TileMap tm) {
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

    private long punchTimer;
    protected boolean punched=false;
    protected long lastTime;

    public void setLastTime(long l){
        lastTime=l;
    }

    private void calculateDX(){
        double ms = delta/100000000d;
        if (!punched){
            if(left) {
                if (boost && !energy.isEmpty()){
                    energy.consump(delta);
                    speedX.set(0,-moveSpeed*boostSpeed);

                }else{
                    speedX.set(0,-moveSpeed);
                }
                speedX.set(1,0d);

            } else if(right) {
                if(boost && !energy.isEmpty()){
                    energy.consump(delta);
                    speedX.set(1, moveSpeed*boostSpeed) ;


                }else{
                    speedX.set(1, moveSpeed);
                }
                speedX.set(0,0d);
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
            if (speedX.get(2)!=0){
                speedX.set(0,0d);
                speedX.set(1,0d);
                speedX.set(2,speedX.get(2)-weight*ms*speedX.get(2)/Math.abs(speedX.get(2)));
                if (Math.abs(speedX.get(2))<0.5*weight){
                    speedX.set(2,0d);
                }
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

    private void calculateDY(){
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

    protected void respawn(){
        x=100;
        y=200;
        health.heal(health.getMaxHealth());
        dead=false;
    }

    protected void getNextPosition(){

        // movement
        calculateDX();

        calculateDY();
    }

    protected void checkFlinching() {
        if (flinching){
            long elapsed =(System.nanoTime()-flinchTimer)/1000000;
            if (elapsed>1000){
                flinching=false;
            }
        }
    }

    public void update(){
        //time
        delta=System.nanoTime()-lastTime;
        lastTime=System.nanoTime();

        //is dead
        if (!(health.getHealth()>0)){
            dead=true;
        }

        //moving
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching
        checkFlinching();

        animation.update();

    }

    public void draw(Graphics2D g){

        setMapPosition();
        super.draw(g);
    }
}



