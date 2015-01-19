package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class OptionPreferences {
	
	private static final String PREF_MUSIC_VOLUME = "Music Volume";
	private static final String PREF_SFX_VOLUME = "SFX Volume";
	private static final String PREF_DIFFICULTY = "Difficulty";
	private static final String PREF_LEVEL = "Level";
	private static final String PREF_CURRENT_LEVEL = "Current Level";
	private static final String PREFS_NAME = "user";
	
	public OptionPreferences(){
		
	}
	
	protected Preferences getPrefs(){
		return Gdx.app.getPreferences( PREFS_NAME );
	}
	
	public float getMusicVolume(){
		return getPrefs().getFloat( PREF_MUSIC_VOLUME, .01f);
	}
	
	public void setMusicVolume(float musicVol){
		getPrefs().putFloat( PREF_MUSIC_VOLUME, musicVol );
        getPrefs().flush();
	}
	
	public float getSfxVolume(){
		return getPrefs().getFloat( PREF_SFX_VOLUME, .01f);
	}
	
	public void setSfxVolume(float musicVol){
		getPrefs().putFloat( PREF_SFX_VOLUME, musicVol );
        getPrefs().flush();
	}
	public int getDifficulty(){
		int difficulty = getPrefs().getInteger(PREF_DIFFICULTY); 
		if(difficulty == 0)
			return 2;
		else
			return difficulty;
	}
	public void setDifficulty(int difficulty){
		getPrefs().putInteger(PREF_DIFFICULTY, difficulty);
		getPrefs().flush();
	}
	
	public int getLevel(){
		int level = getPrefs().getInteger(PREF_LEVEL);
		if (level == 0)
			return 1;
		else return level;
	}
	public void setLevel(int level){
		getPrefs().putInteger(PREF_LEVEL, level);
		getPrefs().flush();
	}
	
	public int getCurrentLevel(){
		int level = getPrefs().getInteger(PREF_CURRENT_LEVEL);
		if (level == 0)
			return 1;
		else return level;
	}
	public void setCurrentLevel(int level){
		getPrefs().putInteger(PREF_CURRENT_LEVEL, level);
		getPrefs().flush();
	}
	
	
	
}
