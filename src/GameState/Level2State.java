package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Audio.Audio;
import Entity.Coin;
import Entity.Player;
import Handlers.MyInput;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level2State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Coin[] coins;
	
	//HUD stuff
	private double total;
	
	private Audio music;
	private Audio sfxWin;
	
	private boolean hasPlayed;
	
	public Level2State(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		
		hasPlayed = false;
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset2.gif");
		tileMap.loadMap("/Maps/map2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Background2.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(25, 220);
		
		coins = new Coin[12];
		for(int i = 0; i < coins.length; i++) {
			coins[i] = new Coin(tileMap, "yellow");
		}
		
		total = 0;
		
		coins[0].setPosition(100, 230);
		coins[1].setPosition(420, 230);
		coins[2].setPosition(420, 170);
		coins[3].setPosition(800, 230);
		coins[4].setPosition(1090, 200);
		coins[5].setPosition(1215, 170);
		coins[6].setPosition(1510, 24);
		coins[7].setPosition(1860, 80);
		coins[8].setPosition(1710, 170);
		coins[9].setPosition(1860, 200);
		coins[10].setPosition(2175, 230);
		coins[11].setPosition(2355, 170);
		
		music = new Audio("/Audio/Level2.wav");
		sfxWin = new Audio("/Audio/win.wav");
		music.play();
	}

	public void update() {
		
		//update keypresses
		handleInput();
		
		//update coins
		for(int i = 0; i < coins.length; i++) {
			if(player.gotCoin(coins[i])) {
				total++;
				coins[i].setPosition(0, 0);
			}
			coins[i].update();
		}
		
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
		
		//draw HUD
		g.drawString("Coins: " + (int)total + "/12", 5, 15);
		
		//draw the player
		player.draw(g);
		
		//draw the coins
		for(int i = 0; i < coins.length; i++) {
			coins[i].draw(g);
		}
		
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
		gsm.setState(gsm.LEVEL2STATE);
	}
	
	public void nextLevel() {
		music.stop();
		gsm.setState(gsm.LEVEL3STATE);
	}
	
	public void win(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("Level Complete!", 210, 60);
		g.drawString("You completed " + (int)((total / 12) * 100) + "%", 210, 75);
		g.drawString("of the level!", 210, 85);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("Press enter to advance.", 210, 100);
		player.stop();
		if(MyInput.keys[MyInput.BUTTON6]) {
			nextLevel();
		}
	}
}
