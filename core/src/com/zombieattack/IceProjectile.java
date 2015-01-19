package com.zombieattack;

import com.badlogic.gdx.math.Vector2;

public class IceProjectile extends Projectile {

	private long duration;
	
	IceProjectile(Vector2 start, Vector2 target, float width, int damage, long duration){
		super(start, target, width, damage);
		this.duration = duration;
	}
	
	public long getDuration(){
		return duration;
	}
	
}
