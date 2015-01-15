package us.tahomasd.xgame;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import org.lwjgl.glfw.GLFW;

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
	 
    // The window handle
    public static long window;
	
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
		boolean esc = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == 1 ? true : false);
		if (esc  != EscapePressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_ESCAPE);
			EscapePressed = esc;
		}
		
		boolean space = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == 1 ? true : false);
		if (space  != SpacePressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_SPACE);
			SpacePressed = space;
		}
		
		boolean z = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_Z) == 1 ? true : false);
		if (z != ZPressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_Z);
			ZPressed = z;
		}
		
		boolean up = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == 1 ? true : false);
		if (up != UpPressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_UP);
			UpPressed = up;
		}
		
		boolean down = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == 1 ? true : false);
		if (down != DownPressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_DOWN);
			DownPressed = down;
		}
		
		boolean left = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == 1 ? true : false);
		if (left != LeftPressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_LEFT);
			LeftPressed = left;
		}
		
		boolean right = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == 1 ? true : false);
		if (right != RightPressed)
		{
			CurrentScreen.OnKeyDown(GLFW.GLFW_KEY_RIGHT);
			RightPressed = right;
		}
	}
	
	public static void Render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
	}
}
