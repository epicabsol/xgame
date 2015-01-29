package us.tahomasd.xgame;

import java.io.File;
import java.util.*;

public class XGameResources {
	public static Texture cursor = null;
	public static Texture start = null;
	public static Texture levels = null;
	public static Texture credits = null;
	public static Texture close = null;
	public static Texture playertest = null;
	
	public static ArrayList<XGameLevelLayout> Levels = new ArrayList<XGameLevelLayout>();
	
	public static Hashtable<String, Texture> TileTextures = new Hashtable<String, Texture>();
	public static void LoadResources()
	{
		// Load UI textures
		cursor = new Texture(new File("res/img/cursor.png").getAbsolutePath());
		start = new Texture(new File("res/img/button_start.png").getAbsolutePath());
		levels = new Texture(new File("res/img/button_levels.png").getAbsolutePath());
		credits = new Texture(new File("res/img/button_credits.png").getAbsolutePath());
		close = new Texture(new File("res/img/button_close.png").getAbsolutePath());
		playertest = new Texture(new File("res/img/playertest.png").getAbsolutePath());
		
		// Load tile textures!
		File TilesFolder = new File("res/img/tiles");
		File[] tiles = TilesFolder.listFiles();
		int i = 0;
		if (tiles != null)
		{
			for (File f : tiles)
			{
				if (f.getName().equals("Thumbs.db"))
				{
					continue;
				}
				try
				{
					TileTextures.put(f.getName(), new Texture(f.getAbsolutePath()));
					i++;
				}
				catch (Exception ex)
				{
					System.out.println("Could not load texture " + f.getName());
				}
			}
		}
		System.out.println("Loaded " + i + " tiles.");
		
		// Load levels!
		File LevelsFolder = new File("res/levels");
		
		File[] levels = LevelsFolder.listFiles();
		if (levels != null)
		{
			for (File f : levels)
			{
				if (f.getName().equals(".DS_Store"))
					continue;
				Levels.add(new XGameLevelLayout(f.getAbsolutePath()));
			}
		}
	}
}
