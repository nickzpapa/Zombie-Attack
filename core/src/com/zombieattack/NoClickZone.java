package com.zombieattack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.DelaunayTriangulator;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

public class NoClickZone {

	//Zones that cannot contain a turret
	ArrayList<Rectangle> pathRectangles;
	ArrayList<Circle> towerCircles;
	Polygon pathArea;
	
	//Used for editing path areas
	boolean isFirst;
	String storagePath;
	
	NoClickZone(String fileName) {
		
		pathRectangles = new ArrayList<Rectangle>();
		towerCircles = new ArrayList<Circle>();
		
		storagePath = fileName;
		System.out.println(storagePath);
		parseFileToPoly();
		
	}
	
	public void draw(OrthographicCamera camera){
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 0.25f, .25f , .25f);
		shapeRenderer.polygon(pathArea.getVertices());
		
	
		for(Circle i : towerCircles)
			shapeRenderer.circle(i.x, i.y, i.radius);
		
		shapeRenderer.end();
		shapeRenderer.dispose();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		
		Texture color = new Texture (Gdx.files.internal("itsadot.png"));
		PolygonSprite poly;
		PolygonSpriteBatch polyBatch;
		EarClippingTriangulator earMe = new EarClippingTriangulator();
		ShortArray triangles = new ShortArray(earMe.computeTriangles(pathArea.getVertices()));
		PolygonRegion polyRegion = new PolygonRegion(
			new TextureRegion(color), 
			pathArea.getVertices(), 
			triangles.toArray()
		);
		poly = new PolygonSprite(polyRegion);
		polyBatch = new PolygonSpriteBatch();
		polyBatch.begin();
		poly.draw(polyBatch);
		polyBatch.end();
		polyBatch.dispose();
		
	}
	
	public void addCircle(Vector2 loc) {
		towerCircles.add(new Circle(loc, 55));
	}
	
	public boolean isGoodSpawnLocation(Vector2 clickLoc) {
    	   
		if(clickLoc == null)
			return false;
		
    	//checking if spawn location is on the zombie path
		else if (pathArea.contains(clickLoc.x, clickLoc.y))
    		return false;
    	//checking if click in the bottom or top of screen not in map
    	for(Rectangle i : pathRectangles)
    		if(i.contains(clickLoc))
    			return false;
    	//checking if spawn location is on another tower
    	for(Circle i : towerCircles) {
    		if(i.contains(clickLoc)){
    			return false;
    		}
    	}
    	return true;
	}
	
	public void parseFile(){
		
		FileHandle file = Gdx.files.internal(storagePath);
		Scanner reader = new Scanner(file.readString());		
		
		while(reader.hasNext()) {
			
			float x1 =  reader.nextFloat();
			float y1 = 	reader.nextFloat();
			float x2 = 	reader.nextFloat();
			float y2 = 	reader.nextFloat();
			
			float width  = 	(112 > Math.abs(x1 - x2)) ? 112 : Math.abs(x1 - x2);
			float height =	(112 > Math.abs(y1 - y2)) ? 112 : Math.abs(y1 - y2);			
			
			pathRectangles.add(new Rectangle(x1, y1, width, height));
			
		}
		pathRectangles.add(new Rectangle (0, 512, 800, 93));
		pathRectangles.add(new Rectangle (0, 0, 800, 93));
		
		for(Rectangle i : pathRectangles)
			System.out.println(i.toString());		
	}
	
	public void parseFileToPoly(){
		
		FileHandle file = Gdx.files.internal(storagePath);
		Scanner reader = new Scanner(file.readString());		
		ArrayList<Float> pathPoints = new ArrayList<Float> ();
		
		while(reader.hasNext()) {			
			float x =  reader.nextFloat();
			float y = 	reader.nextFloat();		
			pathPoints.add(x);
			pathPoints.add(y);			
		}
		int size = pathPoints.size();
		for(int i=0; i<size; i++){
			
		}
		float buf [] = new float[size];
		for(int i=0; i<size; i++)
			buf[i] = pathPoints.get(i);		
		pathArea = new Polygon(buf);
		pathRectangles.add(new Rectangle (0, 530, 800, 78));
		pathRectangles.add(new Rectangle (0, 0, 800, 78));
	}
	
	
	void editPathFile() {		

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(storagePath, true));	
			
			//printing the click location
			//ZombieAttack.printClickLocation();
			
			//getting top left corner
			if(Gdx.input.isKeyPressed(Input.Keys.Z) && Gdx.input.isTouched() && !isFirst){
				Vector2 clickLoc = ZombieAttack.getClickLocation();
				if(clickLoc != null) {
					try {
						writer.write(new String(Float.toString(clickLoc.x) + " " + Float.toString(clickLoc.y) + " "));
						System.out.print("[" + Float.toString(clickLoc.x) + ", " + Float.toString(clickLoc.y) + "], ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isFirst = true;
				}
			}
			//getting bottom left corner
			else if(Gdx.input.isKeyPressed(Input.Keys.X) && Gdx.input.isTouched() && isFirst){
				Vector2 clickLoc = ZombieAttack.getClickLocation();
				if(clickLoc != null) {
					try {
						writer.write(Float.toString(clickLoc.x) + " " + Float.toString(clickLoc.y) + "\n");
						System.out.print("[" + Float.toString(clickLoc.x) + ", " + Float.toString(clickLoc.y) + "]\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isFirst = false;
				}
			}
			

			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
