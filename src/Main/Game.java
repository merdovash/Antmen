package Main;

import javax.swing.*;

public class Game {

	private final static String version = "Antmen v0.02.02";
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Antmen");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setUndecorated(true);
		window.setVisible(true);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}

	public String getVersion(){
		return version;
	}
	
}
