package com.zombieattack;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;
public class Zombie {
	//Position on the screen 
	protected float x;
	protected float y;
	//Zombie vector
	protected float dx; 
	protected float dy;

	protected int health;
	public Circle hitbox;
	
	static float zombieInitSpeed;
	float zombieSpeed;
	
	protected boolean isOnFire, isFrozen;
	int fireDamage = 6;
	long effectDuration, lastEffectTime, lastBurnTime;
	ZombieOnFire zombieOnFire;
	
	
	Zombie(){
		lastEffectTime = 0;
		effectDuration = 0;
		isOnFire = false;
		isFrozen = false;
		zombieOnFire = new ZombieOnFire();
	}
	
	void setOnFire(long duration, int fireDamage){
		isFrozen = false;
		isOnFire = true;
		effectDuration = duration;
		lastEffectTime = TimeUtils.nanoTime();
		lastBurnTime = TimeUtils.nanoTime();
		
		this.fireDamage = fireDamage;
	}
	
	void setFrozen(long duration){
		isOnFire = false;
		isFrozen = true;
		zombieSpeed = 0;
		effectDuration = duration;
		lastEffectTime = TimeUtils.nanoTime();
	}
	
	void update(){
		if(TimeUtils.nanoTime()-lastEffectTime > effectDuration){
			isOnFire = false;
			isFrozen = false;
			zombieSpeed = zombieInitSpeed;
		}		
		if(isOnFire && TimeUtils.nanoTime()-lastBurnTime > 500000000){
			health -= fireDamage;
			lastBurnTime = TimeUtils.nanoTime();
		}
	}
	
}
