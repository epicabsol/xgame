package us.tahomasd.xgame;

public class Vector2d {
	public double X = 0.0;
	public double Y = 0.0;
	public Vector2d() { }
	public Vector2d(double X, double Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	public double length()
	{
		return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
	}
	
	public void normalize()
	{
		double l = length(); // NOTE: Is the memory usage and garbage collection worth caching the length?
		X = X / l;
		Y = Y / l;
	}
	
	public Vector2d add(Vector2d other)
	{
		return new Vector2d(X + other.X, Y + other.Y);
	}
	
	public Vector2d subtract(Vector2d other)
	{
		return new Vector2d(X - other.X, Y - other.Y);
	}
	
	public Vector2d multiply(Vector2d other)
	{
		return new Vector2d(X * other.X, Y * other.Y);
	}
	
	public Vector2d multiply(double scalar)
	{
		return new Vector2d(X * scalar, Y * scalar);
	}
	
	public Vector2d divide(Vector2d other)
	{
		return new Vector2d(X / other.X, Y / other.Y);
	}
	
	public Vector2d divide(double scalar)
	{
		return new Vector2d(X / scalar, Y / scalar);
	}
}
