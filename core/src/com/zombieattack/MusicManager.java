package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class MusicManager{

	public enum ZombieMusic{
		LEVEL("Music/music.ogg");
		private String fileName;
		private Music musicResource;
		
		private ZombieMusic(String fileName){
			this.fileName = fileName;
		}
		public String getFileName(){
			return fileName;
		}
		public Music getMusicResource(){
			return musicResource;
		}
		public void setMusicResource(Music musicBeingPlayed){
			this.musicResource = musicBeingPlayed;
		}
	}
	
	private ZombieMusic musicBeingPlayed;
	
	private float volume = 1f;
	
	public MusicManager(){
		
	}
	
	public void play(ZombieMusic music){
		FileHandle musicFile = Gdx.files.internal(music.getFileName());
		Music musicResource = Gdx.audio.newMusic(musicFile);
		musicResource.setVolume(volume);
		musicResource.setLooping(true);
		musicResource.play();
		
		musicBeingPlayed = music;
		musicBeingPlayed.setMusicResource(musicResource);
	}
	
	public void setVolume(float volume){
		this.volume = volume;
		if(musicBeingPlayed != null)
			musicBeingPlayed.getMusicResource().setVolume(volume);
	}
	
	public void stop(){
		if(musicBeingPlayed != null){
			Music musicResource = musicBeingPlayed.getMusicResource();
			musicResource.stop();
			musicResource.dispose();
			musicBeingPlayed = null;
		}
	}
	
	public void dispose(){
		stop();
	}
	
}
