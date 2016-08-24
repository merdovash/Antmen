package Entity.Skills.Spells.FireBall;

import Entity.Battle.Attack;
import Entity.Skills.Spells.Spell;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class FireBall extends Spell {

    public FireBall(TileMap tm, boolean right, int level) {
        super(tm , right);

        attack = new Attack(damage = level, Attack.FIRE, 0.1 * level);

        power = level * 5;

        this.level=level;
        moveSpeed = level * 2 + 3;
        manacost = 10*level;
        cooldown =(int) (400*level/1.5);
        if (right){
            dx = moveSpeed * 0.6;
        }else {
            dx = -moveSpeed * 0.6;
        }

        cwidth=14;
        cheight=14;

        //load sprite
        width = 30;
        height = 30;
        adressImage = "/Spells/fireball.gif";
        numFrames = new int[]{4, 3};
        loadSprites();
        animation.setFrames(sprites.get(0));
        width = (int) ((10 + 5 * level) * GamePanel.SCALE);
        height = (int) ((10 + 5 * level) * GamePanel.SCALE);
        animation.setDelay(300);

        type = 0;
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

    @Override
    public Object[][] getBuff() {
        return new Object[0][];
    }


    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
