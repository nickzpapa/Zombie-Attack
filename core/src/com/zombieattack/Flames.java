package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Flames {
	
	private float time;
	private float start;
	private Animation flame;
	public float orgWidth, orgHeight, width, height;
	
	private FlameThrower tower;
	
	Flames(FlameThrower tower){		
		this.tower = tower;		
		Texture flameSheet = new Texture (Gdx.files.internal("flamethrower.png"));	
		this.orgWidth = flameSheet.getWidth();
		this.orgHeight= flameSheet.getHeight() / 2;
		TextureRegion[][] flameFrames = TextureRegion.split(flameSheet, (int)orgWidth, (int)orgHeight);
		TextureRegion[] frames = new TextureRegion[2];
			frames[0] = flameFrames[0][0];
			frames[1] = flameFrames[1][0];
		
		
		this.width = this.orgWidth;
		this.height = this.orgHeight;
		flame = new Animation(.1f, frames);
		flame.setPlayMode(Animation.PlayMode.LOOP);		
		time = 0;
		start = TimeUtils.nanoTime();
	}
	
	public void draw(SpriteBatch sb, float delta){				
		
		sb.draw(
				flame.getKeyFrame(time+=delta), 
				tower.barrelTip.x, 	//	location x
				tower.barrelTip.y-height/2,  //	location y
				0,				//	x rotation loc
				height/2,		//	y rotation loc
				width,
				height,
				1,				//	scale x
				1,				//	scale y
				tower.turretDirection					
		);
	}
	
	public float getLen(){
		return width;
	}
	
}
