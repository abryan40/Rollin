package GameState;

import java.awt.Graphics2D;

import Audio.Audio;
import Entity.Player;
import Handlers.MyInput;
import TileMap.Background;
import TileMap.TileMap;

/*
 * Overworld 2 is after the player beats level
 * one. It gives the player the option of going
 * back to level one or continuing onto level 2.
 */

public class Overworld2State extends GameState {

	private Background bg;
	
	private Audio music;
	
	private Player player;
	private TileMap tileMap;
	
	Overworld2State(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/Level2.gif", 1);
			
			music = new Audio("/Audio/ow.wav");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		bg.update();
		handleInput();
		gsm.score = 0;
	}
	
	public void draw(Graphics2D g) {
		
		//draw background
		bg.draw(g);
		
		//draw tilemap
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
	
	}
	
	public void init() {
		
		music.play();
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset4.gif");
		tileMap.loadMap("/Maps/owMap1.map");
		tileMap.setPosition(0, 0);
		
		player = new Player(tileMap);
		player.setPosition(48, 48.9);
		//music.play();
	}
	
	public void handleInput() {
		if(MyInput.keys[MyInput.BUTTON5]) {
			player.setPosition(162, 119);
		}
		if(MyInput.keys[MyInput.BUTTON3]) {
			player.setPosition(48, 49);
		}
		if(MyInput.keys[MyInput.BUTTON6]) {
			if(player.getY() == 49) {
				music.close();
				gsm.setState(gsm.LEVEL1STATE);
			} else if(player.getY() == 119) {
				music.close();
				gsm.setState(gsm.LEVEL2STATE);
			}
		}
	}
	
}
