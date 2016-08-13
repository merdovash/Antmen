package GameState.Levels;

import Entity.FPS;
import Entity.Players.Place;
import Entity.Players.Player;
import Entity.SpawnArea;
import GameState.GameStateManager;
import GameState.LevelState;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Level2State extends LevelState {
    public Level2State(GameStateManager gsm, int x, int y) {

        super(gsm, x, y);
    }

    @Override
    public void init() {

        level = 2;

        fps = new FPS();

        tileMap = new TileMap(50);
        tileMap.loadTiles("/tiles/grasstileset_x50.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        LevelState.HEIGHT = tileMap.getNumRows();

        bg = new Background("/Backgrounds/bglvl1.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(px, py);

        enemies = new ArrayList<>();

        //init spawn Areas
        spawnAreas = new ArrayList<>();
        //spawnAreas.add(new SpawnArea(tileMap, 645, 151, 100, 150, 1));
        spawnAreas.add(new SpawnArea(tileMap, 900, 100, 100, 150, 1));
        spawnAreas.add(new SpawnArea(tileMap, 645, 500, 100, 150, 1));
        spawnAreas.add(new SpawnArea(tileMap, 695, 700, 100, 150, 1));
        spawnAreas.add(new SpawnArea(tileMap, 600, 950, 100, 150, 1));
        //
        // spawnAreas.add( new SpawnArea (tileMap,100,1100,100,150,2));
        //spawnAreas.add (new SpawnArea (tileMap.))

        //save points
        savePoints = new ArrayList<>();
        savePoints.add(new Place(tileMap, 2850, 600));

        nextLevel = new ArrayList<>();
        nextLevel.add(new Place(tileMap, 0, 250, 1));

        menu = false;
        mapLoots = new ArrayList<>();

    }


    public void update() {

        super.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

}

