package zombies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import helpers.GameManager;
import sounds.SoundsGame;

public class ZombieController 
{
	public Array<ZombieTracker> zombies;
	public static boolean teleportAllowed = false;
	private byte[] angles = {-10,-30,-45,-60,10,30,45,60};
	public ZombieController()
	{
		zombies = new Array<ZombieTracker>();
		createZombies();
		setVelocityZombies();
	}
	
	// metodo que cria os zombies
	public void createZombies()
	{
		for(int i = 0;i<GameManager.zombieNumber;i++)
		{
			zombies.add(new ZombieTracker());
			zombies.get(i).setPosition(GameManager.positionsZombies[i][0],GameManager.positionsZombies[i][1]);
			zombies.get(i).boxCollider.setPosition(zombies.get(i).getX()+23,zombies.get(i).getY());
			zombies.get(i).zombieHitBox.setPosition(zombies.get(i).getX()+110,zombies.get(i).getY()+15f);
			zombies.get(i).anguloIncrement = angles[MathUtils.random(angles.length-1)];
		}
	}
	
	public void setZombiesIdle()
	{
		for(int i = 0;i<GameManager.zombieNumber;i++)
		{
			zombies.get(i).setAtacking(false);
			zombies.get(i).setWalking(false);
		}
	}
	
	public void setVelocityZombies()
	{
		for(int i = 0;i<GameManager.zombieNumber;i++)
		{
			if(zombies.get(i).anguloIncrement == 10 || zombies.get(i).anguloIncrement == -10 )
				zombies.get(i).vel = 150f;
			else if(zombies.get(i).anguloIncrement == -30 || zombies.get(i).anguloIncrement == 30 )
				zombies.get(i).vel = 170f;
			else if(zombies.get(i).anguloIncrement == -45 || zombies.get(i).anguloIncrement == 45 )
				zombies.get(i).vel = 190f;
			else if(zombies.get(i).anguloIncrement == -60 || zombies.get(i).anguloIncrement == 60 )
				zombies.get(i).vel = 210f;
		}
	}
	
	// metodo que deixa os zumbis mais fortes
	public void changePowerAndLife()
	{
		ZombieTracker.powerAtack+=25;
		ZombieTracker.zombieLife+=25;
		for (int i = 0;i<zombies.size;i++)
		{
			zombies.get(i).zombieActualLife = ZombieTracker.zombieLife;
		}
	}
		
	// metodo para desenhar os zumbis na tela
	public void drawZombies(SpriteBatch batch)
	{
		for(int i = 0;i<zombies.size;i++)
		{
			zombies.get(i).drawZombieShader(batch);
			zombies.get(i).drawZombieAnimation(batch);
			zombies.get(i).drawZombieLife(batch);
		}
	}
	
	// metodo que ativa a perseguição dos zumbis
	public void activeTracker(float x,float y,Array<ZombieTracker> zombies)
	{
		for(int i = 0;i<zombies.size;i++)
		{
			zombies.get(i).trackerPlayer(x, y,zombies);
		}
	}
	
	// metodo que controla o teleport dos zumbis 
	public void zombieTeleport(float x,float y)
	{
		int contadorX = 0;
		int contadorXmenos = 0;
		int contadorY = 0;
		int contadorYmenos = 0;
		for(int i = 0;i<zombies.size;i++)
		{
			if(zombies.get(i).getX()>x)
				contadorX++;
			else
				contadorXmenos++;
			if(zombies.get(i).getY() > y)
				contadorY++;
			else
				contadorYmenos++;
		}
		if(!zombies.get(0).getDead())
		{
			int choice = MathUtils.random(zombies.size -1);
			
			if(contadorX == zombies.size && (contadorY == zombies.size || contadorYmenos == zombies.size))
			{
				zombies.get(choice).isTeleport = true;
				zombies.get(choice).xTeleport = x - 700;
				zombies.get(choice).yTeleport = y;
				SoundsGame.teleport.play();
				ZombieController.teleportAllowed = true;
			}
			else if(contadorXmenos == zombies.size && (contadorY == zombies.size || contadorYmenos == zombies.size))
			{
				zombies.get(choice).isTeleport = true;
				zombies.get(choice).xTeleport = x + 700;
				zombies.get(choice).yTeleport = y;
				SoundsGame.teleport.play();
				ZombieController.teleportAllowed = true;
			}
			else if(contadorY == zombies.size && (contadorX == zombies.size || contadorXmenos == zombies.size))
			{
				zombies.get(choice).isTeleport = true;
				zombies.get(choice).xTeleport = x;
				zombies.get(choice).yTeleport = y - 700;
				SoundsGame.teleport.play();
				ZombieController.teleportAllowed = true;
			}
			else if(contadorYmenos == zombies.size && (contadorX == zombies.size || contadorXmenos == zombies.size))
			{
				zombies.get(choice).isTeleport = true;
				zombies.get(choice).xTeleport = x;
				zombies.get(choice).yTeleport = y + 700;
				SoundsGame.teleport.play();
				ZombieController.teleportAllowed = true;
			}
		}
		
	}
	
	
	// metodo que apaga os zumbis mortos 
	public void removeZombieDead()
	{
		for(int i = 0;i<zombies.size;i++)
		{
			if(zombies.get(i).status == "dead")
			{
				zombies.get(i).getTexture().dispose();
				zombies.removeIndex(i);
			}
		}
	}
	
	public void disposeZombies()
	{
		for (int i = 0; i<zombies.size;i++)
		{
			zombies.get(i).getTexture().dispose();
		}
	}
}
