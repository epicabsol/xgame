package us.tahomasd.xgame;

import java.io.File;

public class XGameResources {
	public static Texture cursor = null;
	public static Texture start = null;
	public static Texture levels = null;
	public static Texture credits = null;
	public static Texture close = null;
	public static void LoadResources()
	{
		cursor = new Texture(new File("res/img/cursor.png").getAbsolutePath());
		start = new Texture(new File("res/img/button_start.png").getAbsolutePath());
		levels = new Texture(new File("res/img/button_levels.png").getAbsolutePath());
		credits = new Texture(new File("res/img/button_credits.png").getAbsolutePath());
		close = new Texture(new File("res/img/button_close.png").getAbsolutePath());
	}
}
