package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;
import java.util.ArrayList;

public abstract class MapObject {
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected ArrayList<Double> speedX = new ArrayList<>();
	protected ArrayList<Double> speedY = new ArrayList<>();
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	protected Rectangle rectangle;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean inAir;
	protected boolean falling;
	protected boolean pik;
	
	// movement attributes
	protected double moveSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double gravityDown;

	//action with map
	protected boolean jumper;
	private boolean topMiddle;
	private boolean bottomMiddle;
	private boolean middleRight;
	private boolean middleLeft;

	// constructor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		rectangle = getRectangle();
	}
	
	public boolean intersects(MapObject o) {
		return rectangle.intersects(o.getRectangle());
	}

	public Rectangle getRectangle() {
		return new Rectangle(
				(int)(x+xmap),
				(int)(y+ymap-height),
				width,
				height
		);
	}
	private int leftTile =2;
	private int rightTile = 2;
	private int topTile = 2;
	private int bottomTile = 2;

	private int middleH = 2;
	private int middleW = 2;

	public void calculateCorners(double x, double y) {
		leftTile = (int) ((x - 1) / (tileSize * GamePanel.SCALE));
		rightTile = (int)((x + width + 1) / (tileSize*GamePanel.SCALE));
		topTile = (int)((y - height - 1) / (tileSize*GamePanel.SCALE));
		bottomTile = (int)((y + 1) / (tileSize*GamePanel.SCALE));
		middleH = (int)((y-height/2)/(tileSize*GamePanel.SCALE));
		middleW = (int)((x+width/2)/(tileSize*GamePanel.SCALE));


		if (topTile<0){
			topTile=1;
			System.out.println("SUUUUKA");
		}
		if (leftTile<0){
			leftTile=1;
			System.out.println("SUUUUKA");
		}
		if (middleH<0){
			middleH=2;
			System.out.println("SUUUUKA");
		}

		int tl = tileMap.getType(topTile+1-1, leftTile+1-1);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);

		int tm = tileMap.getType(topTile, middleW);
		int bm = tileMap.getType(bottomTile, middleW);
		int ml = tileMap.getType(middleH, leftTile);
		int mr = tileMap.getType(middleH, rightTile);
		
		topLeft = tl != Tile.FREE;
		topRight = tr != Tile.FREE;
		topMiddle = tm !=Tile.FREE;

		bottomLeft = bl != Tile.FREE;
		bottomRight = br != Tile.FREE;
		bottomMiddle = bm !=Tile.FREE;

		middleRight = mr!= Tile.FREE;
		middleLeft = ml!= Tile.FREE;






		if (bm == Tile.JUMP){
			jumper=true;
		}else{
			jumper=false;
		}
		
	}
	
	public void checkTileMapCollision() {

		currCol = (int)(x / (tileSize*GamePanel.SCALE));
		currRow = (int)(y / (tileSize*GamePanel.SCALE));

		xdest = x + dx;
		ydest = y + dy;

		xtemp = x;
		ytemp = y;

		calculateCorners(x, ydest);
		if(dy < 0) {
			if(topLeft || topRight || topMiddle) {
				dy = 0;
				speedY.set(1,0d);
				speedY.set(2,0d);
			}
		}
		if(dy > 0) {
			if(bottomLeft || bottomRight || bottomMiddle) {
				dy = 0;
				falling = false;
				inAir =false;
			}
		}

		ytemp+=dy;

		calculateCorners(xdest, y);
		if(dx < 0) {
			while( (topLeft || bottomLeft || middleLeft) && dx<0) {
				dx+=1;
				xdest=x+dx;
				calculateCorners(xdest, y);
			}
			if (dx>0){
				dx=0;
			}else if(topLeft || bottomLeft || middleLeft) {
				dx = 0;
			}
		}
		if(dx > 0) {
			while( (topRight || bottomRight || middleRight) && dx>0){
				dx-=1;
				xdest=x+dx;
				calculateCorners(xdest,y);
			}
			if (dx<0){
				dx=0;
			}else if(topRight || bottomRight || middleRight) {
				dx=0;
			}
		}
		xtemp+=dx;

		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight && !bottomMiddle) {
				falling = true;
			}
		}

	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}

	public void draw(Graphics2D g){

		if(facingRight) {
			g.drawImage(
					animation.getImage(),
					(int)(x + xmap  ),
					(int)(y + ymap -height),
					width,
					height,
					null
			);
		}
		else {
			g.drawImage(
					animation.getImage(),
					(int)(x + xmap+width),
					(int)(y + ymap-height),
					-width,
					height,
					null
			);

		}

		rectangle=getRectangle();
		g.setColor(Color.GREEN);
		g.draw(rectangle);
	}

	
}
















