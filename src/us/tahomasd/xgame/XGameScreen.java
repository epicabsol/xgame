package us.tahomasd.xgame;

public abstract class XGameScreen {
	public abstract void Update();
	public abstract void Render();
	public abstract void OnKeyDown(int KeyCode);
	public abstract void Load();
	public abstract void Dispose();
}
