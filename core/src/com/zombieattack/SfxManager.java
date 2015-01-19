package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class SfxManager {
	
	public enum ZombieEffects{
		SHARPSOUND("Music/sharpShooter.ogg"),
		MISSILESOUND("Music/missile.ogg"),
		FLAMESOUND("Music/flame.ogg"),
		ICESOUND("Music/ice.ogg"),
		ZOMBIEHIT("Music/zombie_hit.ogg"),
		ZOMBIEDYING("Music/dying.ogg"),
		EXPLOSION("Music/explosion.ogg");
		
		private String fileName;
		private Music soundResource;
		
		private ZombieEffects(String fileName){
			this.fileName = fileName;
		}
		public String getFileName(){
			return fileName;
		}
		public Music getSoundResource(){
			return soundResource;
		}
		public void setSoundResource(Music musicBeingPlayed){
			this.soundResource = musicBeingPlayed;
		}
		
	}
	
private ZombieEffects musicBeingPlayed;
	
	private float volume = 1f;
	
	public SfxManager(){
		
	}
	
	public void play(ZombieEffects music){
		FileHandle musicFile = Gdx.files.internal(music.getFileName());
		Music soundResource = Gdx.audio.newMusic(musicFile);
		soundResource.setVolume(volume);
		soundResource.play();
		
		musicBeingPlayed = music;
		musicBeingPlayed.setSoundResource(soundResource);
	}
	
	public void playLoop(ZombieEffects music){
		FileHandle musicFile = Gdx.files.internal(music.getFileName());
		Music soundResource = Gdx.audio.newMusic(musicFile);
		soundResource.setVolume(volume);
		soundResource.setLooping(true);
		soundResource.play();
		
		musicBeingPlayed = music;
		musicBeingPlayed.setSoundResource(soundResource);
	}
	
	public void setVolume(float volume){
		if (volume < 0 || volume > 100f)
			throw new IllegalArgumentException("Volume must be inside range: [0,1]");
		this.volume = volume;
		if(musicBeingPlayed != null)
			musicBeingPlayed.getSoundResource().setVolume(volume);
	}
	
	public void stop(){
		if(musicBeingPlayed != null){
			Music soundResource = musicBeingPlayed.getSoundResource();
			soundResource.stop();
			soundResource.dispose();
			musicBeingPlayed = null;
		}
	}
	
	public void dispose(){
		stop();
	}

}
