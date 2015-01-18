package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;

public class UIImage extends UIElement {
	public Texture Texture = null;
	public UIImage(Texture tex)
	{
		this.Texture = tex;
	}
	
	@Override
	public double width() {
		return Texture.width();
	}

	@Override
	public double height() {
		return Texture.height();
	}
	
	@Override
	public void Render()
	{
		Texture.render(new Vector2d(this.X, this.Y), this.Z);
	}
}
