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
		if(k == KeyEvent.VK_UP) {
			MyInput.setKey(MyInput.BUTTON7, true);
		}
		if(k == KeyEvent.VK_DOWN) {
			MyInput.setKey(MyInput.BUTTON8, true);
		}
		if(k == KeyEvent.VK_LEFT) {
			MyInput.setKey(MyInput.BUTTON9, true);
		}
		if(k == KeyEvent.VK_RIGHT) {
			MyInput.setKey(MyInput.BUTTON10, true);
		}
		if(k == KeyEvent.VK_J) {
			MyInput.setKey(MyInput.BUTTON11, true);
		}
		if(k == KeyEvent.VK_SHIFT) {
			MyInput.setKey(MyInput.BUTTON11, true);
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
		if(k == KeyEvent.VK_UP) {
			MyInput.setKey(MyInput.BUTTON7, false);
		}
		if(k == KeyEvent.VK_DOWN) {
			MyInput.setKey(MyInput.BUTTON8, false);
		}
		if(k == KeyEvent.VK_LEFT) {
			MyInput.setKey(MyInput.BUTTON9, false);
		}
		if(k == KeyEvent.VK_RIGHT) {
			MyInput.setKey(MyInput.BUTTON10, false);
		}
		if(k == KeyEvent.VK_J) {
			MyInput.setKey(MyInput.BUTTON11, false);
		}
		if(k == KeyEvent.VK_SHIFT) {
			MyInput.setKey(MyInput.BUTTON11, false);
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

