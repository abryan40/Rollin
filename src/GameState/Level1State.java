package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/map1.map");
		tileMap.setPosition(0, 0);
		
		bg = new Background("/Backgrounds/Background.png", 0.1);
		
	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		
		//clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//draw background
		bg.draw(g);
		
		//draw the tilemap
		tileMap.draw(g);
	}

	public void handleInput() {
		
	}

}
