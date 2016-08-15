package Entity.Items;

import Entity.MapObject;
import TileMap.TileMap;

import java.awt.*;


public class MapItem extends MapObject {

    private int id;

    public MapItem(TileMap tm,int id) {
        super(tm);


        this.id=id;
        adressImage = ItemList.getAddressImage(id);
        facingRight = true;
        width = 96;
        height = 96;
        numFrames = new int[]{1};
        loadSprites();
        animation.setFrames(sprites.get(0));
        animation.setDelay(-1);

        width = 50;
        height = 50;

    }


    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public int getID(){
        return id;
    }


}
