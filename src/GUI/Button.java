package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Button extends GuiObject {

    private Color colorActive = new Color(0.7f, 0.7f, 0.7f, 0.3f);
    private Color selectedColor = new Color(200, 75, 75);
    private Color basicColor = new Color(150, 150, 25);

    private Boolean seleceted = false;


    public Button(int x, int y, int width, int height) {
        setColor(color);
        init(x, y, width, height);
    }

    public void setImage(BufferedImage i) {
        image = i;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color NEW) {
        basicColor = NEW;
    }

    public void select(boolean b) {
        seleceted = b;
    }

    public void draw(Graphics2D g) {
        color = seleceted ? selectedColor : basicColor;
        super.draw(g);
    }

}
