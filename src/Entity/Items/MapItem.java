package Entity.Items;

import Entity.Item;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;


public class MapItem extends Item {

    private Rectangle rectangle;

    public MapItem(TileMap tm) {
        super(tm);
    }

    @Override
    public void init() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream(adress));

        }catch (Exception e){
            e.printStackTrace();
        }
        width = 50;
        height= 50;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(sprite,
                (int)(x+xmap),
                (int)(y+ymap-height),
                width,
                height,
                null);

        rectangle=getRectangle();
        g.setColor(Color.GREEN);
        g.draw(rectangle);
    }


}
