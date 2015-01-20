package us.tahomasd.xgame;
/**
 * Stores what a level should look like.
 *
 */
public class XGameLevelLayout {
	public Tile[][] Tiles = new Tile[0][0];
	public CollisionLayer[][] Collision = new CollisionLayer[0][0];
	public Vector2d PlayerStart;
	/**
	 * Defines the types of static (unmoving, unanimated, grid-snapped) tiles.
	 */
	public enum Tile // TODO: More tile types!
	{
		Empty, Stone, Grass, Dirt
	}
	
	public enum CollisionLayer
	{
		Behind, Solid, Front
	}
	
	public XGameLevelLayout(String LevelPath)
	{
		// TODO: Load the level!
		// For now, just initialize it for testing purposes.
		Collision = new CollisionLayer[100][25];
		Tiles = new Tile[100][25];
		for (int x = 0; x < 100; x++)
		{
			for (int y = 0; y < 25; y++)
			{
				if (y > 0)
				{
					if (x > y)
					{
						Collision[x][y] = CollisionLayer.Solid;
						Tiles[x][y] = Tile.Stone;
					}
					else
					{
						Collision[x][y] = CollisionLayer.Behind;
					} 
				}
				else
				{
					Collision[x][y] = CollisionLayer.Solid;
					Tiles[x][y] = Tile.Stone;
				}
			}
		}
		return;
	}
	
	public static Vector2d ToTile(Vector2d pixel)
	{
		return new Vector2d((int) pixel.X / XGameCore.TileSize, (int) pixel.Y / XGameCore.TileSize);
	}
	
	public static Vector2d ToPixel(Vector2d tile)
	{
		return new Vector2d(tile.X * XGameCore.TileSize, tile.Y * XGameCore.TileSize);
	}
}
