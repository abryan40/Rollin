package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Audio.Audio;
import Entity.Player;
import Handlers.MyInput;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private Audio music;
	private Audio sfxWin;
	
	private boolean hasPlayed;
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		hasPlayed = false;
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/map1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Background.png", 0.1);
		
		player = new Player(tileMap);
		//25
		player.setPosition(25, 160);
		
		music = new Audio("/Audio/Level1.wav");
		sfxWin = new Audio("/Audio/win.wav");
		music.play();
	}

	public void update() {
		
		//update keypresses
		handleInput();
		
		//update player
		player.update();
		
		if(player.isDead()) {
			reset();
		}
		
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.HEIGHT / 2 - player.getY());
		tileMap.fixBounds();
		
	}

	public void draw(Graphics2D g) {
		
		//clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//draw background
		bg.draw(g);
		
		//draw the tilemap
		tileMap.draw(g);
		
		//draw the player
		player.draw(g);
		
		if(player.playerWin()) {
			if(!hasPlayed) {
				music.stop();
				sfxWin.play();
				hasPlayed = true;
			} 
			win(g);
		}
	}

	public void handleInput() {
		player.setLeft(MyInput.keys[MyInput.BUTTON3]);
		player.setRight(MyInput.keys[MyInput.BUTTON5]);
		player.setJumping(MyInput.keys[MyInput.BUTTON1]);
		player.setGliding(MyInput.keys[MyInput.BUTTON11]);
	}
	
	public void reset() {
		music.stop();
		gsm.setState(gsm.LEVEL1STATE);
	}
	
	public void nextLevel() {
		gsm.setState(gsm.LEVEL2STATE);
	}
	
	public void win(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("Level Complete!", 200, 100);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("Press enter to advance.", 200, 200);
		player.stop();
		if(MyInput.keys[MyInput.BUTTON6]) {
			nextLevel();
		}
	}
}
