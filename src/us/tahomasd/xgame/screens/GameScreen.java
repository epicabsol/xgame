package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;
import us.tahomasd.xgame.XGameLevelLayout.CollisionLayer;
import us.tahomasd.xgame.XGameLevelLayout.Tile;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

public class GameScreen extends XGameScreen {
	public static XGameLevelLayout CurrentLevel = null;
	
	public Tile[][] Tiles;
	public CollisionLayer[][] Collision;
	public Vector2d Camera = new Vector2d(0, 0);
	public ArrayList<GameEntity> Entities = new ArrayList<GameEntity>();
	
	public GameEntity Player;
	public double PlayerXSpeed = 0;
	
	public Vector2d Gravity = new Vector2d(0, -0.8);
	
	@Override
	public void Load() {
		Player = GameEntity.Create();
		Player.SpriteFrames.add(XGameResources.playertest);
		Entities.add(Player);
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
		Player.position.Y = 250;
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
				switch (Tiles[x][y])
				{
				case Stone:
					t = XGameResources.TileTextures.get("crate1.png");
					break;
				}
				
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
		Player.speed.X = PlayerXSpeed;
		PhysicsTick();
		
		// Make the camera follow the player, in a restricted manner.
		int X = Math.max(0, ((int) Player.position.X + (Player.SpriteFrames.get(0).width() / 2 * XGameCore.Scale()) - (XGameMain.WIDTH / 2)) / XGameCore.Scale());
		int Y = Math.max(0, ((int) Player.position.Y + (Player.SpriteFrames.get(0).height() / 2 * XGameCore.Scale()) - (XGameMain.HEIGHT / 2)) / XGameCore.Scale());
		Camera.X = X;
		Camera.Y = Y;
	}
	
	public void PhysicsTick()
	{
		
	}
	
	public void OldPhysicsTick()
	{
		for (GameEntity e : Entities)
		{
			Vector2d newposX = e.position.clone();
			Vector2d newposY = e.position.clone();
			// Gravity
			e.speed = e.speed.add(new Vector2d(Gravity.X, Gravity.Y));
			
			e.OnGround = false;
			
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
				e.OnGround = true; // NOTE: Don't set this if we hit an entity, only a tile;
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
			if (Player.OnGround)
			{
				Player.speed.Y = 10;
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
