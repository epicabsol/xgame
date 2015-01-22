package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;

import java.util.ArrayList;


//import org.jbox2d.*;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
//import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
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
	public boolean Physics = false;
	public int GroundTouchCount = 0;
	public boolean OnGround()
	{
		if (GroundTouchCount < 0)
		{
			GroundTouchCount = 0;
		}
		return GroundTouchCount > 0;
	}
	public Body Body;
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
	
	public void PushTransform()
	{
		if (Physics)
		{
			Body.setTransform(new Vec2((float) position.X / 16, ((float) position.Y / 16) + 0.5f) , 0.0f);
			Body.setLinearVelocity(new Vec2((float) speed.X / 16, (float) speed.Y / 16));
		}
	}
	
	public void PullTransform()
	{
		if (Physics)
		{
			position.X = (Body.getPosition().x) * 16;
			position.Y = (Body.getPosition().y - 0.5) * 16;
			speed.X = Body.getLinearVelocity().x * 16;
			speed.Y = Body.getLinearVelocity().y * 16;
		}
	}
	
	public static GameEntity Create(Vector2d bbSize) 
	{ 
		GameEntity e = new GameEntity(); 
		e.bbSize = bbSize;
		return e;
	}
	
	public static GameEntity CreateWithPhysics(Vector2d bbSize, float desnsity, boolean fixed)
	{
		GameEntity e = new GameEntity(); 
		e.bbSize = bbSize;
		// Set up the shape
		PolygonShape TileShape = new PolygonShape();
		TileShape.setAsBox((float) bbSize.X / 2 / 16, (float) bbSize.Y / 2 / 16);
		BodyDef bdef = new BodyDef();
		//tile.setPosition(new Vec2(x, y));
		Body TileBody = GameScreen.CollisionWorld.createBody(bdef);
		// Fix the shape to the body
		TileBody.createFixture(TileShape, fixed ? 0.0f : 0.1f);
		TileBody.setFixedRotation(true);
		TileBody.getFixtureList().setRestitution(0.01f);
		if (!fixed)
		{
			TileBody.setType(BodyType.DYNAMIC);
			
		}
		e.Physics = true;
		e.Body = TileBody;
		return e;
	}
}
