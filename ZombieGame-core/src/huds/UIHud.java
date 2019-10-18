package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dreamingclouds.zombiegame.MainGame;

import helpers.GameInfo;
import helpers.GameManager;

public class UIHud 
{
	private MainGame game;
    private Stage stage;
    private Viewport gameViewport;
    
    private Image life;
    public Image playerLife;
    public Image playerHud;
    private Image shotgun;
    private Image sword;
    private Image zombieHud;
    public Image pause;
    public Image tutorial;
    public Image playerHudHurt;
    
    private Label scoreLabel, zombieLabel, waveLabel,shotgunLabel;
    public Label highscoreLabel, highWaveLabel;
    
    
    public UIHud(MainGame game)
    {
    	this.game = game;
    	gameViewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
        stage = new Stage(gameViewport,this.game.getBatch());
        createAndPositionImages();
        creatLabels();
        
        stage.addActor(life);
        stage.addActor(playerLife);
        stage.addActor(playerHud);
        stage.addActor(playerHudHurt);
        stage.addActor(shotgun);
        stage.addActor(sword);
        stage.addActor(zombieHud);
        stage.addActor(scoreLabel);
        stage.addActor(waveLabel);
        stage.addActor(zombieLabel);
        stage.addActor(shotgunLabel);
        stage.addActor(pause);
        stage.addActor(tutorial);
        
    }
    
    void createAndPositionImages()
    {
    	life = new Image(new Texture("Player/LifeBar/player lifeBar.png"));
    	playerLife = new Image(new Texture("Player/LifeBar/greenlife.png"));
    	playerHud = new Image(new Texture("Player/LifeBar/playerHud.png"));
    	playerHudHurt = new Image(new Texture("Player/LifeBar/playerHud hurt.png"));
    	shotgun = new Image(new Texture("Huds Screen/shotgun.png"));
    	sword = new Image(new Texture("Huds Screen/sword.png"));
    	zombieHud = new Image(new Texture("Huds Screen/zombieHud.png"));
    	pause = new Image(new Texture("Backgrounds/pause.png"));
    	tutorial = new Image(new Texture("Backgrounds/tutorial.png"));
    	
    	playerHud.setPosition(25,GameInfo.HEIGHT - 87f);
    	playerHudHurt.setPosition(-150,GameInfo.HEIGHT -87f);
    	life.setPosition(playerHud.getX()+playerHud.getWidth()+20f,playerHud.getY()+24f);
    	playerLife.setPosition(playerHud.getX()+playerHud.getWidth()+23f,playerHud.getY()+27f);
    	zombieHud.setPosition(life.getX()+life.getWidth()+100f,playerHud.getY());
    	sword.setPosition(life.getX(),playerHud.getY());
    	shotgun.setPosition(-100,0);
    	pause.setPosition(920, 540);
    	tutorial.setPosition(52, 20);
    }
    
    void creatLabels()
    {
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/palamecia titling.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size =25;
        BitmapFont font = generator.generateFont(parameter);

        zombieLabel = new Label(""+GameManager.zombieActualNumber+"/"+ GameManager.zombieNumber
                ,new Label.LabelStyle(font, Color.BLACK));
        
        waveLabel = new Label("WAVE "+GameManager.waveNumber
                ,new Label.LabelStyle(font, Color.BLACK));
        
        scoreLabel= new Label("SCORE "+GameManager.playerScore
                ,new Label.LabelStyle(font, Color.BLACK));
        
        shotgunLabel= new Label(""+GameManager.shotgunBullets
                ,new Label.LabelStyle(font, Color.BLACK));
        
        zombieLabel.setPosition(zombieHud.getX()+zombieHud.getWidth(),zombieHud.getY());
        scoreLabel.setPosition(life.getX(),life.getY()+life.getHeight()-3);
        waveLabel.setPosition(playerHud.getX()+250,life.getY()+life.getHeight()-3);
        shotgunLabel.setPosition(-100,0);
    }
    
    public void showHighscore()
    {
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/palamecia titling.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font = generator.generateFont(parameter);
        
        highscoreLabel = new Label("HIGH-SCORE "+GameManager.gameData.getHigherScore(),new Label.LabelStyle(font,Color.BLACK));
        highWaveLabel = new Label("WAVE "+GameManager.gameData.getWaveNumberScore(),new Label.LabelStyle(font,Color.BLACK));
        highscoreLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f-100,Align.center);
        highWaveLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f-150, Align.center);
        
        stage.addActor(highscoreLabel);
        stage.addActor(highWaveLabel);
        
    }
    
    public void playerLifeBar(float actualLife)
    {
    	float newWidth = life.getWidth()*((actualLife*100)/1000)/100;
    	playerLife.setWidth(newWidth);
    }
    
    public void changeWeapon(boolean modeGun)
    {
    	if(modeGun)
    	{
    		shotgun.setPosition(life.getX(),playerHud.getY());
    		shotgunLabel.setPosition(shotgun.getX()+shotgun.getWidth()+8,shotgun.getY()-6);
    		sword.setPosition(-100,0);
    	}
    	else
    	{
    		shotgun.setPosition(-100,0);
    		shotgunLabel.setPosition(-100, 0);
    		sword.setPosition(life.getX(),playerHud.getY());
    	}
    }
    
    public void playerIsHurt(boolean isHurt)
    {
    	if(isHurt)
    	{
        	playerHudHurt.setX(25);
        	playerHud.setX(-150);
    	}
    	else
    	{
    		playerHud.setX(25);
        	playerHudHurt.setX(-150);
    	}
    }
    
    public void decrementZombieNumber()
    {
    	GameManager.zombieActualNumber--;
    	zombieLabel.setText(""+GameManager.zombieActualNumber+"/"+ GameManager.zombieNumber);
    }
    
    public void incrementScore(int score)
    {
    	GameManager.playerScore+= score;
    	scoreLabel.setText("SCORE "+GameManager.playerScore);
    }
    
    public void incrementWave()
    {
    	GameManager.waveNumber++;
    	waveLabel.setText("WAVE "+GameManager.waveNumber);
    }
    
    public void decrementShotgunBullets()
    {
    	GameManager.shotgunBullets--;
//    	if(GameManager.shotgunBullets<0)
//    		GameManager.shotgunBullets =0;
    	shotgunLabel.setText(""+GameManager.shotgunBullets);
    }
    
    public void incrementShotgunBullets()
    {
    	GameManager.shotgunBullets+=4;
    	shotgunLabel.setText(""+GameManager.shotgunBullets);
    }
    
    public void updateZombiesNumber()
    {
    	zombieLabel.setText(""+GameManager.zombieActualNumber+"/"+ GameManager.zombieNumber);
    }
    
    
    public Stage getStage()
    {
        return this.stage;
    }
}
