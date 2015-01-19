package com.zombieattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class  Leave{
	
	Skin skin;
	Dialog reassure;
	TextButton yes, no;
	String exitMessage;
	Label label;
	
	Leave(){
		skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
		        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));
		reassure = new Dialog("", skin, "leave");
		label = new Label("Exit To Main Menu?", skin);
		yes = new TextButton("Yes", skin);
		no = new TextButton("No", skin);
	}
	
	public Dialog drawReassure(final MusicManager music){
		yes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	music.stop();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
		no.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reassure.setVisible(false);
            }
        });
		reassure.add(label).padRight(15).row();
		reassure.add(yes).size(65, 25).padLeft(100).padBottom(10);
		reassure.add(no).size(65, 25).padLeft(60).padBottom(10);
		reassure.pack();
		reassure.setSize(200, 75);
		reassure.setPosition(300, 276);
		reassure.setVisible(false);
		return reassure;
	}
	
	public void showReassure(){
		reassure.setVisible(true);
	}

}
