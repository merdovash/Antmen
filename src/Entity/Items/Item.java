package Entity.Items;

import Entity.Items.Armor.Boosts.BoostStats;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public abstract class Item extends BoostStats {

    protected static String adress;
    protected int ID;
    protected String type;
    private BufferedImage image;

    public long lastUsage;

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
}
