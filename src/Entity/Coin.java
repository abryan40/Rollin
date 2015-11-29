package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.Audio;
import TileMap.TileMap;

public class Coin extends MapObject{

	//animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {8};
	private static final int IDLE = 0;
	
	//audio stuff
	private Audio sfxCoin;
	private boolean hasPlayedOnce;
	
	//coin stuff
	private boolean isOnScreen;
	
	public Coin(TileMap tm, String color) {
		super(tm);
		
		width = 30;
		height = 30;
		
		isOnScreen = true;
		
		sfxCoin = new Audio("/Audio/hitsound.wav");
		hasPlayedOnce = false;
		
		//load sprites
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/" + color + "Coin.gif"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 1; i++) {
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
		animation.setDelay(75);
		
	}
	
	public void playerGot() {
		isOnScreen = false;
		if(!hasPlayedOnce) {
			sfxCoin.play();
			hasPlayedOnce = true;
		}
	}
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(tempX, tempY);
		
		if(currentAction != IDLE) {
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(75);
			width = 30;
		}
		
		animation.update();
	
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		if(isOnScreen) {
			g.drawImage(animation.getImage(), (int)(x + xMap - width / 2), (int)(y + yMap - height / 2), null);
		}
	}

}
