package Entity.Objects;

import Entity.MapObject;
import TileMap.TileMap;

import java.awt.*;

public class Block extends MapObject {

    public Block(TileMap tm) {
        super(tm);

        //sprites
        adressImage = "";
        numFrames = new int[]{1};
        loadSprites();
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
