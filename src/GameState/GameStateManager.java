package GameState;


import java.io.IOException;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;
    private ArrayList<Save> saves;

    static final int MENUSTATE = 0;
    static final int LEVELSTATE = 2;
    static final int UPDATER = 1;
    private int currentSave;

    public GameStateManager(){

        gameStates =  new ArrayList<GameState>();

        currentState = MENUSTATE;

        gameStates.add(new MenuState(this));
        gameStates.add(new Updater(this));
        gameStates.add(new LevelState(this));

        saves = new ArrayList<>();
        saves.add(new Save());

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
}
