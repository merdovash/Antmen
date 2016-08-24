package GameState;


import Entity.Players.Player;
import TileMap.TileMap;

import java.io.IOException;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;

    static final int MENUSTATE = 0;
    static final int SAVES = 2;
    static final int LEVELSTATE = 3;
    static final int UPDATER = 1;
    private int currentSave;
    private int save;


    private ArrayList<Player> saves;
    private ArrayList<TileMap> saveMap;

    public GameStateManager(){

        gameStates =  new ArrayList<GameState>();

        saves = new ArrayList<>();
        saveMap = new ArrayList<>();

        currentState = MENUSTATE;

        gameStates.add(new MenuState(this));
        gameStates.add(new Updater(this));
        gameStates.add(new Saver(this));
        gameStates.add(new LevelState(this, (Saver) gameStates.get(2)));

    }

    void setState(int state){
        currentState = state;

        gameStates.get(currentState).init();
    }


    public void update() throws IOException {
        gameStates.get(currentState).update();
    }

    public void draw(java.awt.Graphics2D g){
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);

    }

    public void keyReleased(int k){

        gameStates.get(currentState).keyReleased(k);
    }

    public void save(int i, Player player, TileMap tm) {
        try {
            saves.set(i, player);
            saveMap.set(i, tm);
        } catch (IndexOutOfBoundsException e) {
            saves.add(player);
            saveMap.add(tm);

        }
    }

    public Player getSave(int save) {
        return saves.get(save);
    }

    public TileMap loadMapSave(int save) {
        return saveMap.get(save);
    }

    public void setSave(int save) {
        this.save = save;

    }
}
