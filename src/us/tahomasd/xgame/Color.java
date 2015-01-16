package us.tahomasd.xgame;

public class Color {
	public int A = 0;
	public int R = 0;
	public int G = 0;
	public int B = 0;
	
	public Color(int R, int G, int B)
	{
		this.A = 255;
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public Color(int R, int G, int B, int A)
	{
		this.A = A;
		this.R = R;
		this.G = G;
		this.B = B;
	}
}
