package com.zombieattack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;

public class Explosion {
	
	private float time;
	private float start;
	private Animation explosion;
	private float x, y, damage, radius;
	
	
	private  final TextureRegion 	ex1 = new TextureRegion(new  Texture("explosion/explosion1.png")),
										ex2 = new TextureRegion(new Texture("explosion/explosion2.png")),
										ex3 = new TextureRegion(new Texture("explosion/explosion3.png")),
										ex4 = new TextureRegion(new Texture("explosion/explosion4.png")),
										ex5 = new TextureRegion(new Texture("explosion/explosion5.png")),
										ex6 = new TextureRegion(new Texture("explosion/explosion6.png")),
										ex7 = new TextureRegion(new  Texture("explosion/explosion7.png")),
										ex8 = new TextureRegion(new Texture("explosion/explosion8.png")),
										ex9 = new TextureRegion(new Texture("explosion/explosion9.png")),
										ex10 = new TextureRegion(new Texture("explosion/explosion10.png")),
										ex11 = new TextureRegion(new Texture("explosion/explosion11.png")),
										ex12 = new TextureRegion(new Texture("explosion/explosion12.png")),
										ex13 = new TextureRegion(new Texture("explosion/explosion13.png")),
										ex14 = new TextureRegion(new Texture("explosion/explosion14.png"));
	
	Explosion(float x, float y, int damage, ZombieList zombies){
		this.x = x;
		this.y = y;
		this.damage = damage;
		radius = 60;
		time = 0;
		start = TimeUtils.nanoTime();
		explosion = new Animation(1/14f, ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, ex13, ex14);
		explosion.setPlayMode(Animation.PlayMode.NORMAL);
		damageEnemies(zombies);
	}
	
	//damages enemies within blast area of explosion
	void damageEnemies(ZombieList zombies) {
		Circle blastArea = new Circle(x, y, radius);
		for(Zombie i : zombies){
			if(i.hitbox.overlaps(blastArea))
				i.health -= damage;							
		}
	}
	
	//Runs explosion animation
	//Returns false if animation has finished
	boolean go(SpriteBatch sb, float delta){		
		if(time < explosion.getAnimationDuration()) {								
			sb.draw(explosion.getKeyFrame(time+=delta), x-radius, y-radius, radius*2, radius*2);
			return true;
		}
		else return false;
	}

}
