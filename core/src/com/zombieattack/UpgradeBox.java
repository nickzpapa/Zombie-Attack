package com.zombieattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UpgradeBox {

	Skin skin;
	Dialog dialog;
	TextButton attribute1, attribute2, attribute3;
	boolean attribute1Up, attribute2Up, attribute3Up;
	Dialog upgradeBox;
	Rectangle area;
	Tower tower;
	
	UpgradeBox(Vector2 location, Tower tower){
		skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
		        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));
		dialog = new Dialog("Upgrades", skin);
		dialog.setVisible(false);
		
		if(tower instanceof SharpShooter) {
			attribute1 = new TextButton("Speed", skin);
			attribute2 = new TextButton("Damage", skin);
			attribute3 = new TextButton("FOV", skin);
		}
		else if(tower instanceof FlameThrower) {
			attribute1 = new TextButton("Time", skin);
			attribute2 = new TextButton("Damage", skin);
			attribute3 = new TextButton("FOV", skin);
		}
		else if(tower instanceof MissleLauncher){
			attribute1 = new TextButton("Speed", skin);
			attribute2 = new TextButton("Damage", skin);
			attribute3 = new TextButton("FOV", skin);
		}
		else if(tower instanceof IceShooter){
			attribute1 = new TextButton("Freeze", skin);
			attribute2 = new TextButton("Damage", skin);
			attribute3 = new TextButton("FOV", skin);
		}
			
		attribute1Up = false;
		attribute2Up = false;
		attribute3Up = false;		
		
		area = new Rectangle(location.x-100+15, location.y + 35, 200, 64);
		upgradeBox = drawUpgrade();
		upgradeBox.setVisible(false);
		this.tower = tower;
	}
	
	private Dialog drawUpgrade(){
		attribute1.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				attribute1Up = true;
				tower.incAttribute1();
			}
	       public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	    	   tower.getAttribute1();
	       }            
	       public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	    	   InfoText.empty();
	       }
               
		});
   		attribute2.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   attribute2Up = true;
                   tower.incAttribute2();
               }
               public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                   tower.getAttribute2();
               }            
               public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	   InfoText.empty();
               }
           });
   		attribute3.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   attribute3Up = true;
                   tower.incAttribute3();
               }
               public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                   tower.getAttribute3();
               }            
               public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	   InfoText.empty();
               }
           });
   		dialog.addListener(new ClickListener(){
   			
   			public void clicked(InputEvent event, float x, float y){
   				if(!area.contains(x, y))
   					dialog.setVisible(false);
   			}
   		});
   		
   		//dialog.padTop(10);
   		dialog.add(attribute1).size(60,25);
   	    dialog.add(attribute2).size(60,25);
   		dialog.add(attribute3).size(60,25);
   		dialog.pack();
   		dialog.setSize(200, 45);
   		dialog.setPosition(area.x, area.y);   		
   		dialog.setVisible(false);
   		return dialog;   	
       
	}
	
	
	void dispose(){
		skin.dispose();
	}
}

