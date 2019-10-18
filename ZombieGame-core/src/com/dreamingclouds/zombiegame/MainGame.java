package com.dreamingclouds.zombiegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameManager;
import scenes.MainMenu;


public class MainGame extends Game {
	private SpriteBatch batch;
	
	@Override
	public void create () 
	{
		batch = new SpriteBatch();
		new GameManager().initializeGameData();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () 
	{
		super.render();
	}
	
	public SpriteBatch getBatch()
	{
		return this.batch;
	}
	
	public void dispose()
	{
		batch.dispose();
		screen.dispose();
	}
}
