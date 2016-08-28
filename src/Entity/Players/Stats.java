package Entity.Players;

import Entity.Battle.Attack;
import Entity.Battle.AttackSpeed;
import Entity.Battle.Defence;
import Entity.Buffs.Buff;
import Entity.Skills.Spells.Spell;
import Entity.States.Energy;
import Entity.States.Health;
import Main.GamePanel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Stats implements Serializable {


    public static final int SPEED = 0;
    public static final int BOOST_SPEED = 1;
    public static final int SPELL_DAMAGE = 2;
    public static final int SPELL_SPEED = 3;
    public static final int SPELL_POWER = 4;
    public static final int ATTACK = 5;
    public static final int ENERGY_CONSUMP = 6;
    public static final int STR = 7;
    public static final int INT = 8;
    public static final int DEX = 9;
    public static final int VIT = 10;
    public static final int AGI = 11;
    public static final int SPK = 12;
    public static final int HEALTH = 13;
    public static final int HEALTH_REGEN = 14;
    public static final int MANA = 15;
    public static final int MANA_REGEN = 16;
    public static final int MANA_CONSUMP = 17;
    public static final int ENERGY = 18;
    public static final int ENERGY_REFILL = 19;
    public static final int ATTACK_SPEED = 20;

    //elements
    public static final int WEAPON = 0;
    public static final int ARMOR = 1;

    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;

    //health
    protected Health health;

    //energy
    public Energy mana;
    Energy energy;

    //level
    private int level;
    private long experience;
    private long nextLevel;
    private boolean congats;

    //stats
    private Param str;
    private Param intel;
    private Param dex;
    private Param vit;
    private Param agi;
    private Param spk;

    private int freePoints;
    private int skillPoints;

    private double boostSpeed;

    private double speed;

    public Inventory inventory;

    public Buff buff;

    //attack
    public Attack attack;
    private int attackPower;
    private AttackSpeed attackSpeed;

    Defence defence;
    private ArrayList<Attack> magicAttack;



    Stats() {
        health = new Health(3);
        level = 1;
        experience = 0;
        nextLevel = 100;

        freePoints = 0;

        str = new Param(1);
        intel = new Param(5);
        dex = new Param(1);
        vit = new Param(2);
        agi = new Param(1);
        spk = new Param(3);


        energy = new Energy(100, Energy.ENERGY);
        energy.setConsumption(4);
        energy.setRefillSpeed(1);

        mana = new Energy(100, Energy.MAGIC);
        mana.setRefillSpeed(1);

        speed = 20 * GamePanel.SCALE;
        boostSpeed = speed * 2.5;

        buff = new Buff();

        attack = new Attack(Attack.TYPE_ENCHANCED);
        attackSpeed = new AttackSpeed();
        magicAttack = new ArrayList<>();
        defence = new Defence();

        permaUpdate();
    }

    void setInventory(Inventory i) {
        inventory = i;
    }

    public void addExp(int exp) {
        experience += exp;
        if (experience >= nextLevel) {
            congats = true;
            levelUp();
            experience = (experience - nextLevel);
            nextLevel += Math.pow((level * 5 + 2), 2);
        }
    }

    private void levelUp() {
        level++;
        freePoints += 2 + level / 20;
        skillPoints += 1;
        health.levelUp(0.5);
        health.heal((int) (health.getMaxHealth()));
    }

    boolean getCongrats() {
        if (congats) {
            congats = false;
            return true;
        }
        return false;
    }

    public void update(long delta) {
        mana.refill((delta));
        health.update(delta);
        health.addBuff(buff);

        speed = (20 + buff.getBuff(Buff.AGI) / 20 + buff.getBuff(Buff.DEX) / 140) * GamePanel.SCALE;
        boostSpeed = (2.5 + buff.getBuff(Buff.AGI) / 40);
        attackSpeed.update(delta);

        buff.update(delta);
        attackSpeed.addBuff(buff);
        attack.addBuff(buff);
    }

    public void permaUpdate() {
        if (inventory != null) {
            if (inventory.getWeapon() != null) {
                addBuffs(inventory.getWeapon().getBuff());
                attack.setWeapon(inventory.getWeapon());
                attackPower = (int) (inventory.getWeapon().getPower());
            }
            if (inventory.getHelm() != null) {
                addBuffs(inventory.getHelm().getBuff());
                defence.addArmor(inventory.getHelm());
            }
            attackSpeed.setWeapon(inventory.getWeapon());
        }
        buff.addBuff(Buff.STATS, Buff.STR, -1, str.getValue());
        buff.addBuff(Buff.STATS, Buff.INT, -1, intel.getValue());
        buff.addBuff(Buff.STATS, Buff.DEX, -1, dex.getValue());
        buff.addBuff(Buff.STATS, Buff.VIT, -1, vit.getValue());
        buff.addBuff(Buff.STATS, Buff.AGI, -1, agi.getValue());
        buff.addBuff(Buff.STATS, Buff.SPK, -1, spk.getValue());
    }

    void addBuffs(Object[][] b) {
        if (b != null) {
            for (int i = 0; i < b.length; i++) {
                if ((boolean) b[i][0]) {
                    buff.addBuff((int) b[i][1], (int) b[i][2], (int) b[i][3], (int) b[i][4]);
                } else {
                    buff.addBuff((int) b[i][1], (int) b[i][2], (int) b[i][3], (double) b[i][4]);
                }

            }
        }
    }

    Attack spellAttack(Spell s) {
        Attack a = null;
        if (s.getAttack() != null) {
            a = s.getAttack();
            a.addBuff(buff);
        }
        s.setCooldown((s.getCooldown() - buff.getBuff(Buff.SPELL_COOLDOWN, 0)) / buff.getBuff(Buff.SPELL_COOLDOWN, 1));
        s.setManacost((s.getManacost() - buff.getBuff(Buff.SPELL_MANACOST, 0)) / buff.getBuff(Buff.SPELL_MANACOST, 1));
        return a;
    }

    private void drawExp(Graphics2D g) {
        double proc = (experience * GamePanel.WIDTH / nextLevel);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, GamePanel.HEIGHT - 10, GamePanel.WIDTH, 10);
        g.setColor(Color.GREEN);
        g.fillRect(0, GamePanel.HEIGHT - 10, (int) proc, 10);
    }

    public void draw(Graphics2D g) {
        mana.draw(g, 1, new Color(50, 50, 100));
        energy.draw(g, 0, new Color(50, 100, 50));
        health.draw(g);

        drawExp(g);
    }


    public void useFreePoints(int i) {
        this.freePoints += i;
    }

    public int[] getStats() {
        return new int[]{str.getValue(), intel.getValue(), dex.getValue(), vit.getValue(), agi.getValue(), spk.getValue()};
    }
    public int getFreePoints() {
        return freePoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }
    public int getLevel() {
        return level;
    }
    public Param getStat(int place) {
        switch (place) {
            case 0:
                return str;
            case 1:
                return intel;
            case 2:
                return dex;
            case 3:
                return vit;
            case 4:
                return agi;
            case 5:
                return spk;
            default:
                return null;
        }
    }

    double getSpeed() {
        return speed;
    }

    double getBoostSpeed() {
        return speed * boostSpeed;
    }

    public double getAttackSpeed() {
        System.out.println("attack speed " + attackSpeed.getAttackSpeed());
        return attackSpeed.getAttackSpeed() * 1000;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
