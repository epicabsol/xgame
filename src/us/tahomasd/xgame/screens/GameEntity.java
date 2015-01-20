package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;
import java.util.ArrayList;
/* GameEntity - an entity for a level that is affected by physics.
 * 
 */
public class GameEntity {
	public Vector2d position = new Vector2d(0, 0);
	public double Z = 0;
	public Vector2d bbSize = new Vector2d(16, 32); // Bounding box size
	public Vector2d speed = new Vector2d(0, 0);
	public ArrayList<Texture> SpriteFrames = new ArrayList<Texture>();
	public double FrameD = 0.0;
	public double AnimationSpeed = 1.0;
	public boolean UseGravity = true;
	public boolean OnGround = false;
	private GameEntity()
	{
		
	}
	public Texture CurrentFrame()
	{
		return SpriteFrames.get((int) Math.floor(FrameD));
	}
	public void StepSprite(double frames)
	{
		FrameD += frames;
		while (FrameD >= SpriteFrames.size())
		{
			FrameD -= SpriteFrames.size();
		}
		while (FrameD < 0)
		{
			FrameD += SpriteFrames.size();
		}
	}
	public boolean Touches(Vector2d mypos, GameEntity other)
	{
		return false; // TODO: Bounding box calculation! (use the Rectangle class)
	}
	public void StepSprite()
	{
		StepSprite(AnimationSpeed);
	}
	public void Render(Vector2d offset)
	{
		CurrentFrame().render(position.divide(XGameCore.Scale()).subtract(offset), Z);
	}
	public void Update()
	{
		
	}
	public static GameEntity Create() { return new GameEntity();}
}
