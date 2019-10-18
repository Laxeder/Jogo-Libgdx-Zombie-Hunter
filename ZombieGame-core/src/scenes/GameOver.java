package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dreamingclouds.zombiegame.MainGame;

import helpers.GameInfo;
import helpers.GameManager;


public class GameOver implements Screen
{
	private MainGame game;
	private Stage stage;
    private Viewport gameViewport;
    private Label highscoreLabel;
    private Label highWaveLabel;
    private Label exit;
    private Texture menu;
    
	public GameOver(MainGame game)
	{
		this.game = game;
		gameViewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
        stage = new Stage(gameViewport,this.game.getBatch());
        menu = new Texture("Backgrounds/background.png");
        showPlayerScore();
	}
	
	// metodo que finaliza a cena e volta para o gameplay
	void exitGame()
	{
		if(Gdx.input.justTouched())
		{
			dispose();
			game.setScreen(new Gameplay(game));
		}
	}
	
	// metodo que mostra o pontuação final do Jogador
	void showPlayerScore()
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/palamecia titling.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font = generator.generateFont(parameter);
        
        highscoreLabel = new Label("YOUR SCORE: "+GameManager.playerScore,new Label.LabelStyle(font,Color.BLACK));
        highWaveLabel = new Label("WAVE "+GameManager.waveNumber,new Label.LabelStyle(font,Color.BLACK));
        exit = new Label("CLICK ON THE SCREEN TO PLAY AGAIN...",new Label.LabelStyle(font,Color.BLACK));
        
        highscoreLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f+100,Align.center);
        highWaveLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f+50, Align.center);
        exit.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f-150, Align.center);
        
        stage.addActor(highscoreLabel);
        stage.addActor(highWaveLabel);
        stage.addActor(exit);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) 
	{
        exitGame();
        game.getBatch().begin();
        game.getBatch().draw(menu,0,0);
        game.getBatch().end();
        
        game.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        
	}

	@Override
	public void resize(int width, int height) {
		gameViewport.update(width,height);
		
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		stage.dispose();
		menu.dispose();
	}

}
