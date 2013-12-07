package com.helion3.tests;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float pitch = 0;
	private float yaw = 0;
	private Vector3f position = new Vector3f(0,0,0);
	
	
	/**
	 * Returns the current yaw
	 * @return
	 */
	public float yaw(){
		return yaw;
	}
	
	
	/**
	 * Sets the current yaw
	 * @param amount
	 */
	public void yaw(float amount){
		yaw -= amount;
    }
	
	
	/**
	 * Returns the current pitch
	 * @return
	 */
	public float pitch(){
		return pitch;
	}
	
	
	/**
	 * Sets the current pitch
	 * @param amount
	 */
    public void pitch(float amount){
		pitch -= amount;
    }
    
    
    /**
	 * Calc the x position from an angle
	 * @param angle
	 * @return
	 */
    protected float getXPos(float angle){
    	return (float)Math.sin(Math.toRadians(angle));
    }
    
    
    /**
     * Calc the z position from an angle
     * @param angle
     * @return
     */
    protected float getZPos(float angle){
    	return (float)Math.cos(Math.toRadians(angle));    	
    }
    
    
    /**
     * 
     */
    public void moveLeft(){
    	position.x += 0.1f * getXPos(yaw- 90);
		position.z += 0.1f * getZPos(yaw- 90);
    }
    
    
    /**
     * 
     */
    public void moveRight(){
    	position.x -= 0.1f * getXPos(yaw-90);
		position.z -= 0.1f * getZPos(yaw-90);
    }
    
    
    /**
     * 
     */
    public void moveForward(){
    	position.x -= (float) getXPos(yaw) * (0.15f);
		position.z -= (float) getZPos(yaw) * (0.15f);
    }
    
    
    /**
     * 
     */
    public void moveBackward(){
    	position.x += (float) getXPos(yaw) * (0.15f);
		position.z += (float) getZPos(yaw) * (0.15f);
    }
    
    
    /**
     * 
     */
    public void moveUp(){
    	position.y += 1;
    }
    
    
    /**
     * 
     */
    public void moveDown(){
    	position.y -= 1;
    }

    
    /**
	 * 
	 */
	public void lookThrough(){
		GL11.glLoadIdentity();
		GL11.glRotatef(pitch, 1.0f, 0, 0);
		GL11.glRotatef(360.0f - yaw, 0, 1.0f, 0);
		GL11.glTranslatef(-position.x, -position.y, -position.z); // would be an x/y/z position of camera/player
	}
}