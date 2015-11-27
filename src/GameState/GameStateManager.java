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
	
	public GameStateManager() {
		gameStates = new GameState[6];
		
		currentState = MENUSTATE;
		gameStates[0] = new MenuState(this);
		gameStates[1] = new Level1State(this);
		gameStates[2] = new HelpState(this);
		gameStates[3] = new Level2State(this);
		gameStates[4] = new Level3State(this);
		gameStates[5] = new CreditsState(this);
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
