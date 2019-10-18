package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.dreamingclouds.zombiegame.MainGame;

import helpers.GameInfo;

public class AboutTheGame implements Screen
{
	MainGame game;
	Texture menu;
	Texture devs;
	Texture back;
	Texture backP;
	
	int x = Gdx.graphics.getWidth();
	int y = Gdx.graphics.getHeight();
	float percentx = (float)x/GameInfo.WIDTH;
	float percenty = (float)y/GameInfo.HEIGHT;
	
	public AboutTheGame(MainGame game)
	{
		this.game = game;
		menu = new Texture("Backgrounds/background.png");
		devs = new Texture("Backgrounds/devs.png");
		back = new Texture("Backgrounds/back.png");
		backP = new Texture("Backgrounds/back p.png");
	}
	
	// metodo que troca a cor do botão "BACK" ao passar o mouse sobre ele
	void drawButton()
	{
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		if(Gdx.graphics.getWidth()>GameInfo.WIDTH)
		{
			percentx = (float)x/GameInfo.WIDTH;
			percenty = (float)y/GameInfo.HEIGHT;
			
			if(Gdx.input.getX()>980*percentx && Gdx.input.getX()<1170*percentx && Gdx.input.getY()>593*percenty && Gdx.input.getY()<653*percenty)
				game.getBatch().draw(backP, 980, 22);
			else
				game.getBatch().draw(back, 980, 22);
		}
		else
		{
			if(Gdx.input.getX()>980 && Gdx.input.getX()<1170 && Gdx.input.getY()>593 && Gdx.input.getY()<653)
				game.getBatch().draw(backP, 980, 22);
			else
				game.getBatch().draw(back, 980, 22);
		}
		
	}
	
	// metódo que verifica se foi selecionado o botão de voltar.
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
				
				if(Gdx.input.getX()>980*percentx && Gdx.input.getX()<1170*percentx && Gdx.input.getY()>593*percenty && Gdx.input.getY()<653*percenty)
				{
					dispose();
					game.setScreen(new MainMenu(game));
				}
			}
			else
			{
				if(Gdx.input.getX()>980 && Gdx.input.getX()<1170 && Gdx.input.getY()>593 && Gdx.input.getY()<653)
				{
					dispose();
					game.setScreen(new MainMenu(game));
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
        game.getBatch().draw(menu,0,0);
        game.getBatch().draw(devs,62,20);
        drawButton();
        game.getBatch().end();
		
	}
	
	@Override
	public void dispose() 
	{
		menu.dispose();
		devs.dispose();
		back.dispose();
		backP.dispose();
		
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
