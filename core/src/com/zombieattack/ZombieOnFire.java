package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class ZombieOnFire {

	private float time;
	private float start;
	private Animation flame;
	private float width, height;

	
	ZombieOnFire(){	
		Texture flameSheet = new Texture(Gdx.files.internal("flames.png"));
		this.width = flameSheet.getWidth()/4;
		this.height = flameSheet.getHeight()/1;
		TextureRegion[][] flameFrames = TextureRegion.split(flameSheet, (int)width, (int)height);
		flame = new Animation(.20f, flameFrames[0]);
		flame.setPlayMode(Animation.PlayMode.LOOP);		
		time = 0;
		start = TimeUtils.nanoTime();
	}
	
	public void draw(SpriteBatch sb, Zombie z, float delta){
		float x = z.hitbox.x-18;
		float y = z.hitbox.y-16;
		sb.draw(flame.getKeyFrame(time+=delta), x, y, width, height);		
	}
	
}
