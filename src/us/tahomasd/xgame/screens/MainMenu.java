package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.XGameScreen;
import org.lwjgl.glfw.GLFW;

public class MainMenu extends XGameScreen {

	@Override
	public void Update() {
		// TODO: Auto-generated method stub

	}

	@Override
	public void Render() {
		// TODO: Auto-generated method stub

	}

	@Override
	public void OnKeyDown(int KeyCode) {
		// TODO Auto-generated method stub
		switch (KeyCode)
		{
			case GLFW.GLFW_KEY_SPACE:
				System.out.println("Space key pressed!");
				break;
		}
	}

}
