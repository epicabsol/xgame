package us.tahomasd.xgame;

import java.util.ArrayList;
import us.tahomasd.xgame.screens.*;

public abstract class XGameScreen {
	public void Update()
	{
		// TODO: Process mouse movement and call UIElement methods accordingly.
		for (UIElement e : UIElements)
		{
			e.Update();
		}
	}
	public void Render()
	{
		for (UIElement e : UIElements)
		{
			e.Render();
		}
	}
	public abstract void OnKeyDown(int KeyCode);
	public abstract void Load();
	public abstract void Dispose();
	public ArrayList<UIElement> UIElements = new ArrayList<UIElement>();
}
