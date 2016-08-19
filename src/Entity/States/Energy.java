package Entity.States;

import Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 7/31/2016.
 */
public class Energy {

    private double capacity;
    private int maxCapacity;
    private double consumption=10;
    private double refillSpeed=5;
    private boolean empty;
    private double percent;
    public final Color GREEN_RED = new Color((int)(250-percent*2),(int)(50+percent*2),0);
    public final Color RED_BLUE = new Color((int)(50+percent*2),0,(int)(250-percent*2));

    //time
    private long delta;
    private long lastTime;
    private long delay;

    private ArrayList<Integer> extendMax = new ArrayList<>();


    private final Font font = new Font("Courier New", Font.PLAIN, (int) (12 * GamePanel.SCALE));

    public Energy(int capacity) {
        maxCapacity = capacity;
        this.capacity = maxCapacity;
        empty=false;
        extendMax.add(0);
        lastTime=System.currentTimeMillis();

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
            lastTime = System.currentTimeMillis();
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
        percent = capacity / maxCapacity * 100;
        g.setColor(c);
        g.fillRect((int) (GamePanel.WIDTH - (350) * GamePanel.SCALE), (int) ((25 + number * 35) * GamePanel.SCALE), (int) ((int) percent * 3 * GamePanel.SCALE), (int) (30 * GamePanel.SCALE));
        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(String.format("%5s / %s", (int) capacity, maxCapacity), (int) (GamePanel.WIDTH - (250) * GamePanel.SCALE), (int) ((43 + number * 35) * GamePanel.SCALE));
    }

    public void setRefillSpeed(double refillSpeed) {
        this.refillSpeed = refillSpeed;
    }
}

