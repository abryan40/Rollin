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
 * First level of the game
 * 10 coins in this level
 */

public class Level1State extends GameState {
	
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
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
	}

	//sets the level up as new
	public void init() {
		hasPlayed = false;
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/map1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Background.png", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(25, 160);

		coins = new Coin[10];
		for(int i = 0; i < coins.length; i++) {
			coins[i] = new Coin(tileMap, "blue");
		}
		
		total = 0;
		if(gsm.hardMode) {
			time = 75;
		} else {
			time = 120;
		}
		hasComputed = false;
		
		coins[0].setPosition(50, 170);
		coins[1].setPosition(350, 140);
		coins[2].setPosition(600, 200);
		coins[3].setPosition(800, 140);
		coins[4].setPosition(1015, 110);
		coins[5].setPosition(1250, 170);
		coins[6].setPosition(1460, 110);
		coins[7].setPosition(1575, 20);
		coins[8].setPosition(1815, 150);
		coins[9].setPosition(1950, 200);
		
		music = new Audio("/Audio/Level1.wav");
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
					score = score + 300;
				} else {
					score = score + 100;
				}
				coins[i].setPosition(0, 0);
			}
			coins[i].update();
		}
		
		//update player
		player.update();
		
		//decrement time
		if(!player.playerWin()) {
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
		
		//draw HUD
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("Coins: " + total + "/10", 5, 10);
		g.drawString("Time: " + (int)time, 5, 20);
		g.drawString("Lives: " + gsm.lives, 5, 30);
		
		//draw the player
		player.draw(g);
		
		//draw the coins
		for(int i = 0; i < coins.length; i++) {
			coins[i].draw(g);
		}
		
		//checks if the player has won
		if(player.playerWin()) {
			if(!hasPlayed) {
				music.stop();
				sfxWin.play();
				hasPlayed = true;
			} 
			win(g);
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
		gsm.lives--;
		if(gsm.lives == 0) {
			gsm.setState(gsm.GAMEOVERSTATE);
		} else {
			gsm.setState(gsm.LEVEL1STATE);
		}
	}
	
	//goes to the next level if the player has won
	public void nextLevel() {
		music.close();
		gsm.setState(gsm.OVERWORLD2STATE);
	}
	
	//computes score
	private void computeScore() {
		if(!hasComputed) {
			score = score + (time * 10);
			gsm.score = gsm.score + (int)score;
		}
		hasComputed = true;
	}
	
	//gets called when the player wins
	public void win(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("Level Complete!", 210, 100);
		g.drawString("You completed " + (total * 10) + "%", 210, 130);
		g.drawString("of the level!", 210, 150);
		computeScore();
		g.drawString("Score: " + (int)score, 210, 170);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("Press enter to advance.", 210, 200);
		player.stop();
		if(MyInput.keys[MyInput.BUTTON6]) {
			nextLevel();
		}
	}
}
