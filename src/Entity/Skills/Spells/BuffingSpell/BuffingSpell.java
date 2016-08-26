package Entity.Skills.Spells.BuffingSpell;

import Entity.Skills.Spells.Spell;

abstract class BuffingSpell extends Spell {

    BuffingSpell() {
        super();
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
