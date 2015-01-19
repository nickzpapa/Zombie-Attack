package com.zombieattack;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.zombieattack.SfxManager.ZombieEffects;

public class MissleLauncher extends Tower {
	
	public SfxManager missileSound = new SfxManager();
	private OptionPreferences audioPreferences = new OptionPreferences();
	
	long lastFireTime;
	long attackSpeed;

	MissleLauncher(Vector2 spawnLoc, float width, float height, int fovRadius) {
		super(spawnLoc, width, height, fovRadius, TowerChoice.MISSLE);
		// TODO Auto-generated constructor stub
		this.damage = 10;
		this.attackSpeed = 3000000000L;
		this.lastFireTime = 0;
		missileSound.setVolume(audioPreferences.getSfxVolume());
	}

	@Override
	public void attackTarget(Zombie zombie) {	
		Vector2 target = new Vector2(zombie.hitbox.x, zombie.hitbox.y);
		if(TimeUtils.nanoTime() - lastFireTime > attackSpeed){
			rotateTurret(target);        	
			projectiles.add(new BombProjectile(barrelTip, target, 10, this.damage));
			lastFireTime = TimeUtils.nanoTime(); 
			missileSound.play(ZombieEffects.MISSILESOUND);
		}    	  
		
	}

	@Override
	public void attackTarget(Zombie zombie, SpriteBatch sb, float delta) {
		// TODO Auto-generated method stub
		
	}

	
	public void getAttribute1(){
		float rate = (1000000000/(float)attackSpeed);
		float newRate = 1000000000/((float)attackSpeed-300000000L);
		String str1 = "Attack Speed: " + new DecimalFormat("#.##").format(rate).toString() + " (Shots/Sec)";
		String str2 = "Next Level:   " + new DecimalFormat("#.##").format(newRate).toString() + " (Shots/Sec)";
		String str3 = "Price:         $" + (attribute1*30);	
		if(attribute1==4){
			str2 = str1;
			str1 = "";
			str3 = "";
		}
		
		InfoText.setText(str1, str2, str3);
	}
	
	public void getAttribute2(){
		String str1 = "Attack Damage: " + this.damage;
		String str2 = "Next Level:    " + (this.damage + 10);
		String str3 = "Price:          $" + (attribute2*30);		
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
		String str3 = "Price:          $" + (attribute3*30);
		if(attribute3==4){
			str2 = str1;
			str1 = "";
			str3 = "";
		}
		InfoText.setText(str1, str2, str3);
	}
	
	public void incAttribute1() {
		if(attribute1<4 && ZombieAttack.money>=(attribute1*30)) {
			attackSpeed-=300000000L;
			ZombieAttack.money -= (attribute1*30);
			++attribute1;
		}			
	}
	
	public void incAttribute2() {
		if(attribute2<4 && ZombieAttack.money>=(attribute2*30)) {
			damage += 10;
			ZombieAttack.money -= (attribute2*30);
			++attribute2;
		}			
	}
	
	public void incAttribute3() {
		if(attribute3<4 && ZombieAttack.money>=(attribute3*30)) {
			fieldOfView.radius += 20;
			ZombieAttack.money -= (attribute3*30);
			++attribute3;
		}			
	}


}
