package GUI;

import Entity.Players.Inventory;
import Entity.Players.Stats;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GUI {

    private final Font font = new Font("Courier New", Font.PLAIN, 18);
    private final Font font2 = new Font("Courier New", Font.PLAIN, 16);
    private final Font font3 = new Font("courier New", Font.PLAIN, (int) (25 * GamePanel.SCALE));
    private final Font font4 = new Font("Courier New", Font.PLAIN, (int) (10 * GamePanel.SCALE));
    private final Stats stats;

    private Rectangle[] button;
    private String[] name = {"Inventory >", "Stats >", "Spells >", "Back to game"};

    private int currentAction;

    private boolean openInventory;


    private boolean openStats;

    private double scale;

    private Inventory inventory;

    //box
    private int size;

    public GUI(Inventory inventory, Stats stats) {
        scale = GamePanel.SCALE;
        size = (int) (60 * scale);
        this.inventory = inventory;
        this.stats = stats;
        init();
        loadSprites();
        buttonSize = (int) (30 * scale);
    }

    private void loadSprites() {
        try {
            statAdd = ImageIO.read(getClass().getResourceAsStream(statAddAdress));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private final Color placeBox = new Color(200, 200, 200);
    private final Color placeBoxI = new Color(100, 100, 100);
    private void drawInventory(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) (GamePanel.WIDTH / 2 - 200 * scale), (int) (100 * scale), (int) (800 * scale), (int) (550 * scale));
        for (int i = 0; i < inventory.height; i++) {
            for (int j = 0; j < inventory.width; j++) {
                if (inventoryPlace[0] == j && inventoryPlace[1] == i) {
                    g.setColor(placeBox);
                } else {
                    g.setColor(placeBoxI);
                }
                g.fillRect((int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size);
                g.setColor(Color.BLACK);
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
                    inventory.getItem(j, i).drawDescription(g);
                }
            }
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Backspace to Back", (int) (530 * scale), (int) (150 * scale));
    }


    public void drawEquipment(Graphics2D g) {
        //headset
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((GamePanel.WIDTH / 2), (int) (150 * scale), size, size);
        g.setColor(Color.cyan);
        g.drawRect((GamePanel.WIDTH / 2), (int) (150 * scale), size, size);
        if (inventory.getHelm() != null) {
            g.drawImage(inventory.getHelm().getImage(), (GamePanel.WIDTH / 2), (int) (150 * scale), size, size, null);
        }
    }

    private final Color menuButton = new Color(50, 10, 10);
    private final Color menuButtonPressed = new Color(100, 20, 20);
    public void draw(Graphics2D g) {

        g.setColor(new Color(0, 0, 0, 0.75f));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setFont(font3);
        for (int i = 0; i < button.length; i++) {
            if (currentAction == i) {
                g.setColor(menuButtonPressed);
            } else {
                g.setColor(menuButton);
            }
            g.fill(button[i]);
            g.setColor(Color.WHITE);
            g.drawString(name[i], (int) (button[i].getX() + 5 * scale), (int) (button[i].getY() + 50 * scale));
        }

        if (openInventory) {
            drawInventory(g);
            drawEquipment(g);
        } else if (openStats) {
            drawStats(g);
        }

    }

    private String[] s = new String[]{"str:", "int:", "dex:", "vit:", "agi:", "spk:"};
    private String statAddAdress = "/GUI/Stats/stats_add.png";
    private String[] explanation = new String[]{
            "Speed: ",
            "Boost speed: ",
            "Magic damage: ",
            "Cast speed: ",
            "Magic power: ",
            "Attack: "};
    private BufferedImage statAdd;
    private int buttonSize;

    private void drawStats(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) (GamePanel.WIDTH / 2 - 200 * scale), (int) (100 * scale), (int) (800 * scale), (int) (550 * scale));
        for (int i = 0; i < 6; i++) {
            if (place == i) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillRect((int) (GamePanel.WIDTH / 2 - 150 * scale), (int) ((200 + 65 * i) * scale), (int) (150 * scale), (int) (50 * scale));

        }
        g.setFont(font3);
        g.setColor(Color.WHITE);
        g.drawString("Level:   " + stats.getLevel(), (int) (GamePanel.WIDTH / 2 - 150 * scale), (int) (150 * scale));
        g.drawString("Free points:  " + stats.getFreePoints(), (int) (GamePanel.WIDTH / 2 - 150 * scale), (int) (180 * scale));
        for (int i = 0; i < 6; i++) {
            g.drawString(String.format("%s %3d", s[i], stats.getStats()[i]), (int) (GamePanel.WIDTH / 2 - 145 * scale), (int) ((230 + 65 * i) * scale));
            if (stats.getFreePoints() > 0)
                g.drawImage(statAdd, (int) (GamePanel.WIDTH / 2 + 5 * scale), (int) ((210 + 65 * i) * scale), -buttonSize, buttonSize, null);
            if (tempAdd[i] > 0)
                g.drawImage(statAdd, (int) (GamePanel.WIDTH / 2 - 85 * scale), (int) ((210 + 65 * i) * scale), buttonSize, buttonSize, null);
        }
        g.setFont(font4);
        double value;
        for (int i = 0; i < explanation.length; i++) {
            g.drawString(explanation[i], (int) (GamePanel.WIDTH / 2 + 105 * scale), (int) ((180 + 20 * i) * scale));
            value = (stats.getModifier(i) - 1) * 100;
            g.drawString("+" + String.valueOf((int) value) + "%", (int) (GamePanel.WIDTH / 2 + 280 * scale), (int) ((180 + 20 * i) * scale));
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
        openInventory = !openInventory;
    }

    public boolean isOpenInventory() {
        return openInventory;
    }

    public void setOpenStats() {
        openStats = !openStats;
    }

    public boolean isOpenStats() {
        return openStats;
    }

    private int place;

    public void statsMove(int i) {
        place += i;
        if (place < 0) {
            place = 5;
        } else if (place > 5) {
            place = 0;
        }
    }

    private int[] tempAdd = {0, 0, 0, 0, 0, 0};

    public void statsAdd(int i) {
        if (i > 0) {
            if (stats.getFreePoints() > 0) {
                stats.getStat(place).addAbs(1);
                tempAdd[place] += 1;
                stats.useFreePoints(-1);
            }
        } else {
            if (tempAdd[place] > 0) {
                stats.getStat(place).addAbs(-1);
                tempAdd[place] -= 1;
                stats.useFreePoints(1);
            }
        }
        stats.calculateModifier();
    }

    public void statsSelect() {
        tempAdd = new int[]{0, 0, 0, 0};
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

    public void inventorySelect() {
        inventory.setItem(inventoryPlace[0], inventoryPlace[1], inventory.equip(inventory.getItem(inventoryPlace[0], inventoryPlace[1])));
    }
}
