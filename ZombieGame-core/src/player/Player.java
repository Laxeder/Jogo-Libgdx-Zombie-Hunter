package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import helpers.GameInfo;
import images.ImagesPlayer;

public class Player extends Sprite
{	
	private byte contadorIdleSword = 0;
	public byte contadorAtackingSword = 0;
	private byte contadorWalkingSword = 0;
	private byte contadorHurtSword = 0; 
	
	private byte contadorIdleGun = 0;
	public byte contadorShootGun = 0;
	private byte contadorWalkingGun = 0;
	private byte contadorHurtGun = 0;
	
	private byte contadorDead = 0;
	
	public byte frameRepeat = 5;
	private byte DeadframeRepeat = 8;

	private float velocityX = 0;
	private float velocityY = 0;
	private float lastVelocityX;
	
	private boolean isWalking;
	private boolean isAtacking;
	private boolean isDead;
	private  boolean isHurt;
	
	private boolean modeSword;
	private boolean modeGun;
	
	public Rectangle boxCollider ;
	public Rectangle swordHitBox;
	
	public Rectangle shotgunHighDamage;
	public Rectangle shotgunLowDamage;
	
	public short playerLife = 1000;
	
	public Player()
	{
		super(new Texture("Player/Animations/Sword/Idle/sword idle1.png"));
		setPosition(GameInfo.WIDTH/2f - getWidth()/2f,GameInfo.HEIGHT/2f - getHeight()/2f);
		
		boxCollider = new Rectangle();
		boxCollider.x = getX()+17;
		boxCollider.y = getY();
		boxCollider.width = 70;
		boxCollider.height = getHeight();
		
		swordHitBox = new Rectangle();
		swordHitBox.width = 60;
		swordHitBox.height = 60;
		
		shotgunHighDamage = new Rectangle(getX()+135,getY()+31,100,40);
		shotgunLowDamage = new Rectangle(getX()+235,getY()+31,200,40);
		
		modeSword = true;
		modeGun = false;
		
	}
	
	// metodo para desenhar a sombra do personagem
	public void drawShader(SpriteBatch batch)
	{
		if(this.isDead && lastVelocityX>0)
			batch.draw(ImagesPlayer.playerSombra,this.getX()-64f,this.getY()-8f);
		else if(this.isDead && lastVelocityX<0)
			batch.draw(ImagesPlayer.playerSombra,this.getX()+66f,this.getY()-8f);
		else
			batch.draw(ImagesPlayer.playerSombra,this.getX()+2,this.getY()-8);
	}
	
