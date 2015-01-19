package com.zombieattack;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public class RegZombie extends Zombie{
	
	//Animation for every direction
	private Animation left;
	private Animation right;
	private Animation up;
	private Animation down;
	public float time;

	Direction currentDir;
	
	static float zombieInitSpeed = .5f;
	static float zombieSpeed;
	int zombieID;
	
	
	//Loads sprites for animations in every direction
	private TextureRegion lOne,	lTwo,lThree,rOne,rTwo ,rThree,uOne,	uTwo, uThree,dOne,dTwo,	dThree;
	
	/*Constructor setting the initial position of the zombie
	 * and  vector to determine the direction the zombie 
	 * will be headed.
	 */
	public RegZombie(float dx, float dy, Direction direction){
		this.dx = dx;
		this.dy = dy;
		this.health = 100;
		this.hitbox = new Circle(dx + 14, dy + 17, 17);
		this.currentDir = direction;
		this.zombieSpeed = this.zombieInitSpeed;
		
		Random rand = new Random();
		this.zombieID = rand.nextInt(6) + 1;
		
		lOne = new TextureRegion(new  Texture("Zombies/Z" + zombieID + "Left1.png"));
		lTwo = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Left2.png"));
		lThree = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Left3.png"));
		rOne = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Right1.png"));
		rTwo = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Right2.png"));
		rThree = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Right3.png"));
		uOne = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Up1.png"));
		uTwo = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Up2.png"));
		uThree = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Up3.png"));
		dOne = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Down1.png"));
		dTwo = new TextureRegion(new Texture("Zombies/Z" + zombieID + "Down2.png"));
		dThree =new TextureRegion(new Texture("Zombies/Z" + zombieID + "Down3.png"));
	}
	
	
	/*
	 * Sets zombie direction
	 */
	public void setDirection(Direction dir) {
		currentDir = dir;
	}
	
	public void move(SpriteBatch sb, float delta) {
		update();
		if(isOnFire)
			zombieOnFire.draw(sb, this, delta);
		if(isFrozen)
			frozen(sb);
		else if(currentDir == Direction.LEFT) 
			setLeft(sb, delta);
		else if(currentDir == Direction.RIGHT)
			setRight(sb, delta);
		else if(currentDir == Direction.UP)
			setUp(sb, delta);
		else if(currentDir == Direction.DOWN)
			setDown(sb, delta);	
	}
	
	void frozen(SpriteBatch sb){
		if(currentDir == Direction.LEFT) {
			sb.draw(lTwo, this.dx, this.dy);
		}
		else if(currentDir == Direction.RIGHT) {
			sb.draw(rTwo, this.dx, this.dy);
		}
		else if(currentDir == Direction.UP) {
			sb.draw(uTwo, this.dx, this.dy);
		}
		else if(currentDir == Direction.DOWN) {
			sb.draw(dTwo, this.dx, this.dy);
		}
		sb.draw(new Texture("icecube.png"), dx-4,dy-4);
	}
	
	
	
	
	void changeToY(float x, float y, Direction direction) {
		
		if(currentDir == Direction.RIGHT)
			if(this.dx == x && this.dy == y)
				this.setDirection(direction);
		if(currentDir == Direction.LEFT)
			if(this.dx == x && this.dy == y)
				this.setDirection(direction);
		
	}
	
	void changeToX(float x, float y, Direction direction) {
		
		if(currentDir == Direction.UP)
			if(this.dx == x && this.dy == y)
				this.setDirection(direction);
		if(currentDir == Direction.DOWN)
			if(this.dx == x && this.dy == y)
				this.setDirection(direction);
		
	}
	
	/* Makes zombie animation and draws it looking to
	 * the left side of the screen, moves it slowly 
	 * in that direction. 
	 */
	public void setLeft(SpriteBatch sb, float delta){
		left = new Animation(1/3f, lOne, lTwo, lThree);
		left.setPlayMode(Animation.PlayMode.LOOP);
		
		sb.draw(left.getKeyFrame(time += delta), dx-=zombieSpeed, dy);
		
		hitbox.x = dx + 14;
		hitbox.y = dy + 17;
		
	}
	
	public void setRight(SpriteBatch sb, float delta){
		right = new Animation(1/3f, rOne, rTwo, rThree);
		right.setPlayMode(Animation.PlayMode.LOOP);
		
		sb.draw(right.getKeyFrame(time += delta), dx+=zombieSpeed, dy);
		
		hitbox.x = dx + 14;
		hitbox.y = dy + 17;
	}
	
	public void setUp(SpriteBatch sb, float delta){
		up = new Animation(1/3f, uOne, uTwo, uThree);
		up.setPlayMode(Animation.PlayMode.LOOP);
		
		sb.draw(up.getKeyFrame(time += delta), dx, dy+=zombieSpeed);
		
		hitbox.x = dx + 14;
		hitbox.y = dy + 17;
		
	}
	
	public void setDown(SpriteBatch sb, float delta){
		down = new Animation(1/3f, dOne, dTwo, dThree);
		down.setPlayMode(Animation.PlayMode.LOOP);
		
		sb.draw(down.getKeyFrame(time += delta), dx, dy-=zombieSpeed);
		
		hitbox.x = dx + 14;
		hitbox.y = dy + 17;
	}

}
