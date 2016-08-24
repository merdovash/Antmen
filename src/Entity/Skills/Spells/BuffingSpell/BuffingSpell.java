package Entity.Skills.Spells.BuffingSpell;

import Entity.Skills.Spells.Spell;
import TileMap.TileMap;

abstract class BuffingSpell extends Spell {

    BuffingSpell(TileMap tm, boolean right) {
        super(tm, right);
        type = 1;
    }

    int time;
    protected boolean active;
    protected boolean drawAnimation;
    long start;
    boolean used;

    protected void setStop() {

    }

}
