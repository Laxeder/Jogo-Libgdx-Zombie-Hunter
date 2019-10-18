package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameManager 
{
	private boolean isPause;
	public static boolean gameStartedFromMainMenu;
	public static int playerScore;
	public static byte zombieNumber;
	public static byte zombieActualNumber;
	public static byte waveNumber;
	public static byte shotgunBullets;
	public static byte numberLifeCollectable;
	public static byte numberAmmunitionColletable;
	public static int FPS = 60;
	
	public static float positionsZombies[][] = new float[64][2];
	
	public static GameData gameData;
	private Json json = new Json();
	private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");
	
	public GameManager()
	{
		isPause = true;
		gameStartedFromMainMenu = true;
		zombieNumber = 5;
		zombieActualNumber = zombieNumber;
		waveNumber = 1;
		shotgunBullets = 4;
		playerScore = 0;
		numberLifeCollectable = 1;
		numberAmmunitionColletable = 1;
		createPositions();
	}

	// metodo inicial que gera as 64 posições possiveis para os Zumbis.
	void createPositions()
	{
		int x= 0;
		int y = 0;
		int n = 0;
		
		while(n<32)
		{
			if(y==16)
			{
				y=0;
				x++;
			}
			positionsZombies[n][0]=-1590+(-250*x);
			positionsZombies[n][1]= -1615 +(250*y);
			y++;
			n++;
		}
		n= 0;
		while(n<32)
		{
			if(n<16)
				positionsZombies[n+32][0]=(positionsZombies[n][0]+4190);
			else
				positionsZombies[n+32][0]=(positionsZombies[n-16][0]+4440);
			positionsZombies[n+32][1] = positionsZombies[n][1];
			n++;
		}
	}
	
	public static void showPositions()
	{
		for (int i = 0;i<positionsZombies.length;i++)
		{
			System.out.println("x: "+positionsZombies[i][0]+" y:"+positionsZombies[i][1]);
		}
	}
	
	// metodo que aumenta o numero de zumbis na tela
	public static void changeWaves()
	{
		zombieNumber+=5;
		if(zombieNumber>64)
			zombieNumber = 64;
		zombieActualNumber = zombieNumber;
	}
	
	// metodo que gera um arquivo de salvamento caso não tenha um ou carrega algum que esteja salvo.
	public void initializeGameData()
    {
        if(!fileHandle.exists())
        {
            gameData = new GameData();
            gameData.setHigherScore(0);
            gameData.setWaveNumberScore(1);
            saveData();
        }
        else
        {
            loadData();
        }
    }
	
	// metodo para salvar
    public void saveData()
    {
        if(gameData!= null)
        {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)),false);
        }
    }
    
    // metodo para carregamento de arquivo
    public void loadData()
    {
        //gameData = json.fromJson(GameData.class,fileHandle.readString());
        gameData = json.fromJson(GameData.class,Base64Coder.decodeString(fileHandle.readString()));
    }

    // metodo que verifica se deve ou não salvar o game
    public void checkForNewHighscores()
    {
        int oldHighscore = gameData.getHigherScore();
        int oldWaveNumber = gameData.getWaveNumberScore();

        if(oldHighscore<playerScore)
        {
            gameData.setHigherScore(playerScore);;
        }
        if(oldWaveNumber<waveNumber)
        {
            gameData.setWaveNumberScore(waveNumber);;
        }

        saveData();
    }
    
    // metodo que define a quantidade de coletaveis por ondas
    public void numbersCollectables()
    {
    	if(waveNumber>1 && waveNumber<=5)
    	{
    		numberLifeCollectable = 3;
    		numberAmmunitionColletable = 3;
    	}
    	else if(waveNumber>5 && waveNumber<10)
    	{
    		numberLifeCollectable = 4;
    		numberAmmunitionColletable = 4;
    	}
    	else
    	{
    		numberLifeCollectable = 5;
    		numberAmmunitionColletable = 5;
    	}
    		
    	
    }
	
	public boolean getPause() 
	{
		return isPause;
	}

	public void setPause(boolean isPause) 
	{
		this.isPause = isPause;
	}
	
	
}
