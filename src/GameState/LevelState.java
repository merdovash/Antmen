package GameState;

import Entity.Enemies.Ant;
import Entity.Enemies.Enemy;
import Entity.Enemies.Spider;
import Entity.Items.MapItem;
import Entity.Players.Player;
import Entity.SpawnArea;
import GUI.GUI;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 8/6/2016.
 */
public abstract class LevelState extends GameState {

    public static int HEIGHT;

    public LevelState(){

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

    ArrayList<SpawnArea> spawnArea;

    ArrayList<MapItem> mapLoot;

    private GUI gui;

    boolean menu;

    private void addLoot(Enemy e){
        double chance = Math.random()*(1);
        for (int i = 0; i<e.loot.size();i++){
            if (chance<=e.loot.getChance(i)){
                mapLoot.add(new MapItem(tileMap, e.loot.getID(i)));
                mapLoot.get(mapLoot.size()-1).setPosition(e.getx(), e.gety());
            }
        }

    }

    private void getLoot(){
        for (int i = 0; i< mapLoot.size(); i++){
            if (    player.getx()>= mapLoot.get(i).getx()
                    && player.getx()<= mapLoot.get(i).getx()+50
                    && player.gety()-50<= mapLoot.get(i).gety()
                    && player.gety()>= mapLoot.get(i).gety()) {

                if (player.addItem(mapLoot.get(i).getID())){
                    mapLoot.remove(i);
                }
            }
        }
    }


    private void addEnemy(){
        for (int i = 0; i< spawnArea.size(); i++){
            if (spawnArea.get(i).isActive()){
                boolean empty =true;
                for (int j=0; j<enemies.size();j++){
                    if (spawnArea.get(i).contains(enemies.get(j))){
                        empty=false;
                        break;
                    }

                }

                if (empty && enemies.size()<spawnArea.size()){
                    if (spawnArea.get(i).isReady()){
                        switch (spawnArea.get(i).getID()){
                            case 1:
                                enemies.add(new Ant(tileMap));
                                break;
                            case 2:
                                enemies.add(new Spider(tileMap));
                                break;
                        }
                        if (enemies.size() != 0) {
                            enemies.get(enemies.size() - 1).setPosition(spawnArea.get(i).getx(), spawnArea.get(i).gety());
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
            gui.update();
            return;
        }
        if (paused){
            paused=false;
            long l=System.nanoTime();
            player.setLastTime(l);
            for (int i =0; i<enemies.size();i++){
                enemies.get(i).setLastTime(l);
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

        //update mapLoot
        for (int i = 0; i< mapLoot.size(); i++){
            mapLoot.get(i).update();
        }

        getLoot();
    }

    private void updateEnemies(){
        //enemies search player
        for (int i=0; i<enemies.size();i++){
            Enemy e = enemies.get(i);
            if (player.getx()<e.getx()+e.visionX && e.getx()-e.visionX<player.getx()){
                if (player.gety()< e.gety()+e.visionY && player.gety()> e.gety()-e.visionY){
                    enemies.get(i).setEnemy(true);
                    if (player.getx()>e.getx()){
                        enemies.get(i).setRight();
                    }else{
                        enemies.get(i).setLeft();
                    }
                }else{
                    enemies.get(i).setEnemy(false);
                }
            }else{
                enemies.get(i).setEnemy(false);
            }
        }

        //enemies get attacked from spells
        for (int i=0; i<enemies.size();i++){
            enemies.get(i).update(tileMap);
            for (int j=0; j<player.spells.size();j++){
                if (enemies.get(i).intersects(player.spells.get(j)) && !player.spells.get(j).isHit()){
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
            if(k == KeyEvent.VK_UP) player.setUp(true);
            if(k == KeyEvent.VK_DOWN) player.setDown(true);
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
            if(k == KeyEvent.VK_UP) player.setUp(false);
            if(k == KeyEvent.VK_DOWN) player.setDown(false);
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
        for(int i = 0; i < spawnArea.size(); i++){
            spawnArea.get(i).draw(g);
        }

        // draw tilemap
        tileMap.draw(g);

        // draw player
        player.draw(g);

        //enemies draw
        if (enemies.size()>0){
            for (int i=0; i<enemies.size();i++){
                enemies.get(i).draw(g);
            }
        }

        //draw items
        for (int i = 0; i< mapLoot.size(); i++){
            mapLoot.get(i).draw(g);
        }


        fps.draw(g);

        if (menu){
            gui.draw(g,player.getItems());
        }
    }

    public void equip(int i) {
        player.equip(i);
    }
}
