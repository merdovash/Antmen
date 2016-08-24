package Entity.Skills;

import java.awt.image.BufferedImage;

public interface SpellManageable {

    BufferedImage getIco();

    long getCooldown();

    boolean isSkill();
}
