package com.zombieattack;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.zombieattack.MusicManager.ZombieMusic;
import com.zombieattack.SfxManager.ZombieEffects;

public class grass3 implements Screen {
	
	//Level Drawing Components
	public OrthographicCamera camera;
	private SpriteBatch batch;
	
	//Game components
	private int playerLife = 0;	
	int difficulty = 0;
	private NoClickZone noClickZone;
	private TowerList towerList;
	private ZombieList zombieList;
	private ZombieList zombieList1;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
    private Random rand = new Random();
	
	 
	//Graphics User Interface Components
	private Texture ui;
	private Texture arrowIn = new Texture(Gdx.files.internal("arrowright.png"));
	private Texture arrowOut = new Texture(Gdx.files.internal("arrowleft.png"));
	private String money = "Money: $ ";	
	private BitmapFont fontLabel;
	private String waveString = "Wave: 0";
	private String zombieString = "Zombies: 0";
	private String playerLifeString = "Lives " + playerLife;
	private Stage stage = new Stage();	
	private Table table = new Table();	
	private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
	        new TextureAtlas(Gdx.files.internal("skins/menuSkins.pack")));	
	private ImageButton exit = new ImageButton(skin, "exit");
	private TurretButtons buttons;
	private InfoText infoText;
	private Leave leave = new Leave();
	
	
	private OptionPreferences optionsPreferences = new OptionPreferences();
	private MusicManager musicManager = new MusicManager();
	private SfxManager dyingSound = new SfxManager();

	
	
	grass3() {		
		super();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ZombieAttack.SCREEN_WIDTH, ZombieAttack.SCREEN_HEIGHT);
		
		ui = new Texture(Gdx.files.internal("UI.png"));
		
		ZombieAttack.money = 100;


/**EDIT**/
		noClickZone = new NoClickZone("NoNoZones/level3.nono");
		towerList = new TowerList(stage, zombieList, noClickZone);		


