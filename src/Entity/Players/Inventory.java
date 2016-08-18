package Entity.Players;

import Entity.Items.Armor.GrabPoint;
import Entity.Items.Item;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Inventory {
    private Item[][] places;
    private int[][] size;
    public int width;
    public int height;

    private Item helm;
    private Item weapon;
    private Item armor;


    public Inventory() {
        width = 10;
        height = 4;
        places = new Item[height][width];
        size = new int[height][width];
    }

    public boolean addItem(Item item) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (places[i][j] == null) {
                    places[i][j] = item;
                    size[i][j] = 1;
                    return true;
                } else if (places[i][j].equals(item) && (size[i][j] + 1) * item.getWeight() <= 64) {
                    size[i][j]++;
                    return true;
                }
            }
        }
        return false;
    }

    public Item getItem(int x, int y) {
        return places[y][x];
    }

    public int getSize(int x, int y) {
        return size[y][x];
    }

    public Item equip(Item i) {
        if (i != null) {
            Item i2;
            if (i.getType().equals("headset")) {
                i2 = helm;
                helm = i;
                return i2;
            }
        }
        return i;
    }


    public Item getHelm() {
        return helm;
    }

    public void setItem(int x, int y, Item item) {
        places[y][x] = item;
    }

    public void draw(Graphics2D g, int x, int y) {
        if (helm != null) {
            BufferedImage img = helm.getImage();
            g.drawImage(img, x - helm.getImage().getWidth() / 2, y - img.getHeight() / 2, (int) (img.getWidth() * 0.75d * GamePanel.SCALE), (int) (img.getHeight() * 0.75d * GamePanel.SCALE), null);
        }
    }

    public void draw(Graphics2D g, GrabPoint headPoint) {
        if (helm != null) {
            BufferedImage img = helm.getImage();
            double scale = (double) (headPoint.getWidth()) / img.getWidth() * GamePanel.SCALE;
            int width = (int) (img.getWidth() * scale) * headPoint.getSide();
            int height = (int) (img.getHeight() * scale);
            g.drawImage(img, headPoint.getX() - width / 2, (headPoint.getY() - height), width, height, null);
        }
    }
}
