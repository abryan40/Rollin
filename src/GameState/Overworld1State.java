package GameState;

import java.awt.Graphics2D;

import Audio.Audio;
import Entity.Player;
import Handlers.MyInput;
import TileMap.Background;
import TileMap.TileMap;

/*
 * Initial overworld state, with the only 
 * options being level one or back to the menu.
 */

public class Overworld1State extends GameState {

	private Background bg;
	
	private Audio music;
	
	private Player player;
	private TileMap tileMap;
	
	Overworld1State(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/Level1.gif", 1);
			
			music = new Audio("/Audio/ow.wav");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//update based on input
	public void update() {
		bg.update();
		handleInput();
		gsm.score = 0;
	}
	
	//draws everything
	public void draw(Graphics2D g) {
		
		//draw background
		bg.draw(g);
		
		//draw tilemap
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
	
	}
	
	//initial setup of level
	public void init() {
		
		music.play();
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset4.gif");
		tileMap.loadMap("/Maps/owMap1.map");
		tileMap.setPosition(0, 0);
		
		player = new Player(tileMap);
		player.setPosition(49, 131.9);
		//music.play();
	}
	
	//checks keypresses and acts accordingly
	public void handleInput() {
		if(MyInput.keys[MyInput.BUTTON2] || MyInput.keys[MyInput.BUTTON5]) {
			player.setPosition(48, 49);
		}
		if(MyInput.keys[MyInput.BUTTON4] || MyInput.keys[MyInput.BUTTON3]) {
			player.setPosition(49, 132);
		}
		if(MyInput.keys[MyInput.BUTTON6]) {

			if(player.getY() == 49) {
				gsm.setState(gsm.LEVEL1STATE);
				music.close();
			} else if(player.getY() == 132) {
				music.close();
				gsm.setState(gsm.MENUSTATE);
			}
		}
	}
	
}
