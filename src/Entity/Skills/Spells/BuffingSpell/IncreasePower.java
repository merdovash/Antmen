package Entity.Skills.Spells.BuffingSpell;

import Entity.Buffs.Buff;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class IncreasePower extends BuffingSpell {

    public IncreasePower(int level) {
        super();
        power = level;
        time = 20;
        used = true;
        manacost = 50;
        cooldown = 35000;
    }

    @Override
    public void start(TileMap tm, boolean right) {
        super.init(tm, right);
    }

    @Override
    public void update() {
        if (start == 0) start = System.currentTimeMillis();
        if (System.currentTimeMillis() >= start + time * 1000) {
            remove = true;
        }
    }

    @Override
    public Object[][] getBuff() {
        if (used) {
            used = false;
            return new Object[][]{
                    {true, Buff.SPELLS, Buff.ATTACK_DMG, time * 1000, power}
            };
        } else {
            return null;
        }
    }

    public void draw(Graphics2D g) {
        if (!remove) {
            g.setColor(new Color(0.7f, 0.2f, 0.3f, 0.5f));
            g.fillRoundRect((int) (GamePanel.WIDTH - 50 * GamePanel.SCALE), 150, (int) (50 * GamePanel.SCALE), (int) (50 * GamePanel.SCALE), 25, 25);
        }
    }
}
