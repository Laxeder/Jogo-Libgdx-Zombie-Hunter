package images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ImagesPlayer 
{
	public static Sprite playerAtackingSword[];
	public static Sprite playerIdleSword[];
	public static Sprite playerWalkingSword[];
	public static Sprite playerHurtSword[];
	
	// Imagens modo Gun
	public static Sprite playerShootGun[];
	public static Sprite playerIdleGun[];
	public static Sprite playerWalkingGun[];
	public static Sprite playerHurtGun[];
	
	// Imagens Dead
	public static Sprite playerDead[];
	
	// Imagens da explosão do tiro
	public static Sprite shotgunExplosion;
	
	public static Sprite playerSombra;
	
	
	public static void loadPlayerImage()
	{
		playerSombra = new Sprite(new Texture("Zombie/Animations/sombra.png"));
		
		playerAtackingSword = new Sprite[10];
		playerIdleSword = new Sprite[10];
		playerWalkingSword = new Sprite[10];
		playerHurtSword = new Sprite[10];
		
		playerShootGun = new Sprite[10];
		playerIdleGun = new Sprite[10];
		playerWalkingGun = new Sprite[10];
		playerHurtGun = new Sprite[10];
		
		shotgunExplosion = new Sprite(new Texture("Player/Animations/Gun/Explosion/fire.png"));
		
		playerDead = new Sprite[10];
		
		for (int i = 0;i<10;i++)
		{
			playerAtackingSword[i] = new Sprite(new Texture(new String("Player/Animations/Sword/Atack/sword atack"+ (i+1) + ".png")));
			playerIdleSword[i] = new Sprite(new Texture(new String("Player/Animations/Sword/Idle/sword idle"+ (i+1) + ".png")));
			playerWalkingSword[i] = new Sprite(new Texture(new String("Player/Animations/Sword/Walk/sword walk"+ (i+1) + ".png")));
			playerHurtSword[i] = new Sprite(new Texture(new String("Player/Animations/Sword/Hurt/sword hurt"+ (i+1) + ".png")));
			
			playerShootGun[i] = new Sprite(new Texture(new String("Player/Animations/Gun/Shoot/gun shoot"+ (i+1) + ".png")));
			playerIdleGun[i] = new Sprite(new Texture(new String("Player/Animations/Gun/Idle/gun idle"+ (i+1) + ".png")));
			playerWalkingGun[i] = new Sprite(new Texture(new String("Player/Animations/Gun/Walk/shoot walk"+ (i+1) + ".png")));
			playerHurtGun[i] = new Sprite(new Texture(new String("Player/Animations/Gun/Hurt/gun hurt"+ (i+1) + ".png")));
			
			playerDead[i] = new Sprite(new Texture ( new String("Player/Animations/Dead/dead"+(i+1) +".png")));
		}
	}
	
	public static void disposeImages()
	{
		for(int i = 0 ;i<playerAtackingSword.length;i++)
		{
			playerAtackingSword[i].getTexture().dispose();
			playerIdleSword[i].getTexture().dispose();
			playerHurtSword[i].getTexture().dispose();
			playerWalkingSword[i].getTexture().dispose();
			playerShootGun[i].getTexture().dispose();
			playerIdleGun[i].getTexture().dispose();
			playerHurtGun[i].getTexture().dispose();
			playerWalkingGun[i].getTexture().dispose();
			playerDead[i].getTexture().dispose();
		}
		
		playerSombra.getTexture().dispose();
		shotgunExplosion.getTexture().dispose();
	}
}
