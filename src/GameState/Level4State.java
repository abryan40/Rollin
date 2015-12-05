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

/*
 * This class is for level 4, or the 
 * secret level. This occurs when the player
 * gets all 19 coins on level 3.
 */

public class Level4State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Coin[] coins;
	
	//HUD stuff
	private int total;
	private double time;
	private double score;
	private boolean hasComputed;
	
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

		coins = new Coin[121];
		for(int i = 0; i < coins.length; i++) {
			coins[i] = new Coin(tileMap, "blue");
		}
		
		total = 0;
		if(gsm.hardMode) {
			time = 120;
		} else {
			time = 200;
		}
		hasComputed = false;
		
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
		j = 60;
		for(int i = 106; i < 121; i++) {
			coins[i].setPosition(j, 20);
			j = j + 30;
		}
		
		music = new Audio("/Audio/Level4.wav");
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
				if(gsm.hardMode) {
					score = score + 30;
				} else {
					score = score + 10;
				}
				coins[i].setPosition(0, 0);
			}
			coins[i].update();
		}
		
		//update player
		player.update();
		
		// decrement time
		if (!player.playerWin()) {
			if(gsm.hardMode) {
				time = time - 0.035;
			} else {
				time = time - 0.015;
			}
			if(time <= 0) {
				reset();
			}
		}

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
		
		// draw HUD
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.setColor(Color.BLACK);
		g.drawString("Coins: " + (int)total, 5, 10);
		g.drawString("Time: " + (int)time, 5, 20);
		
		//draw the player
		player.draw(g);
		
		//draw the coins
		for(int i = 0; i < coins.length; i++) {
			coins[i].draw(g);
		}
		
		//checks if the player has won
		if(player.getX() < 60 && player.getY() <= 20) {
			if(total == 120) { 
				if (!hasPlayed) {
					music.stop();
					sfxWin.play();
					hasPlayed = true;
				}
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
		music.close();
		if(gsm.lives == 0) {
			gsm.setState(gsm.GAMEOVERSTATE);
		} else {
			gsm.setState(gsm.LEVEL4STATE);
		}
	}
	
	//goes to the next level if the player has won
	public void nextLevel() {
		music.close();
		gsm.setState(gsm.CREDITSSTATE);
	}
	
	//computes score
	private void computeScore() {
		if(!hasComputed) {
			score = score + (time * 10);
			gsm.score = gsm.score + (int) score;
		}
		hasComputed = true;
	}
	
	//gets called when the player wins
	public void win(Graphics2D g) {
		computeScore();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Congratulations!", 40, 100);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("You completed the secret", 40, 120);
		if(gsm.hardMode) {
			g.drawString("level on hard mode of Rollin'.", 40, 140);
			g.drawString("Score: " + gsm.score + " x2 = " + (gsm.score * 2), 40, 160);
		} else {
			g.drawString("level of Rollin' with", 40, 140);
			g.drawString("a total score of: " + gsm.score, 40, 160);
		}
		g.drawString("Thank you for playing!", 40, 180);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("Press enter to advance.", 50, 200);
		player.stop();
		if(MyInput.keys[MyInput.BUTTON6]) {
			nextLevel();
		}
	}
}