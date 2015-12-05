package GameState;

import java.awt.Graphics2D;

import Audio.Audio;
import Entity.Player;
import Handlers.MyInput;
import TileMap.Background;
import TileMap.TileMap;

/*
 * Overworld 4 is the same as overworld
 * 3 except it gives the player access to
 * the secret level.
 */

public class Overworld4State extends GameState {

	private Background bg;
	
	private Audio music;
	
	private Player player;
	private TileMap tileMap;
	
	private int count;
	
	Overworld4State(GameStateManager gsm) {
		super(gsm);
		
		try {
			bg = new Background("/Backgrounds/Level3.gif", 1);
			
			music = new Audio("/Audio/ow.wav");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		count = 1;
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
		player.setPosition(243, 171.9);
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
			} else if(count == 1) {
				player.setPosition(285, 58);
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
			} else if(count == 2) {
				player.setPosition(243, 172);
				count--;
			}
		}
		if(MyInput.keys[MyInput.BUTTON6]) {
			music.close();
			if(player.getY() == 49) {
				music.close();
				gsm.setState(gsm.LEVEL1STATE);
			} else if(player.getY() == 119) {
				music.close();
				gsm.setState(gsm.LEVEL2STATE);
			} else if(player.getY() == 172) {
				music.close();
				gsm.setState(gsm.LEVEL3STATE);
			} else if(player.getY() == 58) {
				music.close();
				gsm.setState(gsm.LEVEL4STATE);
			}
		}
	}
}