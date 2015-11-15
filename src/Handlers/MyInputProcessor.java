package Handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyInputProcessor implements KeyListener {

	public boolean keyDown(int k) {
		if(k == KeyEvent.VK_SPACE) {
			MyInput.setKey(MyInput.BUTTON1, true);
		}
		if(k == KeyEvent.VK_W) {
			MyInput.setKey(MyInput.BUTTON2, true);
		}
		if(k == KeyEvent.VK_A) {
			MyInput.setKey(MyInput.BUTTON3, true);
		}
		if(k == KeyEvent.VK_S) {
			MyInput.setKey(MyInput.BUTTON4, true);
		}
		if(k == KeyEvent.VK_D) {
			MyInput.setKey(MyInput.BUTTON5, true);
		}
		if(k == KeyEvent.VK_ENTER) {
			MyInput.setKey(MyInput.BUTTON6, true);
		}
		return true;
	}
		
	public boolean keyUp(int k) {
		if(k == KeyEvent.VK_SPACE) {
			MyInput.setKey(MyInput.BUTTON1, false);
		}
		if(k == KeyEvent.VK_W) {
			MyInput.setKey(MyInput.BUTTON2, false);
		}
		if(k == KeyEvent.VK_A) {
			MyInput.setKey(MyInput.BUTTON3, false);
		}
		if(k == KeyEvent.VK_S) {
			MyInput.setKey(MyInput.BUTTON4, false);
		}
		if(k == KeyEvent.VK_D) {
			MyInput.setKey(MyInput.BUTTON5, false);
		}
		if(k == KeyEvent.VK_ENTER) {
			MyInput.setKey(MyInput.BUTTON6, false);
		}
		return true;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

