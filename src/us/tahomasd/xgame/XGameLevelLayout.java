package us.tahomasd.xgame;
/**
 * Stores what a level should look like.
 *
 */
public class XGameLevelLayout {
	public Tile[][] Tiles;
	public CollisionLayer[][] Collision;
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
	}
	
	public Vector2d ToTile(Vector2d pixel)
	{
		return new Vector2d((int) pixel.X / XGameCore.TileSize, (int) pixel.Y / XGameCore.TileSize);
	}
	
	public Vector2d ToPixel(Vector2d tile)
	{
		return new Vector2d(tile.X * XGameCore.TileSize, tile.Y * XGameCore.TileSize);
	}
}
