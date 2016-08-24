package Entity;

import Entity.Battle.Attack;
import Entity.Battle.Battle;
import Entity.Battle.Defence;
import Entity.Items.GrabPoint;
import Entity.Players.Inventory;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;


public abstract class ActiveMapObject extends MapObject {

    public Health health;
    protected boolean dead;
    protected int weight;
    protected boolean flinching;
    protected long flinchTimer;

    // boost
    protected boolean boost;

    // time
    protected long delta;

    //inventory
    public Inventory inventory;

    //points
    protected GrabPoint headPoint;
    protected GrabPoint weaponPoint;


    protected long atackAnimation;
    protected long lastHit;

    protected Defence defence;
    protected Attack attack;


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

        drawDmg = new ArrayList<>();


        defence = new Defence();
        attack = new Attack(Attack.TYPE_ENCHANCED);

    }

    protected boolean punched = false;
    protected long lastTime;


    public void setLastTime(long l) {
        lastTime = l;
    }

    private void calculateDX() {
        if (AI) {
            fallable = speedsX.get(2) == 0;
        }

        double ms = delta / 100000000d;
        if (!punched) {
            if (left) {
                speedsX.set(0, -moveSpeed);
                speedsX.set(1, 0d);
            } else if (right) {
                speedsX.set(1, moveSpeed);
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
        drawDmg(g);

    }

    private void drawDmg(Graphics2D g) {
        g.setFont(new Font("Courier New", Font.PLAIN, (int) (width / 3 * scale)));
        for (int i = 0; i < drawDmg.size(); i++) {
            g.setColor(new Color(1, 1, 1, (float) (1 - drawDmg.get(i)[1])));
            g.drawString(String.format("%5d", (int) drawDmg.get(i)[0]), (int) (x + xmap + width / 2 * drawDmg.get(i)[1] * scale), (int) (y + ymap - scale * (height + width / 2 * Math.sqrt(drawDmg.get(i)[1]))));
            drawDmg.get(i)[1] += (double) delta / 300000000;
            if (drawDmg.get(i)[1] >= 1) {
                drawDmg.remove(i);
                i--;
            }
        }
    }

    public void drawBorder(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.draw(rectangle);
    }

    protected void drawArmory(Graphics2D g, long angle) {
        if (inventory != null) {
            inventory.drawHelm(g, headPoint);
            inventory.drawWeapon(g, weaponPoint, angle);
        }
    }

    private static final int ARMOR = 0;
    private static final int HEADSET = 1;
    private static final int LEGS = 2;
    private static final int ARMS = 3;
    private static final int BUFFS = 4;

    private ArrayList<Double[]> resistances;

    protected double[] getResistance() {
        resistances = new ArrayList<>();
        resistances.set(ARMOR, inventory.getArmor().getResistance());
        resistances.set(HEADSET, inventory.getHelm().getResistance());
        resistances.set(LEGS, inventory.getLegs().getResistance());
        resistances.set(ARMS, inventory.getArms().getResistance());
        //resistance.set(BUFFS, stats.getResistance());
        double[] resistance = new double[]{0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            for (int place = 0; place < 5; place++) {
                resistance[i] += resistances.get(place)[i];
            }
        }
        return resistance;
    }


    protected ArrayList<double[]> drawDmg;

    public void hit(Attack a) {
        Battle battle = new Battle();
        if (a != null) {
            int dmg = battle.calculateDmg(a, defence);
            health.atacked(dmg);
            drawDmg.add(new double[]{dmg, 0});
            lastHit = System.currentTimeMillis();
        }
    }
}



