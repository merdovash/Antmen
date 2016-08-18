package Entity;

import Entity.Items.Armor.GrabPoint;
import Entity.Players.Inventory;
import Entity.States.Energy;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;


public abstract class ActiveMapObject extends MapObject {

    public Health health;
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

    //inventory
    public Inventory inventory;

    //points
    protected GrabPoint headPoint;


    protected ActiveMapObject(TileMap tm) {
        super(tm);

        speedsX.add(0d); //right
        speedsX.add(0d); //left
        speedsX.add(0d); //punch

        speedsY.add(0d); //grivity
        speedsY.add(0d); //jump
        speedsY.add(0d); //jump square

        gravity = 24 * GamePanel.SCALE;
        health = new Health(10);

    }

    protected boolean punched = false;
    protected long lastTime;


    public void setLastTime(long l) {
        lastTime = l;
    }

    private void calculateDX() {
        double ms = delta / 100000000d;
        if (!punched) {
            if (left) {
                if (boost && !energy.isEmpty()) {
                    energy.consump(delta);
                    speedsX.set(0, -moveSpeed * boostSpeed);

                } else {
                    speedsX.set(0, -moveSpeed);
                }
                speedsX.set(1, 0d);

            } else if (right) {
                if (boost && !energy.isEmpty()) {
                    energy.consump(delta);
                    speedsX.set(1, moveSpeed * boostSpeed);


                } else {
                    speedsX.set(1, moveSpeed);
                }
                speedsX.set(0, 0d);
            } else {
                if (speedsX.get(0) != 0) {
                    speedsX.set(0, 0d);
                } else if (speedsX.get(1) != 0) {
                    speedsX.set(1, 0d);
                }
            }
        } else {
            if (speedsX.get(2) != 0) {
                speedsX.set(0, 0d);
                speedsX.set(1, 0d);
                speedsX.set(2, speedsX.get(2) - weight * ms * speedsX.get(2) / Math.abs(speedsX.get(2)));
                if (Math.abs(speedsX.get(2)) < 0.5 * weight) {
                    speedsX.set(2, 0d);
                }
            } else {
                punched = false;
            }


        }
        // movement
        int temp = 0;
        for (Double SpeedX : speedsX) {
            temp += SpeedX;
        }
        dx = temp * ms;
    }


    private void calculateDY() {
        double ms = delta / 100000000d;

        boolean save = jumper;

        // falling
        if (falling) {

            speedsY.set(0, speedsY.get(0) + gravity * ms);
            if (speedsY.get(0) > -speedsY.get(1) || speedsY.get(0) > -speedsY.get(2)) {
                pik = true;
            }

        } else {
            gravityDown = speedsY.get(0) + speedsY.get(1) + speedsY.get(2);
            for (int i = 0; i < speedsY.size(); i++) {
                speedsY.set(i, 0d);
                pik = false;
            }
            if (!save) {
                int damage = (int) (gravityDown / (gravity * 5.5));
                if (damage > 0) {
                    hit(damage);
                }
            }
        }

        // jumper
        if (jumper && !inAir) {
            speedsY.set(2, jumpStart * 0.8 - gravityDown / 1.8);
            inAir = true;
        }

        // jumping
        if (jumping && !falling) {
            speedsY.set(1, jumpStart);
            inAir = true;
            jumping = false;
        }

        if (inAir) {
            falling = true;
            jumping = false;
            jumper = false;
        }

        double temp = 0;
        for (Double SpeedY : speedsY) {
            temp += SpeedY;
        }
        dy = temp * ms;
    }

    private void getNextPosition() {

        // movement
        calculateDX();

        calculateDY();
    }

    public void hit(int damage) {
        if (flinching) return;
        health.atacked(damage);
        flinching = true;
        flinchTimer = System.nanoTime();
        if (health.getHealth() == 0) {
            dead = true;
        }
    }

    private void checkFlinching() {
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }
    }

    public void update() {
        //time
        delta = System.nanoTime() - lastTime;
        lastTime = System.nanoTime();

        //is dead
        if (!(health.getHealth() > 0)) {
            dead = true;
        }


        //moving
        getNextPosition();

        try {
            checkTileMapCollision();
        } catch (ArrayIndexOutOfBoundsException e) {
            dead = true;

        }
        if (ytemp > (TileMap.getRows() * 50 * GamePanel.SCALE) - 5) {
            dead = true;
        } else {
            setPosition(xtemp, ytemp);
        }


        //check flinching
        checkFlinching();

        animation.update();

        rectangle = getRectangle();

    }


    public void draw(Graphics2D g) {

        setMapPosition();
        super.draw(g);

    }

    public void drawBorder(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.draw(rectangle);
    }

    protected void drawArmory(Graphics2D g) {
        if (inventory != null) {
            inventory.draw(g, headPoint);
        }
    }
}



