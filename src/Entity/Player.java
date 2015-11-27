package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.Audio;
import TileMap.TileMap;

public class Player extends MapObject {

	//player variables
	private boolean dead;
	private boolean win;
	
	//gliding
	private boolean gliding;
	
	//tilemap
	private static int tmHeight;
	private static int tmWidth;
	
	//animations
	private ArrayList<BufferedImage[]> sprites;
	
	/*
	 * number of sprites per animation action
	 * 2 = IDLE, 8 = WALKING, ect.
	 */
	private final int[] numFrames = {2, 8, 1, 2, 2};
	
	//animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	
	private Audio sfxJump;
	
	
	public Player(TileMap tm) {
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		tmHeight = tm.getHeight();
		tmWidth = tm.getWidth();
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		sfxJump = new Audio("/Audio/Jump.wav");
		
		//load sprites
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/spritesheet.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 5; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spriteSheet.getSubimage(j * width, i * height, width,  height);
				}
				sprites.add(bi);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	public void setGliding(boolean b) {
		gliding = b;
	}
	
	private void getNextPosition() {
		
		//movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			} else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		//jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		//falling
		if(falling) {
			if(dy > 0 && gliding) {
				dy += fallSpeed * 0.1;
			} else {
				dy += fallSpeed;
			}
			
			if(dy > 0) {
				jumping = false;
			} else if(dy < 0 && !jumping) {
				dy += stopJumpSpeed;
			} else if(dy > maxFallSpeed) {
				dy = maxFallSpeed;
			}
		}
		
	}
	
	//updates physics and player position
	public void update() {
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(tempX, tempY);
		
		if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(150);
					width = 30;
					height = 30;
				}
			} else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(150);
				width = 30;
			}
		} else if( dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				sfxJump.play();
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		} else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 30;
			}
		} else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(250);
				width = 30;
			}
		}
		
		animation.update();
		
		if(right) {
			facingRight = true;
		} else if(left) {
			facingRight = false;
		}
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xMap - width / 2), (int)(y + yMap - height / 2), null);
		} else {
			g.drawImage(animation.getImage(), (int)(x + xMap - width / 2 + width), (int)(y + yMap - height / 2), -width, height, null);
		}
	}
	
	public boolean isDead() {
		if(getX() > tmWidth - 10 && getY() > tmHeight) {
			dead = false;
		} else if(getX() < 0 || getY() > tmHeight) {
			dead = true;
		} else {
			dead = false;
		}
		return dead;
	}
	
	public boolean playerWin() {
		if(getX() > tmWidth) {
			win = true;
		} else {
			win = false;
		}
		return win;
	}
	
	public void stop() {
		moveSpeed = fallSpeed = stopSpeed = 0;
	}
}
