package us.tahomasd.xgame;

import java.io.*;
import java.nio.*;
import java.io.FileInputStream;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {
	private int _width = 0;
	public int width()
	{
		return _width;
	}
	
	private int _height = 0;
	public int height()
	{
		return _height;
	}
	
	private int _handle = 0;
	public int handle()
	{
		return _handle;
	}
	
	public Texture(String path)
	{
		ByteBuffer buf = null;

		try {
		    // Open the PNG file as an InputStream
		    InputStream in = new FileInputStream(path);
		    // Link the PNG decoder to this stream
		    PNGDecoder decoder = new PNGDecoder(in);
		     
		    // Get the width and height of the texture
		    _width = decoder.getWidth();
		    _height = decoder.getHeight();
		     
		     
		    // Decode the PNG file in a ByteBuffer
		    buf = ByteBuffer.allocateDirect(
		            4 * decoder.getWidth() * decoder.getHeight());
		    decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		    buf.flip();
		     
		    in.close();
		    
		    // Generate the blank texture
		    _handle = GL11.glGenTextures();
		    bind();
		    
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		    
		    // Put the texture in the texture that we just bound
		    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		} catch (IOException e) {
			System.out.println("ERROR: Could not load file \"" + path + "\"");
		    e.printStackTrace();
		    XGameCore.msgBox("Could not load texture from \"" + path + "\"", "Texture.new(String)");
		    System.exit(-1);
		}
	}
	
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, handle());
	}
	
	public void render(Vector2d pos, double Z)
	{
		render(pos, Z, new Vector2d(this.width(), this.height()), new Color(255, 255, 255), 0, new Vector2d(0, 0));
	}
	
	public void render(Vector2d pos, double Z, Vector2d size, Color color, double rotation, Vector2d rp)
	{
		// Position everything correctly
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(pos.X, pos.Y, Z);
		GL11.glRotated(rotation, rp.X, rp.Y, 0);
		
		// Bind the texture
		bind();
		
		// Draw the quad
		GL11.glBegin(GL11.GL_QUADS);
		//GL11.glColor4i(color.R , color.G, color.B ,  color.A );
		GL11.glColor3d((double) color.R / 255, (double) color.G / 255, (double) color.B / 255);
		
		// Quad 1
		GL11.glTexCoord2d(0,1);
		GL11.glVertex2d(0,0);
		GL11.glTexCoord2d(0,0);
		GL11.glVertex2d(0,size.Y);
		GL11.glTexCoord2d(1,0);
		GL11.glVertex2d(size.X, size.Y);
		GL11.glTexCoord2d(1,1);
		GL11.glVertex2d(size.X, 0);
		GL11.glEnd();
		
		// Clear the bound texture
		unbindAll();
	}
	
	public void Dispose()
	{
		GL11.glDeleteTextures(handle());
	}
	
	public static void unbindAll()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
