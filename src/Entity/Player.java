package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Player extends MapObject {

	//player variables
	private int health;
	private int maxHealth;
	private int pepperonis;
	private int maxPepperonis;
	private boolean dead;
	private boolean flinching;
	private long flinchTime;
	
	//pepperoni shot
	private boolean firing;
	private int pepCost;
	private int pepDamage;
	//private ArrayList<Pepperoni> pepperoniList;
	
	//gliding
	private boolean gliding;
	
	//animations
	private ArrayList<BufferedImage[]> sprites;
	
	/*
	 * number of sprites per animation action
	 * 2 = IDLE, 8 = WALKING, ect.
	 */
	private final int[] numFrames = {2, 4, 1, 1, 2, 1};
	
	//animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int PEPPERONISHOT = 4;
	private static final int GLIDING = 5;
	
	
	public Player(TileMap tm) {
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		pepperonis = maxPepperonis = 2500;
		
		pepCost = 200;
		pepDamage = 5;
		//pepperoniList = new ArrayList<Pepperoni>();
		
		//load sprites
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					//check for gliding
					if(i != 6) {
						bi[j] = spriteSheet.getSubimage(j * width, i * height, width,  height);
					} else {
						
						bi[j] = spriteSheet.getSubimage(j * width, i * height * 2, width,  height - 30);
					}
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
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth; 
	}
	
	public int getPepperonis() {
		return pepperonis;
	}
	
	public int getMaxPepperonis() {
		return maxPepperonis;
	}
	
	public void setFiring() {
		firing = true;
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
		
		//cannot attack while moving unless in air
		if((currentAction == PEPPERONISHOT) && !(jumping || falling)) {
			dx = 0;
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
		
		//set animation
		if(firing) {
			if(currentAction != PEPPERONISHOT) {
				currentAction = PEPPERONISHOT;
				animation.setFrames(sprites.get(PEPPERONISHOT));
				animation.setDelay(200);
				width = 30;
			}
		} else if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
					height = 30;
				}
			} else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(30);
				width = 30;
			}
		} else if( dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
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
				animation.setDelay(400);
				width = 30;
			}
		}
		
		animation.update();
		
		//set the direction
		if(currentAction != PEPPERONISHOT) {
			if(right) {
				facingRight = true;
			} else if(left) {
				facingRight = false;
			}
		}
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		//draw player
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTime) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xMap - width / 2), (int)(y + yMap - height / 2), null);
		} else {
			g.drawImage(animation.getImage(), (int)(x + xMap - width / 2 + width), (int)(y + yMap - height / 2), -width, height, null);
		}
	}
	
}
