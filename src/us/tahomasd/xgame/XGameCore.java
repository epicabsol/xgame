package us.tahomasd.xgame;

import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;

import javax.swing.JOptionPane;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
//import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

//import us.tahomasd.xgame.XGameMain;
//import us.tahomasd.xgame.*;   // Do we not need to import classes in the same package? Sweet!
import us.tahomasd.xgame.screens.*;

public class XGameCore {
	public static int TileSize = 32;
	public static int Scale()
	{
		return TileSize / 16;
	}
	public static Vector2d MousePos()
	{
		DoubleBuffer bX = BufferUtils.createDoubleBuffer(1); DoubleBuffer bY = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(XGameCore.window, bX, bY);
		return new Vector2d(bX.get() / Scale(), Math.ceil((XGameMain.HEIGHT - bY.get()) / Scale()));
	}
	public static void MousePos(double X, double Y)
	{
		GLFW.glfwSetCursorPos(window, X * Scale(), (XGameMain.HEIGHT - Y * Scale()));
	}
	public static boolean EscapePressed = false;
	public static boolean SpacePressed = false;
	public static boolean ZPressed = false;
	public static boolean UpPressed = false;
	public static boolean DownPressed = false;
	public static boolean LeftPressed = false;
	public static boolean RightPressed = false;
	public static boolean DrawCursor = true;
	public static XGameScreen CurrentScreen = null;
	
	// Screens in the game
	public static MainMenu MainMenuScreen = null;
	public static XGameScreen GameScreen = null;
	public static XGameScreen ResultsScreen = null;
	 
    // The window handle
    public static long window;
	
	public static void Load()
	{
		MousePos(0, 0);
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glEnable(GL_BLEND);
		GL11.glEnable(GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL_GREATER, 0);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		XGameResources.LoadResources();
		
		SetupViewport();
		
		MainMenuScreen = new MainMenu();
		MainMenuScreen.Load();
		
		//GameScreen.Load();
		
		//ResultsScreen.Load(); These are not initialized yet!
		CurrentScreen = MainMenuScreen;
	}
	
	public static void SetupViewport()
	{
		GL11.glViewport(0, 0, XGameMain.WIDTH , XGameMain.HEIGHT);
		GL11.glMatrixMode(GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, XGameMain.WIDTH, 0, XGameMain.HEIGHT, -400, 400); 
	}
	
	public static void Dispose()
	{
		MainMenuScreen.Dispose();
		//GameScreen.Dispose();
		//ResultsScreen.Dispose(); // These are not initialized yet!
	}
	
	public static void Update()
	{
		// Clip cursor to window
		Vector2d c = MousePos();
		boolean fix = false;
		double x = c.X; double y = c.Y;
		if (XGameMain.RawHeight < 15)
		{
			System.out.println("BAD!!!");
		}
		if (c.Y > XGameMain.RawHeight * 16 )
		{
			fix = true;
			y = XGameMain.RawHeight * 16 ;
		}
		else if (c.Y < 0)
		{
			fix = true;
			y = 0;
		}
		if (c.X < 0)
		{
			fix = true;
			x = 0;
		}
		else if (c.X > XGameMain.RawWidth * 16)
		{
			fix = true;
			x = XGameMain.RawWidth * 16;
		}
		
		if (fix)
		{
			MousePos(x, y);
		}

		Input();
		CurrentScreen.Update();
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
		SetupViewport();
		CurrentScreen.Render();
		if (DrawCursor)
		{
			Vector2d m = MousePos();
			m = m.multiply(Scale());
			m.Y = m.Y - XGameResources.cursor.height() - 1;
			XGameResources.cursor.renderNoScale(m, 250);
		}
	}
	
	public static void msgBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
