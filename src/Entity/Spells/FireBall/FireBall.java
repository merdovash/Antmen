package Entity.Spells.FireBall;

import Entity.Animation;
import Entity.Spells.Spell;
import Main.GamePanel;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by MERDovashkinar on 7/30/2016.
 */
public class FireBall extends Spell {
    BufferedImage ico;
    double k;

    public FireBall(TileMap tm, boolean right, int level) {
        super(tm , right);

        power =level*10;

        this.level=level;
        k=level/3d;
        moveSpeed=2+level;
        manacost = 10*level;
        damage = 1*level;
        cooldown =(int) (400*level/1.5);
        if (right){
            dx=moveSpeed;
        }else {
            dx=-moveSpeed;
        }
        width =30;
        height =30;

        cwidth=14;
        cheight=14;

        //load sprites
        loadAnimation();


    }

    protected void loadAnimation(){
        try {
            BufferedImage spritesheets = ImageIO.read(getClass().getResourceAsStream("/Spells/fireball.gif"));
            sprites = new BufferedImage[4];
            for (int i=0; i<sprites.length;i++){
                sprites[i] = spritesheets.getSubimage(i*width, 0,  width, height);
            }
            hitSprites = new BufferedImage[3];
            for (int i=0; i<hitSprites.length;i++){
                hitSprites[i]=spritesheets.getSubimage(i*width, height, width, height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update(){
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
         if (dx==0 && !hit){
             setHit();
         }

        animation.update();

        if (hit && animation.hasPlayedOnce()){
            remove=true;
        }
    }

    public void draw(Graphics2D g) {

        setMapPosition();
        if (facingRight) {
            g.drawImage(
                    animation.getImage(),
                    (int) (x + xmap - width * GamePanel.SCALE / 2),
                    (int) (y + ymap - height * GamePanel.SCALE / 2),
                    (int)((15+level*5)*GamePanel.SCALE),
                    (int)((15+level*5)*GamePanel.SCALE),
                    null
            );
        } else {
            g.drawImage(
                    animation.getImage(),
                    (int) (x + xmap + width * GamePanel.SCALE / 2),
                    (int) (y + ymap - height * GamePanel.SCALE / 2),
                    -(int)((15+level*5)*GamePanel.SCALE),
                    (int)((15+level*5)*GamePanel.SCALE),
                    null
            );

        }
        rectangle= new Rectangle((int)(x +xmap-width/2),(int)(y+ymap-height/2),width, height);
        g.setColor(Color.GREEN);
        g.draw(rectangle);
    }
}
