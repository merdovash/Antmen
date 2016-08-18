package GameState;

import Entity.Enemies.Ants.Ant;
import Entity.Enemies.Ants.AntViking;
import Entity.Enemies.Enemy;
import Entity.FPS;
import Entity.Items.ItemList;
import Entity.Items.MapItem;
import Entity.Players.Player;
import GUI.GUI;
import Interactive.DoorPoint;
import Interactive.SavePoint;
import Interactive.SpawnPoint;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


class LevelState extends GameState {

    private static int HEIGHT;

    private int px = (int) (100 * GamePanel.SCALE);
    private int py = (int) (245 * GamePanel.SCALE);

    private TileMap tileMap;
    private Background bg;

    private ArrayList<Enemy> enemies;

    private ArrayList<SpawnPoint> spawnAreas;

    private ArrayList<SavePoint> savePoints;
    private ArrayList<DoorPoint> nextLevel;

    private ArrayList<MapItem> mapLoots;

    private GUI gui;

    private boolean menu;

    LevelState(GameStateManager gsm) {
        this.gsm = gsm;
        menu = true;
        init();
    }

    @Override
    public void init() {
        load();
    }

    private void load() {

        level = "Beginning_1";

        fps = new FPS();
        loadLevel();

        loadPlayer();
        gui = new GUI(player.inventory, player.stats);


        menu = false;
    }

    private void loadLevel() {
        loadMap();
        enemies = new ArrayList<>();
        loadSpawns();
        loadSavePoints();
        loadNext();
        mapLoots = new ArrayList<>();

        if (!(player == null)) player.load(tileMap, px, py);

    }


    private void loadMap() {
        tileMap = new TileMap(50);
        tileMap.loadTiles("/tiles/grasstileset_x50.gif");
        tileMap.loadMap("/Maps/" + level + "/map.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        LevelState.HEIGHT = tileMap.getNumRows();

        bg = new Background("/Maps/" + level + "/bg.gif", 0.1);
    }

    private void loadSpawns() {
        spawnAreas = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream("/Maps/" + level + "/spawnPlaces.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int num = Integer.parseInt(br.readLine());
            int[] params;

            String delims = "\\s+";
            for (int i = 0; i < num; i++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                params = new int[tokens.length];
                for (int j = 0; j < tokens.length; j++) {
                    params[j] = Integer.parseInt(tokens[j]);
                }
                spawnAreas.add(new SpawnPoint(tileMap, params[0], params[1], params[2], params[3], params[4]));
            }
            br.close();
            in.close();
        } catch (Exception e) {
            System.out.println("/Maps/" + level + "/spawnPlaces.txt file not found");
            e.printStackTrace();
        }
    }

    private void loadSavePoints() {
        savePoints = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream("/Maps/" + level + "/savePoints.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int num = Integer.parseInt(br.readLine());
            int[] params;

            String delims = "\\s+";
            for (int i = 0; i < num; i++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                params = new int[tokens.length];
                for (int j = 0; j < tokens.length; j++) {
                    params[j] = Integer.parseInt(tokens[j]);
                }
                savePoints.add(new SavePoint(tileMap, params[0], params[1]));
            }
            br.close();
            in.close();
        } catch (Exception e) {
            System.out.println("/Maps/" + level + "/savePoint.txt file not found");
            e.printStackTrace();
        }
    }

    private void loadNext() {
        nextLevel = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream("/Maps/" + level + "/next.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int num = Integer.parseInt(br.readLine());
            int[] params;

            String delims = "\\s+";
            for (int i = 0; i < num; i++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                params = new int[tokens.length];
                for (int j = 0; j < tokens.length - 1; j++) {
                    params[j] = Integer.parseInt(tokens[j]);
                }
                nextLevel.add(new DoorPoint(tileMap, params[0], params[1], params[2], params[3], tokens[4]));
            }
            br.close();
            in.close();
        } catch (Exception e) {
            System.out.println("/Maps/" + level + "/next.txt file not found");
            e.printStackTrace();
        }
    }

    private void loadPlayer() {
        player = new Player(tileMap);
        player.setPosition(px, py);
    }


    private void addLoot(Enemy e) {
        double chance = Math.random() * (1);
        int replace = 0;
        for (int i = 0; i < e.loot.size(); i++) {
            if (chance <= e.loot.getChance(i)) {
                mapLoots.add(new MapItem(tileMap, e.loot.getID(i)));
                mapLoots.get(mapLoots.size() - 1).setPosition(e.getx() + replace, e.gety() + replace / 2);
                replace += 10;
            }
        }
    }

    private void getLoot() {
        for (int i = 0; i < mapLoots.size(); i++) {
            if (player.getRectangle().intersects(mapLoots.get(i).getRectangle())) {
                if (player.inventory.addItem(ItemList.getItem(mapLoots.get(i).getID()))) {
                    mapLoots.remove(i);
                }
            }
        }
    }


    private void addEnemy() {
        for (int i = 0; i < spawnAreas.size(); i++) {
            if (spawnAreas.get(i).isEmpty() && enemies.size() < spawnAreas.size()) {
                switch (spawnAreas.get(i).getId()) {
                    case 1:
                        enemies.add(new Ant(tileMap));
                        break;
                    case 2:
                        enemies.add(new AntViking(tileMap));
                        spawnAreas.get(i).setCooldown(25000);
                        break;
                }
                if (enemies.size() != 0) {
                    if (spawnAreas.get(i).isEmpty()) {
                        spawnAreas.get(i).setEnemy(enemies.get(enemies.size() - 1));
                    }
                    enemies.get(enemies.size() - 1).setPosition(spawnAreas.get(i).getx(), spawnAreas.get(i).gety());
                }
            }
        }
    }


    private boolean paused;


    public void update() {

        // refresh fps
        fps.update();

        if (menu) {
            paused = true;
            return;
        }
        if (paused) {
            paused = false;
            long l = System.nanoTime();
            player.setLastTime(l);
            for (Enemy enemy : enemies) {
                enemy.setLastTime(l);
            }
        }



        //add enemies
        addEnemy();

        //player update
        player.update();
        player.checkAtack(enemies);

        //update map
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );

        //bg moves
        //bg.setPosition(tileMap.getx(),tileMap.gety());

        updateEnemies();

        //update mapLoots
        for (MapItem mapLoot : mapLoots) {
            mapLoot.update();
        }

        getLoot();

        checkSavePoint();

        checkNextLevel();

    }

