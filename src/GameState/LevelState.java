package GameState;

import Entity.Enemies.Ant;
import Entity.Enemies.Enemy;
import Entity.Enemies.Spider;
import Entity.Items.MapItem;
import Entity.Players.Place;
import Entity.Players.Player;
import Entity.SpawnArea;
import GUI.GUI;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class LevelState extends GameState {

    static int HEIGHT;

    protected LevelState() {

    }

    LevelState(GameStateManager gsm){

        this.gsm = gsm;
        menu =true;
        init();
        gui = new GUI();
    }

    TileMap tileMap;
    Background bg;

    Player player;

    ArrayList<Enemy> enemies;

    ArrayList<SpawnArea> spawnAreas;

    ArrayList<Place> savePoints;
    ArrayList<Place> nextLevel;

    ArrayList<MapItem> mapLoots;

    private GUI gui;

    boolean menu;

    private void addLoot(Enemy e){
        double chance = Math.random()*(1);
        for (int i = 0; i<e.loot.size();i++){
            if (chance<=e.loot.getChance(i)){
                mapLoots.add(new MapItem(tileMap, e.loot.getID(i)));
                mapLoots.get(mapLoots.size() - 1).setPosition(e.getx(), e.gety());
            }
        }

    }

    private void getLoot(){
        for (int i = 0; i < mapLoots.size(); i++) {
            if (player.getRectangle().intersects(mapLoots.get(i).getRectangle())) {
                if (player.addItem(mapLoots.get(i).getID())) {
                    mapLoots.remove(i);
                }
            }
        }
    }


    private void addEnemy(){
        for (int i = 0; i < spawnAreas.size(); i++) {
            if (spawnAreas.get(i).isActive()) {
                boolean empty =true;
                for (Enemy enemy : enemies) {
                    if (spawnAreas.get(i).contains(enemy)) {
                        empty = false;
                        break;
                    }

                }

                if (empty && enemies.size() < spawnAreas.size()) {
                    if (spawnAreas.get(i).isReady()) {
                        switch (spawnAreas.get(i).getID()) {
                            case 1:
                                enemies.add(new Ant(tileMap));
                                break;
                            case 2:
                                enemies.add(new Spider(tileMap));
                                break;
                        }
                        if (enemies.size() != 0) {
                            enemies.get(enemies.size() - 1).setPosition(spawnAreas.get(i).getx(), spawnAreas.get(i).gety());
                        }
                    }
                }
            }
        }
    }

private boolean paused;
    public void update(){
        fps.update();
        if (menu) {
            paused=true;
            gui.update(player.getItems());
            return;
        }
        if (paused){
            paused=false;
            long l=System.nanoTime();
            player.setLastTime(l);
            for (Enemy enemy : enemies) {
                enemy.setLastTime(l);
            }
        }
        // refresh fps


        //add enemies
        addEnemy();

        //player update
        player.update();
        player.checkAtack(enemies);

        //update map
        tileMap.setPosition(
                GamePanel.WIDTH /2  - player.getx(),
                GamePanel.HEIGHT /2 - player.gety()+50
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
    }

    private void checkSavePoint() {
        for (Place savePoint : savePoints) {
            if (player.getRectangle().intersects(savePoint.getRectangle())) {
                player.setRespawn(savePoint.getx(), savePoint.gety());
            }
        }
    }

    private void checkNextLevel() {

    }

    private void updateEnemies(){
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
        for (int i=0; i<enemies.size();i++){
            enemies.get(i).update(tileMap);
            for (int j=0; j<player.spells.size();j++){
                if (i == 0) {
                    System.out.println(player.spells.get(j).isHit() + " " + enemies.get(i).health.getHealth());
                }
                if (!(player.spells.get(j).isHit()) && enemies.get(i).intersects(player.spells.get(j))) {
                    player.spells.get(j).setHit();
                    enemies.get(i).hit(player.spells.get(j).getDamage());
                    enemies.get(i).punch(player.spells.get(j).getPower(),player.spells.get(j).getx());
                }
            }
            if (enemies.size() > 0) {
                if (enemies.get(i).isDead()) {
                    addLoot(enemies.get(i));
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

    private void setPause(){
        menu = !menu;
    }

    private void menuAction() {
        if (gui.getCurrentAction()==3){
            setPause();
        }else if (gui.getCurrentAction()==0){
            gui.openInventory();
        }
    }

    public void keyPressed(int k) {
        if (!paused){
            if(k == KeyEvent.VK_A) player.setLeft(true);
            if(k == KeyEvent.VK_D) player.setRight(true);
            if(k == KeyEvent.VK_SPACE) player.setJumping(true);
            if(k == KeyEvent.VK_S) player.setScratching();
            if(k == KeyEvent.VK_SHIFT) player.setBoost(true);
            if(k == KeyEvent.VK_1) player.use1spell(true);
            if(k == KeyEvent.VK_2) player.use2spell(true);
            if(k == KeyEvent.VK_3) player.use3spell(true);
        }
        if (!menu){
            if(k == KeyEvent.VK_ENTER) setPause();
        }else{
            if (gui.isInventory()){
                if(k == KeyEvent.VK_W) gui.inventoryMove(0,-1);
                if(k == KeyEvent.VK_S) gui.inventoryMove(0,1);
                if(k == KeyEvent.VK_A) gui.inventoryMove(-1,0);
                if(k == KeyEvent.VK_D) gui.inventoryMove(1,0);
                if(k == KeyEvent.VK_BACK_SPACE) gui.openInventory();
                if(k == KeyEvent.VK_ENTER) gui.select();
            }else{
                if(k == KeyEvent.VK_ENTER) menuAction();
                if(k == KeyEvent.VK_W) gui.setCurrentAction(-1);
                if(k == KeyEvent.VK_S) gui.setCurrentAction(1);
            }
        }



    }



    public void keyReleased(int k) {
        if (!paused){
            if(k == KeyEvent.VK_A) player.setLeft(false);
            if(k == KeyEvent.VK_D) player.setRight(false);
            if(k == KeyEvent.VK_SPACE) player.setJumping(false);
            if(k == KeyEvent.VK_Q) player.respawn();
            if(k == KeyEvent.VK_SHIFT) player.setBoost(false);
            if(k == KeyEvent.VK_1) player.use1spell(false);
            if(k == KeyEvent.VK_2) player.use2spell(false);
            if(k == KeyEvent.VK_3) player.use3spell(false);
        }
    }

    public void draw(Graphics2D g){

        // draw bg
        bg.draw(g);

        //draw spawn areas
        for (SpawnArea spawnArea : spawnAreas) {
            spawnArea.draw(g);
        }
        for (Place place : savePoints) {
            place.draw(g);
        }
        for (Place place : nextLevel) {
            place.draw(g);
        }

        // draw tilemap
        tileMap.draw(g);

        //enemies draw
        if (enemies.size()>0){
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

        if (menu){
            gui.draw(g);
        }
    }

    protected void equip(int i) {
        player.equip(i);
    }
}
