package TileMap;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// map
	private int[][] map;
	private int tileSize;
	private int tileWidth;
	private int tileHeight;
	private static int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		tileWidth=tileSize;
		tileHeight=tileSize;
		numRowsToDraw = (int)(GamePanel.HEIGHT / (tileSize*GamePanel.SCALE) + 2);
		numColsToDraw = (int)(GamePanel.WIDTH / (tileSize*GamePanel.SCALE) + 2);
		tween = 1;
	}

	public TileMap(int tileHeight, int tileWidth){
		this.tileHeight=tileHeight;
		this.tileWidth=tileWidth;
		numColsToDraw = (int)(GamePanel.WIDTH/(tileWidth*GamePanel.SCALE) +2);
		numRowsToDraw = (int)(GamePanel.HEIGHT/(tileHeight*GamePanel.SCALE) +2);
		tween = 1;
	}

	public static int getRows() {
		return numRows;
	}

	public void loadTiles(String s) {
		
		try {

			tileset = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			numTilesAcross = tileset.getWidth() / tileWidth;
			tiles = new Tile[3][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
							col * tileWidth,
							0,
							tileWidth,
							tileHeight
						);
				tiles[0][col] = new Tile(subimage, Tile.FREE);
				subimage = tileset.getSubimage(
							col * tileWidth,
							tileHeight,
							tileWidth,
							tileHeight
						);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
				subimage = tileset.getSubimage(
						col*tileWidth,
						tileHeight*2,
						tileWidth,
						tileHeight
				);
				if (col==1 || col==3 || col==4){
					tiles[2][col] = new Tile(subimage, Tile.JUMP);
				}else{
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED);
				}

			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void loadMap(String s) {
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = (int)(numCols * tileWidth*GamePanel.SCALE);
			height = (int)(numRows * tileHeight*GamePanel.SCALE);
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x ; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public int getType(int row, int col) {
		int rc;
		rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public void setTween(double d) { tween = d; }
	
	public void setPosition(double x, double y) {


		this.x += (x - this.x);
		this.y += (y - this.y);

		
		fixBounds();
		
		colOffset = (int)(-this.x /(tileWidth*GamePanel.SCALE));
		rowOffset = (int)(-this.y /(tileHeight*GamePanel.SCALE));
		
	}

	
	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin-25;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
						(int) Math.round(x + col * tileWidth * GamePanel.SCALE),
						(int) Math.round(y + row * tileHeight * GamePanel.SCALE),
						(int)(tileSize* GamePanel.SCALE),
						(int)(tileSize* GamePanel.SCALE),
					null
				);

			}
			
		}
		
	}

	public int getNumRows() {
		return numRows;
	}
}



















