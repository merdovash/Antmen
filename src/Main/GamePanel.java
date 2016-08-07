package Main;

import GameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by MERDovashkinar on 7/29/2016.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

    public static int WIDTH = 192;
    public static int HEIGHT = 108;
    public static double SCALE = 2;

    private Thread thread;
    private boolean running;
    private int FPS =60;
    private long targetTime = 1000 / FPS;

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;

    public GamePanel(){
        super();
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        HEIGHT = sSize.height;
        WIDTH =sSize.width;
		SCALE=HEIGHT/(50*14);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));


        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();
        if(thread==null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();

        }
    }

    public void init(){
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running=true;

        gsm = new GameStateManager();
    }

    @Override
    public void run() {

        init();

        long start;
        long delta;
        long wait;
        while(running){
            start=System.nanoTime();

            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
            draw();
            drawToScreen();

            delta= System.nanoTime() - start;
            wait=targetTime - delta / 1000000;

            if (wait>0){
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



        }
    }

    private void update() throws IOException {
        gsm.update();
    }

    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2= getGraphics();
        g2.drawImage(image,0,0,(WIDTH),(HEIGHT), null);
        g2.dispose();

    }

    @Override
    public void keyTyped(KeyEvent key) {

    }

    @Override
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent key) {

        gsm.keyReleased(key.getKeyCode());
    }
}
