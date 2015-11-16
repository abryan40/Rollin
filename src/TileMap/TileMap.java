package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

//tilemap class for mapping
public class TileMap {

	//position
	private double x, y;
	
	//bounds
	private int xMin, xMax, yMin, yMax;
	
	//smooth scrolling
	private double tween;
	
	//map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private int numTilesDown;
	private Tile[][] tiles;
	
	//drawing bounds
	private int rowOffset;    //which row to start drawing
	private int colOffset;    //which col to start drawing
	private int numRowsDrawn; //how many rows need to be drawn
	private int numColsDrawn; //how many cols need to be drawn
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsDrawn = GamePanel.HEIGHT / tileSize + 2; //+2 is for smoother drawing
		numColsDrawn = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	//loads the tileset into memory
	public void loadTiles(String s) {
		
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			numTilesDown = tileset.getHeight() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
				for(int col = 0; col < numTilesAcross; col++) {
					subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
					tiles[0][col] = new Tile(subimage, Tile.NORMAL);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
				}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//loads the map file into memory
	public void loadMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//returns tile size
	public int getTileSize() {
		return tileSize;
	}
	
	//returns x
	public int getX() {
		return (int)x;
	}
	
	//returns y
	public int getY() {
		return (int)y;
	}
	
	//returns width
	public int getWidth() {
		return width;
	}
	
	//returns height
	public int getHeight() {
		return height;
	}
	
	//returns number of rows
	public int getNumRows() {
		return numRows; 
	}
	
	//returns number of columns
	public int getNumCols() {
		return numCols; 
	}
	
	//returns type 
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	//sets position of where everything will be drawn
	public void setPosition(double x, double y) {
		
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	//fixes bounds so the drawing won't be off screen
	public void fixBounds() {
		if(x < xMin) {
			x = xMin;
		}
		if(y < yMin) {
			y = yMin;
		}
		if(x > xMax) {
			x = xMax;
		}
		if(y > yMax) {
			y = yMax;
		}
		
	}
	
	//draws everything stored in memory
	public void draw(Graphics2D g) {
		
		for(int row = rowOffset; row < rowOffset + numRowsDrawn; row++) {
			if(row >= numRows) {
				break;
			}
			
			for(int col = colOffset; col < colOffset + numColsDrawn; col++) {
				if(col >= numCols) {
					break;
				}
				if(map[row][col] == 0) {
					continue;
				}
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize, (int)y + row * tileSize, null);
			}
		}
	}
}
