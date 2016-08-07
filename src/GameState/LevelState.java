package GameState;

import Entity.Enemies.Ant;
import Entity.Enemy;
import Entity.Player;
import Entity.SpawnArea;
import TileMap.TileMap;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 8/6/2016.
 */
abstract class LevelState extends GameState {

    LevelState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    TileMap tileMap;
    Background bg;

    Player player;

    ArrayList<Enemy> enemies;

    ArrayList<SpawnArea> spawnArea;


    void addEnemy(){
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

    public void update(){
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
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_A) player.setLeft(true);
        if(k == KeyEvent.VK_D) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_SPACE) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_S) player.setScratching();
        if(k == KeyEvent.VK_SHIFT) player.setBoost(true);
        if(k==KeyEvent.VK_1) player.use1spell(true);
        if(k==KeyEvent.VK_2) player.use2spell(true);
        if(k==KeyEvent.VK_3) player.use3spell(true);
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_A) player.setLeft(false);
        if(k == KeyEvent.VK_D) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_SPACE) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
        if(k == KeyEvent.VK_Q) player.respawn();
        if(k == KeyEvent.VK_SHIFT) player.setBoost(false);
        if(k==KeyEvent.VK_1) player.use1spell(false);
        if(k==KeyEvent.VK_2) player.use2spell(false);
        if(k==KeyEvent.VK_3) player.use3spell(false);
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
    }
}
