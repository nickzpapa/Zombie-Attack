package com.zombieattack;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.zombieattack.SfxManager.ZombieEffects;

public class FlameThrower extends Tower{

	//Tower Attributes
	public long flameDuration;
	public float flameSizeRatio;
	public float flameLen;
		
	Polygon flameArea;
	Flames flames;
	LineSeg flamePath;
	
	Zombie targetZ;
	private long attackTimer;
	
	public SfxManager flameSound = new SfxManager(),
			burningSound = new SfxManager();
	private OptionPreferences audioPreferences = new OptionPreferences();	
	
	FlameThrower(Vector2 spawnLoc, float width, float height, int fovRadius) {
		super(spawnLoc, width, height, fovRadius, TowerChoice.FLAME);
		
		this.flameDuration = 3000000000L;
		
		float polyVertices[] = new float[] {
			barrelTip.x, barrelTip.y,
			barrelTip.x + 84, barrelTip.y + 18,
			barrelTip.x + 84, barrelTip.y - 18,
			barrelTip.x + 112, barrelTip.y
		};	
		
		flames = new Flames(this);
		flameLen = flames.getLen();
		flamePath = new LineSeg(barrelTip, center, flameLen);	
		this.setFOV(90);
		setLength(75);
		
		flameArea = new Polygon(polyVertices);
		flameArea.setOrigin(center.x, center.y);
		targetZ = null;
		attackTimer = TimeUtils.nanoTime();
		
		flameSound.setVolume(audioPreferences.getSfxVolume());
		
		this.damage = 5;
	}

	public void draw(SpriteBatch sb, float delta, OrthographicCamera camera) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 0, 0, 1);
		shapeRenderer.polygon(flameArea.getVertices());
		shapeRenderer.end();
		shapeRenderer.dispose();
		
		flames.draw(sb, delta);		
	}
	

	public void attackTarget(Zombie zombie, SpriteBatch sb, float delta) {
		
		if(isAttacking())
			zombie = targetZ;
		else if(zombie == targetZ && zombie.isOnFire){
			return;
		}
		else {
			targetZ = zombie;
			attackTimer = TimeUtils.nanoTime();
			flameSound.play(ZombieEffects.FLAMESOUND);
		}
		
		Vector2 target = new Vector2(zombie.hitbox.x, zombie.hitbox.y);
		this.setLength(barrelTip.dst(target));
		rotateTurret(target);
		flamePath.rotate(turretDirection);
		if(flamePath.isCircleCollision(zombie.hitbox)){
			zombie.setOnFire(flameDuration, this.damage);
		}

		flames.draw(sb, delta);
		
	}
	
	public boolean isAttacking(){
		if(targetZ == null) {
			return false;
		}
		else if(TimeUtils.nanoTime() - attackTimer > 1000000000L){
			return false;
		}
		
		else
			return true;
	}
	
	public void damageEnemy(){
		
	}
	
	
	public void setLength(float newLength){
		flameSizeRatio = (newLength/flames.orgWidth);
		flames.width =  (int) (flames.orgWidth*flameSizeRatio);
		flames.height = (int) (flames.orgHeight*flameSizeRatio);
		
		this.flameLen = newLength;
		this.flamePath.setLength(newLength);
		
	}

	@Override
	public void attackTarget(Zombie zombie) {
		// TODO Auto-generated method stub
		
	}

	
	public void getAttribute1(){
		float rate = ((float)flameDuration/1000000000);
		float newRate = ((float)flameDuration+1000000000L)/1000000000;
		String str1 = "Burn Time: " + new DecimalFormat("#.##").format(rate).toString() + " (Seconds)";
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
		String str1 = "Flame Damage: " + this.damage + " (Dmg/Sec)";
		String str2 = "Next Level:    " + (this.damage + 2) + " (Dmg/Sec)";
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
			flameDuration+=1000000000L;
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
