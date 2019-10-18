package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dreamingclouds.zombiegame.MainGame;

import collectables.CollectableController;
import helpers.GameInfo;
import helpers.GameManager;
import huds.UIHud;
import images.ImagesCollectables;
import images.ImagesPlayer;
import images.ImagesZombies;
import player.Player;
import sounds.SoundsGame;
import zombies.ZombieController;
import zombies.ZombieTracker;

public class Gameplay implements Screen
{
	private byte loadGameplay = 0;
	private MainGame game;
	private Player player;
	private Sprite map;
	private OrthographicCamera mainCamera;
	private GameManager manager;
	
	private UIHud hud;
	
	private ZombieController zombies;
	private CollectableController collectables;
    private Viewport gameViewport;
    
	public Gameplay(MainGame game)
	{
		ImagesZombies.loadZombieImage();
		ImagesPlayer.loadPlayerImage();
		ImagesCollectables.loadCollectablesImages();
		SoundsGame.LoadSounds();
		
		this.game = game;
		player = new Player();
		map = new Sprite (new Texture("MAP/Deserto.png"));
		map.setPosition((GameInfo.MAPWIDTH - GameInfo.WIDTH)/2f * -1,(GameInfo.MAPHEIGHT - GameInfo.HEIGHT)/2f * -1);
		mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        mainCamera.position.set(player.getX()+ player.getWidth()/2f,player.getY()+player.getHeight()/2f,0);
        
        manager = new GameManager();
        collectables = new CollectableController(game);        
        
        hud = new UIHud(game);
        hud.showHighscore();
        zombies = new ZombieController();
        
        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT,mainCamera);
	}
	
	/*
	 *  Método responsável pelos inputs do Jogador
	 * Através dele podemos verificar a direção que o Jogador está andando,
	 * atacando, trocando de arma
	 */
	void updatePlayer()
	{
		if(!player.getDead())
		{
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			{
				player.setVelocityX(250f);
				player.setLastVelocityX(player.getVelocityX());
				player.setWalking(true);
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			{
				player.setVelocityX(-250f);
				player.setLastVelocityX(player.getVelocityX());
				player.setWalking(true);
			}
		
			else
			{
				player.setVelocityX(0);
				player.setWalking(false);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.UP))
			{
				player.setVelocityY(250f);
				player.setWalking(true);
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			{
				player.setVelocityY(-250f);
				player.setWalking(true);
			}
			else
			{
				player.setVelocityY(0);
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.A))
			{
				player.setAtacking(true);
				player.setWalking(false);
				
				if(player.getModeSword() && player.contadorAtackingSword == 0)
					SoundsGame.swordAtack.play();
				if(player.getModeGun() && GameManager.shotgunBullets==0)
					player.setAtacking(false);
				
				else if(player.getModeGun() && player.contadorShootGun==0 && GameManager.shotgunBullets>0)
				{
					hud.decrementShotgunBullets();
					SoundsGame.shotgunFire.play();
				}
				
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.B) && !player.getAtacking())
			{
				changePlayerMode();
			}
			player.movePlayer();
			
		}
	}
	
	// metodo para que troca a arma do Player
	void changePlayerMode()
	{
		if(player.getModeSword())
		{
			player.setModeSword(false);
			player.setModeGun(true);
			hud.changeWeapon(true);
			SoundsGame.changeWeaponShotgun.play();
		}
		else
		{
			player.setModeGun(false);
			player.setModeSword(true);
			hud.changeWeapon(false);
			SoundsGame.changeWeaponSword.play();
		}
	}
	
	/*
	 * Método responsável por limitar o Jogador a ficar dentro do 
	 * espaço de jogo
	 */
	void limitsPlayer()
	{
		if(player.getX()< map.getX())
			player.setX(map.getX());
		else if(player.getX()+player.getWidth()> map.getX()+map.getWidth())
			player.setX(map.getX()+map.getWidth()- player.getWidth());
		
		if(player.getY()< map.getY())
			player.setY(map.getY());
		else if(player.getY()+player.getHeight()> map.getY()+map.getHeight())
			player.setY(map.getY()+map.getHeight()-player.getHeight());
	}
	
	// Método que movimenta a câmera em relação ao centro do Jogador
	void moveCamera()
	{
		if(player.getX()>= map.getX()+ ((GameInfo.WIDTH - player.getWidth())/2f) &&
				player.getX()+player.getWidth() <= map.getX()+map.getWidth() - ((GameInfo.WIDTH - player.getWidth())/2f))
		{
			mainCamera.position.x = player.getX()+ player.getWidth()/2f;
		}
		else
		{
			if(player.getX() < 0)
			{
				mainCamera.position.x = map.getX()+ GameInfo.WIDTH/2f;
			}
			else
				mainCamera.position.x = map.getX() + map.getWidth() -(GameInfo.WIDTH/2f);
		}
		
		if(player.getY()>= map.getY()+ ((GameInfo.HEIGHT - player.getHeight())/2f) &&
				player.getY()+player.getHeight() <= map.getY()+map.getHeight() - ((GameInfo.HEIGHT - player.getHeight())/2f))
		{
			mainCamera.position.y = player.getY()+ player.getHeight()/2f;
		}
		else
		{
			if(player.getY() < 0)
			{
				mainCamera.position.y = map.getY()+ GameInfo.HEIGHT/2f;
			}
			else
				mainCamera.position.y = map.getY() + map.getHeight() -(GameInfo.HEIGHT/2f);
		}
	}
	
	// Método que verifica se o zumbi foi atacado pelo Jogador 
	void deadOfZombie()
	{
		for(ZombieTracker zombie: zombies.zombies)
		{
			if(player.getModeSword())
			{
				if(player.swordHitBox.overlaps(zombie.boxCollider) && player.getAtacking() && player.contadorAtackingSword> player.frameRepeat*3
						&& player.contadorAtackingSword<player.frameRepeat*5 && !zombie.getDead())
				{
					SoundsGame.swordAtack.stop();
					if(!zombie.getIsHurt())
					{
						zombie.setIsHurt(true);
						zombie.zombieActualLife-=25f;
						SoundsGame.zombieHurt.play();
					}
					if(zombie.zombieActualLife<=0 && !zombie.getDead())
					{
						zombie.setDead(true);
						hud.decrementZombieNumber();
						hud.incrementScore(200);
					}
					break;
				}
			}
			else
			{
				if(player.shotgunHighDamage.overlaps(zombie.boxCollider) && player.getAtacking() && player.contadorShootGun> player.frameRepeat*2
						&& player.contadorShootGun<player.frameRepeat*5)
				{
					if(!zombie.getIsHurt())
					{
						zombie.zombieActualLife-=100f;
						zombie.setIsHurt(true);
						
					}
						
					if(zombie.zombieActualLife<=0 && !zombie.getDead())
					{
						zombie.setDead(true);
						hud.decrementZombieNumber();
						hud.incrementScore(200);
						
					}
				}
				else if(player.shotgunLowDamage.overlaps(zombie.boxCollider) && player.getAtacking() && player.contadorShootGun> player.frameRepeat*2
						&& player.contadorShootGun<player.frameRepeat*5)
				{
					if(!zombie.getIsHurt())
					{
						zombie.zombieActualLife-=50f;
						zombie.setIsHurt(true);
					}
						
					if(zombie.zombieActualLife<=0 && !zombie.getDead())
					{
						zombie.setDead(true);
						hud.decrementZombieNumber();
						hud.incrementScore(200);
					}
				}
			}
			
		}
	}
	
	// Método que verifica se o player foi atingido pelo zumbi
	void zombieHitPlayer()
	{
		for (ZombieTracker zombie: zombies.zombies)
		{
			if(zombie.zombieHitBox.overlaps(player.boxCollider) && zombie.getAtacking() && zombie.contadorAtacking> zombie.frameRepeat*3
					&& zombie.contadorAtacking<zombie.frameRepeat*5)
			{
				player.setHurt(true);
				zombie.zombieOnHit++;
				if(zombie.zombieOnHit==1)
				{
					SoundsGame.swordAtack.stop();
					player.playerLife-=ZombieTracker.powerAtack;
					if(player.playerLife <0)
						player.playerLife = 0;
					SoundsGame.playerHurt.play();
					hud.playerLifeBar(player.playerLife);
					if(player.playerLife<=0)
					{
						player.setDead(true);
						manager.checkForNewHighscores();
						SoundsGame.playerDead.play();
					}
				}
				break;
			}	
		}
	}
	
	// metodo que troca as waves assim que uma é completada
	void changeWaves()
	{
		if(GameManager.zombieActualNumber==0 && zombies.zombies.size==0)
		{
			hud.incrementWave();
			manager.numbersCollectables();
			GameManager.changeWaves();
			hud.updateZombiesNumber();
			zombies.createZombies();
			zombies.setVelocityZombies();
			collectables.createAmmunition();
			collectables.createLife();
			
			// Altero a força e a vida dos zumbis a cada 5 ondas
			if(GameManager.waveNumber%5==0)
			{
				zombies.changePowerAndLife();
			}
		}
	}
	
	
	// metodo que verifica se o player pegou os itens
	void takeCollectables()
	{
		if(collectables.energyLife.size>0)
		{
			for(int i = 0;i<collectables.energyLife.size;i++)
			{
				if(player.boxCollider.overlaps(collectables.energyLife.get(i).box))
				{
					collectables.energyLife.removeIndex(i);
					player.playerLife+=150;
					if(player.playerLife>1000)
						player.playerLife = 1000;
					hud.playerLifeBar(player.playerLife);
					SoundsGame.playerTake.play();
					break;
				}
			}
		}
		else
			collectables.existsLife = false;
		
		if(collectables.ammunition.size>0)
		{
			for(int i = 0;i<collectables.ammunition.size;i++)
			{
				if(player.boxCollider.overlaps(collectables.ammunition.get(i).box))
				{
					collectables.ammunition.removeIndex(i);
					hud.incrementShotgunBullets();
					SoundsGame.changeWeaponShotgun.play();
					break;
				}
			}
		}
		else
			collectables.existsAmmunition = false;
	}
	
	// metodo que desenha os itens de vida ou munição
	void drawCollectables()
	{
		if(collectables.energyLife.size>0)
			collectables.drawLifeCollectable();
		if(collectables.ammunition.size>0)
			collectables.drawAmmunitionCollectable();
	}
	
	
	
	// Metodo que reune os métodos de verificações
	// Dessa forma podemos pausar o jogo
	void update()
	{
		if(Gdx.input.justTouched())
		{
			if(!manager.getPause())
				manager.setPause(true);
			else
				manager.setPause(false);
		}
		
		if(!manager.getPause())
		{
			hud.tutorial.setX(-1400);
			hud.pause.setX(-400);
			hud.highscoreLabel.setX(-500);
			hud.highWaveLabel.setX(-500);
			hud.playerIsHurt(player.getHurt());
			updatePlayer();
	        moveCamera();
	        limitsPlayer();
	        deadOfZombie();
	        if(!player.getDead())
	        {
	        	if(zombies.zombies.size==1)
	        		zombies.zombies.get(0).anguloIncrement = 0;
	        	if(zombies.zombies.size>1 && !ZombieController.teleportAllowed && GameManager.waveNumber>1)
	        		zombies.zombieTeleport(player.getX()+player.getWidth()/2f, player.getY()+player.getHeight()/2f);
	        	zombieHitPlayer();
	        	zombies.activeTracker(player.getX()+player.getWidth()/2f, player.getY()+player.getHeight()/2f,zombies.zombies);
	        	changeWaves();
	        	takeCollectables();
	        	
	        }
	        else
	        {
	        	for(int i = 0;i<zombies.zombies.size;i++)
				{
					zombies.zombies.get(i).setAtacking(false);
					zombies.zombies.get(i).setWalking(false);
				}
	        }
	        zombies.removeZombieDead();
		}
		else
		{
			zombies.setZombiesIdle();
			hud.pause.setX(920);
			hud.tutorial.setX(52);
		}
		
		if(player.getDead())
		{
			// Através desse Action podemos gerar uma saida de cena com Fading
			RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                	SoundsGame.music.stop();
                	SoundsGame.music.dispose();
                	loadGameplay = 0;
                	dispose();
                    game.setScreen(new GameOver(game));
                    
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(2f));
            sa.addAction(Actions.fadeOut(3f));
            sa.addAction(run);
            

            hud.getStage().addAction(sa);
		}
	}
	
	@Override
	public void show() 
	{
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(loadGameplay<1)
			loadGameplay++;
        else
        {
	        update();
	        
	        game.getBatch().begin();
	        //map.draw(game.getBatch());
	        game.getBatch().draw(map,map.getX(),map.getY());
	        drawCollectables();
	        
	        player.drawShader(game.getBatch()); 
	        player.drawPlayerAnimation(game.getBatch());
	        
	        zombies.drawZombies(game.getBatch());
	        game.getBatch().end();
	        
	        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
	        hud.getStage().draw();
	        hud.getStage().act();

        }
        
        
        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();
	}

	@Override
	public void resize(int width, int height) 
	{
		gameViewport.update(width,height);
	}

	@Override
	public void pause() 
	{
		
		
	}

	@Override
	public void resume() 
	{
		
		
	}

	@Override
	public void hide() 
	{
		
		
	}

	@Override
	public void dispose() 
	{
		//nightEffect.getTexture().dispose();
		map.getTexture().dispose();
		hud.getStage().dispose();
		player.getTexture().dispose();
		zombies.disposeZombies();
		ImagesZombies.disposeImages();
		ImagesPlayer.disposeImages();
		ImagesCollectables.disposeCollectables();
		SoundsGame.disposeAllSounds();
		//this.dispose();
	}

	

}
