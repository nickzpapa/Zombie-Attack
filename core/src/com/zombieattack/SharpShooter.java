package com.zombieattack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.zombieattack.SfxManager.ZombieEffects;

import java.text.DecimalFormat;

public class SharpShooter extends Tower{

	private long lastFireTime;
	
	public SfxManager sharpSound = new SfxManager();
	private OptionPreferences audioPreferences = new OptionPreferences();
	
	SharpShooter(Vector2 spawnLoc, float width, float height, int fovRadius) {
		super(spawnLoc, width, height, fovRadius, TowerChoice.SHARPSHOOTER);
		this.damage = 7;
		this.attackSpeed = 500000000L;
		this.fieldOfView.radius = 150;
		this.lastFireTime = 0;
		sharpSound.setVolume(audioPreferences.getSfxVolume());
	}

	@Override
	public void attackTarget(Zombie zombie) {	
		Vector2 target = new Vector2(zombie.hitbox.x, zombie.hitbox.y);
		if(TimeUtils.nanoTime() - lastFireTime > attackSpeed){
			rotateTurret(target);        	
			projectiles.add(new Projectile(barrelTip, target, 10, this.damage));
			lastFireTime = TimeUtils.nanoTime(); 
			sharpSound.play(ZombieEffects.SHARPSOUND);
		}    	  
		
	}

	@Override
	public void attackTarget(Zombie zombie, SpriteBatch sb, float delta) {
	}
	
	public void getAttribute1(){
		float rate = (1000000000/(float)attackSpeed);
		float newRate = 1000000000/((float)attackSpeed-100000000L);
		String str1 = "Attack Speed: " + new DecimalFormat("#.##").format(rate).toString() + " (Shots/Sec)";
		String str2 = "Next Level:   " + new DecimalFormat("#.##").format(newRate).toString() + " (Shots/Sec)";
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
			attackSpeed-=100000000L;
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
