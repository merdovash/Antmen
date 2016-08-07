package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 8/3/2016.
 */
public class Trace{
    private ArrayList<long[]> trace;
    private Color color;
    private TileMap tileMap;

    private int xmap;
    private int ymap;

    Trace (){
        color = new Color (50,50,50);
        trace = new ArrayList<>();
    }

    public void addPlace(int x, int y, TileMap tm){
        tileMap=tm;
        xmap = (int) tileMap.getx();
        ymap = (int) tileMap.gety();
        long[] f = {x,y, System.currentTimeMillis()};
        trace.add(f);
        if (trace.get(0)[2]+1000<System.currentTimeMillis()){
            trace.remove(0);
        }
    }

    public void draw(Graphics2D g){

        for (int i=0; i<trace.size();i++){
            g.setColor(color);
            g.fillRect((int)trace.get(i)[0]+xmap,(int)trace.get(i)[1]+ymap,5,5);
        }
    }
}
