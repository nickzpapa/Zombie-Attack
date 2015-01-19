package com.zombieattack;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.text.DecimalFormat;

public abstract class Tower {
	
	//	Tower specific attributes
	public float fovRadius;		//	the radius of the field of view
	public long attackSpeed;		//	or "attack delay"
	public int damage;
	
	//	Tower location and interaction attributes
	public Vector2 center, corner;
	public float width, height;	
	public Circle fieldOfView, spawnCircle;			//	a tower's field of view
	public float turretDirection;					//	the direction the turret is facing in degrees (0-359)
	protected Vector2 barrelTip;
	
	/*	Tower Rectangles */
	public Rectangle towerArea;
	public ArrayList<Projectile> projectiles;	//	all the projectiles a tower creates
	public boolean isAttacking;
		
	UpgradeBox upgradeBox;
	
	long clickWaitTime;
	boolean showingUpgradeBox;
	
	public SfxManager iceSound = new SfxManager();
	protected OptionPreferences audioPreferences = new OptionPreferences();
	
	int attribute1, attribute2, attribute3;
	
	
/**
 * Creates a tower object at the provided location
 * @param isCenterLoc	Is the provided location for the center or corner
 * @param xLoc			X location
 * @param yLoc			Y location
 * @param width			Width of the tower
 * @param height		Height of the tower
 */
	Tower(Vector2 spawnLoc, float width, float height, int fovRadius, TowerChoice choice) {
		
		//	setting width and height
		this.width = width;
		this.height = height;
		
		this.attribute1 = 1;
		this.attribute2 = 1;
		this.attribute3 = 1;
		
		//	setting the location of the tower
		this.center 	= new Vector2(spawnLoc);
		this.corner 	= new Vector2(center.x-15, center.y-15);
		this.barrelTip 	= barrelOrigin();

		//	setting the field of view circle
		this.fovRadius 	 = fovRadius;
		this.fieldOfView = new Circle(center.x, center.y, fovRadius);
		this.spawnCircle = new Circle(center.x, center.y, 54);
		
		this.damage = damage;
		
		//	setting default turret direction
		this.turretDirection = 0;	
		
		this.projectiles = new ArrayList<Projectile>();
		this.isAttacking = false;
		
		towerArea = new Rectangle(corner.x, corner.y, width, height);
		upgradeBox = new UpgradeBox(corner, this);
		upgradeBox.upgradeBox.setVisible(false);
		clickWaitTime = TimeUtils.nanoTime();
	}
	
/**
 * Gets/Sets the direction of which a turret is pointed in degrees.
 * @param target	The location of another object to be pointed at.
 * @return			The direction in degrees the turret is rotated.
 */
    public float rotateTurret(Vector2 target) {    	
    	float dx = target.x - this.center.x;
    	float dy = target.y - this.center.y;
    	turretDirection = (float) (Math.atan2(dy, dx) * 180 / Math.PI);
    	
    	Vector2 temp = new Vector2(barrelOrigin());
    	temp.x-=center.x;
    	temp.y-=center.y;
    	barrelTip.x = (temp.x) * MathUtils.cosDeg(turretDirection) - (temp.y) * MathUtils.sinDeg(turretDirection);
    	barrelTip.y = (temp.x) * MathUtils.sinDeg(turretDirection) + (temp.y) * MathUtils.cosDeg(turretDirection);
    	barrelTip.x += center.x;
    	barrelTip.y += center.y;
    	
    	//System.out.println((barrelOrigin().toString()) + (turretDirection) +  (barrelTip.toString()));
    	return turretDirection;
    }
    
    public Vector2 getBarrelTip(){
    	return barrelTip;
    }
    
    
    private Vector2 barrelOrigin(){
    	return new Vector2(corner.x+width, center.y);
    	
    }
    
    
    public boolean isZombie(Circle zombieLoc) {
    	if(fieldOfView.contains(zombieLoc))
    		return true;
    	else return false;
    }
      
    public void setFOV(float radius){
    	this.fieldOfView.radius = radius;
    }
    
    public void attackTarget(Zombie zombie) {}
    
    public void attackTarget(Zombie zombie, SpriteBatch sb, float delta) {}
    


    
    
    public boolean isWithinSpawn(Vector2 location){
    	
    	if(spawnCircle.contains(location))
    		return true;
    	else 
    		return false;
    	
    }
    
    public boolean showUpgradeBox(Vector2 loc){
    	if(loc == null) return false;
    	else if(TimeUtils.nanoTime() > clickWaitTime + 50000000L && this.towerArea.contains(loc))	{	
    		if(!showingUpgradeBox) {
    			upgradeBox.upgradeBox.setVisible(true);
    			clickWaitTime = TimeUtils.nanoTime();
    			return true;
    		}
    		else if(!upgradeBox.area.contains(loc)) {
    			System.out.println("hereh");
    			upgradeBox.upgradeBox.setVisible(false);
    			clickWaitTime = TimeUtils.nanoTime();
    			return false;
    		}    		
    	}
		return false;    	
    }
    
    public void drawFOV(OrthographicCamera camera){
    	if(upgradeBox.upgradeBox.isVisible()) {
    		
    		Gdx.gl.glEnable(GL20.GL_BLEND);
    		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    		

    		ShapeRenderer shapeRenderer = new ShapeRenderer();
    		shapeRenderer.setProjectionMatrix(camera.combined);
    		shapeRenderer.begin(ShapeType.Filled);

    		shapeRenderer.setColor(.3f, .3f, 1f, .3f);
    		shapeRenderer.circle(fieldOfView.x, fieldOfView.y, fieldOfView.radius);
    		shapeRenderer.end();
    		
    		shapeRenderer.begin(ShapeType.Line);
    		shapeRenderer.setColor(.5f, .5f, 1f, .5f);
    		shapeRenderer.circle(fieldOfView.x, fieldOfView.y, fieldOfView.radius);
    		shapeRenderer.end();
    		shapeRenderer.dispose();
    		Gdx.gl.glDisable(GL20.GL_BLEND);
    		
    		
    		
    		
//    		ShapeRenderer shapeRenderer = new ShapeRenderer();
//    		shapeRenderer.setProjectionMatrix(camera.combined);
//    		shapeRenderer.begin(ShapeType.Line);
//    		shapeRenderer.setColor(1, 0.25f, .25f , .25f);
//    		shapeRenderer.polygon(pathArea.getVertices());
//    		
//    	
//    		for(Circle i : towerCircles)
//    			shapeRenderer.circle(i.x, i.y, i.radius);
//    		
//    		shapeRenderer.end();
//    		shapeRenderer.dispose();
//    		Gdx.gl.glDisable(GL20.GL_BLEND);
    		
    	}
    	
    }

	public abstract void getAttribute1();
	public abstract void getAttribute2();  	
	public abstract void getAttribute3(); 
	public abstract void incAttribute1();
	public abstract void incAttribute2();
	public abstract void incAttribute3();
    
}
