package us.tahomasd.xgame.screens;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.io.File;

import us.tahomasd.xgame.*;
//import us.tahomasd.xgame.screens.*;

public class MainMenu extends XGameScreen {
	public static Texture titleTexture = null;
	public UIImage title = null;
	public UIButton startbutton = null;
	public UIButton levelsbutton = null;
	public UIButton creditsbutton = null;
	public UIButton closebutton = null;
	@Override
	public void Render() {
		super.Render();
	}

	@Override
	public void OnKeyDown(int KeyCode) {
		super.OnKeyDown(KeyCode);
	}

	@Override
	public void Load() {
		
		// Start button
		startbutton = new UIButton(XGameResources.start);
		startbutton.X = (XGameMain.RawWidth * 16 / 2) - (startbutton.width() / 2);
		startbutton.Y = 134;
		this.UIElements.add(startbutton);
		
		// Levels button
		levelsbutton = new UIButton(XGameResources.levels);
		levelsbutton.X = (XGameMain.RawWidth * 8) - (levelsbutton.width() / 2);
		levelsbutton.Y = 90;
		this.UIElements.add(levelsbutton);
		
		// Credits button
		creditsbutton = new UIButton(XGameResources.credits);
		creditsbutton.X = (XGameMain.RawWidth * 8) - (creditsbutton.width() / 2);
		creditsbutton.Y = 46;
		this.UIElements.add(creditsbutton);
		
		// Close button
		closebutton = new UIButton(XGameResources.close);
		closebutton.X = (XGameMain.RawWidth * 8) - (closebutton.width() / 2);
		closebutton.Y = 2;
		this.UIElements.add(closebutton);
		
		// Title image
		titleTexture = new Texture(new File("res/img/title.png").getAbsolutePath());
		title = new UIImage(titleTexture);
		title.X = (XGameMain.RawWidth * 16 / 2) - (titleTexture.width() / 2);
		title.Y = 170;
		title.Z = 160;
		this.UIElements.add(title);
		
	}

	@Override
	public void Dispose() {
		titleTexture.Dispose();
		titleTexture = null;
	}
	
	@Override
	public void OnButtonPressed(UIButton b)
	{
		if (b.equals(startbutton))
		{
			GameScreen.CurrentLevel = XGameResources.Levels.get(0);
			XGameCore.SetScreen(XGameCore.GameScreen);
		}
		else if (b.equals(levelsbutton))
		{
			
		}
		else if (b.equals(creditsbutton))
		{
			
		}
		else if (b.equals(closebutton))
		{
			glfwSetWindowShouldClose(XGameCore.window, GL_TRUE);
		}
	}
}
