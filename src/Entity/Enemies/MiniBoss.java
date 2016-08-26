package Entity.Enemies;

import Entity.Items.Armor.Headset.Helmet;
import Entity.Items.GrabPoint;
import Entity.Players.Inventory;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;

public class MiniBoss extends Enemy {

    protected MiniBoss(TileMap tm) {
        super(tm);

        inventory = new Inventory(attack, defence);
        inventory.equip(new Helmet());


    }

    protected void init() {
        width *= 1.25;
        height *= 1.25;

        headPoint = new GrabPoint((int) (x + xmap + width * scale / 2), (int) (y + ymap - height * scale + 5 * scale), facingRight, (int) ((width - 28) * GamePanel.SCALE));
    }

    public void update() {
        super.update();

        headPoint.update((int) (x + xmap + width * scale / 2), (int) (y + ymap - (height - 42) * scale), facingRight);
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        drawArmory(g, new long[]{0, 0, 0});
    }

    protected void drawHealth(Graphics2D g) {
        double proc = health.getHealth() / health.getMaxHealth();

        g.fillRect((int) (x + xmap - 25 * scale), (int) (y + ymap - (height + 40) * scale), (int) ((width + 50) * scale * proc), 10);
        drawName(g);
    }
}
