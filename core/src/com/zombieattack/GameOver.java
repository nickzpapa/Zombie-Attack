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

public class GameOver implements Screen{
	
	private Stage stage = new Stage();
	private Table table = new Table();
	
	
	private Screen retryLevel;
	
	private SpriteBatch sprite = new SpriteBatch();
    private Texture background = new Texture(Gdx.files.internal("Background.jpg"));
    
    private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));

    private TextButton retry = new TextButton("Retry", skin),
    	mainMenu = new TextButton("Main Menu", skin),
    	exit = new TextButton("Exit", skin);
    
    private Label gameOver = new Label("THE ZOMBIES \n   ATE YOUR \n    BRAINS!",skin, "title");
    
    GameOver(Screen retryLevel){
    	this.retryLevel = retryLevel;
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
		
		retry.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(retryLevel);
            }
        });
        mainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });
		
		
		table.add(gameOver).padBottom(40).row();
		table.add(retry).padBottom(40).row();
		table.add(mainMenu).padBottom(40).row();
		table.add(exit).padBottom(40).row();
		
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
