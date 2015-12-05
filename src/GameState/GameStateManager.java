package GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {
	
	//holds game states
	private GameState[] gameStates;
	private int currentState;
	
	//different states game can be in
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int HELPSTATE = 2;
	public static final int LEVEL2STATE = 3;
	public static final int LEVEL3STATE = 4;
	public static final int CREDITSSTATE = 5;
	public static final int LEVEL4STATE = 6;
	public static final int OVERWORLD1STATE = 7;
	public static final int OVERWORLD2STATE = 8;
	public static final int OVERWORLD3STATE = 9;
	public static final int OVERWORLD4STATE = 10;
	public static final int HARDMODESTATE = 11;
	public static final int GAMEOVERSTATE = 12;
	
	//score that remains consistent over the course of the game
	public int score;
	public int lives;
	
	public boolean hardMode;
	
	public GameStateManager() {
		gameStates = new GameState[13];
		
		currentState = MENUSTATE;
		gameStates[0] = new MenuState(this);
		gameStates[1] = new Level1State(this);
		gameStates[2] = new HelpState(this);
		gameStates[3] = new Level2State(this);
		gameStates[4] = new Level3State(this);
		gameStates[5] = new CreditsState(this);
		gameStates[6] = new Level4State(this);
		gameStates[7] = new Overworld1State(this);
		gameStates[8] = new Overworld2State(this);
		gameStates[9] = new Overworld3State(this);
		gameStates[10] = new Overworld4State(this);
		gameStates[11] = new HardmodeState(this);
		gameStates[12] = new GameOverState(this);
		
		score = 0;
		hardMode = false;
		lives = 5;
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates[currentState].init();
	}
	
	public void update() {
		gameStates[currentState].update();
 	}
	
	public void draw(Graphics2D g) {
		gameStates[currentState].draw(g);
	}
	
}
