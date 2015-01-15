package us.tahomasd.xgame;

public class Color {
	public byte A = 0;
	public byte R = 0;
	public byte G = 0;
	public byte B = 0;
	
	public Color(byte R, byte G, byte B)
	{
		this.A = 0;
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public Color(byte R, byte G, byte B, byte A)
	{
		this.A = A;
		this.R = R;
		this.G = G;
		this.B = B;
	}
}