	// metodo que desenha as animações do personagem
	public void drawPlayerAnimation(SpriteBatch batch)
    {
		if(!isWalking && !isAtacking && !isDead && !isHurt)
		{
			if(modeSword)
			{
				contadorWalkingSword = 0;
				contadorAtackingSword = 0;
				contadorDead = 0;
				contadorHurtSword = 0;
				
				for(Sprite frame: ImagesPlayer.playerIdleSword)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				batch.draw(ImagesPlayer.playerIdleSword[contadorIdleSword/frameRepeat],getX(),getY());
				contadorIdleSword++;
				if(contadorIdleSword> (ImagesPlayer.playerIdleSword.length*frameRepeat -1))
				{
					contadorIdleSword = 0;
				}
			}
			else
			{
				contadorWalkingGun = 0;
				contadorShootGun = 0;
				contadorDead = 0;
				contadorHurtGun = 0;
				
				for(Sprite frame: ImagesPlayer.playerIdleGun)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				if(lastVelocityX>0)
					batch.draw(ImagesPlayer.playerIdleGun[contadorIdleGun/frameRepeat],getX(),getY());
				else
					batch.draw(ImagesPlayer.playerIdleGun[contadorIdleGun/frameRepeat],getX()-17f,getY());
				contadorIdleGun++;
				if(contadorIdleGun> (ImagesPlayer.playerIdleGun.length*frameRepeat -1))
				{
					contadorIdleGun = 0;
				}
			}
		}
		
		else if(isAtacking && !isHurt)
		{
			if(modeSword)
			{
				contadorIdleSword = 0;
				contadorWalkingSword = 0;
				contadorDead = 0 ;
				contadorHurtSword = 0;
				
				for(Sprite frame: ImagesPlayer.playerAtackingSword)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				if(lastVelocityX<0) 
				{	
					swordHitBox.setPosition(getX()-11f,getY()+10f);
					batch.draw(ImagesPlayer.playerAtackingSword[contadorAtackingSword/frameRepeat],getX()-18f,getY()-6f);
				}
				else
				{
					swordHitBox.setPosition(getX()+58f,getY()+10f);
					batch.draw(ImagesPlayer.playerAtackingSword[contadorAtackingSword/frameRepeat],getX()-9f,getY()-6f);
				}
				contadorAtackingSword++;
				if(contadorAtackingSword> (ImagesPlayer.playerAtackingSword.length*frameRepeat -1))
				{
					contadorAtackingSword = 0;
					this.isAtacking = false;
				}
			}
			else
			{
				contadorIdleGun = 0;
				contadorWalkingGun = 0;
				contadorDead = 0 ;
				contadorHurtGun = 0;
				
				for(Sprite frame: ImagesPlayer.playerShootGun)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				if(lastVelocityX<0 && !ImagesPlayer.shotgunExplosion.isFlipX())
	        		ImagesPlayer.shotgunExplosion.flip(true, false);
	        	else if(lastVelocityX >0 && ImagesPlayer.shotgunExplosion.isFlipX())
	        		ImagesPlayer.shotgunExplosion.flip(true, false);
				
				if(lastVelocityX<0) 
				{	
					shotgunHighDamage.setPosition(getX()-121f,getY()+31f);
					shotgunLowDamage.setPosition(getX()-321f,getY()+31f);
					batch.draw(ImagesPlayer.playerShootGun[contadorShootGun/frameRepeat],getX()-16f,getY());
					if(contadorShootGun/frameRepeat == 2)
					{
						batch.draw(ImagesPlayer.shotgunExplosion,getX()-89f,getY()+33f);
					}
				}
				else
				{
					shotgunHighDamage.setPosition(getX()+135f,getY()+31f);
					shotgunLowDamage.setPosition(getX()+235f,getY()+31f);
					batch.draw(ImagesPlayer.playerShootGun[contadorShootGun/frameRepeat],getX()-8f,getY());
					if(contadorShootGun/frameRepeat == 2)
					{
						batch.draw(ImagesPlayer.shotgunExplosion,getX()+105f,getY()+33f);
					}
				}
				contadorShootGun++;
				if(contadorShootGun> (ImagesPlayer.playerShootGun.length*frameRepeat -1))
				{
					contadorShootGun = 0;
					this.isAtacking = false;
				}
			}
		}
		
		else if(isHurt && !isDead)
		{
			if(modeSword)
			{
				contadorIdleSword = 0;
				contadorWalkingSword = 0;
				contadorDead = 0 ;
				contadorAtackingSword = 0;
				for(Sprite frame: ImagesPlayer.playerHurtSword)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				if(lastVelocityX>0)
					batch.draw(ImagesPlayer.playerHurtSword[contadorHurtSword/frameRepeat],getX()-7f,getY());
				else
					batch.draw(ImagesPlayer.playerHurtSword[contadorHurtSword/frameRepeat],getX()-4f,getY());
				contadorHurtSword++;
				if(contadorHurtSword> (ImagesPlayer.playerHurtSword.length*frameRepeat -1))
				{
					contadorHurtSword = 0;
					isHurt = false;
					isAtacking = false;
				}
			}
			else
			{
				contadorIdleGun = 0;
				contadorWalkingGun = 0;
				contadorDead = 0 ;
				contadorShootGun = 0;
				for(Sprite frame: ImagesPlayer.playerHurtGun)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				if(lastVelocityX>0)
					batch.draw(ImagesPlayer.playerHurtGun[contadorHurtGun/frameRepeat],getX()-6f,getY());
				else
					batch.draw(ImagesPlayer.playerHurtGun[contadorHurtGun/frameRepeat],getX()-16f,getY());
				contadorHurtGun++;
				if(contadorHurtGun> (ImagesPlayer.playerHurtGun.length*frameRepeat -1))
				{
					contadorHurtGun = 0;
					isHurt = false;
					isAtacking = false;
				}
			}
		}
		
		else if(isDead)
		{
			contadorWalkingSword = 0;
			contadorIdleSword = 0;
			contadorAtackingSword = 0 ;
			contadorHurtSword = 0;

			for(Sprite frame: ImagesPlayer.playerDead)
	        {
	        	if(lastVelocityX<0 && !frame.isFlipX())
	        		frame.flip(true, false);
	        	else if(lastVelocityX >0 && frame.isFlipX())
	        		frame.flip(true, false);
	        }
			
			if(lastVelocityX > 0)
				batch.draw(ImagesPlayer.playerDead[contadorDead/DeadframeRepeat],getX()-74f,getY()-13f);
			else
				batch.draw(ImagesPlayer.playerDead[contadorDead/DeadframeRepeat],getX() +26f,getY()-13f);
			contadorDead++;
			if(contadorDead> (ImagesPlayer.playerDead.length*DeadframeRepeat -1))
			{
				contadorDead = 49;
			}
		}
		
		else if(isWalking )
		{
			if(modeSword)
			{
				for(Sprite frame: ImagesPlayer.playerWalkingSword)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				if(lastVelocityX>0)
					batch.draw(ImagesPlayer.playerWalkingSword[contadorWalkingSword/frameRepeat],getX(),getY()-5.5f);
				else
					batch.draw(ImagesPlayer.playerWalkingSword[contadorWalkingSword/frameRepeat],getX()-9f,getY()-5.5f);
				contadorWalkingSword++;
				if(contadorWalkingSword> (ImagesPlayer.playerWalkingSword.length*frameRepeat -1))
				{
					contadorWalkingSword = 0;
				}
			}
			else
			{
				for(Sprite frame: ImagesPlayer.playerWalkingGun)
		        {
		        	if(lastVelocityX<0 && !frame.isFlipX())
		        		frame.flip(true, false);
		        	else if(lastVelocityX >0 && frame.isFlipX())
		        		frame.flip(true, false);
		        }
				
				if(lastVelocityX>0)
					batch.draw(ImagesPlayer.playerWalkingGun[contadorWalkingGun/frameRepeat],getX()+6f,getY()-6f);
				else
					batch.draw(ImagesPlayer.playerWalkingGun[contadorWalkingGun/frameRepeat],getX()-22f,getY()-6f);
				contadorWalkingGun++;
				if(contadorWalkingGun> (ImagesPlayer.playerWalkingGun.length*frameRepeat -1))
				{
					contadorWalkingGun = 0;
				}
			}
		}
    }
	
	// metodo que move o Jogador
	public void movePlayer()
	{
		this.setPosition(getX()+(velocityX * Gdx.graphics.getDeltaTime()), getY()+(velocityY* Gdx.graphics.getDeltaTime()));
		this.boxCollider.setPosition(getX()+17, getY());
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
	
	public boolean getHurt() 
	{
		return isHurt;
	}

	public void setHurt(boolean isHurt) 
	{
		this.isHurt = isHurt;
	}
	
	public void setModeSword(boolean modeSword)
	{
		this.modeSword = modeSword;
	}
	
	public boolean getModeSword()
	{
		return this.modeSword;
	}
	
	public void setModeGun(boolean modeGun)
	{
		this.modeGun = modeGun;
	}
	
	public boolean getModeGun()
	{
		return this.modeGun;
	}
	
}
