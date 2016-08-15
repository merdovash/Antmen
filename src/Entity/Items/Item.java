package Entity.Items;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public abstract class Item {

    protected static String adress;
    protected int ID;
    protected String type;
    private BufferedImage image;

    protected int weight;

    public Item() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(adress));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
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
}
