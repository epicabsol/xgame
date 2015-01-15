package us.tahomasd.xgame;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

//import us.tahomasd.xgame.XGameMain;
//import us.tahomasd.xgame.*;   // Do we not need to import classes in the same package? Sweet!
import us.tahomasd.xgame.screens.*;

public class XGameCore {
	public static int TileSize = 16;
	public static boolean EscapePressed = false;
	public static boolean SpacePressed = false;
	public static boolean ZPressed = false;
	public static boolean UpPressed = false;
	public static boolean DownPressed = false;
	public static boolean LeftPressed = false;
	public static boolean RightPressed = false;
	public static XGameScreen CurrentScreen = null;
	
	// Screens in the game
	public static MainMenu MainMenuScreen = null;
	public static XGameScreen GameScreen = null;
	public static XGameScreen ResultsScreen = null;
	
	public static void Load()
	{
		MainMenuScreen = new MainMenu();
	}
	
	public static void Update()
	{
		Input();
		
	}
	
	public static void Input()
	{
		
		
	}
	
	public static void Render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
	}
}
