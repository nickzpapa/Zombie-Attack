package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TurretButtons {
	
	NoClickZone noClickZone;
	Skin skin;
	CheckBox sharpTurret, missileTurret, flameTurret, iceTurret;
	Table table;

	boolean placeTower = false;
	boolean alreadyPressed = false;
	CheckBox pressed;
	InfoText infoText;
	
	TurretButtons(InfoText infoText, NoClickZone noClickZone){
		this.noClickZone = noClickZone;
		skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
		        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));
		table = new Table();
		sharpTurret = new CheckBox("", skin, "turret1");		
		iceTurret = new CheckBox("", skin, "turret2");
		flameTurret = new CheckBox("", skin, "turret3");
		missileTurret = new CheckBox("", skin, "turret4");	
		pressed = null;
		this.infoText = infoText;
		addListeners();
	}
	
	public Table drawButtons(){
		table.add(sharpTurret).size(52,30).padLeft(575).padBottom(65);
		table.add(iceTurret).size(52,30).padLeft(75).padBottom(65);
		table.add(flameTurret).size(52,30).padLeft(75).padBottom(65);
		table.add(missileTurret).size(52,30).padLeft(75).padBottom(65);
		
		
		return table;
	}
	
	public int setSharpTurret(int moneyEarned, OrthographicCamera camera, TowerList towerList){
		if(moneyEarned < 50 || iceTurret.isChecked() || flameTurret.isChecked() || missileTurret.isChecked()){
			sharpTurret.setDisabled(true);
    		sharpTurret.setChecked(false);
		}
		else
			sharpTurret.setDisabled(false);
			
    	if(sharpTurret.isChecked()){
    		noClickZone.draw(camera);
	    	if(Gdx.input.isTouched()) {
	        	if (towerList.addTower(ZombieAttack.getClickLocation(), TowerChoice.SHARPSHOOTER)) {
						sharpTurret.setChecked(false);	
						moneyEarned -= 50;
				}
	    	}
    	}
    	else
    		pressed = null;
    	return moneyEarned;
	}
	
	public int setMissileTurret(int moneyEarned, OrthographicCamera camera, TowerList towerList){
		if(moneyEarned < 200 || iceTurret.isChecked() || flameTurret.isChecked() || sharpTurret.isChecked()){
			missileTurret.setDisabled(true);
			missileTurret.setChecked(false);
		}
    	else 
    		missileTurret.setDisabled(false);
    
    	if(missileTurret.isChecked()){
    		pressed = missileTurret;
    		noClickZone.draw(camera);
	    	if(Gdx.input.isTouched()) {
	        	if (towerList.addTower(ZombieAttack.getClickLocation(), TowerChoice.MISSLE)) {
					missileTurret.setChecked(false);	
					pressed = null;
					moneyEarned -= 200;
				}
			}
	    }
    	else
    		pressed = null;
    	return moneyEarned;
	}
	
	public int setFlameTurret(int moneyEarned, OrthographicCamera camera, TowerList towerList){
		if(moneyEarned < 100 || iceTurret.isChecked() || sharpTurret.isChecked() || missileTurret.isChecked()){
			flameTurret.setDisabled(true);
    		flameTurret.setChecked(false);
		}
		else
			flameTurret.setDisabled(false);
		
    	if(flameTurret.isChecked()){
    		pressed = flameTurret;
    		noClickZone.draw(camera);
	    	if(Gdx.input.isTouched()) {
	    		if (towerList.addTower(ZombieAttack.getClickLocation(), TowerChoice.FLAME)) {
					flameTurret.setChecked(false);	
					pressed = null;
					moneyEarned -= 100;
				}
			}
	    }
    	else
    		pressed = null;
    	return moneyEarned;
	}
	
	public int setIceTurret(int moneyEarned, OrthographicCamera camera, TowerList towerList){
		if(moneyEarned < 75 || flameTurret.isChecked() || sharpTurret.isChecked() || missileTurret.isChecked()){
			iceTurret.setDisabled(true);
			iceTurret.setChecked(false);
		}
		else
			iceTurret.setDisabled(false);
		
		
    	if(iceTurret.isChecked()){
    		pressed = iceTurret;
    		noClickZone.draw(camera);
	    	if(Gdx.input.isTouched()) {
				if (towerList.addTower(ZombieAttack.getClickLocation(), TowerChoice.ICE)) {
					iceTurret.setChecked(false);
					pressed = null;
					moneyEarned -= 75;
				}
			}
	    }
    	else
    		pressed = null;
    	return moneyEarned;
	}
	
	
	void addListeners() {
		sharpTurret.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
			}
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                InfoText.setText("SharpShooter", "$50", "");
            }            
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                InfoText.empty();
            }
		});
		flameTurret.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
			}
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                InfoText.setText("Flamethrower", "$100", "");
            }            
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                InfoText.empty();
            }
		});
		iceTurret.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
			}
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                InfoText.setText("Ice Shooter", "$75", "");
            }            
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                InfoText.empty();
            }
		});
		missileTurret.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				
			}
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                InfoText.setText("Bomb Launcher", "$200", "");
            }            
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                InfoText.empty();
            }
		});
	}
}
