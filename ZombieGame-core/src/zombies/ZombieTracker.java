package zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import helpers.GameInfo;
import images.ImagesZombies;


public class ZombieTracker extends Sprite 
{
	// contadores para as animações
	private byte contadorTeleport = 0;
	private byte contadorIdle;
	public byte contadorAtacking;
	private byte contadorWalking;
	private byte contadorDead;
	private byte contadorHurt;
	
	public byte frameRepeat = 8;
	
	// velocidades
	private float velocityX = 0;
	private float velocityY = 0;
	private float lastVelocityX;
	private float distanceTarget;
	private float angulo;
	public float vel = 170f;
	
	// vida do zumbi
	public static float zombieLife = 100;
	public float zombieActualLife;
	
	// colisor do zumbi
	public Rectangle boxCollider ;
	
	// hitbox do zumbi
	public Rectangle zombieHitBox;
	
	// força dos ataques físicos do zumbi
	public static short powerAtack = 100;
	
	public byte zombieOnHit = 0;
	
	private float sizeBarLife;
	
	private boolean isWalking;
	private boolean isAtacking;
	private boolean isDead;
	private boolean isHurt;
	public boolean isTeleport;
	public boolean isAppear;
	
	public float lastPositionX;
	public float lastPositionY;
	
	public String status;
	
	public float xTeleport;
	public float yTeleport;
	
	public int anguloIncrement = 0;
	
	public ZombieTracker()
	{
		super(new Texture("Zombie/Animations/Idle/idle1.png"));
		zombieActualLife = zombieLife;
		isWalking = false;
		isAtacking = false;
		isDead = false;
		
		boxCollider = new Rectangle();
		boxCollider.width = 80;
		boxCollider.height = 135;
		
		zombieHitBox = new Rectangle();
		zombieHitBox.width = 50f;
		zombieHitBox.height = 90f;
		
		status = "alive";
		
		contadorIdle = 0;
		contadorAtacking = 0;
		contadorWalking = 0;
		contadorDead = 0;
		
		contadorHurt = 0;
	}
	
	// Método que atualiza o tamanho da barra de vida 
	void sizeOfLifeBar()
	{	
		sizeBarLife = ImagesZombies.zombieLifebar.getWidth()*((zombieActualLife*100)/zombieLife)/100;
	}
	
	// Método que desenha a sombra do zumbi
	public void drawZombieShader(SpriteBatch batch)
	{	
		batch.draw(ImagesZombies.zombieSombra,this.getX()+14,this.getY()-8);
	}
	
	// Método que desenha a vida do zumbi após ser atingido pela primeira vez
	public void drawZombieLife(SpriteBatch batch)
	{
		if(zombieActualLife<zombieLife)
		{
			if(zombieActualLife>=0)
			{
				batch.draw(ImagesZombies.zombieLifebar,this.getX(),this.getY()+163);
				sizeOfLifeBar();
				batch.draw(ImagesZombies.zombieRedLifebar,this.getX(),this.getY()+163,sizeBarLife,7);
			}
		}
	}
	
