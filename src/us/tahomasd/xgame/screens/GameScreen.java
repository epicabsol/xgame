package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;
import us.tahomasd.xgame.XGameLevelLayout.CollisionLayer;
import us.tahomasd.xgame.XGameLevelLayout.Tile;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.lwjgl.glfw.GLFW;

public class GameScreen extends XGameScreen {
	public static World CollisionWorld;
	public static CollisionCallback callback;
	public static XGameLevelLayout CurrentLevel = null;
	
	public Tile[][] Tiles;
	public CollisionLayer[][] Collision;
	public Vector2d Camera = new Vector2d(0, 0);
	public ArrayList<GameEntity> Entities = new ArrayList<GameEntity>();
	
	public GameEntity Player;
	public double PlayerXSpeed = 0;
	
	public Vector2d Gravity = new Vector2d(0, -5.00);
	
	@Override
	public void Load() {
		//Player = GameEntity.Create(new Vector2d(16, 32));
		
	}

	@Override
	public void Dispose() {
		
		
	}
	
	@Override
	public void OnOpened()
	{
		// Load the level in 'CurrentLevel'
		this.Tiles = CurrentLevel.Tiles.clone();
		this.Collision = CurrentLevel.Collision.clone();
		
		Entities.clear();
		
		CollisionWorld = new World(new Vec2((float) Gravity.X, (float) Gravity.Y));
		callback = new CollisionCallback();
		CollisionWorld.setContactListener(callback);
		
		Player = GameEntity.CreateWithPhysics(new Vector2d(16, 32), 10.0f, false);
		Player.position.Y = 250;
		Player.SpriteFrames.add(XGameResources.playertest);
		Entities.add(Player);
		Player.PushTransform();
		
		for (int x = 0; x < Tiles.length; x++)
		{
			for (int y = 0; y < Tiles[0].length; y++)
			{
				if (Collision[x][y] == CollisionLayer.Solid)
				{
					// Set up the shape
					PolygonShape TileShape = new PolygonShape();
					TileShape.setAsBox((float) 0.5, (float) 0.5);
					BodyDef tile = new BodyDef();
					tile.setPosition(new Vec2(x, y));
					// Set up the body and tag it as ground
					Body TileBody = CollisionWorld.createBody(tile);
					TileBody.setUserData("G");
					// Fix the shape to the body
					TileBody.createFixture(TileShape, (float) 0.0);
					TileBody.getFixtureList().setRestitution(0.00f);
				}
			}
		}
	}
	
	@Override
	public void Render()
	{
		for (GameEntity e : Entities)
		{
			e.Render(Camera);
		}
		for (int x = 0; x < Tiles.length ; x++)
		{
			for (int y = 0; y < Tiles[0].length; y++)
			{
				if (Tiles[x][y] == null)
				{
					continue;
				}
				Texture t = null;
				
				/* TODO: Use once we have more tile textures. For now, just draw solid things as a box.
				switch (Tiles[x][y])
				{
				case Ground:
					t = XGameResources.TileTextures.get("crate1.png");
					break;
				}  */
				
				t = XGameResources.TileTextures.get("crate1.png");
				
				if (t != null)
				{
					t.render(XGameLevelLayout.ToPixel(new Vector2d(x, y)).divide(XGameCore.Scale()).subtract(Camera), 0);
				}
			}
		}
	}
	
	@Override 
	public void Update()
	{
		boolean SideKey = false; 
		if (XGameCore.RightPressed)
		{
			PlayerXSpeed += 0.5;
			SideKey = true;
		}
		if (XGameCore.LeftPressed)
		{
			PlayerXSpeed += -0.5;
			SideKey = true;
		}
		PlayerXSpeed = clamp(-5, 5, PlayerXSpeed);
		if (!SideKey)
		{
			PlayerXSpeed *= 0.75;
		}
		Player.speed.X = PlayerXSpeed * 10;
		PhysicsTick();
		
		// Make the camera follow the player, in a restricted manner.
		int X = Math.max(0, ((int) Player.position.X + (Player.SpriteFrames.get(0).width() / 2 * XGameCore.Scale()) - (XGameMain.WIDTH / 2)) / XGameCore.Scale());
		int Y = Math.max(0, ((int) Player.position.Y + (Player.SpriteFrames.get(0).height() / 2 * XGameCore.Scale()) - (XGameMain.HEIGHT / 2)) / XGameCore.Scale());
		Camera.X = X;
		Camera.Y = Y;
		Camera = Camera.multiply(XGameCore.Scale());
	}
	
	public void PhysicsTick()
	{
		for (GameEntity e : Entities)
		{
			e.PushTransform();
		}
		CollisionWorld.step(0.1f, 10, 5);
		for (GameEntity e : Entities)
		{
			e.PullTransform();
		}
	}
	
