package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Audio.Audio;
import Handlers.MyInput;
import Handlers.MyInputProcessor;
import TileMap.Background;


public class MenuState extends GameState {

	private Background bg;
	
	private Audio music;
	
	private int currentOption = 0;
	private String [] options = {
			"Start",
			"Help",
			"Quit"
	};

	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	MenuState(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/Menu.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			font = new Font("Times New Roman", Font.PLAIN, 12);
			
			music = new Audio("/Audio/mainTheme.wav");
			music.play();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		bg.update();
		handleInput();
		gsm.score = 0;
		gsm.lives = 5;
	}
	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Rollin'", 120, 100);
		
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentOption) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}
	
	public void init() {
		music.play();
	}
	
	private void select() {
		switch (currentOption) {
		case 0: 
			music.stop();
			gsm.setState(GameStateManager.HARDMODESTATE);
			break;
		case 1:
			gsm.setState(GameStateManager.HELPSTATE);
			break;
		case 2: 
			//close
			System.exit(0);
		}
	}
	
	public void handleInput() {
		
		if(MyInput.isPressed(MyInput.BUTTON6)) {
			select();
		} else if(MyInput.isPressed(MyInput.BUTTON2) || MyInput.isPressed(MyInput.BUTTON7)) {
			if(currentOption > 0) {
				currentOption--;
			}
		} else if(MyInput.isPressed(MyInput.BUTTON4) ||MyInput.isPressed(MyInput.BUTTON8)) {
			if(currentOption < options.length - 1) {
				currentOption++;
			}
		}
	}
}
