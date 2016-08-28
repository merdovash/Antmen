package Entity.NewSkills;

import Entity.Players.Stats;
import TileMap.TileMap;

public abstract class Skill {
    private TileMap tileMap;
    private Stats stats;

    protected long cooldown;
    protected int cost;

    protected byte hand;

    Skill(int level, byte hand) {

    }

    public void start(Stats s, TileMap tm) {
        stats = s;
        tileMap = tm;
    }

    public long getCooldown() {
        return cooldown;
    }

    public int getCost() {
        return cost;
    }

    public byte getHand() {
        return hand;
    }

}
