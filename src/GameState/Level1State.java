package GameState;

import Entity.Enemies.Ant;
import Entity.FPS;
import Entity.Player;
import Entity.SpawnArea;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

class Level1State extends LevelState {



	Level1State(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {

		fps= new FPS();
		
		tileMap = new TileMap(50);
		tileMap.loadTiles("/tiles/grasstileset_x50.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);



		bg = new Background("/Backgrounds/bglvl1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 200);


		enemies = new ArrayList<>();


		//init spawn Areas
		spawnArea = new ArrayList<>();
		spawnArea.add( new SpawnArea (tileMap,645,151,150,150));
		spawnArea.add( new SpawnArea (tileMap,900,100,150,150));
		//spawnArea.add (new SpawnArea (tileMap.))

		pause=false;

	}


	
	public void update() {

		super.update();
	}

	public void draw(Graphics2D g){
		super.draw(g);
	}

}












