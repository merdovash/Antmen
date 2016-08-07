package GameState;

import Entity.FPS;

import java.io.IOException;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected FPS fps;
	
	public abstract void init();
	public abstract void update() throws IOException;
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);

}
