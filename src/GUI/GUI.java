package GUI;

import Entity.Items.ItemList;
import Entity.Players.Player;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GUI {

    private final Font font = new Font("Courier New", Font.PLAIN,18);

    private Rectangle[] button;
    private String[] name = {"Inventory >", "blabla" , "blabla" ,"Back to game"};

    private int currentAction;

    private Player player;
    private boolean inventory;

    private int[] listInventory;

    private double scale;

    //box
    private int size;

    public GUI(){
        scale = GamePanel.SCALE;
        size = (int) (60 * scale);
        init();
    }

    private void initMapItems() {
    }

    public void init() {
        button = new Rectangle[4];
        for (int i =0; i<button.length;i++){
            button[i] = new Rectangle((int) (GamePanel.WIDTH / 2 - 500 * scale), (int) ((100 + i * 150) * scale), (int) (300 * scale), (int) (100 * scale));
        }
        initMapItems();
    }

    public void update(int[] items) {
        listInventory = items;
    }

    public void draw(Graphics2D g) {

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
            g.drawString(name[i], (int) (button[i].getX() + 5 * scale), (int) (button[i].getY() + 50 * scale));
        }

        if (inventory){
            g.setColor (Color.DARK_GRAY);
            g.fillRect((int) (GamePanel.WIDTH / 2 - 200 * scale), (int) (100 * scale), (int) (800 * scale), (int) (550 * scale));
            for (int i=0;i<4;i++){
                for (int j=0;j<10;j++){
                    if (inventoryPlace[0]==j && inventoryPlace[1]==i){
                        g.setColor(Color.GREEN);
                    }else{
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect((int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size);
                    g.setColor(Color.cyan);
                    g.drawRect((int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size);
                }
            }
            for (int i = 0; i < listInventory.length; i++) {
                if (listInventory[i] != 0) {
                    BufferedImage ico = null;
                    try {
                        ico = ImageIO.read(getClass().getResourceAsStream(ItemList.getString(listInventory[i])));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(ico, (int) (GamePanel.WIDTH / 2 + (-150 + 71 * i / 10) * scale), (int) ((350 + i % 10 * 70) * scale), size, size, null);
                }
            }
            g.setColor(Color.WHITE);
            g.drawString("Backspace to Back", (int) (530 * scale), (int) (150 * scale));
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

    public void select() {
        if (listInventory[inventoryPlace[1]*4+inventoryPlace[0]]!=0){
            equip(listInventory[inventoryPlace[1] * 4 + inventoryPlace[0]]);
        }
    }

    private void equip(int i) {

    }
}
