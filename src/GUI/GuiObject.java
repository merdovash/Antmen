package GUI;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GuiObject {


    protected Integer x;
    protected Integer y;
    protected Integer width;
    protected Integer height;

    protected Color color = new Color(99, 99, 99);

    protected Font font = new Font("Courier New", Font.PLAIN, (int) (25 * GamePanel.GUI_SCALE));
    protected Color fontColor = new Color(0, 150, 0);

    protected Rectangle rectangle;

    protected String text = "";

    protected BufferedImage image;

    public final void init(int x, int y, int width, int height) {
        this.x = (int) (x * GamePanel.GUI_SCALE);
        this.y = (int) (y * GamePanel.GUI_SCALE);
        this.width = (int) (width * GamePanel.GUI_SCALE);
        this.height = (int) (height * GamePanel.GUI_SCALE);

        rectangle = new Rectangle(this.x, this.y, this.width, this.height);

    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(rectangle);
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
        g.setFont(font);
        g.setColor(fontColor);
        g.drawString(text, x + 3, y + height / 2);
    }
}
