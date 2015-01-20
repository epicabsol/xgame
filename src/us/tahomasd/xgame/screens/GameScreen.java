package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;
import us.tahomasd.xgame.XGameLevelLayout.CollisionLayer;
import us.tahomasd.xgame.XGameLevelLayout.Tile;

import java.util.ArrayList;

public class GameScreen extends XGameScreen {
	public static XGameLevelLayout CurrentLevel = null;
	
	public Tile[][] Tiles;
	public CollisionLayer[][] Collision;
	public Vector2d Camera;
	public ArrayList<GameEntity> Entities = new ArrayList<GameEntity>();
	public GameEntity Player;
	
	public Vector2d Gravity = new Vector2d(0, -0.1);
	
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
		Player.position.Y = 150;
	}
	
	@Override
	public void Render()
	{
		for (GameEntity e : Entities)
		{
			e.Render();
		}
	}
	
	@Override 
	public void Update()
	{
		PhysicsTick();
	}
	
	public void PhysicsTick()
	{
		for (GameEntity e : Entities)
		{
			Vector2d newpos = e.position.clone();
			// Gravity
			e.speed = e.speed.add(Gravity);
			
			
			newpos = newpos.add(e.speed);
			
			// TODO: also check against other entities.
			if (EntityMapCollides(e, newpos))
			{ // If the entity would collide where it wants to go, then:
				// Reset the speed.
				e.speed = new Vector2d(0, 0);
				
				// Find a spot to put it.
				boolean works = false;
				Vector2d owant = newpos; // Where it originally wanted to go
				double factor = 1.0;
				while (!works)
				{
					if (!EntityMapCollides(e, newpos)) // TODO: check against other entities here as well
					{
						works = true;
					}
					else
					{
						// perform linear interpolation of about 10 increments and find where it starts colliding and leave it there.
						newpos = owant.lerp(e.position, factor);
						factor -= (1.0 / 10); // Replace 10 with however many steps should be tested.
					}
				}
				//System.out.println("factor: " + factor);
			}
			e.position = newpos;
			
			e.Update();
		}
	}
	
	public boolean EntityMapCollides(GameEntity e, Vector2d pos)
	{
		//boolean good = true;
		Vector2d bottomright = new Vector2d(e.position.X + e.bbSize.X, e.position.Y + e.bbSize.Y);
		for (int x = (int) XGameLevelLayout.ToTile(pos).X; x <= (int) XGameLevelLayout.ToTile(bottomright).X; x++)
		{
			for (int y = (int) XGameLevelLayout.ToTile(pos).Y; y <= (int) XGameLevelLayout.ToTile(bottomright).Y; y++)
			{
				if (Collision[x][y] == CollisionLayer.Solid)
				{
					//System.out.println("Collision had!");
					return true;
				}
				else
				{
					//System.out.println("Tile at " + x + ", " + y + " is " + Collision[x][y].toString());
				}
			}
		}
		return false; // Check collision against all maptiles that are in the entity's bounding box and are Solid.
	}
}