/**EDIT**/
        tiledMap = new TmxMapLoader().load("Maps/Level3.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        fontLabel = new BitmapFont(Gdx.files.internal("fonts/SmallzFont.fnt"));
        infoText = new InfoText();
        buttons = new TurretButtons(infoText, noClickZone);
        
        difficulty = optionsPreferences.getDifficulty();
        if(difficulty == 1)
        	playerLife = 5;
        else if(difficulty == 2)
        	playerLife = 3;
        else if(difficulty == 3)
        	playerLife = 1;
	}
		

	@Override
	public void render(float delta) {
		
		
		camera.update();	
		Vector2 clickLocation = ZombieAttack.getClickLocation();			
		
		//refreshing open gl screen
		Gdx.gl20.glClearColor(0, 102/255f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//rendering map
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();  
		

		ZombieAttack.money = buttons.setSharpTurret(ZombieAttack.money, camera, towerList);
		ZombieAttack.money = buttons.setMissileTurret(ZombieAttack.money, camera, towerList);
		ZombieAttack.money = buttons.setIceTurret(ZombieAttack.money, camera, towerList);
		ZombieAttack.money = buttons.setFlameTurret(ZombieAttack.money, camera, towerList);
		money = "Money: $" + ZombieAttack.money;

		
		if(!zombieList1.waveBreak() && !zombieList.waveBreak()){
			zombieList.spawnZombie(-20, 232, Direction.RIGHT);
			zombieList1.spawnZombie(-20, 360, Direction.RIGHT);
		}
		if(zombieList.isLevelFinished() && zombieList1.isLevelFinished()){
			musicManager.stop();
			optionsPreferences.setCurrentLevel(9);
			if(optionsPreferences.getLevel() <= optionsPreferences.getCurrentLevel())
				optionsPreferences.setLevel(9);
			((Game)Gdx.app.getApplicationListener()).setScreen(new NextLevel(new water3()));
		}

		towerList.drawFOV(camera);
        
			
		batch.begin();{		
			
			batch.draw(ui, 0, 0);
			batch.draw(ui, 0, 543, 800, 58);
			
			waveString =  "Wave  " + zombieList.getCurrentWave();
			zombieString = "Zombies  " + (zombieList.getZombiesLeft() + zombieList1.getZombiesLeft());
			playerLifeString = "Lives " + playerLife;
			
			//Display labels
			fontLabel.setColor(0.0f, 0.7f, 0.05f, 1.0f);
			fontLabel.draw(batch, money, 620, 570);
			fontLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			fontLabel.draw(batch, waveString, 0 , 598);
			fontLabel.draw(batch, zombieString, 0, 570);
			fontLabel.setColor(1.0f, 0.0f, 0.0f, 1.0f);
			fontLabel.draw(batch, playerLifeString, 620 , 600);
			
	        //updating the projectiles and turrets
	        towerList.updateProjectiles(delta, zombieList);
	        towerList.updateProjectiles(delta, zombieList1);
	        
			if(rand.nextInt(2) == 0){
				towerList.updateTurrets(clickLocation, batch, delta, zombieList1);
				towerList.updateTurrets(clickLocation, batch, delta, zombieList);
			}
			else{
				towerList.updateTurrets(clickLocation, batch, delta, zombieList);
				towerList.updateTurrets(clickLocation, batch, delta, zombieList1);
			}
			towerList.drawTowers(batch, delta);		
			

			//	rendering zombie
			Iterator<Zombie> iter = zombieList.iterator();
			while(iter.hasNext()) {
				RegZombie z = (RegZombie) iter.next();
				//zombieList.spawnZombie(-20, 168, Direction.RIGHT);
				z.changeToY(226, 232, Direction.DOWN);
				z.changeToX(226, 136, Direction.RIGHT);
				z.changeToY(608, 136, Direction.UP);
				z.changeToX(608, 290, Direction.RIGHT);
				z.move(batch, delta);
				if(z.health <= 0) {
					iter.remove();	
					ZombieAttack.money += 10;
					money = "Money: $ " + ZombieAttack.money;
					++zombieList.zombiesKilled;
				}
				if(z.dx > 805) {
					iter.remove();
					--playerLife;
					if(playerLife <= 0)
						((Game)Gdx.app.getApplicationListener()).setScreen(new GameOver(new grass3()));
				}
			}
			
			Iterator<Zombie> iter1 = zombieList1.iterator();
			while(iter1.hasNext()) {
				RegZombie z1 = (RegZombie) iter1.next();
				z1.changeToY(232, 360, Direction.UP);
				z1.changeToX(232, 456, Direction.RIGHT);
				z1.changeToY(608, 456, Direction.DOWN);
				z1.changeToX(608, 290, Direction.RIGHT);
				z1.move(batch, delta);
				if(z1.health <= 0) {
					dyingSound.play(ZombieEffects.ZOMBIEDYING);
					iter1.remove();	
					ZombieAttack.money += 5;
					money = "Money: $ " + ZombieAttack.money;
					++zombieList1.zombiesKilled;
				}
				if(z1.dx > 805) {
					iter1.remove();
					--playerLife;
					if(playerLife <= 0)
						((Game)Gdx.app.getApplicationListener()).setScreen(new GameOver(new grass3()));
				}
			}	
			infoText.draw(batch, 0);
			if(zombieList.waveBreak() && zombieList1.waveBreak())
				if(zombieList.getWaveBreakTimeLeft() > zombieList1.getWaveBreakTimeLeft())
					infoText.draw(batch, zombieList.getWaveBreakTimeLeft());
				else
					infoText.draw(batch, zombieList1.getWaveBreakTimeLeft());
			
		}batch.end();	//end drawing level
		
		zombieList.drawHealth(camera);
		zombieList1.drawHealth(camera);

		//tower buttons
		stage.act();
		stage.draw();
		
		ZombieAttack.moneyCheat();
	}
	
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {	
		
		musicManager.setVolume(optionsPreferences.getMusicVolume());
		dyingSound.setVolume(optionsPreferences.getSfxVolume());
		musicManager.play(ZombieMusic.LEVEL);
		
		batch = new SpriteBatch();
		
		exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	leave.showReassure();
            }
        });
	
		exit.setPosition(380, 558);
		
		table.setFillParent(true);
		stage.addActor(leave.drawReassure(musicManager));
		stage.addActor(buttons.drawButtons());
		stage.addActor(exit);
		Gdx.input.setInputProcessor(stage);	

		zombieList = new ZombieList("SpawnTimes/level1.wave");
		zombieList1 = new ZombieList("SpawnTimes/level1.wave");
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
		batch.dispose();
		ui.dispose();
		fontLabel.dispose();
		stage.dispose();
		skin.dispose();
		tiledMap.dispose();
	}

}
