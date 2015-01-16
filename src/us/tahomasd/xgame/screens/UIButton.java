package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;

public class UIButton extends UIElement {
	public Texture NormalTexture;
	public Texture HoverTexture;
	public Texture PressedTexture;
	
	public UIButton(Texture all)
	{
		this.NormalTexture = all;
		this.HoverTexture = all;
		this.PressedTexture = all;
	}
	
	public UIButton(Texture Normal, Texture Hover, Texture Pressed)
	{
		this.NormalTexture = Normal;
		this.HoverTexture = Hover;
		this.PressedTexture = Pressed;
	}

	@Override
	public double width() {
		// TODO Auto-generated method stub
		return NormalTexture.width() * XGameCore.Scale();
	}

	@Override
	public double height() {
		// TODO Auto-generated method stub
		return NormalTexture.height() * XGameCore.Scale();
	}

	@Override
	public void Render() {
		// TODO Auto-generated method stub
		switch (state)
		{
		case Normal:
			NormalTexture.render(new Vector2d(this.X, this.Y), Z);
			break;
		case Hover:
			HoverTexture.render(new Vector2d(this.X, this.Y), Z);
			break;
		case Pressed:
			PressedTexture.render(new Vector2d(this.X, this.Y), Z);
			break;
		}
	}

}
