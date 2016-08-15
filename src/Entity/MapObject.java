package Entity;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    protected ArrayList<Double> speedsX = new ArrayList<>();
    protected ArrayList<Double> speedsY = new ArrayList<>();

    // dimensions
    protected int width;
    protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	protected Rectangle rectangle;
	
	// collision
	private int currRow;
	private int currCol;
	private double xdest;
	private double ydest;
	protected double xtemp;
	protected double ytemp;
	private boolean topLeft;
	private boolean topRight;
	private boolean bottomLeft;
	private boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean jumping;
	boolean inAir;
	protected boolean falling;
	protected boolean pik;
	
	// movement attributes
	protected double moveSpeed;
    double gravity;
	protected double jumpStart;
	double gravityDown;

	//action with map
	boolean jumper;
	private boolean topMiddle;
	private boolean bottomMiddle;
	private boolean middleRight;
	private boolean middleLeft;
    protected ArrayList<BufferedImage[]> sprites;
    protected String adressImage;
    protected int[] numFrames;

    // constructor
    public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		rectangle = getRectangle();
        scale = Math.round((int) (GamePanel.SCALE * 10)) / 10d;
    }

	MapObject() {
		rectangle = getRectangle();
		scale = Math.round((int) (GamePanel.SCALE * 10)) / 10d;
	}

	protected void loadSprites() {
		//load sprite
		try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(adressImage));

            sprites = new ArrayList<>();
            for (int i = 0; i < numFrames.length; i++) {

                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {

                    bi[j] = spritesheet.getSubimage(
                            j * width,
                            i * height,
                            width,
                            height
                    );
                }

                sprites.add(bi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrame(0);
    }

    public boolean intersects(MapObject o) {
		return rectangle.intersects(o.getRectangle());
	}

    public Rectangle getRectangle() {
        return new Rectangle(
                (int)(x+xmap),
                (int) (y + ymap - height * scale),
                (int) (width * scale),
                (int) (height * scale)
        );
	}


    private int leftTile =2;
	private int rightTile = 2;
	private int topTile = 2;
	private int bottomTile = 2;

	private int middleH = 2;
	private int middleW = 2;

    private void calculateCorners(double x, double y) {
        leftTile = (int) ((x - 1) / (tileSize * GamePanel.SCALE));
        rightTile = (int) ((x + width * scale + 1) / (tileSize * GamePanel.SCALE));
        topTile = (int) ((y - height * scale - 1) / (tileSize * GamePanel.SCALE));
        bottomTile = (int)((y + 1) / (tileSize*GamePanel.SCALE));
        middleH = (int) ((y - height * scale / 2) / (tileSize * GamePanel.SCALE));
        middleW = (int) ((x + width * scale / 2) / (tileSize * GamePanel.SCALE));


		//if (topTile<0){
		//	topTile=1;
		//}
        //if (leftTile<0){
        //	leftTile=1;
        //}
        //if (middleH<0){
        //	middleH=2;
        //}

        int tl = tileMap.getType(topTile, leftTile);
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

        jumper = (bm == Tile.JUMP) || (bl == Tile.JUMP);

    }

	protected boolean fallable;
	
	protected void checkTileMapCollision() {

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
                speedsY.set(1, 0d);
                speedsY.set(2, 0d);
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

		if (fallable){
			calculateCorners(xdest, y+1);
			if (!bottomRight || !bottomLeft){
				dx=0;
			}
		}
		xtemp+=dx;

		if(!falling) {
			calculateCorners(x, ydest+1);
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
	
	protected void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}

    protected double scale;

    public void draw(Graphics2D g){
        setMapPosition();
        if(facingRight) {
			g.drawImage(
					animation.getImage(),
					(int)(x + xmap  ),
                    (int) (y + ymap - height * scale),
                    (int) (width * scale),
                    (int) (height * scale),
                    null
			);
		}
		else {
			g.drawImage(
					animation.getImage(),
                    (int) (x + xmap + width * scale),
                    (int) (y + ymap - height * scale),
                    (int) (-width * scale),
                    (int) (height * scale),
                    null
			);

		}

		rectangle=getRectangle();
		g.setColor(Color.GREEN);
		g.draw(rectangle);
	}

	
}
















