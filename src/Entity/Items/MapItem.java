package Entity.Items;

import Entity.MapObject;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public class MapItem extends MapObject {

    private Rectangle rectangle;
    private BufferedImage sprite;
    private String adress;
    private int id;

    public MapItem(TileMap tm,int id) {
        super(tm);
        System.out.println(id);
        adress = ItemList.getString(id);
        this.id=id;
        init();
    }

    public void init() {
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream(adress));

        }catch (Exception e){
            e.printStackTrace();
        }
        width = 50;
        height= 50;
    }

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

    public int getID(){
        return id;
    }


}
