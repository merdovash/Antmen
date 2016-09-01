package GUI;

import java.awt.*;
import java.util.ArrayList;

public class Panel {

    private ArrayList<ArrayList<Menu>> menus;

    private int[] selected = new int[]{0, 0};

    public Panel() {
        menus = new ArrayList<>();
    }


    public void add(Menu o, int i) {
        if (menus.size() - 1 < i) {
            menus.add(new ArrayList<Menu>());
        }
        menus.get(i).add(o);
    }

    public int getPanelWidth(int i) {
        return menus.get(i).size() - 1;
    }

    public int getPanelHeight() {
        return menus.size() - 1;
    }

    public void selecet(int i, int j) {
        menus.get(selected[0]).get(selected[1]).select(false);
        selected = new int[]{i, j};
        menus.get(i).get(j).select(true);
    }

    public void activate(int i, int j) {
        menus.get(i).get(j).activate();

    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < getPanelHeight() + 1; i++) {
            for (int j = 0; j < getPanelWidth(i) + 1; j++) {
                menus.get(i).get(j).draw(g);
            }
        }
    }
}
