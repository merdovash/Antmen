package Entity;

import java.awt.*;

public class FPS {
    private int fps;
    private long delta;
    private Font font;
    private long lastTime;

    public FPS(){
        font = new Font("Arial", Font.PLAIN,36);
        lastTime = System.currentTimeMillis();
    }

    public void update(){
        delta= System.currentTimeMillis()-lastTime;
        lastTime=System.currentTimeMillis();
        if (delta==0){
            delta=1;
        }
        fps= (int) (1000/delta);
    }

    public void draw(Graphics2D g){
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString(String.valueOf(fps),0,50);
    }
}
