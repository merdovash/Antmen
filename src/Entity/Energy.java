package Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 7/31/2016.
 */
public class Energy {

    private double opacity;
    private int maxOpacity;
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

    private boolean rearmable=false;
    private boolean delayable=false;
    private boolean usable=false;

    Energy(int opacity){
        maxOpacity=opacity;
        this.opacity=maxOpacity;
        empty=false;
        extendMax.add(0);
        lastTime=System.currentTimeMillis();

    }

    public void consump(long delta){
        if (!empty){
            double c=consumption*delta/100000000;
            if (opacity>=c) {
                opacity -= c;
            }
            if (opacity<c){
                opacity=0;
                empty=true;
            }
        }
    }

    public boolean use(long delay, int cost){
        delta = System.currentTimeMillis() - lastTime;
        if (opacity>=cost){
            if(delta >this.delay){
                opacity-=cost;
                this.delay=delay;
                lastTime=System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }

    public void recalculate(){

    }

    public void extendBy(int value){
        extendMax.set(0,extendMax.get(0)+value);
    }

    public void extend(double value){
        maxOpacity*=value;
    }

    public void addTempExtendMax(int value){
        extendMax.add(value);
    }

    public void refill(long delta){
        if (opacity<maxOpacity){
            double c= refillSpeed *delta/100000000;
            opacity+=c;
            if (opacity>maxOpacity){
                opacity=maxOpacity;
                empty=false;
            }
        }

    }

    public boolean isEmpty(){return empty;}
    public double getOpacity(){return opacity;}
    public double getMaxOpacity(){return maxOpacity;}

    public void setRearm(boolean b){rearmable=b;}
    public void setConsumption(int value) {consumption = value;}
    public void setUsable(boolean b){usable =b;}
    public void setDelayable(boolean b){delayable=b;}

    public void plusEnergy(int value){
        opacity+=value;
        if (opacity>maxOpacity) opacity=maxOpacity;
    }

    public void draw(Graphics2D g, int number,Color c){
        percent=opacity/maxOpacity*100;
        g.setColor(c);
        g.fillRect(1000, 25+number*35, (int)percent*3, 30);
    }

}

