package Entity.Players;

import Entity.Battle.Attack;
import Entity.Battle.Defence;
import Entity.Items.GrabPoint;
import Entity.Items.Item;
import Main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Inventory {
    private Item[][] places;
    private int[][] size;
    public int width;
    public int height;

    private Item helm;
    private Item weapon;
    private Item armor;
    private Item legs;
    private Item arms;

    private Attack attack;
    private Defence defence;


    public Inventory(Attack a, Defence d) {
        width = 10;
        height = 4;
        places = new Item[height][width];
        size = new int[height][width];
        attack = a;
        defence = d;
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
                defence.addArmor(i);
                return i2;
            } else if (i.getType().equals("weapon")) {
                i2 = weapon;
                weapon = i;
                attack.setWeapon(i);
                return i2;
            }
        }
        return i;
    }

    //get equipment
    public Item getHelm() {
        return helm;
    }

    public Item getWeapon() {
        return weapon;
    }

    public Item getArmor() {
        return armor;
    }

    public Item getLegs() {
        return legs;
    }

    public Item getArms() {
        return arms;
    }


    public void setItem(int x, int y, Item item) {
        places[y][x] = item;
    }

    //draw equipment
    public void drawHelm(Graphics2D g, GrabPoint headPoint) {
        if (helm != null) {
            BufferedImage img = helm.getImage();
            double scale = (double) (headPoint.getWidth()) / img.getWidth() * GamePanel.SCALE;
            int width = (int) (img.getWidth() * scale) * headPoint.getSide();
            int height = (int) (img.getHeight() * scale);
            g.drawImage(img, headPoint.getX() - width / 2, (headPoint.getY() - height), width, height, null);
        }
    }


    public void drawWeapon(Graphics2D g, GrabPoint weaponPoint, long angle) {
        if (weapon != null) {
            BufferedImage img = weapon.getImage();
            double scale = (double) (weaponPoint.getWidth()) / img.getWidth() * GamePanel.SCALE;
            int width = (int) (img.getWidth() * scale) * weaponPoint.getSide();
            int height = (int) (img.getHeight() * scale);
            Image im = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            AffineTransform at = new AffineTransform();
            at.setToRotation(Math.toRadians(weaponPoint.getSide() * angle), weaponPoint.getX() - width / 2, weaponPoint.getY());
            at.translate(weaponPoint.getX() - weaponPoint.getSide() * width / 2, weaponPoint.getY() - height);
            g.drawImage(im, at, null);
        }
    }


    public double getDefence() {
        double def = 0;
        if (armor != null) {
            def += armor.getDef();
        }
        if (legs != null) {
            def += legs.getDef();
        }
        if (arms != null) {
            def += arms.getDef();
        }
        if (helm != null) {
            def += helm.getDef();
        }
        return def;
    }
}
