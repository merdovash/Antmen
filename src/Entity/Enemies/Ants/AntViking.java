package Entity.Enemies.Ants;

import Entity.Animation;
import Entity.Enemies.DropList;
import Entity.Enemies.MiniBoss;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class AntViking extends MiniBoss {

    public AntViking(TileMap tm) {
        super(tm);

        width = 71;
        height = 135;

        //load Sprites
        numFrames = new int[]{3};
        adressImage = "/Sprites/Enemies/ant.gif";
        loadSprites();
        animation = new Animation();
        animation.setFrames(sprites.get(0));
        animation.setDelay(300);

        moveSpeed = 15 * GamePanel.SCALE;

        health = new Health(15);
        damage = 2;
        weight = 7;

        agressive = true;
        visionX = 400;
        visionY = 100;

        right = true;
        left = false;
        lastTime = System.nanoTime();

        fallable = true;

        //add loot
        loot = new DropList();
        loot.add(1d, 1);
        loot.add(0.8, 2);

        exp = 75;

        init();
    }

    public void update() {
        super.update();


        if (dx == 0) {
            if (!enemy) {
                if (right) {
                    setLeft();
                } else {
                    setRight();
                }
            }
        }

        //change direction
        facingRight = right;

    }

    public void draw(Graphics2D g) {
        setMapPosition();

        super.draw(g);


    }

}
