package com.tind.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Class to handle all player inputs
 * extends InputAdapter and implements InputProcessor
 */
public class Control extends InputAdapter implements InputProcessor {
    // CAMERA
    OrthographicCamera camera;

    // DIRECTIONS
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    // ACTIONS
    public boolean interact;
    public float   angle;
    public int     direction;
    public Enums.Compass facing;
    public boolean spacePressed;
   
    // MOUSE
    public boolean  leftMouseBtn;
    public boolean  rightMouseBtn;
    public boolean  processedClick = true;
    public Vector2  mouseClickPos = new Vector2();
    public Vector2  mousePos = new Vector2();
    public Vector2  mapClickPos = new Vector2();
    
    // DEBUG
    public boolean debug;
    public boolean reset;
    public boolean inventory;
    
    // SCREEN
    int screenWidth;
    int screenHeight;

    public Control(int screenWidth, int screenHeight, OrthographicCamera camera){
        this.camera = camera;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    private void setMouseClickedPos(int screenX, int screenY){
        // Set mouse position (flip screen Y)
        mouseClickPos.set(screenX, screenHeight - screenY);
        mapClickPos.set(get_map_coords(mouseClickPos));
    }
        
    public Vector2 get_map_coords(Vector2 mouseCoords){
        Vector3 v3 = new Vector3(mouseCoords.x, screenHeight - mouseCoords.y, 0);
        this.camera.unproject(v3);
        return new Vector2(v3.x,v3.y);
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Keys.DOWN:
                down = true;
                break;
            case Keys.UP:
                up = true;
                break;
            case Keys.LEFT:
                left = true;
                break;
            case Keys.RIGHT:
                right = true;
                break;
            case Keys.W:
                up = true;
                break;
            case Keys.A:
                left = true;
                break;
            case Keys.S:
                down = true;
                break;
            case Keys.D:
                right = true;
                break;
            case Keys.SPACE:
            	spacePressed = true;
            	break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.DOWN:
                down = false;
                break;
            case Keys.UP:
                up = false;
                break;
            case Keys.LEFT:
                left = false;
                break;
            case Keys.RIGHT:
                right = false;
                break;
            case Keys.W:
                up = false;
                break;
            case Keys.A:
                left = false;
                break;
            case Keys.S:
                down = false;
                break;
            case Keys.D:
                right = false;
                break;
            case Keys.E:
                interact = true;
                break;
            case Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Keys.BACKSPACE:
                debug = !debug;
                break;
            case Keys.R:
                reset = true;
                break;
            case Keys.I:
                inventory = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            leftMouseBtn = true; 
        } else if (pointer == 0 && button == 0){
            rightMouseBtn = true; 
        }
    
        setMouseClickedPos(screenX, screenY);
        return false;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            leftMouseBtn = false; 
            processedClick = false;
        } else if (pointer == 0 && button == 0){
            rightMouseBtn = false; 
        }
    
        setMouseClickedPos(screenX, screenY);
        return false;
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        setMouseClickedPos(screenX, screenY);
        return false;
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	float flippedY = screenHeight - screenY;
        mousePos.set(screenX, flippedY);
        
    	// Set angle of mouse
    	angle = (float) Math.toDegrees(Math.atan2(screenX - (screenWidth/2), screenY - (screenHeight/2)));
	    angle = angle < 0 ? angle += 360: angle;
	    
	    // Set the COMPAS direction of the mouse cursor
	    direction = (int) Math.floor((angle / 45) + 0.5) & 7;
	    facing = Enums.Compass.values()[direction];
	    	
        return false;
    }
    
    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