	// Método que desenha todas as animações do zumbi 
	public void drawZombieAnimation(SpriteBatch batch)
    {
		if(isTeleport && !isDead)
		{
			isAtacking = false;
			isWalking = false;
			contadorWalking = 0;
			contadorIdle = 0;
			contadorAtacking = 0 ;
			
			if(!isAppear)
			{
				for(Sprite frame: ImagesZombies.zombieTeleport)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				batch.draw(ImagesZombies.zombieTeleport[contadorTeleport/2],getX(),getY());
				contadorTeleport++;
				if(contadorTeleport > (ImagesZombies.zombieTeleport.length*2 -1))
				{
					contadorTeleport = 0;
					isAppear = true;
					this.setPosition(xTeleport, yTeleport);
					this.boxCollider.setPosition(getX()+23, getY());
				}
			}
			else
			{
				for(Sprite frame: ImagesZombies.zombieAppear)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				batch.draw(ImagesZombies.zombieAppear[contadorTeleport/2],getX(),getY());
				contadorTeleport++;
				if(contadorTeleport > (ImagesZombies.zombieAppear.length*2 -1))
				{
					contadorTeleport = 0;
					isTeleport = false;
					isAppear = false;
					ZombieController.teleportAllowed = false;
				}
			}
			
		}
		else
		{
			if(!isWalking && !isAtacking && !isDead)
			{
				contadorWalking = 0;
				contadorAtacking = 0;
				contadorDead = 0;

				for(Sprite frame: ImagesZombies.zombieIdle)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				batch.draw(ImagesZombies.zombieIdle[contadorIdle/frameRepeat],getX(),getY());
				contadorIdle++;
				if(contadorIdle> (ImagesZombies.zombieIdle.length*frameRepeat -1))
				{
					contadorIdle = 0;
				}
			}
			else if(isAtacking && !isDead )
			{
				contadorIdle = 0;
				contadorWalking = 0;
				contadorDead = 0 ;

				for(Sprite frame: ImagesZombies.zombieAtacking)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				if(lastVelocityX>0)
				{
					zombieHitBox.setPosition(getX()+110f, getY()+15f);
					batch.draw(ImagesZombies.zombieAtacking[contadorAtacking/frameRepeat],getX(),getY());
				}
				else
				{
					zombieHitBox.setPosition(getX()-31f, getY()+15f);
					if(contadorAtacking/frameRepeat == 3 || contadorAtacking/frameRepeat == 4)
						batch.draw(ImagesZombies.zombieAtacking[contadorAtacking/frameRepeat],getX()-31f,getY());
					else
						batch.draw(ImagesZombies.zombieAtacking[contadorAtacking/frameRepeat],getX(),getY());
				}
				
				contadorAtacking++;
				if(contadorAtacking> (ImagesZombies.zombieAtacking.length*frameRepeat -1))
				{
					zombieOnHit = 0;
					contadorAtacking = 0;
					this.isAtacking = false;
				}
			}
			
			
			else if(isDead)
			{
				isAtacking = false;
				isWalking = false;
				isHurt = false;
				isTeleport = false;
				contadorWalking = 0;
				contadorIdle = 0;
				contadorAtacking = 0 ;
				contadorTeleport = 0;
				
				for(Sprite frame: ImagesZombies.zombieDead)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				if(lastVelocityX<0)
					batch.draw(ImagesZombies.zombieDead[contadorDead/frameRepeat],getX()-57f,getY()-17f);
				else
					batch.draw(ImagesZombies.zombieDead[contadorDead/frameRepeat],getX(),getY()-17f);
				contadorDead++;
				
				if(contadorDead> (ImagesZombies.zombieDead.length*frameRepeat -1))
				{
					contadorDead = 0;
					status = "dead";
				}
			}
			
			
			else if(isWalking )
			{
				for(Sprite frame: ImagesZombies.zombieWalking)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				batch.draw(ImagesZombies.zombieWalking[contadorWalking/frameRepeat],getX(),getY());
				contadorWalking++;
				
				if(contadorWalking> (ImagesZombies.zombieWalking.length*frameRepeat -1))
				{
					contadorWalking = 0;
				}
			}
			if(isHurt)
			{
				isWalking = false;
				isAtacking = false;
				isTeleport = false;
				ZombieController.teleportAllowed = false;
				contadorWalking = 0;
				contadorAtacking = 0;
				contadorDead = 0;
				
				contadorHurt++;
				if(contadorHurt>4*frameRepeat)
				{
					contadorHurt = 0;
					isHurt = false;
				}
			}
		}
		
		
    }
	
	// Método que habilita a perseguição ao Jogador
	public void trackerPlayer(float x, float y,Array<ZombieTracker> zombies)
	{
		distanceTarget = (float)Math.sqrt(Math.pow(((getX()+getWidth()/2f) - x),2)+ Math.pow(((getY()+getHeight()/2f) - y),2));
		if(distanceTarget<100 && !isDead && !isHurt)
		{
			isWalking = false;
			isAtacking = true;
		}
		else if(distanceTarget<3000 && !isDead && !isHurt && !isTeleport)
		{
			isWalking = true;
			killPlayer(x,y,zombies);
		}
		else
			isWalking = false;
	}
	
