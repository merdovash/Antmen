package GUI;

import Entity.Players.Inventory;
import Entity.Players.Stats;
import Entity.Skills.SkillList;
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
    private String[] name = {"Back to game", "Inventory >", "Stats >", "Spells >", "Menu"};

    private int currentAction;

    private boolean openInventory;


    private boolean openStats;

    private double scale;

    private Inventory inventory;

    //box
    private int size;

    private SkillList skillList;
    private boolean openSkills;
    private boolean submenu;

    public GUI(Inventory inventory, Stats stats) {
        scale = GamePanel.SCALE;
        size = (int) (60 * scale);
        this.inventory = inventory;
        this.stats = stats;
        init();
        loadSprites();
        buttonSize = (int) (30 * scale);

        //skils
        skillList = new SkillList();
        skillWindow = new Window(425, 100, 1400, 880);
        //skills = new GUI.GuiObjects[skillList.size()];
        //for (int i = 0; i < skillList.size(); i++) {
        //    skills[i] = new GUI.GuiObjects(500 + i * 55, 500, 50, 50);
        //    skills[i].setImage(skillList.getImage(i));
        //    skills[i].add(spellOption, GUI.GuiObjects.LEFT);
        //}



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
        button = new Rectangle[5];
        for (int i = 0; i < button.length; i++) {
            button[i] = new Rectangle((int) (GamePanel.WIDTH / 2 - 550 * scale), (int) ((100 + i * 100) * scale), (int) (200 * scale), (int) (66 * scale));
        }
        initMapItems();
    }

    public void update() {
        if (openSkills) {
            for (int i = 0; i < skillList.size(); i++) {
                //skills[i].select(i == spellCurrentPosition);
                // skills[i].activate(i == spellCurrentPosition && submenu);
            }
        }

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
                    g.drawImage(inventory.getItem(j, i).getIco(), (int) (GamePanel.WIDTH / 2 + (-150 + 71 * j) * scale), (int) ((350 + i * 70) * scale), size, size, null);
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


    private void drawEquipment(Graphics2D g) {
        //headset
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((GamePanel.WIDTH / 2), (int) (150 * scale), size, size);
        g.setColor(Color.cyan);
        g.drawRect((GamePanel.WIDTH / 2), (int) (150 * scale), size, size);
        if (inventory.getHelm() != null) {
            g.drawImage(inventory.getHelm().getIco(), (GamePanel.WIDTH / 2), (int) (150 * scale), size, size, null);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((GamePanel.WIDTH / 2 + size), (int) (150 * scale + size), size, size);
        g.setColor(Color.cyan);
        g.drawRect((GamePanel.WIDTH / 2 + size), (int) (150 * scale + size), size, size);
        if (inventory.getWeapon() != null) {
            g.drawImage(inventory.getWeapon().getIco(), (GamePanel.WIDTH / 2 + size), (int) (150 * scale + size), size, size, null);
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
        } else if (openSkills) {
            drawSkills(g);
        }


    }

    private String[] spellOption = new String[]{"", "Set", "Back"};
    private int spellCurrentPosition;
    private Window skillWindow;
    //private GUI.GuiObjects[] skills;


    private void drawSkills(Graphics2D g) {
        skillWindow.draw(g);
        //for (int i = 0; i < skills.length; i++) {
        //    skills[i].draw(g);
        //}
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
            value = 0;
            g.drawString("+" + String.valueOf((int) value) + "%", (int) (GamePanel.WIDTH / 2 + 280 * scale), (int) ((180 + 20 * i) * scale));
        }
        stats.permaUpdate();
    }

    public void setCurrentAction(int i) {
        currentAction += i;
        if (currentAction > 4) {
            currentAction = 0;
        } else if (currentAction < 0) {
            currentAction = 4;
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

    public void setOpenSkills() {
        openSkills = !openSkills;
    }

    public boolean isOpenSkills() {
        return openSkills;
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
    }

    public void statsSelect() {
        tempAdd = new int[]{0, 0, 0, 0, 0, 0};
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

    public void skillMove(int i) {
        spellCurrentPosition += i;
        if (spellCurrentPosition > skillList.size() - 1) {
            spellCurrentPosition = 0;
        }
        if (spellCurrentPosition < 0) {
            spellCurrentPosition = skillList.size() - 1;
        }
    }

    public void inventorySelect() {
        inventory.setItem(inventoryPlace[0], inventoryPlace[1], inventory.equip(inventory.getItem(inventoryPlace[0], inventoryPlace[1])));
    }

    public void skillsSelect() {
        submenu = true;
    }

    private Integer[] move;
    private int downBorder;
    private int leftBorder;

    public void move(int i, int j) {
        move[0] += i;
        move[1] += j;

        if (move[0] > downBorder) move[0] = 0;
        else if (move[0] < 0) move[0] = downBorder;
        if (move[1] > leftBorder) move[1] = 0;
        else if (move[1] < 0) move[1] = leftBorder;

    }

    private int calibrate(int what, int min, int max) {
        if (what > max) what = min;
        else if (what < min) what = max;
        return what;
    }

    public void select() {

    }

    public void deselect() {

    }
}
