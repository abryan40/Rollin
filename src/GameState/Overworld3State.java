package GameState;

import java.awt.Graphics2D;

import Audio.Audio;
import Entity.Player;
import Handlers.MyInput;
import TileMap.Background;
import TileMap.TileMap;

/*
 * Overworld 3 gives the player the
 * option to choose between all three
 * levels, but also shows that a secret 
 * level exists.
 */

public class Overworld3State extends GameState {

	private Background bg;
	
	private Audio music;
	
	private Player player;
	private TileMap tileMap;
	
	private int count;
	
	Overworld3State(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/Level3.gif", 1);
			
			music = new Audio("/Audio/ow.wav");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		count = 0;
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
		player.setPosition(162, 118.9);
		//music.play();
	}
	
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON5)) {
			if(count == 0) {
				player.setPosition(243, 172);
				count++;
			} else if(count == -1) {
				player.setPosition(162, 119);
				count++;
			}
		}
		if(MyInput.isPressed(MyInput.BUTTON3)) {
			if(count == 0) {
				player.setPosition(48, 49);
				count--;
			} else if(count == 1) {
				player.setPosition(162, 119);
				count--;
			}
		}
		if(MyInput.keys[MyInput.BUTTON6]) {
			if(player.getY() == 49) {
				music.close();
				gsm.setState(gsm.LEVEL1STATE);
			} else if(player.getY() == 119) {
				music.close();
				gsm.setState(gsm.LEVEL2STATE);
			} else if(player.getY() == 172) {
				music.close();
				gsm.setState(gsm.LEVEL3STATE);
			}
		}
	}
}