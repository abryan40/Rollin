package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Audio.Audio;
import Handlers.MyInput;
import TileMap.Background;

/*
 * This state is for the player to select
 * whether or not they want to play in
 * hardmode or not.
 */
public class HardmodeState extends GameState {

	private Background bg;
	
	private int currentOption = 0;
	private String [] options = {
			"No",
			"Yes",
	};
	
	private Audio sfxChoice, sfxPick;
	
	HardmodeState(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/Hardmode.gif", 1);
			
			sfxChoice = new Audio("/Audio/Jump.wav");
			sfxPick = new Audio("/Audio/hitsound.wav");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		bg.update();
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		//draw background
		bg.draw(g);
		
		g.setColor(Color.RED);
		g.drawString("Would you like to play in hard mode?", 70, 60);
		g.drawString("In hard mode, you will get less time,", 70, 100);
		g.drawString("but you will get a 2x score multiplier.", 70, 120);
		g.drawString("You will also be limited to 3 lives.", 70, 140);
		
		
		for(int i = 0; i < options.length; i++) {
			if(i == currentOption) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 145, 180 + i * 15);
		}
	}
	
	private void select() {
		switch (currentOption) {
		case 0: 
			gsm.setState(GameStateManager.OVERWORLD1STATE);
			break;
		case 1:
			gsm.setState(GameStateManager.OVERWORLD1STATE);
			gsm.hardMode = true;
			gsm.lives = 3;
			break;
		default:
			//nothing
		}
	}
	
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON6)) {
			sfxPick.play();
			for(int i = 0; i < 1000000000; i++) {
				i = i;
			}
			select();
		} else if(MyInput.isPressed(MyInput.BUTTON2) || MyInput.isPressed(MyInput.BUTTON7)) {
			if(currentOption > 0) {
				currentOption--;
				sfxChoice.play();
			}
		} else if(MyInput.isPressed(MyInput.BUTTON4) ||MyInput.isPressed(MyInput.BUTTON8)) {
			if(currentOption < options.length - 1) {
				currentOption++;
				sfxChoice.play();
			}
		}
	}

	public void init() {
		//not needed
		
	}
	
}
