package com.zombieattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class NextLevel implements Screen{
	
	private Stage stage = new Stage();
	private Table table = new Table();
	
	private SpriteBatch sprite = new SpriteBatch();
    private Texture background = new Texture(Gdx.files.internal("Background.jpg"));
    
    private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));

    private TextButton next = new TextButton("Next Level", skin),
    	mainMenu = new TextButton("Main Menu", skin);
    
    private OptionPreferences preferences = new OptionPreferences();
    
    private Label congrats = new Label("You Have Completed Level "+ preferences.getCurrentLevel() ,skin, "title");

    private Screen nextLevel;
    
    NextLevel(Screen nextLevel){
    	this.nextLevel = nextLevel;
    }
    
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.begin();
        sprite.draw(background, 0, 0);
        sprite.end();
        stage.act();
        stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void show() {
		
		next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(nextLevel);
            }
        });
        mainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
		
		table.add(congrats).padBottom(40).row();
		table.add(next).padBottom(40).row();
		table.add(mainMenu).padBottom(40).row();
		
		table.setFillParent(true);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}

}
