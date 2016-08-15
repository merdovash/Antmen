package GameState;

import Entity.FPS;
import Entity.Players.Player;

import java.io.IOException;

abstract class GameState {

	GameStateManager gsm;
	FPS fps;
	Player player;
	String level;

	
	public abstract void init();
	public abstract void update() throws IOException;
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);

	public String getLevel() {
		return level;
	}
}
