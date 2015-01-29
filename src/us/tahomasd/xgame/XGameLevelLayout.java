package us.tahomasd.xgame;
/**
 * Stores what a level should look like.
 *
 */

import java.io.*;
import java.util.*;
public class XGameLevelLayout {
	public Tile[][] Tiles = new Tile[0][0];
	public CollisionLayer[][] Collision = new CollisionLayer[0][0];
	public Vector2d PlayerStart;
	 
	/**
	 * Defines the types of static (unmoving, unanimated, grid-snapped) tiles.
	 */
	public enum Tile // TODO: More tile types!
	{
		Cloud, MushroomBlock, StarBlock, OneupBlock, CoinBlock, EmptyBlock, Coin,
		BrownTile, Ground, PipeTopN, WarpEnter, WarpExit, PipeBottom, BreakableBlock, FlagPole, Castle,
		Water, Lava, Bush, Empty
	}
	
	public enum CollisionLayer
	{
		Behind, Solid, Front
	}
	
	public XGameLevelLayout(String LevelPath)
	{
		Scanner sc;
	
		try {
			sc = new Scanner(new File(LevelPath));
			int height = 0;
			int width = 0;
			String t = "";
			while(sc.hasNextLine())
			{
				t = sc.nextLine();
				if (height == 0)
					width = t.length();
				height += 1;
			}
			sc = new Scanner(new File(LevelPath));
			// TODO: Load the level!
			// For now, just initialize it for testing purposes.
			String s = "";
			Collision = new CollisionLayer[width][height];
			Tiles = new Tile[width][height];
			for (int y = height - 1; y > 0; y--)
			{
				s = sc.nextLine();
				System.out.println(s);
				for (int x = 0; x < s.length(); x++)
				{
					switch(s.charAt(x)){
						case '@': Tiles[x][y] = Tile.Cloud;
						Collision[x][y] = CollisionLayer.Behind;
								break;
						case 'M': Tiles[x][y] = Tile.MushroomBlock;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case '*': Tiles[x][y] = Tile.StarBlock;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case 'l': 
							try
							{
								Tiles[x][y] = Tile.OneupBlock;
							Collision[x][y] = CollisionLayer.Front;
							}
							catch(Exception ex)
							{
								System.out.println("");
							}
								break;
						case 'C': Tiles[x][y] = Tile.CoinBlock;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case 'B': Tiles[x][y] = Tile.EmptyBlock;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case '0': Tiles[x][y] = Tile.Coin;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case 'X': Tiles[x][y] = Tile.BrownTile;
						Collision[x][y] = CollisionLayer.Solid;
								break;
						case 'G': Tiles[x][y] = Tile.Ground;
						Collision[x][y] = CollisionLayer.Solid;
								break;
						case 'P': Tiles[x][y] = Tile.PipeTopN;
						Collision[x][y] = CollisionLayer.Solid;
								break;
						case 'V': Tiles[x][y] = Tile.WarpEnter;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case '^': Tiles[x][y] = Tile.WarpExit;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case 'p': Tiles[x][y] = Tile.PipeBottom;
						Collision[x][y] = CollisionLayer.Solid;
								break;
						case '%': Tiles[x][y] = Tile.BreakableBlock;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case '|': Tiles[x][y] = Tile.FlagPole;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case '7': Tiles[x][y] = Tile.Castle;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case 'W': Tiles[x][y] = Tile.Water;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case 'L': Tiles[x][y] = Tile.Lava;
						Collision[x][y] = CollisionLayer.Front;
								break;
						case '$': Tiles[x][y] = Tile.Bush;
						Collision[x][y] = CollisionLayer.Behind;
								break;
						case '.': Tiles[x][y] = Tile.Empty;
						Collision[x][y] = CollisionLayer.Behind;
								break;
					}/*Cloud, MushroomBlock, StarBlock, OneupBlock, CoinBlock, EmptyBlock, Coin,
			BrownTile, Ground, PipeTopN, WarpEnter, WarpExit, PipeBottom, BreakableBlock, FlagPole, Castle,
			Water, Lava, Bush, Empty*/
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return;
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
