package Entity.Items.Loot;


import Entity.Items.MapItem;
import TileMap.TileMap;

import java.awt.*;


public class Branch extends MapItem {

    public Branch(TileMap tm) {
        super(tm);
        adress+="Loot/branch.gif";
        super.init();
        ID=1;
    }

    public void draw(Graphics2D g){
        super.draw(g);
    }

}
