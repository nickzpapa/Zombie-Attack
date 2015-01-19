package com.zombieattack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;


public class ZombieList extends ArrayList<Zombie>{


	//private static final long serialVersionUID = 1L;
	private int waves, currentWave, currentZombie;
	ArrayList<ArrayList <Float>> spawnTimes;
	
	long timeStart;
	
	boolean waveBreak, levelFinished;
	public int zombiesKilled;
	
	ZombieList(String spawnFileName) {
		super();
		this.currentWave = 0;
		this.currentZombie = 0;
		
		this.timeStart = TimeUtils.nanoTime();
		this.spawnTimes = new ArrayList<ArrayList <Float>>();
		
		this.parseLevel(spawnFileName);
		this.waves = spawnTimes.size();
		this.waveBreak = true;
		this.zombiesKilled = 0;
		
	}
	
	boolean isLevelFinished(){
		return levelFinished;
	}
	
	int getCurrentWave(){
		return currentWave+1;
	}
	
	int getZombiesLeft() {
		if(!isLevelFinished())
			return spawnTimes.get(currentWave).size() - zombiesKilled;
		else return 0;
	}
	
	public void drawHealth(OrthographicCamera camera) {
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		
		//for red
		shapeRenderer.setColor(1, 0, 0, 1);
		for(Zombie i : this) {
			shapeRenderer.rect(i.dx, i.dy+35, 28, 4 );
		}
		//for green
		shapeRenderer.setColor(0, 1, 0, 1);
		for(Zombie i : this) {
			float width = (float) ((i.health)*(28.0)/(100));
			shapeRenderer.rect(i.dx, i.dy+35, width, 4 );
		}
		
		shapeRenderer.end();
		shapeRenderer.dispose();		
		
	}
	
	
	private void parseLevel(String fileName) {
		
		FileHandle file = Gdx.files.internal(fileName);
		Scanner fileIn = new Scanner(file.readString());		
		Scanner stringManip = null;

		String waveString = "";
		while(fileIn.hasNext()) {
			waveString = fileIn.nextLine();
			stringManip = new Scanner(waveString);
			ArrayList<Float> waveTimes = new ArrayList<Float>();
			
			stringManip.nextFloat();
			
			while(stringManip.hasNext()) {
				waveTimes.add(stringManip.nextFloat());
			}
			spawnTimes.add(waveTimes);
		}
	}
	
	
	public boolean waveBreak() {
		if(waveBreak){
			if (TimeUtils.nanoTime() >= timeStart+toNano(15)) {
				waveBreak = false;
				timeStart = TimeUtils.nanoTime();	
			}
		}
		return waveBreak;
	}
	
	public int getWaveBreakTimeLeft(){
		if(waveBreak())
			return (int) (((timeStart+toNano(15))/1000000000) -(TimeUtils.nanoTime()/1000000000));
		else
			return 0;
	}
	
	void spawnZombie(float startX, float startY, Direction dir) {	
		
		if(!waveBreak()){	
			if(currentWave<waves && currentZombie < spawnTimes.get(currentWave).size()){
				
				//checking if its time to spawn next zombie for current wave
				if( TimeUtils.nanoTime() >= timeStart+toNano(spawnTimes.get(currentWave).get(currentZombie))) {
					System.out.println("[Wave: " + (currentWave+1) + "] [Zombie:" + (currentZombie+1) + "]");
					this.add(new RegZombie(startX, startY, dir));
					++currentZombie;
				}
			}
			else if(this.isEmpty()){
				if(currentWave == spawnTimes.size()-1)
					levelFinished = true;
				else{
					currentZombie = 0;
					++currentWave;
					waveBreak = true;
					zombiesKilled = 0;
					timeStart = TimeUtils.nanoTime();
				}
			}
		}
	}
	
	private long toNano(float seconds){
		return (long) (seconds * Math.pow(10, 9));	
	}
		
}
