package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Handlers.MyInput;
import TileMap.Background;

public class MenuState extends GameState {

	private Background bg;
	
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
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Jokerman", Font.PLAIN, 22);
			font = new Font("Times New Roman", Font.PLAIN, 12);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		bg.update();
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Suddenly, Cold Pizza", 55, 70);
		
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentOption) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}
	
	public void init() {
		
	}
	
	private void select() {
		switch (currentOption) {
		case 0: 
			gsm.setState(GameStateManager.LEVEL1STATE);
			break;
		case 1:
			//help
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
