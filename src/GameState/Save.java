package GameState;

import Entity.Players.Player;

class Save {
    private Player player;
    private int level;

    Save() {
        player = new Player();
        level = 0;
    }

    public void saveGame(Player player, int level) {
        this.player = player;
        this.level = level;
    }

    public Player loadPlayer() {
        return player;
    }

    public int loadLevel() {
        return level;
    }
}
