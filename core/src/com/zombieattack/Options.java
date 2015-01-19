package com.zombieattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Options implements Screen {
	
	private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));
    
    private Label title = new Label("Options",skin, "title");
    private Label volumeLabel = new Label("Music Volume",skin);
    private Label sfxLabel = new Label("Sound Effects",skin);
    private Slider volume = new Slider(0, 1, .01f, false, skin);
    private Slider sfx = new Slider(0, 1, .01f, false, skin);
    private TextButton mainMenu = new TextButton("Main Menu", skin);
    
    private CheckBox easy = new CheckBox (" Easy",skin);
    private CheckBox normal = new CheckBox (" Normal",skin);
    private CheckBox hard = new CheckBox (" Hard",skin);
    private ButtonGroup difficulty = new ButtonGroup(easy, normal, hard);

    private SpriteBatch sprite = new SpriteBatch();
    private Texture background = new Texture(Gdx.files.internal("Background.jpg"));
    
    private OptionPreferences preferences = new OptionPreferences();
    
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.begin();
        sprite.draw(background, 0, 0);
        sprite.end();
        stage.act();
        stage.draw();
        
    	if(easy.isChecked())
			preferences.setDifficulty(1);
		else if(normal.isChecked())
			preferences.setDifficulty(2);
		else if (hard.isChecked())
			preferences.setDifficulty(3);
		
		if(preferences.getDifficulty() == 1)
			easy.setChecked(true);
		else if(preferences.getDifficulty() == 2)
			normal.setChecked(true);
		else if(preferences.getDifficulty() == 3)
			hard.setChecked(true);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		mainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
		
		volume.setValue(preferences.getMusicVolume());
		volume.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				preferences.setMusicVolume(volume.getValue());
				System.out.println(volume.getValue());
			}
		});
		
		sfx.setValue(preferences.getSfxVolume());
		sfx.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				float value = sfx.getValue();
				preferences.setSfxVolume(value);
			}
		});
		
		difficulty.add(easy, normal, hard);
		title.setPosition(300, 450);	
		mainMenu.setPosition(300, 140);
		table.add(volumeLabel).padBottom(40);
		table.add(volume).size(250,40).padBottom(40).row();
		table.add(sfxLabel).padBottom(40);
		table.add(sfx).size(250,40).padBottom(40).row();
		table.add(easy).padBottom(40);
		table.add(normal).padBottom(40);
		table.add(hard).padBottom(40).row();
	
		
		table.setFillParent(true);
		stage.addActor(title);
		stage.addActor(mainMenu);
        stage.addActor(table);
        
        
        Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
