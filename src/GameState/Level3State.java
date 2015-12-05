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
 * This is the "final" level of the game.
 * There are 19 coins to collect in this level, 
 * scattered and hidden throughout.
 * If the player collects all of them, he is taken
 * back to the overworld, with the option of going
 * to the secret level.
 */

public class Level3State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Coin[] coins;
	
	//HUD stuff
	private double total;
	int percent;
	private double time;
	private double score;
	private boolean hasComputed;
	
	private Audio music;
	private Audio sfxWin;
	
	private boolean hasPlayed;
	
	public Level3State(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		
		hasPlayed = false;
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset3.gif");
		tileMap.loadMap("/Maps/map3.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Background3.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(25, 430);
		
		coins = new Coin[19];
		for(int i = 0; i < coins.length; i++) {
			coins[i] = new Coin(tileMap, "grey");
		}
		
		total = 0;
		if(gsm.hardMode) {
			time = 200;
		} else {
			time = 300;
		}
		hasComputed = false;
		
		coins[0].setPosition(130, 430);
		coins[1].setPosition(60, 280);
		coins[2].setPosition(90, 280);
		coins[3].setPosition(120, 280);
		coins[4].setPosition(435, 530);
		coins[5].setPosition(680, 440);
		coins[6].setPosition(1035, 50);
		coins[7].setPosition(1350, 100);
		coins[8].setPosition(915, 170);
		coins[9].setPosition(1035, 410);
		coins[10].setPosition(1710, 260);
		coins[11].setPosition(2085, 140);
		coins[12].setPosition(2285, 110);
		coins[13].setPosition(2505, 320);
		coins[14].setPosition(960, 530);
		coins[15].setPosition(1370, 530);
		coins[16].setPosition(1890, 380);
		coins[17].setPosition(2100, 260);
		coins[18].setPosition(2230, 530);
		
		
		music = new Audio("/Audio/Level3.wav");
		sfxWin = new Audio("/Audio/finalWin.wav");
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
		
		//calculate percentage of coins
		calculatePercent();
		
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
		
		// draw HUD
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		g.drawString("Coins: " + (int)total + "/ ?", 5, 10);
		g.drawString("Time: " + (int) time, 5, 20);
		g.drawString("Lives: " + gsm.lives, 5, 30);

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
		music.close();
		gsm.lives--;
		if(gsm.lives == 0) {
			gsm.setState(gsm.GAMEOVERSTATE);
		} else {
			gsm.setState(gsm.LEVEL3STATE);
		}
	}
	
	public void nextLevel() {
		music.close();
		sfxWin.stop();
		if(percent != 100) {
			gsm.setState(gsm.CREDITSSTATE);
		} else {
			gsm.setState(gsm.OVERWORLD4STATE);
		}
	}
	
	//computes score
	private void computeScore() {
		if(!hasComputed) {
			score = score + (time * 10);
			gsm.score = gsm.score + (int)score;
		}
		hasComputed = true;
	}
	
	private void calculatePercent() {
		percent =  (int)((total / 19) * 100);
	}
	
	public void win(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawString("YOU WIN!", 155, 60);
		g.drawString("You completed " + percent + "%", 155, 80);
		g.drawString("of the level!", 155, 100);
		computeScore();
		g.drawString("Score: " + (int)score, 155, 120);
		if(percent != 100) {
			g.drawString("Maybe try collecting", 155, 140);
			g.drawString("everything next time...", 155, 160);
			g.drawString("Total score: " + gsm.score, 155, 180);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			g.drawString("Press enter to advance.", 155, 200);
		} else {
			g.drawString("You unlocked a secret level!", 155, 140);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			g.drawString("Press enter to advance.", 155, 160);
		}
		player.stop();
		if(MyInput.keys[MyInput.BUTTON6]) {
			nextLevel();
		}
	}
}
