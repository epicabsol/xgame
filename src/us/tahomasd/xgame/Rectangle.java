package us.tahomasd.xgame;

import org.lwjgl.opengl.GL11;

public class Rectangle {
	public Vector2d topLeft = new Vector2d(0, 0);
	public Vector2d size = new Vector2d(0, 0);
	
	public Rectangle() { }
	public Rectangle(Vector2d topLeft, Vector2d size)
	{
		this.topLeft = topLeft;
		this.size = size;
	}
	public Rectangle(double X, double Y, double Width, double Height)
	{
		setX(X); setY(Y); setWidth(Width); setHeight(Height);
	}
	
	public double getY()
	{
		return topLeft.Y;
	}
	
	public void setY(double Y)
	{
		topLeft.Y = Y;
	}
	
	public double getX()
	{
		return topLeft.X;
	}
	
	public void setX(double X)
	{
		topLeft.X = X;
	}
	
	public double getWidth()
	{
		return Math.abs(size.X);
	}
	
	public void setWidth(double Width)
	{
		size.X = Math.abs(Width);
	}
	
	public double getHeight()
	{
		return Math.abs(size.Y);
	}
	
	public void setHeight(double Height)
	{
		size.Y = Math.abs(Height);
	}
	
	public boolean containsPoint(Vector2d p)
	{
		return (p.X < getX() + getWidth() && p.X > getX() && p.Y > getY() && p.Y < getY() + getHeight());
	}
	
	public void render(Color color, double Z)
	{
		// Position everything correctly
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				GL11.glTranslated(getX() * XGameCore.Scale(), getY() * XGameCore.Scale(), Z);
				GL11.glScaled(XGameCore.Scale(), XGameCore.Scale(), XGameCore.Scale());
				//GL11.glRotated(rotation, rp.X, rp.Y, 0); No rotation here
				
				// Clear any bound texture
				Texture.unbindAll();
				
				// Draw the quad
				GL11.glBegin(GL11.GL_LINE_LOOP);
				//GL11.glColor4i(color.R , color.G, color.B ,  color.A );
				GL11.glColor3d((double) color.R / 255, (double) color.G / 255, (double) color.B / 255);
				
				// Quad 1
				GL11.glVertex2d(0,0);
				GL11.glVertex2d(0,size.Y);
				GL11.glVertex2d(size.X, size.Y);
				GL11.glVertex2d(size.X, 0);
				
				GL11.glEnd();
	}
}
