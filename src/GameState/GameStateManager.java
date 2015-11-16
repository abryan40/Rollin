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
	
	public GameStateManager() {
		gameStates = new GameState[2];
		
		currentState = MENUSTATE;
		gameStates[0] = new MenuState(this);
		gameStates[1] = new Level1State(this);
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
