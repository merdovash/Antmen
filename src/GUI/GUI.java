package GUI;

import Entity.Player;

import java.awt.*;

/**
 * Created by vlad on 08.08.16.
 */
public class GUI {

    private final Font font = new Font("Courier New", Font.PLAIN,18);

    private Rectangle[] button;

    private int currentAction;

    private Player player;

    public GUI(Player p){
        player=p;
        button = new Rectangle[4];
        for (int i =0; i<button.length;i++){
            button[i]= new Rectangle(200,100+i*150,300,100);
        }

    }

    public void update(){

    }

    public void draw(Graphics2D g){
        for (int i =0; i<button.length;i++){
            if (currentAction==i){
                g.setColor(Color.blue);
            }else {
                g.setColor(Color.GREEN);
            }
            g.fill(button[i]);
        }
    }

    public void setCurrentAction(int i){
        currentAction+=i;
        if (currentAction>3){
            currentAction=0;
        }else if (currentAction<0){
            currentAction=3;
        }
    }
}
