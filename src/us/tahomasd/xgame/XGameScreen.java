package us.tahomasd.xgame;

import java.util.ArrayList;

import us.tahomasd.xgame.screens.*;

import org.lwjgl.glfw.GLFW;

public abstract class XGameScreen {
	public boolean MouseDown = false;
	public UIElement FocusedElement = null;
	public void FocusElement(UIElement e)
	{
		if (FocusedElement != null)
		{
			FocusedElement.Focused = false;
		}
		FocusedElement = e;
		FocusedElement.Focused = true;
	}
	public void Update()
	{
		Vector2d m = XGameCore.MousePos();
		
		boolean b = false; // To track if the mouse was over an element
		// Mouse movement
		for (UIElement e : UIElements)
		{
			if (e.bounds().containsPoint(new Vector2d(m.X ,m.Y)))
			{
				if (!e.MouseIn)
				{
					e.MouseIn = true;
					e.OnMouseEnter();
					FocusElement(e);
				}
				b = true;
			}
			else
			{
				if (e.MouseIn)
				{
					e.MouseIn = false;
					e.OnMouseLeave();
				}
			}
		}
		if (!b)
		{ // No element was hovered over, so clear the hover.
			if (FocusedElement != null)
			{
				FocusedElement.Focused = false;
			}
			FocusedElement = null;
		}
		
		// Mouse button up/down
		boolean MouseDown = GLFW.glfwGetMouseButton(XGameCore.window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
		if (MouseDown == true && this.MouseDown == false)
		{ // The mouse has just been pressed
			for (UIElement e : UIElements)
			{
				if (e.bounds().containsPoint(new Vector2d(m.X, m.Y)))
				{ // If the mouse is clicking on 'e'
					if (e.MouseDown == false)
					{ // If the mouse isn't already down on 'e' (Should never be, but might as well check)
						e.MouseDown = true;
						e.OnMouseDown();
					}
				}
			}
		}
		else if (MouseDown == false && this.MouseDown == true)
		{ // The mouse has just been released
			for (UIElement e : UIElements)
			{
				if (e.MouseDown == true)
				{ // If the mouse isn't already up on 'e'
					e.OnMouseUp();
					if (e instanceof UIButton)
					{
						OnButtonPressed((UIButton) e);
					}
				}
				e.MouseDown = false;
			}
		}
		this.MouseDown = MouseDown;
		
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
	public void OnKeyDown(int KeyCode)
	{
		if (FocusedElement != null)
		{
			switch (KeyCode)
			{
			case GLFW.GLFW_KEY_LEFT:
				FocusElement(FocusedElement.Left);
				break;
			case GLFW.GLFW_KEY_RIGHT:
				FocusElement(FocusedElement.Right);
				break;
			case GLFW.GLFW_KEY_UP:
				FocusElement(FocusedElement.Above);
				break;
			case GLFW.GLFW_KEY_DOWN:
				FocusElement(FocusedElement.Below);
				break;
			case GLFW.GLFW_KEY_SPACE:
				if (FocusedElement instanceof UIButton)
				{
					OnButtonPressed((UIButton) FocusedElement);
				}
				break;
			}
		}		
	}

	public abstract void Load();
	public abstract void Dispose();
	public ArrayList<UIElement> UIElements = new ArrayList<UIElement>();
	public void OnButtonPressed(UIButton b) { }
}
