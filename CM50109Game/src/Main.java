import org.lwjgl.*;
import org.lwjgl.util.input.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
 
public class Main {
	private long window;
	private static GameState state = GameState.MAINMENU;
	public Input input;
	
	private static enum GameState {
		GAME, MAINMENU;
	}
	
	public void run() {
		init();
		update();
		render();

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
		window = glfwCreateWindow(800, 600, "Grey Dungeon", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, new Input());


			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - 800) / 2,
				(vidmode.height() - 600) / 2
			);
		} // the stack frame is popped automatically
		
	// Make OpenGL Current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}
	
	
	private void update() {
		glfwPollEvents();
		if (Input.keys[GLFW_KEY_UP]) {
	
		}
	}
	
	private void render() {
		switch(state) {
		case GAME:
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			GL11.glRectf(0, 0, 800, 600);
			break;
		case MAINMENU:
			GL11.glColor3f(0.0f, 0.0f, 0.0f);
			GL11.glRectf(0, 0, 800, 600);
			break;
		}
	}
	
	public static void main(String[] args) {
		new Main().run();
	}
}