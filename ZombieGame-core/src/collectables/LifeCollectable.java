package collectables;

import com.badlogic.gdx.math.Rectangle;

public class LifeCollectable 
{
	public Rectangle box;
	
	public LifeCollectable()
	{
		box = new Rectangle();
		box.width = 50;
		box.height = 50;
	}
}
