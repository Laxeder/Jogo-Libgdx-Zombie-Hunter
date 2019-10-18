package images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ImagesZombies 
{
	public static Sprite zombieAtacking[];
	public static Sprite zombieDead[];
	public static Sprite zombieIdle[];
	public static Sprite zombieWalking[];
	public static Sprite zombieTeleport[];
	public static Sprite zombieAppear[];
	public static Sprite zombieSombra;
	
	public static Sprite zombieRedLifebar;
	public static Texture zombieLifebar;
	
	
	public static void loadZombieImage()
	{
		zombieSombra = new Sprite(new Texture("Zombie/Animations/sombra.png"));
		zombieLifebar = new Texture("Zombie/LifeBar/black lifebar.png");
		zombieRedLifebar = new Sprite(new Texture("Zombie/LifeBar/red lifebar.png"));
		
		zombieTeleport = new Sprite[9];
		for(int i = 0;i<9;i++)
			zombieTeleport[i] = new Sprite(new Texture(new String("Zombie/Animations/Teleport/t"+ (i+1) + ".png")));
		
		zombieAppear = new Sprite[9];
		for(int i = 0;i<9;i++)
			zombieAppear[i] = new Sprite(new Texture(new String("Zombie/Animations/Teleport/t"+ (9-i) + ".png")));
		
		zombieAtacking = new Sprite[8];
		for (int i = 0;i<8;i++)
			zombieAtacking[i] = new Sprite(new Texture(new String("Zombie/Animations/Atack/atack"+ (i+1) + ".png")));
			
		
		zombieDead = new Sprite[12];
		for (int i = 0;i<12;i++)
			zombieDead[i] = new Sprite(new Texture(new String("Zombie/Animations/Dead/dead"+ (i+1) + ".png")));
		
		zombieIdle = new Sprite[15];
		for (int i = 0;i<15;i++)
			zombieIdle[i] = new Sprite(new Texture(new String("Zombie/Animations/Idle/idle"+ (i+1) + ".png")));
		
		zombieWalking = new Sprite[10];
		for (int i = 0;i<10;i++)
			zombieWalking[i] = new Sprite(new Texture(new String("Zombie/Animations/Walking/walk"+ (i+1) + ".png")));
	}
	
	public static void disposeImages()
	{
		for (int i = 0;i<8;i++)
			zombieAtacking[i].getTexture().dispose();
		
		for(int i = 0;i<9;i++)
		{
			zombieTeleport[i].getTexture().dispose();
			zombieAppear[i].getTexture().dispose();
		}
		
		for (int i = 0;i<12;i++)
			zombieDead[i].getTexture().dispose();
		
		for (int i = 0;i<15;i++)
			zombieIdle[i].getTexture().dispose();
		
		for (int i = 0;i<10;i++)
			zombieWalking[i].getTexture().dispose();
		
		zombieSombra.getTexture().dispose();
		zombieRedLifebar.getTexture().dispose();;
		zombieLifebar.dispose();
		
	}
}
