package GameState;

import Entity.Players.Player;
import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

class Saver extends GameState {

    private GameStateManager gsm;
    private ArrayList<TileMap> saveMap;

    Saver(GameStateManager gsm) {
        this.gsm = gsm;
        currentstate = 0;
        saveMap = new ArrayList<TileMap>();
        saves = new ArrayList<>();
    }


    private ArrayList<Player> saves;
    private int currentstate;
    private int maxState;

    @Override
    public void init() {

    }

    @Override
    public void update() throws IOException {
        maxState = saves.size() > 3 ? saves.size() : 3;
    }


    private String[] title = {"Load Game", "New Game"};

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setFont(new Font("Courier New", Font.PLAIN, (int) (14 * GamePanel.SCALE)));
        for (int i = 0; i < (saves.size() < 3 ? 3 : saves.size()); i++) {
            g.setColor(currentstate == i ? new Color(55, 99, 165) : new Color(77, 189, 11));
            g.fillRoundRect((int) (300 * GamePanel.SCALE), (int) ((300 + 75 * i) * GamePanel.SCALE), (int) (150 * GamePanel.SCALE), (int) (50 * GamePanel.SCALE), 25, 25);
            g.setColor(new Color(150, 150, 150));
            g.drawString((i >= (saves.size())) ? title[1] : (title[0] + " #" + i), (int) (325 * GamePanel.SCALE), (int) ((325 + 75 * i) * GamePanel.SCALE));
        }
    }

    private void move(int i) {
        currentstate += i;
        if (currentstate >= maxState) {
            currentstate = 0;
        }
        if (currentstate < 0) {
            currentstate = maxState;
        }
    }

    private void select() {

        gsm.setSave(currentstate);
        gsm.setState(GameStateManager.LEVELSTATE);
    }

    void save(int i, Player player, TileMap tm) {
        saves.set(i, player);
        saveMap.set(i, tm);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_S) move(1);
        if (k == KeyEvent.VK_W) move(-1);
        if (k == KeyEvent.VK_ENTER) select();
    }


    @Override
    public void keyReleased(int k) {

    }

    int getCurrentSave() {
        System.out.println(currentstate);
        return currentstate;
    }

    void create(TileMap tileMap) {
        saveMap.add(tileMap);
    }

    TileMap loadMapSave(int currentSave) {
        return saveMap.get(currentSave);
    }

    Player loadSave(int currentSave) {
        return saves.get(currentSave);
    }

    void create(Player player) {
        saves.add(player);
        System.out.println(saves.size());
    }
}
