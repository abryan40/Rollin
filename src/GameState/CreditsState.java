package GameState;

import java.awt.Graphics2D;

import Audio.Audio;
import Handlers.MyInput;
import TileMap.Background;

/*
 * All this class is used for is the credits
 * this state gets called when the player wins,
 * either on the last level, or the secret level.
 */

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
			music.close();
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
}
