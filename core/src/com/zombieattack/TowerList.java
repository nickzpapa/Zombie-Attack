package com.zombieattack;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zombieattack.SfxManager.ZombieEffects;

public class TowerList extends ArrayList<Tower>{

	NoClickZone noClickZone;
	
	TowerTexture sharpTexture;	//sharpshooter texture
	TowerTexture iceTexture; //ice shoooter texture
	TowerTexture flameTexture; //flamethower texture
	TowerTexture missleTexture; //missle-launcher texture
	Stage stage;

	public SfxManager hitSound = new SfxManager();
	private OptionPreferences audioPreferences = new OptionPreferences();
	
	ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	
	TowerList(Stage stage, ZombieList zombies, NoClickZone noClickZone) {
		this.noClickZone = noClickZone;
		sharpTexture = new TowerTexture("Turrets/turret1.png", "Turrets/bullet.png");
		iceTexture = new TowerTexture("Turrets/turret4.png", "Turrets/ice_shot.png");
		missleTexture = new TowerTexture("Turrets/turret3.png", "Turrets/exploda_dot.png");
		flameTexture = new TowerTexture("Turrets/turret5.png", "flamethrower.png");
		hitSound.setVolume(audioPreferences.getSfxVolume());
		this.stage = stage;
		
	}
	
	
	public void updateTurrets(Vector2 clickLoc, SpriteBatch sb, float delta, ZombieList zombies) {
		
        for(Tower t : this) {  
        	t.showUpgradeBox(clickLoc);
            for(Zombie z : zombies)
	            if(t.isZombie(z.hitbox)){
	            	if(t instanceof FlameThrower)
	            		t.attackTarget(z, sb, delta);
	            	else
	            	   	t.attackTarget(z);
	            	t.isAttacking = true;
	            }
	            else
	            	t.isAttacking = false;
        }
		
	}	
	
	public void updateProjectiles(float delta, ZombieList zombies) {
		
		for(Tower t : this) {
			Iterator<Projectile> piter = t.projectiles.iterator();
			while(piter.hasNext()) {

				Projectile p = piter.next();
				p.update(delta);
        		for(Zombie z : zombies) {
        			if(p.hitbox.overlaps(z.hitbox)) {    
        				if(p instanceof BombProjectile){
        					explosions.add(new Explosion(z.hitbox.x, z.hitbox.y, t.damage, zombies));
        					hitSound.play(ZombieEffects.EXPLOSION);
        				}
        				else if(p instanceof IceProjectile) {
        					z.setFrozen(((IceProjectile) p).getDuration());
        					z.health -= p.damage;
        				}
        				else
        					z.health -= p.damage;  
        				hitSound.play(ZombieEffects.ZOMBIEHIT);

        				piter.remove();
        				break;   
        			}
        		}
			}
        }
	}
	
	
    public boolean addTower(Vector2 clickLoc, TowerChoice choice) {
    	
    	if(noClickZone.isGoodSpawnLocation(clickLoc)) {
    		Tower t = null;
    		if(choice == TowerChoice.SHARPSHOOTER) {
    			t = new SharpShooter(clickLoc, 52, 30, 125);
    			stage.addActor(t.upgradeBox.upgradeBox);
    		}
    		else if(choice == TowerChoice.ICE){
    			t = new IceShooter(clickLoc, 52, 30, 125);
    			stage.addActor(t.upgradeBox.upgradeBox);
    		}
    		else if(choice == TowerChoice.MISSLE){
    			t = new MissleLauncher(clickLoc, 52, 30, 125);
    			stage.addActor(t.upgradeBox.upgradeBox);	
    		}    
    		else if(choice == TowerChoice.FLAME){
    			t = new FlameThrower(clickLoc, 52, 30, 125);
    			stage.addActor(t.upgradeBox.upgradeBox);	
    		}  
    		
    		if(t==null)
    			return false;
    		else 
    			this.add(t);
    		
    		noClickZone.addCircle(clickLoc);
    		return true;
    	}
    	else
    		return false;
    }
    
    
    public void drawTowers(SpriteBatch batch, float delta) {
    	
		for(Tower t : this) {	
			
			//getting the texture of the tower to draw
			TowerTexture towerTexture = null;
			if(	t instanceof SharpShooter)
				towerTexture = sharpTexture;
			else if(t instanceof IceShooter)
				towerTexture = iceTexture;
			else if(t instanceof FlameThrower)
				towerTexture = flameTexture;
			else if(t instanceof MissleLauncher)
				towerTexture = missleTexture;
				
			
			//drawing projectiles created by a tower
			if(t instanceof SharpShooter || t instanceof IceShooter || t instanceof MissleLauncher){				
				for(Projectile p : t.projectiles) {
					float pRadius = p.hitbox.radius;
					batch.draw(towerTexture.getProjectile(), p.hitbox.x-pRadius, p.hitbox.y-pRadius, 2*pRadius, 2*pRadius);
				}				
			}
			
			//drawing turret with direction
			batch.draw(
					new TextureRegion(towerTexture.getTurret()), 
					t.center.x-15, 	//	location x
					t.center.y-15,  //	location y
					15,				//	x rotation loc
					15,				//	y ratation loc
					towerTexture.getTurret().getWidth(),
					towerTexture.getTurret().getHeight(),
					1,				//	scale x
					1,				//	scale y
					t.turretDirection					
			);	
				

		} 
		
		//drawing bomb explosion(s) and removing if the animation has finished
		Iterator<Explosion> x = explosions.iterator();
		while(x.hasNext()) {
			Explosion explosion = x.next();
			if(!explosion.go(batch, delta))
				x.remove();
		}    	
    }
	
    
    void drawFOV(OrthographicCamera camera){
    	for(Tower i : this)
    		i.drawFOV(camera);
    }
    
}
