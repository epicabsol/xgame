package us.tahomasd.xgame;

import java.io.File;

public class XGameResources {
	public static Texture cursor = null;
	public static void LoadResources()
	{
		cursor = new Texture(new File("res/img/cursor.png").getAbsolutePath());
	}
}
