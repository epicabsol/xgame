package us.tahomasd.xgame.screens;

import java.io.File;

import us.tahomasd.xgame.*;

import org.lwjgl.glfw.GLFW;

public class MainMenu extends XGameScreen {
	public static Texture title = null;
	@Override
	public void Update() {

	}

	@Override
	public void Render() {
		title.render(new Vector2d(0, 0), 0, new Vector2d(426, 144), new Color(255, 255, 255, 255), 0.0, new Vector2d(0,0));
	}

	@Override
	public void OnKeyDown(int KeyCode) {
		switch (KeyCode)
		{
			case GLFW.GLFW_KEY_SPACE:
				System.out.println("Space key pressed!");
				break;
		}
	}

	@Override
	public void Load() {
		title = new Texture(new File("res/img/title.png").getAbsolutePath());
	}

	@Override
	public void Dispose() {
		title.Dispose();
		title = null;
	}

}
