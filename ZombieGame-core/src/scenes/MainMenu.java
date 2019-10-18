package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.dreamingclouds.zombiegame.MainGame;

import helpers.GameInfo;

public class MainMenu implements Screen
{
	private MainGame game;
	
	// imagens
	private Texture menu;
	private Texture title;
	private Texture startGameRed;
	private Texture aboutTheGameRed;
	private Texture startGame;
	private Texture aboutTheGame;
	
	int x = Gdx.graphics.getWidth();
	int y = Gdx.graphics.getHeight();
	float percentx = (float)x/GameInfo.WIDTH;
	float percenty = (float)y/GameInfo.HEIGHT;
	
	public MainMenu(MainGame game)
	{
		this.game = game;
		//menu = new Texture("Backgrounds/background.png");
		title = new Texture("Backgrounds/title.png");
		startGameRed = new Texture("Backgrounds/startgame red.png");
		aboutTheGameRed = new Texture("Backgrounds/about red.png");
		startGame = new Texture("Backgrounds/startgame.png");
		aboutTheGame = new Texture("Backgrounds/about.png");
	}
	
	// metodo que irá trocar a cor da opção caso o ponteiro do mouse esteja sobre a mesma
	void selectOption()
	{
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		if(Gdx.graphics.getWidth()>GameInfo.WIDTH)
		{
			percentx = (float)x/GameInfo.WIDTH;
			percenty = (float)y/GameInfo.HEIGHT;
			
			if(Gdx.input.getX()>91*percentx && Gdx.input.getX()<446*percentx && Gdx.input.getY()>550*percenty && Gdx.input.getY()<610*percenty)
				game.getBatch().draw(startGameRed,91,66);
			else
				game.getBatch().draw(startGame,91,66);
			
			if(Gdx.input.getX()>603*percentx && Gdx.input.getX()<1108*percentx && Gdx.input.getY()>550*percenty && Gdx.input.getY()<610*percenty)
				game.getBatch().draw(aboutTheGameRed,603,66);
			else
				game.getBatch().draw(aboutTheGame,603,66);
		}
		else
		{
			if(Gdx.input.getX()>91 && Gdx.input.getX()<446 && Gdx.input.getY()>550 && Gdx.input.getY()<610)
				game.getBatch().draw(startGameRed,91,66);
			else
				game.getBatch().draw(startGame,91,66);
			
			if(Gdx.input.getX()>603 && Gdx.input.getX()<1108 && Gdx.input.getY()>550 && Gdx.input.getY()<610)
				game.getBatch().draw(aboutTheGameRed,603,66);
			else
				game.getBatch().draw(aboutTheGame,603,66);
		}
	}
	
	// metodo que seleciona as opções
	void optionClick()
	{
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		if(Gdx.input.justTouched())
		{
			if(Gdx.graphics.getWidth()>GameInfo.WIDTH)
			{
				percentx = (float)x/GameInfo.WIDTH;
				percenty = (float)y/GameInfo.HEIGHT;
				
				if(Gdx.input.getX()>91*percentx && Gdx.input.getX()<446*percentx && Gdx.input.getY()>550*percenty && Gdx.input.getY()<610*percenty)
				{
					dispose();
					game.setScreen(new Gameplay(game));
				}
				
				else if(Gdx.input.getX()>603*percentx && Gdx.input.getX()<1108*percentx && Gdx.input.getY()>550*percenty && Gdx.input.getY()<610*percenty)
				{
					dispose();
					game.setScreen(new AboutTheGame(game));
				}
			}
			else
			{
				if(Gdx.input.getX()>91 && Gdx.input.getX()<446 && Gdx.input.getY()>550 && Gdx.input.getY()<610)
				{
					dispose();
					game.setScreen(new Gameplay(game));
				}
				
				else if(Gdx.input.getX()>603 && Gdx.input.getX()<1108 && Gdx.input.getY()>550 && Gdx.input.getY()<610)
				{
					dispose();
					game.setScreen(new AboutTheGame(game));
				}
			}
		}
		
	}
	
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        optionClick();
        game.getBatch().begin();
        //game.getBatch().draw(menu,0,0);
        game.getBatch().draw(title,185,250);
        selectOption();
        game.getBatch().end();
		
	}
	
	@Override
	public void dispose() 
	{
		//menu.dispose();
		title.dispose();
		startGameRed.dispose();
		aboutTheGameRed.dispose();
		startGame.dispose();
		aboutTheGame.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	

}
