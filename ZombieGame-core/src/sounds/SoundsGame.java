package sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundsGame 
{
	public static Sound changeWeaponSword, changeWeaponShotgun, shotgunFire;
	public static Sound zombieHurt;
	public static Sound swordAtack;
	public static Sound playerHurt;
	public static Sound playerDead;
	public static Sound teleport;
	public static Sound playerTake;
	
	public static Music music;
	
	public static void LoadSounds()
	{
		changeWeaponShotgun = Gdx.audio.newSound(Gdx.files.internal("Sounds/takeshotgun.mp3"));
        changeWeaponSword = Gdx.audio.newSound(Gdx.files.internal("Sounds/takesword.mp3"));
        shotgunFire = Gdx.audio.newSound(Gdx.files.internal("Sounds/shotgunfire.mp3"));
        
        zombieHurt = Gdx.audio.newSound(Gdx.files.internal("Sounds/zombieHurt.mp3"));
        swordAtack = Gdx.audio.newSound(Gdx.files.internal("Sounds/swordatack.mp3"));
        playerHurt = Gdx.audio.newSound(Gdx.files.internal("Sounds/playerHurt.mp3"));
        playerDead = Gdx.audio.newSound(Gdx.files.internal("Sounds/playerdeath.mp3"));
        playerTake = Gdx.audio.newSound(Gdx.files.internal("Sounds/take.wav"));
        
        teleport = Gdx.audio.newSound(Gdx.files.internal("Sounds/teleport.wav"));
        
        music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(.1f);
        music.play();
	}
	
	public static void disposeAllSounds()
	{
		changeWeaponShotgun.dispose();
		changeWeaponSword.dispose();
		shotgunFire.dispose();
		zombieHurt.dispose();
		swordAtack.dispose();
		playerHurt.dispose();
		playerDead.dispose();
		teleport.dispose();
		playerTake.dispose();
		music.dispose();
	}
}
