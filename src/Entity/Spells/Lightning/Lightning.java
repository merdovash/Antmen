package Entity.Spells.Lightning;

import Entity.Spells.Spell;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 8/2/2016.
 */
public abstract class Lightning extends Spell {

    protected final static String path = "/Spells/Lightning/lightning.gif";

    protected ArrayList<Integer[]> place;

    protected double speed;
    protected long lastTime;
    protected int range;
    protected double l;

    public Lightning(TileMap tm, boolean right) {
        super(tm, right);
        loadAnimation();
        lastTime=System.nanoTime();
        l=0;
    }

    public void update(){
        long delta =System.nanoTime() - lastTime;
        l+=speed*delta;
        if (l>range) l=range;
        if (l/size>place.size()){
            place.add(new Integer[]{(int)l,(int)y});
        }
    }

    public void draw(Graphics2D g){
        for (int i=0;i<place.size();i++){
            x=place.get(i)[0];
            y=place.get(i)[1];
            g.drawImage(animation.getImage(),(int)(x+size*i),(int)y,null);
        }
    }

}
