package com.zombieattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ZombieAttack extends Game {

    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 600;

	public SpriteBatch batch;
	public BitmapFont font;
    public ShapeRenderer shape;
	
    public static int money = 500;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont();
        shape = new ShapeRenderer();
        this.setScreen(new MainMenu());
	}

	@Override
	public void render () {
        super.render();
	}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    public static void moneyCheat() {
    	if(Gdx.input.isKeyPressed(Input.Keys.Z) &&
    			Gdx.input.isKeyPressed(Input.Keys.O) &&
    			Gdx.input.isKeyPressed(Input.Keys.M) &&
    			Gdx.input.isKeyPressed(Input.Keys.B))
    		ZombieAttack.money = 9999;
    }
    
    public static float getAngle(float x1, float y1, float x2, float y2) {
    	
    	float dx = x2 - x1;
    	float dy = y2 - y1;
    	return (float) (Math.atan2(dy, dx) * 180 / Math.PI);
    }
    
    public static Vector2 getClickLocation(){
    	if(Gdx.input.isTouched()){
	        float xLoc = Gdx.input.getX();
	        float yLoc = ZombieAttack.flipY(Gdx.input.getY());
	        
	        return new Vector2(xLoc, yLoc);
    	}
    	else
    		return null;
    }

    public static void printClickLocation(){
    	Vector2 clickLoc = getClickLocation();
    	if(clickLoc != null)
    		System.out.println("Click Location: [" + clickLoc.x + ", " + clickLoc.y + "]");
    }
    
    public static boolean isWithinRectangle(Rectangle rect, float xLoc, float yLoc) {

        //  is Location within the Y and Y bounds of the Rectangle?
        if(xLoc >= rect.getX() && xLoc <= (rect.getX()+rect.getWidth()) &&
            yLoc >= rect.getY() && yLoc <= rect.getY()+rect.getHeight()
          ) {
                return true;
        }
        else {
            return false;
        }
    }

    public static float flipY(float yLoc){
        return ZombieAttack.SCREEN_HEIGHT - yLoc;
    }
    
    static public void speedUpZombies() {
    	if(Gdx.input.isKeyPressed(Input.Keys.Z))  
    		RegZombie.zombieSpeed = 10f;
    	else
    		RegZombie.zombieSpeed = .5f;
    }
    
}
