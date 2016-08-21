package Entity.Items;

import Entity.Items.Armor.Boosts.BoostStats;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public abstract class Item extends BoostStats {

    //image stuff
    protected int width;
    protected int height;
    private BufferedImage image;
    protected static String adress;
    private BufferedImage ico;

    //item stuff
    protected int ID;
    protected String type;
    protected String[] description;

    //equipment stuff
    protected double[] element;
    protected String rare;

    //weapons stuff
    public long lastUsage;

    //inventory stuff
    protected int weight;

    public void init() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(adress));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BufferedImage getImage() {
        return image;
    }

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return ID;
    }

    public void drawDescription(Graphics2D g) {

    }

    public BufferedImage getIco() {
        if (ico == null) {
            loadIco();
        }
        return ico;
    }

    private void loadIco() {
        try {
            ico = ImageIO.read(getClass().getResourceAsStream(ItemList.getAddressImage(ID)));
        } catch (IOException e) {
            System.out.println("ico not found: " + ico);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return 0;
    }

    public int getRange() {
        return 0;
    }

    public double[] getDamage() {
        return new double[]{0, 0, 0};
    }

    public Double[] getResistance() {
        return new Double[]{0d, 0d, 0d, 0d, 0d, 0d, 0d};
    }


    public double[] getElement() {
        return element;
    }

    public static final int NORMAL = 0;
    private static final int FIRE = 1;
    private static final int EARTH = 2;
    private static final int WATER = 3;
    private static final int WIND = 4;
    private static final int HOLY = 5;
    private static final int SHADOW = 6;

    public static double getElementResistance(double[] weapon, double[] armor) {
        if (weapon[0] == NORMAL) {
            if (armor[0] == NORMAL) return 1;
            if (armor[0] == FIRE) return 0.75;
            if (armor[0] == WATER) return 0.75;
            if (armor[0] == WIND) return 0.75;
            if (armor[0] == EARTH) return 0.75;
            if (armor[0] == HOLY) return 0.5;
            if (armor[0] == SHADOW) return 0.5;
        } else if (weapon[0] == FIRE) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == WATER) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == WIND) return 1;
            if (armor[0] == EARTH) return 1.5 * (1 + weapon[1]);
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == WATER) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 1.5 * (1 + weapon[1]);
            if (armor[0] == WATER) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == WIND) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == EARTH) return 1;
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == WIND) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 1;
            if (armor[0] == WATER) return 1.5 * (1 + weapon[1]);
            if (armor[0] == WIND) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == EARTH) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == EARTH) {
            if (armor[0] == NORMAL) return 1 * (1 + weapon[1]);
            if (armor[0] == FIRE) return 1 * (-armor[1] + weapon[1]);
            if (armor[0] == WATER) return 1;
            if (armor[0] == WIND) return 1.5 * (1 + weapon[1]);
            if (armor[0] == EARTH) return 0.5 * (1 - armor[1] + weapon[1]);
            if (armor[0] == HOLY) return 1.25 * (1 + weapon[1] - armor[1] * 2);
            if (armor[0] == SHADOW) return 0.5 * (1 - armor[1]);
        } else if (weapon[0] == HOLY) {
            if (armor[0] == NORMAL) return 1;
            if (armor[0] == FIRE) return 1;
            if (armor[0] == WATER) return 1;
            if (armor[0] == WIND) return 1;
            if (armor[0] == EARTH) return 1;
            if (armor[0] == HOLY) return 0.5 * (weapon[1] - armor[1]);
            if (armor[0] == SHADOW) return 1 * (1 * weapon[1]);
        } else if (weapon[0] == SHADOW) {
            if (armor[0] == NORMAL) return 1 * (weapon[1] - armor[1]);
            if (armor[0] == FIRE) return 1.5;
            if (armor[0] == WATER) return 1.5;
            if (armor[0] == WIND) return 1.5;
            if (armor[0] == EARTH) return 1.5;
            if (armor[0] == HOLY) return 0;
            if (armor[0] == SHADOW) return 0;
        }
        return 1;
    }

    public double getPower() {
        return 0;
    }

    public int getDef() {
        return 0;
    }
}






