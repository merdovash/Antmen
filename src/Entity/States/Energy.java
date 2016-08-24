package Entity.States;

import Entity.Buffs.Buff;
import Entity.Buffs.Buffable;
import Main.GamePanel;

import java.awt.*;
import java.io.Serializable;

public class Energy implements Serializable, Buffable {

    private double basicMaxCapacity;
    private double capacity;
    private int maxCapacity;
    private double consumption=10;
    private double basicRefillSpeed;
    private double refillSpeed=5;
    private boolean empty;

    private int type;
    public static final int ENERGY = 0;
    public static final int MAGIC = 1;




    private final Font font = new Font("Courier New", Font.PLAIN, (int) (12 * GamePanel.SCALE));

    public Energy(int capacity, int type) {
        maxCapacity = capacity;
        basicMaxCapacity = capacity;
        empty=false;
        this.type = type;

        this.capacity = maxCapacity;

        basicRefillSpeed = 5;
    }

    public void consump(long delta){
        if (!empty){
            double c=consumption*delta/100000000;
            if (capacity >= c) {
                capacity -= c;
            }
            if (capacity < c) {
                capacity = 0;
                empty=true;
            }
        }
    }

    public boolean use(int cost) {
        if (capacity >= cost) {
            capacity -= cost;
            return true;
        }
        return false;
    }

    public void refill(long delta){
        if (capacity < maxCapacity) {
            double c= refillSpeed *delta/100000000;
            capacity += c;
            if (capacity > maxCapacity) {
                capacity = maxCapacity;
                empty=false;
            }
        }

    }

    public boolean isEmpty(){return empty;}

    public void setConsumption(int value) {consumption = value;}

    public void draw(Graphics2D g, int number,Color c){
        double percent = capacity / maxCapacity * 100;
        g.setColor(c);
        g.fillRect((int) (GamePanel.WIDTH - (350) * GamePanel.SCALE), (int) ((25 + number * 35) * GamePanel.SCALE), (int) ((int) percent * 3 * GamePanel.SCALE), (int) (30 * GamePanel.SCALE));
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(String.format("%5s / %s", (int) capacity, maxCapacity), (int) (GamePanel.WIDTH - (250) * GamePanel.SCALE), (int) ((43 + number * 35) * GamePanel.SCALE));
    }

    public void setRefillSpeed(double refillSpeed) {
        this.refillSpeed = refillSpeed;
    }

    @Override
    public void addBuff(Buff b) {
        switch (type) {
            case ENERGY:
                basicMaxCapacity = 100 + b.getBuff(Buff.VIT) / 2 + b.getBuff(Buff.AGI) / 6;
                maxCapacity = (int) newValue(basicMaxCapacity, Buff.ENERGY, b);
                basicRefillSpeed = 1 + b.getBuff(Buff.VIT) / 4 + b.getBuff(Buff.AGI) / 3;
                refillSpeed = newValue(basicRefillSpeed, Buff.ENERGY_REFILL, b);
                break;
            case MAGIC:
                basicRefillSpeed = b.getBuff(Buff.INT) / 2.5;
                refillSpeed = newValue(basicRefillSpeed, Buff.MANA_REFILL, b);
                basicMaxCapacity = 100 + b.getBuff(Buff.INT);
                maxCapacity = (int) newValue(basicMaxCapacity, Buff.MANA, b);
                break;
        }


    }

    public boolean consumpAbs(int attackPower) {
        if (capacity > attackPower) {
            capacity -= attackPower;
            return true;
        } else {
            return false;
        }
    }
}

