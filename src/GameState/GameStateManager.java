package GameState;


import GameState.Levels.Level1State;
import GameState.Levels.Level2State;

import java.io.IOException;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;

    private final LevelState[][] levels = {
            {new Level1State(this)},
            {new Level2State(this, 100, 195)},
            {new Level1State(this, 5150, 775)}};

    static final int MENUSTATE = 0;
    static final int LEVEL1STATE = 2;
    static final int LEVEL2STATE = 3;
    static final int UPDATER = 1;

    public GameStateManager(){

        gameStates =  new ArrayList<GameState>();

        currentState = MENUSTATE;

        gameStates.add(new MenuState(this));
        gameStates.add(new Updater(this));
        gameStates.add(new Level1State(this));

    }

    void setState(int state){
        currentState = state;
        gameStates.get(currentState).init();
    }

    void setLevel(int currentLevel, int nextLevel) {
        System.out.println(gameStates.size());
        gameStates.add(levels[currentLevel][nextLevel - 1]);
        gameStates.get(gameStates.size() - 1).init();
        System.out.println(gameStates.size());
        gameStates.remove(gameStates.size() - 2);
        System.out.println(gameStates.size());
        currentState = gameStates.size() - 1;
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
}
