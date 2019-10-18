package com.dreamingclouds.zombiegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dreamingclouds.zombiegame.MainGame;

import helpers.GameInfo;
import helpers.GameManager;

public class DesktopLauncher 
{
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Zombie Hunter";
		config.width = GameInfo.WIDTH;
		config.height = GameInfo.HEIGHT;
		config.foregroundFPS = GameManager.FPS;
		new LwjglApplication(new MainGame(), config);	
	}
}
