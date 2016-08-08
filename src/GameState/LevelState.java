package GameState;

import Entity.Enemies.Ant;
import Entity.Enemy;
import Entity.Player;
import Entity.SpawnArea;
import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Background;
import GUI.GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 8/6/2016.
 */
abstract class LevelState extends GameState {

    LevelState(GameStateManager gsm){

        this.gsm = gsm;
        pause=true;
        init();
        gui = new GUI(player);
    }

    TileMap tileMap;
    Background bg;

    Player player;

    ArrayList<Enemy> enemies;

    ArrayList<SpawnArea> spawnArea;

    private GUI gui;

    boolean pause;


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
                        enemies.add(new Ant(tileMap));
                        enemies.get(enemies.size()-1).setPosition(spawnArea.get(i).getx(),spawnArea.get(i).gety());
                    }
                }
            }
        }
    }

private boolean paused;
    public void update(){
        fps.update();
        if (pause) {
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
    }

    private void setPause(){
        pause = !pause;
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_W) gui.setCurrentAction(-1);
        if(k == KeyEvent.VK_S) gui.setCurrentAction(1);
        if (!paused){
            if(k == KeyEvent.VK_A) player.setLeft(true);
            if(k == KeyEvent.VK_D) player.setRight(true);
            if(k == KeyEvent.VK_UP) player.setUp(true);
            if(k == KeyEvent.VK_DOWN) player.setDown(true);
            if(k == KeyEvent.VK_SPACE) player.setJumping(true);
            if(k == KeyEvent.VK_E) player.setGliding(true);
            if(k == KeyEvent.VK_S) player.setScratching();
            if(k == KeyEvent.VK_SHIFT) player.setBoost(true);
            if(k == KeyEvent.VK_1) player.use1spell(true);
            if(k == KeyEvent.VK_2) player.use2spell(true);
            if(k == KeyEvent.VK_3) player.use3spell(true);
        }
        if(k == KeyEvent.VK_ENTER) setPause();
    }

    public void keyReleased(int k) {
        if (!paused){
            if(k == KeyEvent.VK_A) player.setLeft(false);
            if(k == KeyEvent.VK_D) player.setRight(false);
            if(k == KeyEvent.VK_UP) player.setUp(false);
            if(k == KeyEvent.VK_DOWN) player.setDown(false);
            if(k == KeyEvent.VK_SPACE) player.setJumping(false);
            if(k == KeyEvent.VK_E) player.setGliding(false);
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
                if (enemies.get(i).isDead()){
                    enemies.remove(i);
                    i--;
                }
            }
        }


        fps.draw(g);

        if (pause){
            gui.draw(g);
        }
    }
}
