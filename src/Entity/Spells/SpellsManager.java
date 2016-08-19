package Entity.Spells;

import Entity.Spells.FireBall.FireBall;
import Main.GamePanel;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MERDovashkinar on 8/2/2016.
 */
public class SpellsManager {
    private Map<Integer, BufferedImage> ico;
    private BufferedImage menu;
    private int id[];
    private int place[];

    private final Color cd = new Color(0, 0, 0, 0.7f);


    public SpellsManager(){
        place = new int[3];
        ico = new HashMap();
        ico.put(0,null);
        for (int i=1;i<4;i++){
            try {
                ico.put(100+i,ImageIO.read(getClass().getResourceAsStream("/Spells/FireBall/"+i+".gif")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            menu = ImageIO.read(getClass().getResourceAsStream("/Spells/menu.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        place[0]=101;
        place[1]=102;
        place[2]=103;

        cooldown = new double[][]{{0, 0}, {0, 0}, {0, 0}};

    }

    public int getId1(){ return id[0];}
    public int getId2(){ return id[1];}
    public int getId3(){ return id[2];}


    public void draw(Graphics2D g, long delta) {
        g.drawImage(menu,GamePanel.WIDTH/2-160,GamePanel.HEIGHT-110,(int)(menu.getWidth()*0.66), menu.getHeight(),null);
        for (int i = 0; i < 3; i++) {
            g.drawImage(ico.get(place[i]), GamePanel.WIDTH / 2 - 150 + 60 * i, GamePanel.HEIGHT - 100, null);
            if (cooldown[i][0] > 0) {
                g.setColor(cd);
                g.fillRect(GamePanel.WIDTH / 2 - 150 + 60 * i, GamePanel.HEIGHT - 100, menu.getHeight(), (int) (menu.getHeight() * (cooldown[i][0] / cooldown[i][1])));
                cooldown[i][0] -= delta / 1000000d;
            } else if (cooldown[i][0] != 0) {
                cooldown[i][0] = 0;
            }
        }
    }

    private double[][] cooldown;

    public Spell use(TileMap tm, boolean r, int n) {
        Spell s = null;
        if (cooldown[n][0] == 0) {
            switch (place[n]) {
                case 101:
                    s = new FireBall(tm, r, 1);
                    break;
                case 102:
                    s = new FireBall(tm, r, 2);
                    break;
                case 103:
                    s = new FireBall(tm, r, 3);
                    break;
                default:
                    break;
            }
            cooldown[n][0] = s.getCooldown();
            cooldown[n][1] = s.getCooldown();
        }
        return s;
    }
}
