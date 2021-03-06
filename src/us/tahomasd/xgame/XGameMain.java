package us.tahomasd.xgame;

/* XGameMain
 *    The entry point for XGame
 *    
 *    Ben Garcia
 *    2015
 */
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Properties;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import us.tahomasd.xgame.XGameCore;
import us.tahomasd.xgame.Audio;

@SuppressWarnings("unused")
public class XGameMain {
	// We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    
    public static int RawWidth = 25;
    public static int WIDTH = RawWidth * XGameCore.TileSize;
    public static int RawHeight = 15;
    public static int HEIGHT = RawHeight * XGameCore.TileSize;
 
    public void run() { 
        try {
            init();
            loop();
            XGameCore.Dispose();
            // Release window and window callbacks
            glfwDestroyWindow(XGameCore.window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFW error callback
            glfwTerminate();
            errorCallback.release();
            //Audio.Dispose();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE); // the window will not stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // the window will not be resizable
 
        // Create the window
        XGameCore.window = glfwCreateWindow(WIDTH, HEIGHT, "XGame", NULL, NULL);
        if ( XGameCore.window == NULL ) 
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(XGameCore.window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            XGameCore.window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(XGameCore.window);
        // Enable v-sync
        glfwSwapInterval(1);
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        //
        // NOTE: We need to have this here before XGameCore.Load() so we have an OpenGL Context to create textures with (and stuff)
        GLContext.createFromCurrent();
        
        glfwSetInputMode(XGameCore.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        
        //Audio.MakeInitialized();
        
        // Load the game
        XGameCore.Load();
 
        // Make the window visible
        glfwShowWindow(XGameCore.window);
    }
 
    private void loop() {
 
        // Set the clear color
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
 
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        long lastnanos = System.currentTimeMillis();
        while ( glfwWindowShouldClose(XGameCore.window) == GL_FALSE ) {
        	//System.out.println("Current: " + System.currentTimeMillis() + " Last: " + lastnanos + " Difference: " + (System.nanoTime() - lastnanos));
        	if (System.currentTimeMillis() - lastnanos < 20)
        	{
        		//System.out.println("Skipped because over max FPS");
        		continue;
        	}
        	
        	XGameCore.Update();
        	
            XGameCore.Render();
 
            glfwSwapBuffers(XGameCore.window); // swap the color buffers
 
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents(); // NOTE: Keyboard polling doens't seem to be working yet - Ignore next note. -> NOTE: Let's try to implement input by polling the keyboard per frame, not by calbacks. This has always worked better for me.
            lastnanos = System.currentTimeMillis();
        }
    }
    
    public static void main(String[] args) {
    	boolean is64 = false;
    	if (System.getProperty("os.name").contains("Windows"))
		{
    		is64 = (System.getenv("ProgramFiles(x86)") != null);
		}
    	else
    	{
    		is64 = (System.getProperty("os.arch").indexOf("64") != -1);
    	}
    	switch (LWJGLUtil.getPlatform())
    	{
		case LINUX:
			System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/linux/" + (is64 ? "x64" : "x86")).getAbsolutePath());
			break;
		case MACOSX:
			System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/macosx/x64").getAbsolutePath());
			break;
		case WINDOWS:
			System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/windows/" + (is64 ? "x64": "x86")).getAbsolutePath());
			break;
    	}
    	
    	//System.setProperty("org.lwjgl.librarypath","xgame/lib/lwjgl/native/macosx/x64");
        new XGameMain().run();
    }
}
