package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Handlers.MyInput;
import TileMap.Background;

/*
 * This is just here for the player to know
 * how to play the game.
 */

public class HelpState extends GameState {

	private Background bg;
	
	HelpState(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/HelpScreen.gif", 1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void init() {
		
	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
	}

	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON6)) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
}
