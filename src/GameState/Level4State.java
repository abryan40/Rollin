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

public class Level4State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Coin[] coins;
	
	//HUD stuff
	private int total;
	
	//audio stuff
	private Audio music;
	private Audio sfxWin;
	private boolean hasPlayed;
	
	public Level4State(GameStateManager gsm) {
		super(gsm);
	}

	//sets the level up as new
	public void init() {
		hasPlayed = false;
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset4.gif");
		tileMap.loadMap("/Maps/map4.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Background4.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(16, 400);

		coins = new Coin[122];
		for(int i = 0; i < coins.length; i++) {
			coins[i] = new Coin(tileMap, "blue");
		}
		
		total = 0;
		
		//don't ask how this works
		//just know it was like two hours of plug and chug
		int j = 120;
		for(int i = 0; i < 15; i++) {
			coins[i].setPosition(j, 410);
			j = j + 30;
		}
		j = 150;
		for(int i = 16; i < 30; i++) {
			coins[i].setPosition(j, 380);
			j = j + 30;
		}
		j = 210;
		for(int i = 30; i < 42; i++) {
			coins[i].setPosition(j, 350);
			j = j + 30;
		}
		j = 240;
		for(int i = 42; i < 52; i++) {
			coins[i].setPosition(j, 290);
			j = j + 30;
		}
		j = 240;
		for(int i = 52; i < 60; i++) {
			coins[i].setPosition(j, 260);
			j = j + 30;
		}
		j = 90;
		for(int i = 60; i < 73; i++) {
			coins[i].setPosition(j, 200);
			j = j + 30;
		}
		j = 120;
		for(int i = 73; i < 85; i++) {
			coins[i].setPosition(j, 170);
			j = j + 30;
		}
		j = 180;
		for(int i = 85; i < 95; i++) {
			coins[i].setPosition(j, 140);
			j = j + 30;
		}
		j = 210;
		for(int i = 95; i < 106; i++) {
			coins[i].setPosition(j, 80);
			j = j + 30;
		}
		j = 30;
		for(int i = 106; i < 122; i++) {
			coins[i].setPosition(j, 20);
			j = j + 30;
		}
		
		music = new Audio("/Audio/Level1.wav");
		sfxWin = new Audio("/Audio/win.wav");
		//music.play();
	}

	public void update() {
		
		//update keypresses
		handleInput();
		
		//update coins
		for(int i = 0; i < coins.length; i++) {
			if(player.gotCoin(coins[i])) {
				total++;
				coins[i].setPosition(0,0);
			}
			coins[i].update();
		}
		
		//update player
		player.update();
		System.out.println(player.getX() + ", " + player.getY());
		
		//checks if the player is dead
		if(player.isDead()) {
			reset();
		}
		
		//set the map location
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
		g.drawString("Coins: " + total, 5, 15);
		
		//draw the player
		player.draw(g);
		
		//draw the coins
		for(int i = 0; i < coins.length; i++) {
			coins[i].draw(g);
		}
		
		//checks if the player has won
		if(player.getX() < 40 && player.getY() <= 20) {
			if(total == 121) {
				win(g);
			} else { 
				g.setColor(Color.BLACK);
				g.drawString("Come back with more coins.", 5, 100);
				g.setColor(Color.WHITE);
			}
		}
	}

	//handles keyboard input
	public void handleInput() {
		player.setLeft(MyInput.keys[MyInput.BUTTON3]);
		player.setRight(MyInput.keys[MyInput.BUTTON5]);
		player.setJumping(MyInput.keys[MyInput.BUTTON1]);
		player.setGliding(MyInput.keys[MyInput.BUTTON11]);
	}
	
	//resets the level
	public void reset() {
		music.stop();
		gsm.setState(gsm.LEVEL4STATE);
	}
	
	//goes to the next level if the player has won
	public void nextLevel() {
		gsm.setState(gsm.CREDITSSTATE);
	}
	
	//gets called when the player wins
	public void win(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Congratulations!", 50, 100);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("You completed the secret", 50, 120);
		g.drawString("level of Rollin'!", 50, 140);
		g.drawString("Thank you for playing!", 50, 160);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("Press enter to advance.", 50, 200);
		player.stop();
		if(MyInput.keys[MyInput.BUTTON6]) {
			nextLevel();
		}
	}
}