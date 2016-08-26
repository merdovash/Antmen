package Entity.Players;

import Entity.ActiveMapObject;
import Entity.Enemies.Enemy;
import Entity.Items.Armor.Headset.Helmet;
import Entity.Items.GrabPoint;
import Entity.Items.Weapons.Knifes.Knife;
import Entity.Items.Weapons.Swords.Sword;
import Entity.Skills.Attacking;
import Entity.Skills.Skill;
import Entity.Skills.SpellManageable;
import Entity.Skills.Spells.Spell;
import Entity.Skills.SpellsManager;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Player extends ActiveMapObject implements Serializable {

    //moving
    private double basicmoveSpeed;

    //respawn
    private int respawnX;
    private int respawnY;

    //spells
    private SpellsManager sm;

    // fireball
    private boolean firing;
    public ArrayList<Spell> spells;


    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;
    private boolean attacking = false;

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;

    // inventory

    //Stats
    public Stats stats;

    //skills
    private boolean skill;
    private Skill skills;
    private boolean newSkill;


    //trace
    //private Trace trace;


    public Player(TileMap tm) {

        super(tm);

        init();
    }

    private void init() {


        width = 82;
        height = 136;
        cwidth = width / 2;
        cheight = height / 2 - 15;

        facingRight = true;
        basicmoveSpeed = moveSpeed = 20 * GamePanel.SCALE;
        jumpStart = -55 * GamePanel.SCALE;

        spells = new ArrayList<>();
        sm = new SpellsManager();


        scratchDamage = 8;
        scratchRange = 40;

        lastTime = System.nanoTime();


        // load sprite
        numFrames = new int[]{2, 6, 1, 1, 1, 1, 3};
        adressImage = "/Sprites/Player/murisprites.gif";
        loadSprites();

        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);

        pik = false;

        //trace = new Trace();
        rectangle = getRectangle();

        fallable = false;

        respawnX = 200;
        respawnY = 200;

        inventory = new Inventory(attack, defence);
        inventory.addItem(new Sword());
        inventory.addItem(new Helmet());
        inventory.addItem(new Knife());

        headPoint = new GrabPoint((int) (x + xmap + (width / 2 + 2) * scale), (int) (y + ymap - (height - 25) * scale), facingRight, (int) ((width - 35) * GamePanel.SCALE));
        weaponPoint = new GrabPoint(0, 0, facingRight, (int) ((10) * GamePanel.SCALE));

        stats = new Stats();
        AI = false;
        stats.setInventory(inventory);
    }

    public void load(TileMap tm, int x, int y) {
        tileMap = tm;
        this.x = x;
        this.y = y;
    }

    public void setScratching() {
        scratching = true;
    }

    public void setBoost(boolean b) {
        boost = b;
    }

    private boolean spell1;
    private boolean spell2;
    private boolean spell3;
    private boolean spell4;

    public void use1spell(boolean b) {
        spell1 = b;
    }
    public void use2spell(boolean b) {
        spell2 = b;
    }
    public void use3spell(boolean b) {
        spell3 = b;
    }
    public void use4spell(boolean b) {
        spell4 = b;
    }

    public void checkAtack(ArrayList<Enemy> enemies) {

        for (Enemy enemy : enemies) {
                if (inventory.getWeapon() == null) {
                    if (facingRight) {
                        if (enemy.getx() > x && enemy.getx() < x + scratchRange && enemy.gety() > y - height / 2 && enemy.gety() < y + height / 2) {
                            enemy.hit(scratchDamage);
                        }
                    } else {
                        if (enemy.getx() < x && enemy.getx() > x - scratchRange && enemy.gety() > y - height / 2 && enemy.gety() < y + height / 2) {
                            enemy.hit(scratchDamage);
                        }
                    }
                } else if (skills != null) {
                    if (attackPlace != null) {
                        if (attackPlace.intersects(enemy.getRectangle())) {
                            System.out.println("find enemy ");
                            enemy.hit(skills.getAttack());
                            enemy.punch(skills.getPower(), (int) (x));
                            try {
                                ((Attacking) skills).setHit();
                            } catch (ClassCastException e) {
                                System.out.println("setColor hit failed");
                            }

                        }
                    }
                }

            if (rectangle.intersects(enemy.getRectangle())) {
                hit(enemy.getDamage());

            }
            for (int i = 0; i < spells.size(); i++) {
                if (spells.get(i).getRectangle().intersects(enemy.getRectangle()) && spells.get(i).isActive()) {
                    enemy.hit(spells.get(i).getAttack());
                    spells.get(i).setHit();
                }
            }
        }
    }

    public void hit(int damage) {
        if (flinching) return;
        stats.health.atacked(damage);
        flinching = true;
        flinchTimer = System.nanoTime();
        if (stats.health.getHealth() == 0) {
            dead = true;
            x = 0;
            y = 0;
        }
    }

    private void useSpells() {
        Spell s;
        SpellManageable smb = null;
        if (spell1 && currentAction != FIREBALL) {
            smb = sm.use(tileMap, facingRight, 0);
            spell1 = false;
        } else if (spell2 && currentAction != FIREBALL) {
            smb = sm.use(tileMap, facingRight, 1);
            spell2 = false;
        } else if (spell3 && currentAction != FIREBALL) {
            smb = sm.use(tileMap, facingRight, 2);
            spell3 = false;
        } else if (spell4 && currentAction != FIREBALL) {
            smb = sm.use(tileMap, facingRight, 3);
            spell4 = false;
        }
        if (smb != null) {
            if (!smb.isSkill()) {
                s = (Spell) smb;
                s.start(tileMap, facingRight);
                s.setAttack(stats.spellAttack(s));
                useSpell(s);
            } else {
                if (skills == null) {
                    useSkill((Skill) smb);
                }
            }
        }
    }

    private void useSpell(Spell s) {
        if (stats.mana.use(s.manacost)) {
            s.setPosition(x, y, height);
            spells.add(s);
        }
    }

    public void respawn() {
        x = respawnX;
        y = respawnY;
        stats.health.setAlive();
        dead = false;
    }


    private boolean attackAnimation;
    private Rectangle attackPlace;
    private long atackAnimationX;
    public void update() {
        if (!dead) {
            updateSpeed();
            super.update();

            stats.update(delta);

            updateSkills();
            useSpells();

            //refill energy
            if (!boost || stats.energy.isEmpty()) stats.energy.refill(delta);

            //check attackAnimation has stopped
            if (currentAction == SCRATCHING) {
                if (animation.hasPlayedOnce()) {
                    scratching = false;
                }
            }
            if (currentAction == FIREBALL) {
                if (animation.hasPlayedOnce()) firing = false;
            }

            //setColor animation
            firing = spell1 || spell2 || spell3;


            // setColor direction
            if (currentAction != SCRATCHING && currentAction != FIREBALL) {
                if (right) facingRight = true;
                if (left) facingRight = false;
            }


        } else {
            //time
            delta = System.nanoTime() - lastTime;
            lastTime = System.nanoTime();
        }

        //update firebals
        for (int i = 0; i < spells.size(); i++) {
            spells.get(i).update();
            if (spells.get(i).getType() == 1) {
                stats.addBuffs(spells.get(i).getBuff());
            }
            if (spells.get(i).checkRemove()) {
                spells.remove(i);
                i--;
            }

        }

        setAnimation();

        if (stats.getCongrats()) {
            levelUp = true;
            levelUpTime = System.currentTimeMillis();
            place = 0;
        }

    }

    private void updateSpeed() {
        if (boost && !stats.energy.isEmpty()) {
            stats.energy.consump(delta);
            moveSpeed = stats.getBoostSpeed();
        } else {
            moveSpeed = stats.getSpeed();
        }
    }

    private void updateSkills() {
        if (skills != null) {
            if (newSkill) {
                newSkill = false;
                skills.start(stats);
            }
            skills.update(delta);
            if (stats.inventory.getWeapon() != null) sword(skills.getPositionY());
            if (!skills.getActive()) {
                skills = null;
            }
        } else {
            attackPlace = null;
        }
    }

    private void sword(long[] place) {
        if (place[0] > 0) {
            if (facingRight) {
                attackPlace = new Rectangle(
                        (int) (weaponPoint.getX() + place[1] * GamePanel.SCALE),
                        (int) (weaponPoint.getY() + place[2] * GamePanel.SCALE),
                        (int) ((double) (place[0]) / 100 * inventory.getWeapon().getRange()),
                        10);
            } else {
                attackPlace = new Rectangle(
                        (weaponPoint.getX() - (int) ((double) (place[0]) / 100 * inventory.getWeapon().getRange() + place[1] * GamePanel.SCALE)),
                        (int) (weaponPoint.getY() + GamePanel.SCALE),
                        (int) ((double) (place[0]) / 100 * inventory.getWeapon().getRange()),
                        10);
            }
        } else {
            attackPlace = null;
        }
    }

    private void setAnimation() {
        // setColor animation
        if (scratching) {
            if (inventory.getWeapon() == null) {
                if (currentAction != SCRATCHING) {
                    currentAction = SCRATCHING;
                    animation.setFrames(sprites.get(SCRATCHING));
                    animation.setDelay(50);
                    width = 82;
                }
            }

        } else if (firing) {
            if (currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 82;
            }
        } else if (dy > 0) {
            if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 82;
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 82;
            }
        } else if (left || right) {
            if (boost && !stats.energy.isEmpty()) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 82;
                }
            } else if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 82;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 82;
            }
        }
    }

    private boolean levelUp = false;
    private long levelUpTime;
    private int place;
    public void draw(Graphics2D g) {
        setMapPosition();

        for (Spell spell : spells) {
            spell.draw(g);
        }

        drawUpdateGrabPoint();

        //trace.draw(g);

        // draw player
        if (flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }

        if (!dead) {
            super.draw(g);
            drawArmory(g, skills != null ? skills.getPositionY() : new long[]{0, 0, 0});
        }

        if (levelUp) {
            if (System.currentTimeMillis() - levelUpTime < 2000) {
                g.setColor(new Color(1, 1, 1, 0.5f));
                g.fillRect((int) (x + xmap + width * scale / 2 - place / 2), (int) (y + ymap - height * scale / 2 - place), (place), (int) ((width * scale / 2 + 100) - place));
                place = (int) (((System.currentTimeMillis() - levelUpTime) / 20) * scale);
            }
        }

        g.drawRect(weaponPoint.getX(), weaponPoint.getY(), 2, 2);
        if (attackPlace != null) {
            g.draw(attackPlace);
        }

    }

    private void drawUpdateGrabPoint() {
        headPoint.update((int) (x + xmap + (width / 2 + 2) * scale), (int) (y + ymap - (height - 42) * scale), facingRight);
        weaponPoint.update((int) (x + xmap + (width / 2 + weaponPoint.getSide() * 30) * scale), (int) (y + ymap - (height / 2 - 15) * scale), facingRight);
    }

    public void drawGui(Graphics2D g) {
        stats.draw(g);
        sm.draw(g, delta);
    }


    public void setRespawn(int x, int y) {
        respawnX = x;
        respawnY = y;
    }

    private void useSkill(Skill s) {
        if (stats.energy.consumpAbs(s.getCost())) {
            skills = s;
            newSkill = true;
        }
    }
}