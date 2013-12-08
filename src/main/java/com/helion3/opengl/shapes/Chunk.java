package com.helion3.opengl.shapes;

import com.helion3.opengl.rendering.TextureQuadRenderer;


public class Chunk {
	
	public static final int ROWS = 16;
	public static final int COLUMNS = 16;
	public static final int HEIGHT = 16;
	
	private int bufferSize = 192 * ROWS * COLUMNS * HEIGHT; // 192 per block * chunk dimensions
	private TextureQuadRenderer quadTesselator = new TextureQuadRenderer(bufferSize);
	
	
	/**
	 * 
	 * @param chunkX
	 * @param chunkZ
	 */
	public Chunk( int chunkX, int chunkZ ){
		// Draw intial list of blocks, and cache it
		prerender(chunkX,chunkZ);
	}
	

	/**
	 * Build the tileset for this chunk
	 * @param chunkX
	 * @param chunkZ
	 */
	public void prerender( int chunkX, int chunkZ ){
		for( int x = 0; x < ROWS; x++ ){
			for( int z = 0; z < COLUMNS; z++ ){
				for( int y = 0; y < HEIGHT; y++ ){
					prerenderBlock( (chunkX*ROWS)+x, y, (chunkZ*COLUMNS)+z );
				}
			}
		}
		quadTesselator.saveToBuffer();
	}
	
	
	/**
	 * 
	 */
	public void render(){
		quadTesselator.render();
	}
	
	
	/**
	 * Adds all of the blocks for this chunk to the renderer
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	protected void prerenderBlock( int x, int y, int z ){
		
		float size = 0.5f;
		
		float textureDim = 0.0625f;
		
		float topLeftX = textureDim * 1;
		float topLeftY = textureDim * 1;
		float topRightX = topLeftX + textureDim;
		float topRightY = topLeftY;
		float bottomLeftX = topLeftX;
		float bottomLeftY = topLeftY + textureDim;
		float bottomRightX = topRightX;
		float bottomRightY = topRightY + textureDim;
		
		// NORTH FACE
		
		// bottom left
		quadTesselator.addVertex(-(size)+x, -(size)+y, size+z);
		quadTesselator.addColor(1, 0, 0);
		quadTesselator.addTextureCoord(bottomLeftX,bottomLeftY);
		
		// bottom right
		quadTesselator.addVertex(size+x, -(size)+y, size+z);
		quadTesselator.addColor(0, 1, 0);
		quadTesselator.addTextureCoord(bottomRightX,bottomRightY);
		
		// top right
		quadTesselator.addVertex(size+x, size+y, size+z);
		quadTesselator.addColor(0, 0, 1);
		quadTesselator.addTextureCoord(topRightX,topRightY);
		
		// top left
		quadTesselator.addVertex(-(size)+x, size+y, size+z);
		quadTesselator.addColor(1, 0, 1);
		quadTesselator.addTextureCoord(topLeftX,topLeftY);
		
		// SOUTH FACE
		
		// bottom left
		quadTesselator.addVertex(size+x, -(size)+y, -(size)+z);
		quadTesselator.addColor(1, 0, 0);
		quadTesselator.addTextureCoord(bottomLeftX,bottomLeftY);
		
		// bottom right
		quadTesselator.addVertex(-(size)+x, -(size)+y, -(size)+z);
		quadTesselator.addColor(0, 1, 0);
		quadTesselator.addTextureCoord(bottomRightX,bottomRightY);
		
		// top right
		quadTesselator.addVertex(-(size)+x, size+y, -(size)+z);
		quadTesselator.addColor(0, 0, 1);
		quadTesselator.addTextureCoord(topRightX,topRightY);
		
		// top left
		quadTesselator.addVertex(size+x, size+y, -(size)+z);
		quadTesselator.addColor(1, 0, 1);
		quadTesselator.addTextureCoord(topLeftX,topLeftY);
		
		// WEST FACE
		
		// bottom left
		quadTesselator.addVertex(size+x, -(size)+y, size+z);
		quadTesselator.addColor(1, 0, 0);
		quadTesselator.addTextureCoord(bottomLeftX,bottomLeftY);
		
		// bottom right
		quadTesselator.addVertex(size+x, -(size)+y, -(size)+z);
		quadTesselator.addColor(0, 1, 0);
		quadTesselator.addTextureCoord(bottomRightX,bottomRightY);
		
		// top right
		quadTesselator.addVertex(size+x, size+y, -(size)+z);
		quadTesselator.addColor(0, 0, 1);
		quadTesselator.addTextureCoord(topRightX,topRightY);
		
		// top left
		quadTesselator.addVertex(size+x, size+y, size+z);
		quadTesselator.addColor(1, 0, 1);
		quadTesselator.addTextureCoord(topLeftX,topLeftY);
		
		// EAST FACE
		
		// bottom left
		quadTesselator.addVertex(-(size)+x, -(size)+y, -(size)+z);
		quadTesselator.addColor(1, 0, 0);
		quadTesselator.addTextureCoord(bottomLeftX,bottomLeftY);
		
		// bottom right
		quadTesselator.addVertex(-(size)+x, -(size)+y, size+z);
		quadTesselator.addColor(0, 1, 0);
		quadTesselator.addTextureCoord(bottomRightX,bottomRightY);
		
		// top right
		quadTesselator.addVertex(-(size)+x, size+y, size+z);
		quadTesselator.addColor(0, 0, 1);
		quadTesselator.addTextureCoord(topRightX,topRightY);
		
		// top left
		quadTesselator.addVertex(-(size)+x, size+y, -(size)+z);
		quadTesselator.addColor(1, 0, 1);
		quadTesselator.addTextureCoord(topLeftX,topLeftY);
		
		// TOP FACE
		
		// bottom left
		quadTesselator.addVertex(-(size)+x, size+y, size+z);
		quadTesselator.addColor(1, 0, 0);
		quadTesselator.addTextureCoord(bottomLeftX,bottomLeftY);
		
		// bottom right
		quadTesselator.addVertex(size+x, size+y, size+z);
		quadTesselator.addColor(0, 1, 0);
		quadTesselator.addTextureCoord(bottomRightX,bottomRightY);
		
		// top right
		quadTesselator.addVertex(size+x, size+y, -(size)+z);
		quadTesselator.addColor(0, 0, 1);
		quadTesselator.addTextureCoord(topRightX,topRightY);
		
		// top left
		quadTesselator.addVertex(-(size)+x, size+y, -(size)+z);
		quadTesselator.addColor(1, 0, 1);
		quadTesselator.addTextureCoord(topLeftX,topLeftY);
		
		// BOTTOM FACE
		
		// bottom left
		quadTesselator.addVertex(size+x, -(size)+y, -(size)+z);
		quadTesselator.addColor(1, 0, 0);
		quadTesselator.addTextureCoord(bottomLeftX,bottomLeftY);
		
		// bottom right
		quadTesselator.addVertex(size+x, -(size)+y, size+z);
		quadTesselator.addColor(0, 1, 0);
		quadTesselator.addTextureCoord(bottomRightX,bottomRightY);
		
		// top right
		quadTesselator.addVertex(-(size)+x, -(size)+y, size+z);
		quadTesselator.addColor(0, 0, 1);
		quadTesselator.addTextureCoord(topRightX,topRightY);
		
		// top left
		quadTesselator.addVertex(-(size)+x, -(size)+y, -(size)+z);
		quadTesselator.addColor(1, 0, 1);
		quadTesselator.addTextureCoord(topLeftX,topLeftY);
		
	}
}