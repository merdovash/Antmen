package GUI;

import Entity.Players.Inventory;
import Main.GamePanel;

import java.awt.*;

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
                if (place[0] == j && place[1] == i) {
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
                if (null != inventory.getItem(j, i)) {
                    g.drawImage(inventory.getItem(j, i).getImage(), (int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size, null);
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(inventory.getSize(j, i)), (int) (GamePanel.WIDTH / 2 + (-145 + 71 * j) * scale), (int) ((365 + i * 70) * scale));
                }
            }
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Backspace to Back", (int) (530 * scale), (int) (150 * scale));
    }

    public void drawEquipment(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((GamePanel.WIDTH / 2), (int) (150 * scale), size, size);
        g.setColor(Color.cyan);
        g.drawRect((GamePanel.WIDTH / 2), (int) (150 * scale), size, size);
        if (inventory.getHelm() != null) {
            g.drawImage(inventory.getHelm().getImage(), (GamePanel.WIDTH / 2), (int) (150 * scale), size, size, null);
        }
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
            drawEquipment(g);
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

    private int[] place = new int[]{0, 0};

    public void inventoryMove(int i, int j) {
        place[0] += i;
        place[1] += j;
        if (place[0] < 0) {
            place[0] = 9;
        } else if (place[0] > 9) {
            place[0] = 0;
        }
        if (place[1] < 0) {
            place[1] = 3;
        } else if (place[1] > 3) {
            place[1] = 0;
        }
    }

    public void select() {
        inventory.setItem(place[0], place[1], inventory.equip(inventory.getItem(place[0], place[1])));
    }
}
