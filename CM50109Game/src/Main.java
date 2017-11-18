import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/*version:
v0.1: Set up windows with a dungeon quad
v0.2: Draw a quad(player), quad can move with button up,down,right,left
*/
public class Main {

	// The window handle
	private long window;

	public void run() {
		System.out.println("Game is running on version " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(800, 600, "Julian Dungeon", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// init OpenGL
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, 800, 0, 600, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
	    float x=0;	//v0.2	Coordinate x_offset
	    float y=0;	//v0.2	Coordinate y_offset
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			
			// Set the clear color
		    GL11.glColor3f(0.4f,0.4f,0.4f);
			
			// draw quad
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex2f(0,0); // top left
	        GL11.glVertex2f(800,0); // top right
	        GL11.glVertex2f(800,600); // bottom right
	        GL11.glVertex2f(0,600); // bottom left
	        GL11.glEnd();
	    	//glfwSwapBuffers(window); // swap the color buffers
			
			//v0.2	Implementation of key input
			if(glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE) {
				System.out.println(y);
				y += 1f;
			}
			if(glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE) {
				System.out.println(y);
				y -= 1f;
			}
			if(glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE) {
				System.out.println(x);
				x -= 1f;
			}
			if(glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE) {
				System.out.println(x);
				x += 1f;
			}
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the frame buffer
			
			//v0.2 draw a quad player -> movement according to the key input
			// Set the clear color
		    GL11.glColor3f(1f,1f,1f);
			
			// draw quad
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex2f(250+x,250+y); // top left
	        GL11.glVertex2f(300+x,250+y); // top right
	        GL11.glVertex2f(300+x,300+y); // bottom right
	        GL11.glVertex2f(250+x,300+y); // bottom left
	        GL11.glEnd();
			glfwSwapBuffers(window); // swap the color buffers
			/*
			Player p = new Player(50,50);
			p.drawPlayer();
			glfwSwapBuffers(window); // swap the color buffers*/
		}
		
		
	}

	public static void main(String[] args) {
		new Main().run();
		
	}

}