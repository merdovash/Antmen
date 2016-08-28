package GUI;

import java.awt.*;
import java.util.ArrayList;

public class Window extends GuiObject {

    private ArrayList<GuiObject> objects;
    private ArrayList<ArrayList<Button>> panel;

    public Window(int x, int y, int width, int height) {
        init(x, y, width, height);
        objects = new ArrayList<GuiObject>();
        panel = new ArrayList<>();
    }

    public void add(GuiObject o) {
        objects.add(o);
    }

    public void add(Button p, int i) {
        if (i > panel.size() - 1) {
            panel.add(new ArrayList<>());
        }
        panel.get(i).add(p);
    }

    public void setColor(Color c) {
        color = c;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).draw(g);
        }
        for (int i = 0; i < panel.size(); i++) {
            for (int j = 0; j < panel.get(i).size(); j++) {
                panel.get(i).get(j).draw(g);
            }
        }
    }

    public int getWindowHeight() {
        return panel.size() - 1;
    }

    public int getWindowWidth(int i) {
        return panel.get(i).size() - 1;
    }

    private int[] s = new int[]{0, 0};

    public void select(int i, int j) {
        panel.get(s[0]).get(s[1]).select(false);
        s = new int[]{i, j};
        panel.get(i).get(j).select(true);
    }

    public Window activate(int i, int j) {
        if (panel.get(i).get(j) instanceof Menu) {
            return ((Menu) panel.get(i).get(j)).activate();
        }
        return null;
    }
}
