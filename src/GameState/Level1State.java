package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Entity.Player;
import Handlers.MyInput;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/map1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Background.png", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(200, 100);
	}

	public void update() {
		
		//update keypresses
		handleInput();
		
		//update player
		player.update();
		
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
	}

	public void handleInput() {
		player.setUp(MyInput.keys[MyInput.BUTTON2]);
		player.setLeft(MyInput.keys[MyInput.BUTTON3]);
		player.setDown(MyInput.keys[MyInput.BUTTON4]);
		player.setRight(MyInput.keys[MyInput.BUTTON5]);
		player.setJumping(MyInput.keys[MyInput.BUTTON1]);
		player.setGliding(MyInput.keys[MyInput.BUTTON12]);
		if(MyInput.isPressed(MyInput.BUTTON11)) player.setFiring();
	}
}
