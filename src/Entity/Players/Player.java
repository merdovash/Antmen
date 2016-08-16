package Entity.Players;

import Entity.ActiveMapObject;
import Entity.Enemies.Enemy;
import Entity.Spells.Spell;
import Entity.Spells.SpellsManager;
import Entity.States.Energy;
import Entity.States.Health;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Player extends ActiveMapObject {

    //respawn
    private int respawnX;
    private int respawnY;

    //spells
    private SpellsManager sm;

    // fireball
    private boolean firing;
    public ArrayList<Spell> spells;
    private Energy mana;


    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;

    // gliding
    private boolean gliding;


    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;

    // inventory
    public Inventory inventory;

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

        moveSpeed = 20 * GamePanel.SCALE;
        jumpStart = -55 * GamePanel.SCALE;
        boostSpeed = 2.5;

        energy = new Energy(400);
        energy.setConsumption(15);

        mana = new Energy(200);
        mana.setRefillSpeed(2);

        facingRight = true;

        health = new Health(10);

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

        inventory = new Inventory();
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

    public void use1spell(boolean b) {
        spell1 = b;
    }

    public void use2spell(boolean b) {
        spell2 = b;
    }

    public void use3spell(boolean b) {
        spell3 = b;
    }

    public void checkAtack(ArrayList<Enemy> enemies) {


        for (Enemy enemy : enemies) {
            if (scratching) {
                if (facingRight) {
                    if (enemy.getx() > x && enemy.getx() < x + scratchRange && enemy.gety() > y - height / 2 && enemy.gety() < y + height / 2) {
                        enemy.hit(scratchDamage);
                    }
                } else {
                    if (enemy.getx() < x && enemy.getx() > x - scratchRange && enemy.gety() > y - height / 2 && enemy.gety() < y + height / 2) {
                        enemy.hit(scratchDamage);
                    }
                }
            }
            for (Spell spell : spells) {
                if (spell.intersects(enemy)) {
                    enemy.hit(spell.getDamage());
                    spell.setHit();
                    break;
                }

            }

            if (rectangle.intersects(enemy.getRectangle())) {
                hit(enemy.getDamage());
            }
        }
    }


    private void useSpells() {
        if (spell1 && currentAction != FIREBALL) {
            Spell s = sm.use(tileMap, facingRight, 0);
            if (mana.use(s.cooldown, s.manacost)) {
                s.setPosition(x, y, height);
                spells.add(s);
            }
        } else if (spell2 && currentAction != FIREBALL) {
            Spell s = sm.use(tileMap, facingRight, 1);
            if (mana.use(s.cooldown, s.manacost)) {
                s.setPosition(x, y, height);
                spells.add(s);
            }
        } else if (spell3 && currentAction != FIREBALL) {
            Spell s = sm.use(tileMap, facingRight, 2);
            if (mana.use(s.cooldown, s.manacost)) {
                s.setPosition(x, y, height);
                spells.add(s);
            }
        }
    }

    public void respawn() {
        x = respawnX;
        y = respawnY;
        health.setAlive();
        dead = false;
    }


    public void update() {
        if (!dead) {
            super.update();

            //trace
            //trace.addPlace((int) (x + width / 2), (int) (y - 25), tileMap);

            //refill energy
            if (!boost || energy.isEmpty()) energy.refill(delta);

            //check attack has stopped
            if (currentAction == SCRATCHING) {
                if (animation.hasPlayedOnce()) scratching = false;
            }
            if (currentAction == FIREBALL) {
                if (animation.hasPlayedOnce()) firing = false;
            }

            //spell atack;
            mana.refill(delta);
            useSpells();

            //update firebals
            for (int i = 0; i < spells.size(); i++) {
                spells.get(i).update();
                if (spells.get(i).checkRemove()) {
                    spells.remove(i);
                    i--;
                }
            }

            //set animation
            firing = spell1 || spell2 || spell3;


            // set direction
            if (currentAction != SCRATCHING && currentAction != FIREBALL) {
                if (right) facingRight = true;
                if (left) facingRight = false;
            }
        } else {
            //time
            delta = System.nanoTime() - lastTime;
            lastTime = System.nanoTime();
        }
        setAnimation();
    }


    private void setAnimation() {
        // set animation
        if (scratching) {
            if (currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 82;
            }
        } else if (firing) {
            if (currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 82;
            }
        } else if (dy > 0) {
            if (gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 82;
                }
            } else if (currentAction != FALLING) {
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
            if (boost && !energy.isEmpty()) {
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


    public void draw(Graphics2D g) {
        setMapPosition();

        for (Spell spell : spells) {
            spell.draw(g);
        }

        health.draw(g);
        energy.draw(g, 0, energy.GREEN_RED);
        mana.draw(g, 1, energy.RED_BLUE);

        sm.draw(g);

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
            if (inventory.getHelm() != null) {
                g.drawImage(inventory.getHelm().getImage(), (int) (x + xmap + 2 * scale), (int) (y + ymap - (height - (-30)) * scale), (int) (85 * scale), (int) (85 * scale), null);
            }
        }
    }


    public void setRespawn(int x, int y) {
        respawnX = x;
        respawnY = y;
    }
}