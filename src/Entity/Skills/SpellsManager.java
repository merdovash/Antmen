package Entity.Skills;

import Entity.Skills.PhysicalSkill.BasicAttack;
import Entity.Skills.PhysicalSkill.MultiAttack;
import Entity.Skills.Spells.BuffingSpell.IncreasePower;
import Entity.Skills.Spells.FireBall.FireBall;
import Main.GamePanel;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class SpellsManager implements Serializable {
    transient private Map<Integer, BufferedImage> ico;
    private int place[];
    private int tileSize;

    private final Color cd = new Color(0, 0, 0, 0.7f);


    public SpellsManager() {
        place = new int[4];
        ico = new HashMap();
        ico.put(0, null);
        for (int i = 1; i < 4; i++) {
            try {
                ico.put(100 + i, ImageIO.read(getClass().getResourceAsStream("/Spells/FireBall/" + i + ".gif")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ico.put(1, ImageIO.read(getClass().getResourceAsStream("/Skills/BasicAttack/attack.gif")));
            ico.put(202, ImageIO.read(getClass().getResourceAsStream("/Spells/IncreasePower/power.gif")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        place[0] = 1;
        place[1] = 102;
        place[2] = 202;
        place[3] = 301;


        cooldown = new double[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}};

        tileSize = (int) (50 * GamePanel.SCALE);
    }


    public void draw(Graphics2D g, long delta) {
        g.setColor(new Color(0.3f, 0.3f, 0.8f, 0.3f));
        for (int i = 0; i < place.length; i++) {
            g.fillRect(GamePanel.WIDTH / 2 - 160 + (tileSize + 5) * i, GamePanel.HEIGHT - 110, tileSize, tileSize);
        }
        for (int i = 0; i < 3; i++) {
            g.drawImage(ico.get(place[i]), GamePanel.WIDTH / 2 - 160 + (tileSize + 5) * i, GamePanel.HEIGHT - 110, tileSize, tileSize, null);
            if (cooldown[i][0] > 0) {
                g.setColor(cd);
                g.fillRect(GamePanel.WIDTH / 2 - 160 + (tileSize + 5) * i, GamePanel.HEIGHT - 110, tileSize, (int) (tileSize * (cooldown[i][0] / cooldown[i][1])));
                cooldown[i][0] -= (int) delta / 1000000d;
            } else if (cooldown[i][0] != 0) {
                cooldown[i][0] = 0;
            }
        }
    }

    private double[][] cooldown;

    public SpellManageable use(TileMap tm, boolean r, int n) {
        SpellManageable s = null;
        if (cooldown[n][0] == 0) {
            switch (place[n]) {
                case 1:
                    s = new BasicAttack();
                    System.out.println("basickAttack");
                    break;
                case 101:
                    s = new FireBall(tm, r, 1);
                    break;
                case 102:
                    s = new FireBall(tm, r, 2);
                    break;
                case 103:
                    s = new FireBall(tm, r, 3);
                    break;
                case 202:
                    s = new IncreasePower(tm, r, 2);
                    break;
                case 301:
                    s = new MultiAttack(1);
                default:
                    break;
            }
            cooldown[n][0] = s.getCooldown();
            cooldown[n][1] = s.getCooldown();
        }
        return s;
    }
}
