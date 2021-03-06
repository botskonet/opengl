package com.helion3.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.helion3.opengl.shapes.*;


public class Game {
	
	private static Game instance;
	
    /**
     * 
     * @return
     */
    public static Game getInstance(){
    	if( instance == null ) instance = new Game();
    	return instance;
    }
    
    
    /**
	 * Public static
	 */
    public static final String name = "OpenGL Tests";
	public static final short GAME_WIDTH = 1024;
	public static final short GAME_HEIGHT = 768;
	public static int delta;
	
	/**
	 * Private
	 */
	private long lastFrame;			
	private int fps;	
	private long lastFPS;
	private Camera camera = new Camera();
	private Texture texture;
	private float mouseSensitivity = 0.1f;
	private World world;
	
	    	
	/**
	 * 
	 */
    public Game(){}
    
    
	/**
	 * 
	 * @param name
	 * @throws LWJGLException 
	 */
    public void start() throws LWJGLException {
		
		// Init display
		Display.setResizable(true);
		Display.setTitle( Game.name );
		Display.setVSyncEnabled(true);
		Display.setDisplayMode(new DisplayMode(Game.GAME_WIDTH, Game.GAME_HEIGHT));
		
		// Set viewport
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
		Display.create(pixelFormat, contextAtrributes);
		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
		
		MouseHelper.grab();
		
        initGL();
        getDelta();
        lastFPS = getTime();
        
        world = new World();

        // Begin loop
        displayLoop();
		
	}
	    
	    
    /**
	 * 
	 */
	protected void displayLoop(){
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			if (Display.wasResized()) {
				initGL();
			}
			getDelta();
			update();
			render();
			Display.update();
		}
		Display.destroy();
		System.exit(0);
	}
		
		
	/**
	 * 
	 */
	protected void initGL(){
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		GLU.gluPerspective(60f, ((float) Display.getWidth() / (float) Display.getHeight()), 0.1f, 200.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glClearColor(0.1f, 0.4f, 0.6f, 0.0f);
		glClearDepth(1.0f);
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/terrain.png"));
			texture.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	protected void updateFPS(){
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	protected int getDelta(){
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    if (delta == 0) {
	    	delta = 1;
	    }
	    Game.delta = delta;
	    return delta;
	}
	
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	protected long getTime(){
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	
	/**
	 * 
	 * @param delta
	 */
	protected void update(){
		
		updateFPS();
		
		camera.yaw(Mouse.getDX() * mouseSensitivity);
		camera.pitch(Mouse.getDY() * mouseSensitivity);
		
		// Player movement
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
			camera.moveRight();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)){
			camera.moveLeft();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
			camera.moveForward();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)){
			camera.moveBackward();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			camera.moveUp();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_C)){
			camera.moveDown();
		}

	}
	

	/**
	 * Render
	 * @todo move to screens
	 */
	private void render(){
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

		camera.lookThrough();
		
//		Triangle.draw();
//		ColorSquare.draw( 20, 1, 20 );
//		TextureSquare.draw( texture, 20, 1, 20 );
//		TextureCube.draw( texture, 20, 1, 20 );
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		
		texture.bind();
		glActiveTexture(GL_TEXTURE0 + 0);
		
		world.render();
		
		// Show fake cursor
		glPolygonMode(GL_FRONT, GL_FILL);
		glLoadIdentity();
		UserInterface.enterOrtho();
		glColor3f(1f,1f,1f);
		UserInterface.drawString( Fonts.arialPlain.get(), "+", (Display.getWidth()/2), (Display.getHeight()/2), org.newdawn.slick.Color.white);
		
    	String d = "";
        d += "Coord: x:" + (int)camera.getPos().getX() + " y:" + (int)camera.getPos().getY() + " z:" + (int)camera.getPos().getZ();
        d += " Chks: " + world.getLoadedChunks().size();

        int lineHeight = Fonts.arialPlain.get().getHeight(d);
        int y = Display.getHeight() - lineHeight;
//        UserInterface.fillRect(2, y-3, UserInterface.getLineWidth(Fonts.arialPlain.get(), d)+20, (lineHeight)+6, new java.awt.Color(64, 64, 64));
        UserInterface.drawString( Fonts.arialPlain.get(), d, 5, y, org.newdawn.slick.Color.white );
		
        UserInterface.leaveOrtho();
		
	}


	
	
//	/**
//	 * 
//	 */
//	protected void drawSquareViaTriangles(){
//		
//		// triangle A
//		
//		// top left
//		triangleTesselator.addVertex(20, 40, 0f);
//		triangleTesselator.addColor(1, 0, 1);
//		
//		// bottom left
//		triangleTesselator.addVertex(20, 20, 0f);
//		triangleTesselator.addColor(1, 0, 0);
//		
//		// bottom right
//		triangleTesselator.addVertex(40, 20, 0f);
//		triangleTesselator.addColor(0, 1, 0);
//
//		
//		// Triangle B
//		
//		// top left
//		triangleTesselator.addVertex(20, 40, 0f);
//		triangleTesselator.addColor(1, 0, 1);
//
//		// top right
//		triangleTesselator.addVertex(40, 40, 0f);
//		triangleTesselator.addColor(0, 0, 1);
//		
//		// bottom right
//		triangleTesselator.addVertex(40, 20, 0f);
//		triangleTesselator.addColor(0, 1, 0);
//	
//		triangleTesselator.render();
//		
//	}
}