package GUI;

import Entity.Players.Inventory;
import Entity.Players.Stats;
import Entity.Skills.SkillList;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GUI2 {
    private double scale;

    private Inventory inventory;
    private Stats stats;

    private SkillList skillList;

    private ArrayList<Window> working;

    public GUI2(Inventory inventory, Stats stats) {
        scale = GamePanel.GUI_SCALE;
        this.inventory = inventory;
        this.stats = stats;
        working = new ArrayList<Window>();

        loadSprites();
        initMainMenu();

        initStatsMenu();
        initInventoryMenu();


    }


    private BufferedImage statAdd;

    private void loadSprites() {
        try {
            String statAddAdress = "/GUI/Stats/stats_add.png";
            statAdd = ImageIO.read(getClass().getResourceAsStream(statAddAdress));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initInventoryMenu() {
        Window w = new Window(50, 150, GamePanel.WIDTH - 100, GamePanel.HEIGHT - 100);

        Button b = new Button(200, 250, 100, 100);
        b.setColor(new Color(10, 10, 150));
        b.setText("HGFCVBH");
        w.add(b, 0);

        menus[1].add(w);
    }


    private Panel statsPanel;

    private Menu statsAdd;
    TextOutput t;

    private void initStatsMenu() {
        t = new TextOutput(400, 400, "HI");

    }

    private Window mainWindow;

    private String[] menuOption = new String[]{"Back to game", "Inventory >", "Stats >", "Spells >", "Menu"};
    private Menu[] menus = new Menu[menuOption.length];

    private void initMainMenu() {
        mainWindow = new Window(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        mainWindow.setColor(new Color(0.2f, 0.2f, 0.2f, 0.75f));

        int height = 100;
        int width = (int) ((double) GamePanel.WIDTH / menuOption.length);
        for (int i = 0; i < menuOption.length; i++) {
            menus[i] = new Menu(width * i, 0, width, height);
            menus[i].setText(menuOption[i]);
            mainWindow.add(menus[i], 0);
        }
    }

    public void start() {
        working.add(mainWindow);
        select();
    }

    private int[] place = new int[]{0, 0};

    public void move(int i, int j) {
        place[0] += i;
        place[1] += j;

        place[0] = calibrate(place[0], 0, working.get(working.size() - 1).getWindowHeight());
        place[1] = calibrate(place[1], 0, working.get(working.size() - 1).getWindowWidth(place[0]) - 1);
        System.out.println(place[0] + " " + place[1]);
        select();
    }

    private int calibrate(int what, int min, int max) {
        if (what > max) what = min;
        else if (what < min) what = max;
        return what;
    }

    public void select() {
        working.get(working.size() - 1).select(place[0], place[1]);
    }

    public void draw(Graphics2D g) {
        mainWindow.draw(g);
        t.draw(g);
    }

    public void activate() {
        working.add(working.get(working.size() - 1).activate(place[0], place[1]));
    }

    public void back() {
        working.remove(working.size() - 1);
    }

    public boolean isActive() {
        return working.size() > 0;
    }
}
