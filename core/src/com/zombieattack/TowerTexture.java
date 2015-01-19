package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TowerTexture extends Texture{
	
	private Texture turret;		//	a texture which maintains a turret
	private Texture projectile;	//	a texture which maintains projectiles
	private Texture fovRadius;	//	a texture 
	
/**
 * Constructor if tower has a rotating turret
 * @param towerFileName		Location/Name of tower image
 * @param turretFileName	Location/Name of turret image
 * @param prjctlFileName	Location/Name of projectile image
 */
	TowerTexture(String towerFileName, String turretFileName, String prjctlFileName) {
		super(Gdx.files.internal(towerFileName));
		turret = new Texture(Gdx.files.internal(turretFileName));
		projectile = new Texture(Gdx.files.internal(prjctlFileName));
	}
	
/**
 * Constructor if tower does not have a rotating turret
 * @param towerFileName	The location of the tower image
 * @param prjctlFileName The location of the projetile image
 */
	TowerTexture(String towerFileName, String prjctlFileName) {
		super(Gdx.files.internal(towerFileName));
		projectile = new Texture(Gdx.files.internal(prjctlFileName));
		turret = new Texture(Gdx.files.internal(towerFileName));
	}
	
/**
 * Gets the turret texture if it has one
 * @return	The turret texture or null if there is none
 */
	public Texture getTurret(){
		try{
			if(turret != null)
				return turret;
			else
				throw new NullPointerException();			
		}
		catch(NullPointerException npex){
			System.err.println("Turret texture has not been initialized.");
			npex.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
/**
 * Gets the projectile texture
 * @return	The projectile texture
 */
	public Texture getProjectile(){
		return projectile;
	}

}
