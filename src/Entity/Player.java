package Entity;

import Entity.Spells.Spell;
import Entity.Spells.SpellsManager;
import Main.GamePanel;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends ActiveMapObject {
	
	// player stuff
	private int fire;
	private int maxFire;
	private boolean flinching;
	private long flinchTimer;


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
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		2, 6, 1, 1, 1, 1, 3
	};
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;

	// time
	private long lastTime;

    //trace
    private Trace trace;

    public Player(TileMap tm) {
		
		super(tm);


		width = 82;
		height = 136;
		cwidth = width/2;
		cheight = height/2-15;
		
		moveSpeed = 20* GamePanel.SCALE;
		jumpStart = -55* GamePanel.SCALE;
		boostSpeed = 2.5;

		energy = new Energy(200);
        energy.setConsumption(15);

        mana = new Energy(200);
        mana.setUsable(true);
		
		facingRight = true;
		
		health =new Health(10);
		fire = maxFire = 2500;

		spells = new ArrayList<Spell>();
        sm = new SpellsManager();

		
		scratchDamage = 8;
		scratchRange = 40;

		lastTime=System.nanoTime();



		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/murisprites.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i != 7) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {
						bi[j] = spritesheet.getSubimage(
								j * width * 2,
								i * height,
								width*2,
								height
						);
					}
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);

        pik=false;

        trace = new Trace();
        rectangle=getRectangle();
		
	}
	
	public int getHealth() { return health.getHealth(); }
	public int getMaxHealth() { return health.getMaxHealth(); }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	
	public void setFiring() { 
		firing = true;
	}
	public void setScratching() {
		scratching = true;
	}
	public void setGliding(boolean b) { 
		gliding = b;
	}
	public void setBoost(boolean b) {boost=b;}

    private boolean spell1;
    private boolean spell2;
    private boolean spell3;
    public void use1spell(boolean b) {spell1=b;}
    public void use2spell(boolean b) {spell2=b;}
    public void use3spell(boolean b) {spell3=b;}

    public void checkAtack(ArrayList<Enemy> enemies) {


        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (scratching) {
                if (facingRight) {
                    if (e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                } else {
                    if (e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                }
            }
            for (int j = 0; j < spells.size(); j++) {
                if (spells.get(j).intersects(e)) {
                    e.hit(spells.get(j).getDamage());
                    spells.get(i).setHit();
                    break;
                }

            }

            if (rectangle.intersects(e.getRectangle())){
                hit(e.getDamage());
            }
        }
    }

    private void hit(int damage){
        if (flinching) return;
        health.atacked(damage);
        flinching=true;
        flinchTimer = System.nanoTime();
    }


	protected void getNextPosition() {
		super.getNextPosition();

		// cannot move while attacking, except in air
		if(
		(currentAction == SCRATCHING || currentAction == FIREBALL) &&
		!(jumping || falling)) {
			dx = 0;
		}
	}

    public void useSpells(){
        if (spell1 && currentAction!=FIREBALL){
            Spell s = sm.use(tileMap,facingRight,0);
            if (mana.use(s.cooldown,s.manacost )){
                s.setPosition(x, y, height);
                spells.add(s);
            }
        }else if (spell2 && currentAction!=FIREBALL){
            Spell s = sm.use(tileMap,facingRight,1);
            if (mana.use(s.cooldown,s.manacost )){
                s.setPosition(x, y, height);
                spells.add(s);
            }
        }else if (spell3 && currentAction!=FIREBALL){
            Spell s = sm.use(tileMap,facingRight,2);
            if (mana.use(s.cooldown,s.manacost )){
                s.setPosition(x, y, height);
                spells.add(s);
            }
        }
    }

	public void respawn(){
		x=200;
		y=200;
		health.heal(health.getMaxHealth());

	}

	public void update() {

		delta=System.nanoTime()-lastTime;
		lastTime=System.nanoTime();

        trace.addPlace((int)(x+width/2),(int)(y-25),tileMap);
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		//move();

		if(!boost|| energy.isEmpty()) energy.refill(delta);


		//check atcke has stoped
		if (currentAction==SCRATCHING){
			if (animation.hasPlayedOnce()) scratching=false;
		}
		if (currentAction==FIREBALL){
			if (animation.hasPlayedOnce()) firing =false;
		}

		//spell atack;
		mana.refill(delta);
        useSpells();



		//update firebals
		for (int i = 0; i< spells.size(); i++){
			spells.get(i).update();
			if (spells.get(i).checkRemove()){
				spells.remove(i);
				i--;
			}
		}

        if (flinching){
            long elapsed =(System.nanoTime()-flinchTimer)/1000000;
            if (elapsed>1000){
                flinching=false;
            }
        }
		firing =spell1||spell2||spell3;
		// set animation
		if(scratching) {
			if(currentAction != SCRATCHING) {
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 82;
			}
		}
		else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 82;
			}
		}
		else if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 82;
				}
			}
			else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 82;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 82;
			}
		}
		else if(left || right) {
			if (boost && !energy.isEmpty()){
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 82;
				}
			}else if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 82;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 82;
			}
		}

		animation.update();
		
		// set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}

		super.update();
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();

		for (int i = 0; i< spells.size(); i++){
			spells.get(i).draw(g);
		}

        health.draw(g);

        energy.draw(g,0,energy.GREEN_RED);
        mana.draw(g,1,energy.RED_BLUE);

        sm.draw(g);

        trace.draw(g);

		// draw player
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}

		if (!dead){
			super.draw(g);
		}


	}
	
}