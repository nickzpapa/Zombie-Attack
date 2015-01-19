package com.zombieattack;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Projectile {
	
	private Vector2 start;
	public float velX, velY;
	public float direction;
	public Circle hitbox;
	public long timeCreated;
	public int damage;
	
/**
 * Creates a new Projectile using a the given location as the center	
 * @param locX		X center location
 * @param locY		Y center location
 * @param width		width of projectile
 * @param height	height of projectile
 */
	Projectile(Vector2 start, Vector2 target, float width, int damage) {
		this.start= start; 
		this.hitbox = new Circle(start.x, start.y, width/2);
		this.damage = damage;
		this.timeCreated = TimeUtils.nanoTime();
		
		float dirX = target.x - start.x;
		float dirY = target.y - start.y;		
		float trajectory = (float) Math.sqrt(dirX*dirX + dirY*dirY);
		
		if (trajectory != 0) {
			dirX /= trajectory;
			dirY /= trajectory;
		}
		velX = dirX * 1000;
		velY = dirY * 1000;
	}
	
	
	public void update(float deltaTime){
		hitbox.x += velX * deltaTime;
		hitbox.y += velY * deltaTime;
	}
	

}
