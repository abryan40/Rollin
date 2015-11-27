package GameState;

import java.awt.Graphics2D;

import Audio.Audio;
import Handlers.MyInput;
import TileMap.Background;

public class CreditsState  extends GameState {

	private Background bg;
	
	private Audio music;
	
	CreditsState(GameStateManager gsm) {
		super(gsm);
		
		music = new Audio("/Audio/credits.wav");
		
		try {
			bg = new Background("/Backgrounds/Credits.gif", 1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void init() {
		music.play();
	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
	}

	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON6)) {
			music.stop();
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
}
