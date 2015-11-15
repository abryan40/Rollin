package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import Handlers.MyInput;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	//dimensions of the window
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	//game thread
	private Thread thread;
	private boolean isRunning;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	//GameStateManager
	private GameStateManager gsm;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus(true);
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//game loop
		while(isRunning) {
			
			//start time
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			//time elapsed
			elapsed = System.nanoTime() - start;
			
			//frame wait
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		isRunning = true;
		
		gsm = new GameStateManager();
	}
	
	private void update() {
		//update everything
		gsm.update();
		MyInput.update();
	}
	
	private void draw() {
		gsm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		i = MyInput.convertKeyCode(i);
		MyInput.setKey(i, true);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		i = MyInput.convertKeyCode(i);
		MyInput.setKey(i, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
