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

public class Level1State extends LevelState {



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

	}


	
	public void update() {

		fps.update();

		addEnemy();

		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH /2  - player.getx(),
			GamePanel.HEIGHT /2 - player.gety()+50
		);
		//bg moves
		//bg.setPosition(tileMap.getx(),tileMap.gety());

		player.checkAtack(enemies);



		for (int i=0; i<enemies.size();i++){
		//	enemies.get(i).checkEnemy(player);
			enemies.get(i).update(tileMap);
			if (enemies.get(i).isDead()){
				enemies.remove(i);
				i--;
			}
			for (int j=0; j<player.spells.size();j++){
				if (enemies.get(i).intersects(player.spells.get(j)) && !player.spells.get(j).isHit()){
					player.spells.get(j).setHit();
					enemies.get(i).hit(player.spells.get(j).getDamage());
					enemies.get(i).punch(player.spells.get(j).getPower(),player.spells.get(j).getx());
				}
			}
		}

		super.update();
	}

	public void draw(Graphics2D g){
		super.draw(g);
	}

}












