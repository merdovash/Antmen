package Entity.Blocks;

import Entity.ActiveMapObject;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class Block extends ActiveMapObject {

    private int x0;
    private int x1;
    private int y0;
    private int y1;

    private double xSpeed;
    private double ySpeed;

    private boolean facingUp;

    public Block(TileMap tm, int x, int y, int width, int height) {
        super(tm);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        scale = GamePanel.SCALE;
        lastTime = System.currentTimeMillis();
    }

    public void setBorder(int startX, int startY, int endX, int endY) {
        x0 = (int) (startX * scale);
        y0 = (int) (startY * scale);
        x1 = (int) (endX * scale);
        y1 = (int) (endY * scale);
        if (x0 < x1) {
            facingRight = true;
        } else {
            facingRight = false;
        }
        if (y0 < y1) {
            facingUp = true;
        } else {
            facingUp = false;
        }
    }

    public Rectangle getRealRectangle() {
        return rectangle;
    }

    public void setSpeed(double speedX, double speedY) {
        xSpeed = speedX * scale;
        ySpeed = speedY * scale;
    }


    public void update() {
        double delta = (System.currentTimeMillis() - lastTime) / 1000d;
        lastTime = System.currentTimeMillis();
        if (facingRight) {
            x += xSpeed * delta;
            if (x > x1) {
                x = x1;
                facingRight = false;
            }
        } else {
            x -= xSpeed * delta;
            if (x < x0) {
                x = x0;
                facingRight = true;
            }
        }
        if (facingUp) {
            y -= ySpeed * delta;
            if (y < y0) {
                y = y0;
                facingUp = false;
            }
        } else {
            y += ySpeed * delta;
            if (y > y1) {
                y = y1;
                facingUp = true;
            }
        }


        rectangle = getRectangle();
    }

    public void draw(Graphics2D g) {
        setMapPosition();

        g.setColor(Color.red);
        g.draw(rectangle);
    }


}
