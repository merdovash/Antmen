package GUI;

import java.awt.*;
import java.util.ArrayList;

public class Panel {

    private ArrayList<ArrayList<GuiObject>> objects;

    public Panel() {
        objects = new ArrayList<>();
    }

    public void add(GuiObject o, int i) {
        if (objects.size() - 1 < i) {
            objects.add(new ArrayList<GuiObject>());
        }
        objects.get(i).add(o);
    }

    public int getPanelWidth(int i) {
        return objects.get(i).size();
    }

    public int getPanelHeight() {
        return objects.size();
    }

    public void selecet(int i, int j) {
        try {
            ((Button) objects.get(i).get(j)).select(true);
        } catch (ClassCastException e) {
            System.out.println("не удалось привести к Button");
        }
    }

    public void activate(int i, int j) {
        try {
            ((Menu) objects.get(i).get(j)).activate(true);
        } catch (ClassCastException e) {
            System.out.println("не удалось привести к Menu");
        }
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < getPanelHeight(); i++) {
            for (int j = 0; j < getPanelWidth(i); j++) {
                objects.get(i).get(j).draw(g);
            }
        }
    }
}
