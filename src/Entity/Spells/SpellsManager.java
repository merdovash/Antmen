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

    }

    public int getId1(){ return id[0];}
    public int getId2(){ return id[1];}
    public int getId3(){ return id[2];}


    public void draw(Graphics2D g){
        g.drawImage(menu,GamePanel.WIDTH/2-160,GamePanel.HEIGHT-110,(int)(menu.getWidth()*0.66), menu.getHeight(),null);
        g.drawImage(ico.get(place[0]), GamePanel.WIDTH/2-150, GamePanel.HEIGHT-100, null);
        g.drawImage(ico.get(place[1]), GamePanel.WIDTH/2-90, GamePanel.HEIGHT-100, null);
        g.drawImage(ico.get(place[2]), GamePanel.WIDTH/2-30, GamePanel.HEIGHT-100, null);
    }

    public Spell use( TileMap tm, boolean r, int n){
        switch (place[n]){
            case 101:
                return new FireBall(tm, r, 1);
            case 102:
                return new FireBall(tm, r, 2);
            case 103:
                return new FireBall(tm, r, 3);
            default:
                return null;
        }
    }
}
