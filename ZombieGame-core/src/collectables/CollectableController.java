package collectables;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.dreamingclouds.zombiegame.MainGame;

import helpers.GameManager;
import images.ImagesCollectables;

public class CollectableController 
{
	private MainGame game;
	public Array<LifeCollectable> energyLife;
	public Array<AmmunitionCollectable> ammunition;
	public boolean existsLife;
	public boolean existsAmmunition;
	
	public CollectableController(MainGame game)
	{
		this.game = game;
		energyLife = new Array<LifeCollectable>();
		ammunition = new Array<AmmunitionCollectable>();
		existsLife = false;
		existsAmmunition = false;
	}
	
	public void createLife()
	{
		if(!existsLife)
		{
			for(int i = 0;i< GameManager.numberLifeCollectable;i++)
			{
				energyLife.add(new LifeCollectable());
				energyLife.get(i).box.setPosition(MathUtils.random(-1000,2300),MathUtils.random(-1000,2300));
			}

			existsLife = true;
		}
		
		
	}
	
	public void createAmmunition()
	{
		if(!existsAmmunition)
		{
			for(int i = 0;i< GameManager.numberAmmunitionColletable;i++)
			{
				ammunition.add(new AmmunitionCollectable());
				ammunition.get(i).box.setPosition(MathUtils.random(-1000,2300),MathUtils.random(-1000,2300));
				
//				System.out.println(ammunition.get(i).box.getX());
//				System.out.println(ammunition.get(i).box.getY());
			}
			existsAmmunition = true;
		}
		
	}
	
	public void drawLifeCollectable()
	{
		for(int i = 0;i<energyLife.size;i++)
		{
			game.getBatch().draw(ImagesCollectables.life,energyLife.get(i).box.getX(),energyLife.get(i).box.getY());
		}
	}
	
	public void drawAmmunitionCollectable()
	{
		for(int i = 0;i<ammunition.size;i++)
		{
			game.getBatch().draw(ImagesCollectables.ammunition,ammunition.get(i).box.getX(),ammunition.get(i).box.getY());
		}
	}
	
	public void removeLifeCollectable()
	{
		
	}
	
	public void removeAmmunitionCollectable()
	{
		
	}
	
	
}
