package Entity.Items;

import Entity.Buffs.WeaponModifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;


public abstract class Item implements Serializable {

    //image stuff
    protected int width;
    protected int height;
    transient private BufferedImage image;
    protected static String adress;
    transient private BufferedImage ico;

    //item stuff
    protected int ID;
    protected String type;
    protected int buffType;

    //equipment stuff
    private double[] element;

    //weapons stuff
    public long lastUsage;
    protected String weaponType;
    protected WeaponModifier wp;

    //inventory stuff
    protected int weight;
    private double[] elementDef;

    public void init() {
        elementDef = new double[]{0, 0, 0, 0, 0, 0, 0};
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

    public String getWeaponType() {
        return weaponType;
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

    public int getDamage() {
        return 0;
    }

    public Double[] getResistance() {
        return new Double[]{0d, 0d, 0d, 0d, 0d, 0d, 0d};
    }

    public double[] getElement() {
        return element;
    }

    public double getPower() {
        return 0;
    }

    public int getDef() {
        return 0;
    }

    public double[] getElementDef() {
        return elementDef;
    }

    public abstract Object[][] getBuff();
}






