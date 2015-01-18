package us.tahomasd.xgame.screens;


import us.tahomasd.xgame.*;

public abstract class UIElement {
	public double X;
	public double Y;
	public double Z = 50;
	public int MouseX = 0;
	public int MouseY = 0;
	public boolean Focused = false;
	public boolean MouseIn = false;
	public boolean MouseDown = false;
	public UIElement Above = this;
	public UIElement Below = this;
	public UIElement Left = this;
	public UIElement Right = this;
	public abstract double width();
	public abstract double height();
	public Vector2d size()
	{
		return new Vector2d(width(), height());
	}
	public Rectangle bounds()
	{
		return new Rectangle(X, Y, width(), height());
	}
	public void Update() { }
	public void Render()
	{
		// TODO: Draw a focus rectangle if the UIElement is focused.
	}
	public enum State
	{
		Normal, Hover, Pressed
	}
	public State state = State.Normal;
	public void OnMouseDown() { }
	public void OnMouseUp() { }
	public void OnMouseEnter() { }
	public void OnMouseLeave() { }
}
