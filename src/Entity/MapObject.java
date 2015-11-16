package Entity;

import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

/*class that holds all variables that 
 * each map object, such as player,
 * enemy, items will have
 */
public abstract class MapObject {

	//tile variables
	protected TileMap tileMap;
	protected int tileSize;
	protected double xMap;
	protected double yMap;
	
	//posiion and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimensions
	protected int width;
	protected int height;
	
	//collision box
	protected int cwidth;
	protected int cheight;
	
	//collision variables
	protected int currRow;
	protected int currCol;
	protected double xDestination;
	protected double yDestination;
	protected double tempX;
	protected double tempY;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	//variables that determine what the object is doing
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	//physics stuff
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed; //speed decrements by this
	protected double fallSpeed; //gravity
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	/*checks if the rectangle of one map object
	 * intersects with the rectangle of 
	 * another map object
	 */
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	/*
	 * returns the map objects rectangle
	 */
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
	}
	
	/* 
	 * calculates corners of tileMap for use with 
	 * checkTileMapCollision()
	 */
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }
		
		//top left, top right, bottom left, bottom right
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}
	
	/*
	 * checks the map objects collision 
	 * witht the tile map
	 */
	public void checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xDestination = x + dx;
		yDestination = y + dy;
		
		tempX = x;
		tempY = y;
		
		calculateCorners(x, yDestination);
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0; //stop moving in the up direction
				tempY = currRow * tileSize + cheight / 2;
			} else {
				tempY += dy;
			}
		}
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0; 
				falling = false;
				tempY = (currRow + 1) * tileSize - cheight / 2;
			} else {
				tempY += dy;
			}
		}
		
		calculateCorners(xDestination, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				tempX = currCol * tileSize + cwidth / 2;
			} else {
				tempX += dx;
			}
		}
		if(dx > 0) {
			if(topLeft || topRight) {
				dx = 0;
				tempX = (currCol + 1) * tileSize - cwidth / 2;
			} else {
				tempX += dx;
			}
		}
		if(!falling) {
			calculateCorners(x, yDestination + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getCWidth() {
		return cwidth;
	}
	
	public int getCHeight() {
		return cheight;
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	//sets local position
	public void setMapPosition() {
		xMap = tileMap.getX();
		yMap = tileMap.getY();
	}
	
	public void setLeft(boolean b) {
		left = b;
	}
	
	public void setRight(boolean b) {
		right = b;
	}
	
	public void setUp(boolean b) {
		up = b;
	}
	
	public void setDown(boolean b) {
		down = b;
	}

	public void setJumping(boolean b) {
		jumping = b;
	}
	
	/*
	 * returns whether or not something will be on the screen
	 * this determines if the map object should be drawn or not
	 */
	public boolean notOnScreen() {
		return x + xMap + width < 0 || x + xMap - width > GamePanel.WIDTH || y + yMap + height < 0 || y + yMap - height > GamePanel.HEIGHT;
	}
} 