	// Método que calcula a direção que o zumbi deve andar
	public void killPlayer(float x, float y,Array<ZombieTracker> zombies)
	{
		float ca = Math.abs((getX()+getWidth()/2f) - x);
		float co = Math.abs((getY()+getHeight()/2f) - y);
		if(ca ==0)
			ca = 0.1f;
		
		angulo = Math.round((Math.atan(co/ca)*180/GameInfo.PI));
		
		// quando o x do player for menor que o do zumbi
		// e o y do player maior que o do zumbi
		if(x<= getX()+getWidth()/2f && y >= getY()+getHeight()/2f)
			angulo = 180 - angulo;
		
		// quando o x do player for menor que o do zumbi
		// e o y do player menor que o do zumbi
		else if(x<= getX()+getWidth()/2f && y < getY()+getHeight()/2f)
			angulo += 180;
		
		// quando o x do player for maior que o do zumbi
		// e o y do player menor que o do zumbi
		else if(x> getX()+getWidth()/2f && y < getY()+getHeight()/2f)
			angulo = 360 - angulo;
		
		angulo+=anguloIncrement;
		
		velocityX =(float) (Math.cos(angulo*GameInfo.PI/180)*vel);
		velocityY = (float) (Math.sin(angulo*GameInfo.PI/180)*vel);

		if(!isAtacking)
			moveZombie(zombies);
	}
	
	
	// Método que move o zumbi na direção definida
	public void moveZombie(Array<ZombieTracker> zombies)
	{
		lastPositionX = getX();
		lastPositionY = getY();
		this.setPosition(getX()+(velocityX* Gdx.graphics.getDeltaTime()), getY()+(velocityY *Gdx.graphics.getDeltaTime()));
		this.boxCollider.setPosition(getX()+23, getY());
		lastVelocityX = velocityX;
		zombieFreeWay(zombies);
	}
	
	// Método que verifica se hã passagem livre entre os zumbis
	public void zombieFreeWay(Array<ZombieTracker> zombies)
	{
		for(int i = 0;i<zombies.size;i++)
		{
			if(this.getX() == zombies.get(i).getX() && this.getY() == zombies.get(i).getY())
				continue;
			if(this.boxCollider.overlaps(zombies.get(i).boxCollider))
			{
				if(distanceTarget< zombies.get(i).distanceTarget)
				{
					zombies.get(i).setPosition(zombies.get(i).lastPositionX,zombies.get(i).lastPositionY);
					zombies.get(i).boxCollider.setPosition(zombies.get(i).getX()+23, zombies.get(i).getY());
				}
				else
				{
					this.setPosition(lastPositionX, lastPositionY);
					this.boxCollider.setPosition(lastPositionX +23f, lastPositionY);
				}
				break;
			}
		}
		
	}
	
	
	// Setter and Getters
	
	public void setVelocityX(float velocityX)
	{
		this.velocityX = velocityX;
	}
	
	public float getVelocityX()
	{
		return this.velocityX;
	}
	
	public void setVelocityY(float velocityY)
	{
		this.velocityY = velocityY;
	}
	
	public float getVelocityY()
	{
		return this.velocityY;
	}
	
	public void setWalking(boolean isWalking)
	{
		this.isWalking = isWalking;
	}
	
	public void setLastVelocityX(float lastVelocityX)
	{
		this.lastVelocityX = lastVelocityX;
	}

	public boolean getAtacking() 
	{
		return isAtacking;
	}

	public void setAtacking(boolean isAtacking) 
	{
		this.isAtacking = isAtacking;
	}

	public boolean getDead() 
	{
		return isDead;
	}

	public void setDead(boolean isDead) 
	{
		this.isDead = isDead;
	}
	
	public float getDistanceTarget()
	{
		return this.distanceTarget;
	}
	
//	public void setZombieLife(float zombieLife)
//	{
//		this.zombieLife = zombieLife;
//	}
//	public float getZombieLife()
//	{
//		return this.zombieLife;
//	}
	
	public void setIsHurt(boolean isHurt)
	{
		this.isHurt = isHurt;
	}
	
	public boolean getIsHurt()
	{
		return this.isHurt;
	}
}
