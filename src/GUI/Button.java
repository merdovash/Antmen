package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Button extends GuiObject {

    private Color colorActive = new Color(0.7f, 0.7f, 0.7f, 0.3f);

    private Boolean seleceted = false;


    public Button(int x, int y, int width, int height) {
        setColor(new Color(40, 101, 101));
        init(x, y, width, height);
    }

    public void setImage(BufferedImage i) {
        image = i;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color NEW) {
        color = NEW;
    }

    public void select(boolean b) {
        seleceted = b;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        if (seleceted) {
            g.setColor(colorActive);
            g.fill(rectangle);
        }
    }

}
