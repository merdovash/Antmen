package GUI;

import GUI.Interfaces.Activatable;
import Main.GamePanel;

import java.awt.*;

public class Menu extends Button implements Activatable {
    public static final int LEFT = 0;

    private Boolean show;

    private Color optionColor = new Color(180, 40, 40);

    Button[] buttons;

    public Menu(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void add(String[] options, int type) {
        int menuWidth = calculateWidth(options) * font.getSize();
        int menuHeight = (int) (25 * GamePanel.GUI_SCALE);
        switch (type) {
            case LEFT:
                buttons = new Button[options.length];
                for (int i = 0; i < options.length; i++) {
                    buttons[i] = new Button(x + width, y + (menuHeight + 1) * i, menuWidth, menuHeight);
                    buttons[i].setText(options[i]);
                    buttons[i].setColor(optionColor);
                }
                break;
            default:
                break;
        }
    }

    public void setOptionColor(Color oc) {

    }

    private int calculateWidth(String[] s) {
        int temp = 0;
        for (int i = 0; i < s.length; i++) {
            if (s.length > temp) temp = s.length;
        }
        return temp;
    }


    public void draw(Graphics2D g) {
        super.draw(g);
        if (show) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].draw(g);
            }
        }
    }

    @Override
    public void activate(boolean b) {
        show = b;
    }
}
