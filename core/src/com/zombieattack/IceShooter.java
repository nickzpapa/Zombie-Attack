package com.zombieattack;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.zombieattack.SfxManager.ZombieEffects;

public class IceShooter extends Tower{

	private long lastFireTime;
	private long freezeDuration;
	

	
	IceShooter(Vector2 spawnLoc, float width, float height, int fovRadius) {
		super(spawnLoc, width, height, fovRadius,  TowerChoice.ICE);
		this.damage = 3;
		this.attackSpeed = 500000000;
		this.lastFireTime = 0;
		this.freezeDuration = 1000000000;
		iceSound.setVolume(audioPreferences.getSfxVolume());
		
	}

	@Override
	public void attackTarget(Zombie zombie) {	 
		Vector2 target = new Vector2(zombie.hitbox.x, zombie.hitbox.y);
		if(zombie.isFrozen)
			return;
		else if(TimeUtils.nanoTime() - lastFireTime > attackSpeed){
			rotateTurret(target);        	
			projectiles.add(new IceProjectile(barrelTip, target, 10, this.damage, freezeDuration));
			lastFireTime = TimeUtils.nanoTime(); 
			iceSound.play(ZombieEffects.ICESOUND);
		}    	  
		
	}

	@Override
	public void attackTarget(Zombie zombie, SpriteBatch sb, float delta) {
		// TODO Auto-generated method stub
		
	}

	
	public void getAttribute1(){
		float rate = ((float)freezeDuration/1000000000);
		float newRate = ((float)freezeDuration+1000000000L)/1000000000;
		String str1 = "Freeze Time: " + new DecimalFormat("#.##").format(rate).toString() + " (Seconds)";
		String str2 = "Next Level:   " + new DecimalFormat("#.##").format(newRate).toString() + " (Seconds)";
		String str3 = "Price:         $" + (attribute1*20);	
		if(attribute1==4){
			str2 = str1;
			str1 = "";
			str3 = "";
		}			
		InfoText.setText(str1, str2, str3);
	}
	
	public void getAttribute2(){
		String str1 = "Attack Damage: " + this.damage;
		String str2 = "Next Level:    " + (this.damage + 2);
		String str3 = "Price:          $" + (attribute2*20);	
		if(attribute2==4){
			str2 = str1;
			str1 = "";
			str3 = "";
		}
		InfoText.setText(str1, str2, str3);
	}
	
	public void getAttribute3(){
		String str1 = "Field Of View: " + (int)(fieldOfView.radius);
		String str2 = "Next Level:    " + (int)(fieldOfView.radius + 20);
		String str3 = "Price:          $" + (attribute3*20);
		if(attribute3==4){
			str2 = str1;
			str1 = "";
			str3 = "";
		}
		InfoText.setText(str1, str2, str3);
	}
	
	public void incAttribute1() {
		if(attribute1<4 && ZombieAttack.money>=(attribute1*20)) {
			freezeDuration+=1000000000L;
			ZombieAttack.money -= (attribute1*20);
			++attribute1;
		}			
	}
	
	public void incAttribute2() {
		if(attribute2<4 && ZombieAttack.money>=(attribute2*20)) {
			damage += 2;
			ZombieAttack.money -= (attribute2*20);
			++attribute2;
		}			
	}
	
	public void incAttribute3() {
		if(attribute3<4 && ZombieAttack.money>=(attribute3*20)) {
			fieldOfView.radius += 20;
			ZombieAttack.money -= (attribute3*20);
			++attribute3;
		}			
	}
}