    private void checkSavePoint() {
        for (SavePoint savePoint : savePoints) {
            if (player.getRectangle().intersects(savePoint.getRectangle())) {
                player.setRespawn(savePoint.getx(), savePoint.gety());
            }
        }
    }

    private void checkNextLevel() {
        for (DoorPoint doorPoint : nextLevel) {
            if (player.getRectangle().intersects(doorPoint.getRectangle())) {
                level = doorPoint.getNextLevel();
                player.setRespawn(doorPoint.getPx(), doorPoint.getPy());
                px = doorPoint.getPx();
                py = doorPoint.getPy();
                loadLevel();
            }
        }
    }

    private void updateEnemies() {
        //enemies search player
        for (Enemy enemy : enemies) {
            if (player.getx() < enemy.getx() + enemy.visionX && enemy.getx() - enemy.visionX < player.getx()) {
                if (player.gety() < enemy.gety() + enemy.visionY && player.gety() > enemy.gety() - enemy.visionY) {
                    enemy.setAgressive(true);
                    if (player.getx() > enemy.getx()) {
                        enemy.setRight();
                    } else {
                        enemy.setLeft();
                    }
                } else {
                    enemy.setAgressive(false);
                }
            } else {
                enemy.setAgressive(false);
            }
        }

        //enemies get attacked from spells
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            for (int j = 0; j < player.spells.size(); j++) {
                boolean active = player.spells.get(j).isActive();
                if (enemies.get(i).intersects(player.spells.get(j))) {
                    if (active) {
                        player.spells.get(j).setHit();
                        enemies.get(i).hit((int) (player.spells.get(j).getDamage() * player.stats.getSpellDamageModifier()));
                        enemies.get(i).punch(player.spells.get(j).getPower() * player.stats.getSpellDamageModifier(), player.spells.get(j).getx());
                    }
                }
            }
            if (enemies.size() > 0) {
                if (enemies.get(i).isDead()) {
                    addLoot(enemies.get(i));
                    player.stats.addExp(enemies.get(i).getExp());
                    enemies.remove(i);
                    if (i == enemies.size()) {
                        break;
                    } else {
                        i--;
                    }
                }

            }

        }
    }

    private void setPause() {
        menu = !menu;
    }

    private void menuAction() {
        if (gui.getCurrentAction() == 3) {
            setPause();
        } else if (gui.getCurrentAction() == 0) {
            gui.openInventory();
        } else if (gui.getCurrentAction() == 1) {
            gui.setOpenStats();
        }
    }

    public void keyPressed(int k) {
        if (!paused) {
            if (player != null) {
                if (k == KeyEvent.VK_A) player.setLeft(true);
                if (k == KeyEvent.VK_D) player.setRight(true);
                if (k == KeyEvent.VK_SPACE) player.setJumping(true);
                if (k == KeyEvent.VK_S) player.setScratching();
                if (k == KeyEvent.VK_SHIFT) player.setBoost(true);
                if (k == KeyEvent.VK_1) player.use1spell(true);
                if (k == KeyEvent.VK_2) player.use2spell(true);
                if (k == KeyEvent.VK_3) player.use3spell(true);
            }
        }
        if (!menu) {
            if (k == KeyEvent.VK_ENTER) setPause();
        } else {
            if (gui.isOpenInventory()) {
                if (k == KeyEvent.VK_W) gui.inventoryMove(0, -1);
                if (k == KeyEvent.VK_S) gui.inventoryMove(0, 1);
                if (k == KeyEvent.VK_A) gui.inventoryMove(-1, 0);
                if (k == KeyEvent.VK_D) gui.inventoryMove(1, 0);
                if (k == KeyEvent.VK_BACK_SPACE) gui.openInventory();
                if (k == KeyEvent.VK_ENTER) gui.inventorySelect();
            } else if (gui.isOpenStats()) {
                if (k == KeyEvent.VK_W) gui.statsMove(-1);
                if (k == KeyEvent.VK_S) gui.statsMove(1);
                if (k == KeyEvent.VK_A) gui.statsAdd(-1);
                if (k == KeyEvent.VK_D) gui.statsAdd(1);
                if (k == KeyEvent.VK_ENTER) gui.statsSelect();
                if (k == KeyEvent.VK_BACK_SPACE) gui.setOpenStats();
            } else {
                if (k == KeyEvent.VK_ENTER) menuAction();
                if (k == KeyEvent.VK_W) gui.setCurrentAction(-1);
                if (k == KeyEvent.VK_S) gui.setCurrentAction(1);
            }
        }


    }


    public void keyReleased(int k) {
        if (!paused) {
            if (k == KeyEvent.VK_A) player.setLeft(false);
            if (k == KeyEvent.VK_D) player.setRight(false);
            if (k == KeyEvent.VK_SPACE) player.setJumping(false);
            if (k == KeyEvent.VK_Q) player.respawn();
            if (k == KeyEvent.VK_SHIFT) player.setBoost(false);
            if (k == KeyEvent.VK_1) player.use1spell(false);
            if (k == KeyEvent.VK_2) player.use2spell(false);
            if (k == KeyEvent.VK_3) player.use3spell(false);
        }
    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);

        //draw spawn areas
        for (SpawnPoint spawnPoint : spawnAreas) {
            spawnPoint.draw(g);
        }
        for (SavePoint place : savePoints) {
            place.draw(g);
        }
        for (DoorPoint place : nextLevel) {
            place.draw(g);
        }


        // draw tilemap
        tileMap.draw(g);

        //enemies draw
        if (enemies.size() > 0) {
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
        }

        // draw player
        player.draw(g);

        //draw items
        for (MapItem aMapLoot : mapLoots) {
            aMapLoot.draw(g);
        }

        fps.draw(g);

        player.drawGui(g);

        if (menu) {
            gui.draw(g);
        }
    }

}
