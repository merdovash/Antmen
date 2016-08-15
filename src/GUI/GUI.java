package GUI;

import Entity.Items.ItemList;
import Entity.Players.Inventory;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GUI {

    private final Font font = new Font("Courier New", Font.PLAIN, 18);
    private final Font font2 = new Font("Courier New", Font.PLAIN, 16);

    private Rectangle[] button;
    private String[] name = {"Inventory >", "blabla", "blabla", "Back to game"};

    private int currentAction;

    private boolean open;

    private double scale;

    private Inventory inventory;

    //box
    private int size;

    public GUI(Inventory inventory) {
        scale = GamePanel.SCALE;
        size = (int) (60 * scale);
        this.inventory = inventory;
        init();
    }

    private void initMapItems() {
    }

    private void init() {
        button = new Rectangle[4];
        for (int i = 0; i < button.length; i++) {
            button[i] = new Rectangle((int) (GamePanel.WIDTH / 2 - 500 * scale), (int) ((100 + i * 150) * scale), (int) (300 * scale), (int) (100 * scale));
        }
        initMapItems();
    }

    public void update() {

    }

    private void drawInventory(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) (GamePanel.WIDTH / 2 - 200 * scale), (int) (100 * scale), (int) (800 * scale), (int) (550 * scale));
        for (int i = 0; i < inventory.height; i++) {
            for (int j = 0; j < inventory.width; j++) {
                if (inventoryPlace[0] == j && inventoryPlace[1] == i) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect((int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size);
                g.setColor(Color.cyan);
                g.drawRect((int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size);
            }
        }
        g.setFont(font2);
        for (int i = 0; i < inventory.height; i++) {
            for (int j = 0; j < inventory.width; j++) {
                int id = inventory.getID(j, i);
                if (id != 0) {
                    BufferedImage ico = null;
                    try {
                        ico = ImageIO.read(getClass().getResourceAsStream(ItemList.getAddressImage(id)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    g.drawImage(ico, (int) (GamePanel.WIDTH / 2 + (-150 + 71 * i / 10) * scale), (int) ((350 + i % 10 * 70) * scale), size, size, null);
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(inventory.getSize(j, i)), (int) (GamePanel.WIDTH / 2 + (-145 + 71 * i / 10) * scale), (int) ((365 + i % 10 * 70) * scale));
                }
            }
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Backspace to Back", (int) (530 * scale), (int) (150 * scale));
    }

    public void draw(Graphics2D g) {

        g.setColor(new Color(0, 0, 0, 0.75f));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        for (int i = 0; i < button.length; i++) {
            if (currentAction == i) {
                g.setColor(Color.blue);
            } else {
                g.setColor(Color.GREEN);
            }
            g.fill(button[i]);
            g.setColor(Color.WHITE);
            g.drawString(name[i], (int) (button[i].getX() + 5 * scale), (int) (button[i].getY() + 50 * scale));
        }

        if (open) {
            drawInventory(g);
        }
    }

    public void setCurrentAction(int i) {
        currentAction += i;
        if (currentAction > 3) {
            currentAction = 0;
        } else if (currentAction < 0) {
            currentAction = 3;
        }
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public void openInventory() {
        open = !open;
    }

    public boolean isOpen() {
        return open;
    }

    private int[] inventoryPlace = new int[]{0, 0};

    public void inventoryMove(int i, int j) {
        inventoryPlace[0] += i;
        inventoryPlace[1] += j;
        if (inventoryPlace[0] < 0) {
            inventoryPlace[0] = 9;
        } else if (inventoryPlace[0] > 9) {
            inventoryPlace[0] = 0;
        }
        if (inventoryPlace[1] < 0) {
            inventoryPlace[1] = 3;
        } else if (inventoryPlace[1] > 3) {
            inventoryPlace[1] = 0;
        }
    }

    public void select() {

    }

    private void equip(int i) {

    }
}