	public void OnCollisionStarted(Body b1, Body b2)
	{
		System.out.println("Collision!");
		if (b1.equals(Player.Body))
		{
			if (( (String) b2.getUserData()).equals("G"))
			{
				Player.GroundTouchCount += 1;
			}
			
		}
	}
	
	public void OnCollisionEnded(Body b1, Body b2)
	{
		System.out.println("Collision stopped!");
		if (b1.equals(Player.Body))
		{
			if (( (String) b2.getUserData()).equals("G"))
			{
				Player.GroundTouchCount -= 1;
			}
			
		}
	}
	
	public void OldPhysicsTick()
	{
		for (GameEntity e : Entities)
		{
			Vector2d newposX = e.position.clone();
			Vector2d newposY = e.position.clone();
			// Gravity
			e.speed = e.speed.add(new Vector2d(Gravity.X, Gravity.Y));
			
			newposX = newposX.add(new Vector2d(e.speed.X, 0));
			
			// TODO: also check against other entities.
			if (EntityMapCollides(e, newposX))
			{ // If the entity would collide where it wants to go, then:
				// Reset the speed.
				e.speed = new Vector2d(0, e.speed.Y);
				
				// Find a spot to put it.
				boolean works = false;
				Vector2d owant = newposX; // Where it originally wanted to go
				double factor = 1.0;
				while (!works)
				{
					if (!EntityMapCollides(e, newposX)) // TODO: check against other entities here as well
					{
						works = true;
					}
					else
					{
						// perform linear interpolation of about 10 increments and find where it starts colliding and leave it there.
						newposX = owant.lerp(e.position, factor);
						factor -= (1.0 / 10); // Replace 10 with however many steps should be tested.
					}
				}
				
				//System.out.println("factor: " + factor);
			}
			e.position.X = newposX.X;
			
			
			newposY = newposY.add(new Vector2d(0, e.speed.Y));
			
			// TODO: also check against other entities.
			if (EntityMapCollides(e, newposY))
			{ // If the entity would collide where it wants to go, then:
				// Reset the speed.
				e.speed = new Vector2d(e.speed.X, 0);
				
				// Find a spot to put it.
				boolean works = false;
				Vector2d owant = newposY; // Where it originally wanted to go
				double factor = 1.0;
				while (!works)
				{
					if (!EntityMapCollides(e, newposY)) // TODO: check against other entities here as well
					{
						works = true;
					}
					else
					{
						// perform linear interpolation of about 10 increments and find where it starts colliding and leave it there.
						newposY = owant.lerp(e.position, factor);
						factor -= (1.0 / 10); // Replace 10 with however many steps should be tested.
					}
				}
				//System.out.println("factor: " + factor);
			}
			e.position.Y = newposY.Y;
			
			e.Update();
		}
	}
	
	public boolean EntityMapCollides(GameEntity e, Vector2d pos)
	{
		//boolean good = true;
		Vector2d bottomright = new Vector2d(e.position.X + e.bbSize.X, e.position.Y + e.bbSize.Y);
		for (int x = Math.max((int) XGameLevelLayout.ToTile(pos).X, 0); x <= (int) XGameLevelLayout.ToTile(bottomright).X; x++)
		{
			if (x < 0 || x > Tiles.length)
			{
				continue;
			}
			for (int y = (int) XGameLevelLayout.ToTile(pos).Y; y <= (int) XGameLevelLayout.ToTile(bottomright).Y; y++)
			{
				if (y < 0 || y > Tiles[0].length)
				{
					continue;
				}
				if (Collision[x][y] == CollisionLayer.Solid)
				{
					//System.out.println("Collision had!");
					return true;
				}
				else
				{
					System.out.println("No collision at (" + x + ", " + y + ").");
					//System.out.println("Tile at " + x + ", " + y + " is " + Collision[x][y].toString());
				}
			}
			if (x == (int) XGameLevelLayout.ToTile(bottomright).X)
			{
				System.out.println("last one!");
			}
		}
		System.out.println("No collision.");
		return false; // Check collision against all maptiles that are in the entity's bounding box and are Solid.
	}
	
	@Override public void OnKeyDown(int K)
	{
		switch (K)
		{
		case GLFW.GLFW_KEY_UP:
			//Jump!
			if (Player.OnGround())
			{
				Player.Body.applyForceToCenter(new Vec2(0, 10));
				Player.GroundTouchCount -= 1;
			}
			break;
		case GLFW.GLFW_KEY_SPACE:
			break;
		}
	}
	
	private double clamp(double min, double max, double val)
	{
		if (val < min)
		{
			return min;
		}
		else if (val > max)
		{
			return max;
		}
		else
		{
			return val;
		}
	}
}
