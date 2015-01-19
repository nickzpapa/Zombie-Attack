package com.zombieattack;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelSelect implements Screen{
	
	private Stage stage = new Stage();
	private Table table = new Table();
	
	private SpriteBatch sprite = new SpriteBatch();
    private Texture background = new Texture(Gdx.files.internal("Background.jpg"));
    
    private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));
    

    private TextButton map1 = new TextButton("Level 1", skin, "map1"),
    		map2 = new TextButton("Level 2", skin, "map8"),
    		map3 = new TextButton("Level 3", skin, "map5"),
    		map4 = new TextButton("Level 4", skin, "map2"),
    		map5 = new TextButton("Level 5", skin, "map7"),
    		map6 = new TextButton("Level 6", skin, "map4"),
    	    map7 = new TextButton("Level 7", skin, "map3"),
    	    map8 = new TextButton("Level 8", skin, "map9"),
    	    map9 = new TextButton("Level 9", skin, "map6"),
    		mainMenu = new TextButton("Main Menu", skin);
    
    TextButton[] buttonArray = {map1, map2, map3, map4, map5, map6, map7, map8, map9};
    
    BitmapFont fontLabel = new BitmapFont(Gdx.files.internal("fonts/zombiefont.fnt"));
    private SpriteBatch batch; 
    
    private int unlocked = 0;
    
    String levelSelect = "Level Select";
   
    LevelSelect(int unlocked){
    	this.unlocked = unlocked;
    	for(int i = 0; i < 9; i++){
    		buttonArray[i].setDisabled(true);
    		buttonArray[i].setChecked(true);
    	}
    	for(int i = 0; i < this.unlocked; i++){
    		buttonArray[i].setDisabled(false);
    	}
    	
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
        
        batch.begin();
        fontLabel.setColor(1, 1, 1, 1);
		fontLabel.draw(batch, levelSelect, 260, 580);
        
        batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		map1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new sand1());
            }
        });
		map2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new grass2());
            }
        });
		map3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new water2());
            }
        });
		map4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new sand2());
            }
        });
		map5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new grass1());
            }
        });
		map6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new water1());
            }
        });
		map7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new sand3());
            }
        });
		map8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new grass3());
            }
        });
		map9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	((Game)Gdx.app.getApplicationListener()).setScreen(new water3());
            }
        });
        mainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
		batch = new SpriteBatch();
		
		mainMenu.setPosition(305, 10);
		
		table.add(map1).pad(15);
		table.add(map2).pad(15);
		table.add(map3).row();
		table.add(map4).pad(15);
		table.add(map5).pad(15);
		table.add(map6).row();
		table.add(map7).pad(15);
		table.add(map8).pad(15);
		table.add(map9).pad(15).row();
		table.setFillParent(true);
		
		stage.addActor(table);
		stage.addActor(mainMenu);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
		
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
		stage.dispose();
        skin.dispose();
        sprite.dispose();
        batch.dispose();
	}

}
