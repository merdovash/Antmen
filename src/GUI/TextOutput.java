package GUI;

import Main.GamePanel;

import java.awt.*;

public class TextOutput extends GuiObject {
    public TextOutput(int x, int y, String text) {
        this.text = text;
        rescale();
        super.init(x, y, width, height);
        color = new Color(1f, 1f, 1f);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setText(String s) {
        text = s;
    }

    public void setFont(Font f) {
        font = f;
        rescale();

    }

    private void rescale() {

        width = text.length() * 50;
        height = (int) (font.getSize() + 10 * GamePanel.GUI_SCALE);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
