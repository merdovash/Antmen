package Entity.Skills;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface SpellManageable {

    BufferedImage getIco();

    long getCooldown();

    boolean isSkill();

    default BufferedImage loadIco(String address, int width, int height) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResourceAsStream(address));
            bi = bi.getSubimage(0, 0, width, height);
        } catch (IOException e) {

        } catch (NullPointerException e2) {

        }
        return bi;
    }
}
