package GUI;

import Entity.Player;
import Main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by vlad on 08.08.16.
 */
public class GUI {

    private final Font font = new Font("Courier New", Font.PLAIN,18);

    private Rectangle[] button;
    private String[] name = {"Inventory >", "blabla" , "blabla" ,"Back to game"};

    private int currentAction;

    private Player player;
    private boolean inventory;

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
        g.setColor(new Color (0,0,0, 0.75f));
        g.fillRect(0,0, GamePanel.WIDTH,GamePanel.HEIGHT);
        for (int i =0; i<button.length;i++){
            if (currentAction==i){
                g.setColor(Color.blue);
            }else {
                g.setColor(Color.GREEN);
            }
            g.fill(button[i]);
            g.setColor(Color.WHITE);
            g.drawString(name[i],(int)button[i].getX()+5,(int)button[i].getY()+50);
        }

        if (inventory){
            g.setColor (Color.DARK_GRAY);
            g.fillRect (550, 100, 800, 550);
            for (int i=0;i<4;i++){
                for (int j=0;j<10;j++){
                    if (inventoryPlace[0]==j && inventoryPlace[1]==i){
                        g.setColor(Color.GREEN);
                    }else{
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(600+71*j,350+i*70,60,60);
                    g.setColor(Color.cyan);
                    g.drawRect(600+71*j,350+i*70,60,60);
                }
            }
            g.setColor(Color.WHITE);
            g.drawString("Backspace to Back", 530, 150);
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

    public int getCurrentAction() {
        return currentAction;
    }

    public void openInventory() {
        inventory=!inventory;
    }

    public boolean isInventory(){
        return inventory;
    }

    private int[] inventoryPlace=new int[]{0,0};

    public void inventoryMove(int i, int j){
        inventoryPlace[0]+=i;
        inventoryPlace[1]+=j;
        if (inventoryPlace[0]<0){
            inventoryPlace[0]=9;
        }else if(inventoryPlace[0]>9){
            inventoryPlace[0]=0;
        }
        if (inventoryPlace[1]<0){
            inventoryPlace[1]=3;
        }else if(inventoryPlace[1]>3){
            inventoryPlace[1]=0;
        }
    }
}
