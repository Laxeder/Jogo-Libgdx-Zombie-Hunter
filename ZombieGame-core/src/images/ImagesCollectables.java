package images;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ImagesCollectables 
{
	public static Sprite life;
	public static Sprite ammunition;
	
	public static void loadCollectablesImages()
	{
		life = new Sprite(new Texture("Huds Screen/health energy.png"));
		ammunition = new Sprite(new Texture("Huds Screen/shotgun collectable.png"));
	}
	
	public static void disposeCollectables()
	{
		life.getTexture().dispose();
		ammunition.getTexture().dispose();
	}
}
