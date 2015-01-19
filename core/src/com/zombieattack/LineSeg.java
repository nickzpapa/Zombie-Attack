package com.zombieattack;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LineSeg {

	Vector2 p1, p2;
	Vector2 org1, org2, src;

	LineSeg(Vector2 point, Vector2 src, float length) {

		//setting up points
		this.p1 = new Vector2(point);
		this.p2 = new Vector2(point);
			p2.x += length;
		this.org1 = new Vector2(p1);
		this.org2 = new Vector2(p2);	
		//point to rotate around
		this.src = new Vector2 (src);
	}
	
	public void setLength(float len){
		org2.x = org1.x + len;
	}
	public float getLength(){
		return org2.x - org1.x;
	}

	public void rotate(float degrees){		
    	Vector2 temp = new Vector2(org1);
    	temp.x -= src.x;
    	temp.y -= src.y;
    	p1.x = (temp.x) * MathUtils.cosDeg(degrees) - (temp.y) * MathUtils.sinDeg(degrees);
    	p1.y = (temp.x) * MathUtils.sinDeg(degrees) + (temp.y) * MathUtils.cosDeg(degrees);
    	p1.x += src.x;
    	p1.y += src.y;
    	
    	temp = new Vector2 (org2);
    	temp.x -= src.x;
    	temp.y -= src.y;
    	p2.x = (temp.x) * MathUtils.cosDeg(degrees) - (temp.y) * MathUtils.sinDeg(degrees);
    	p2.y = (temp.x) * MathUtils.sinDeg(degrees) + (temp.y) * MathUtils.cosDeg(degrees);
    	p2.x += src.x;
    	p2.y += src.y;    	
	}
	
	public boolean isCircleCollision(Circle circle){
		return Intersector.intersectSegmentCircle(p1, p2, new Vector2(circle.x, circle.y), (circle.radius*2));	
	}
}
