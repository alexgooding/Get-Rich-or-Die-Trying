import static org.lwjgl.glfw.GLFW.*;


import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

public class Main {
	private Player player;
	
	public static void main(String[] args) {
		
		if(!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window = glfwCreateWindow(640, 480, "My CM50109 Game", 0, 0);
		if(window ==0) {
			throw new IllegalStateException("Faild to create window");
		}
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();	//Compatible for all versions
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor()) ;
		glfwSetWindowPos(window, (videoMode.width() -640) / 2, (videoMode.height() - 480) / 2);
		
		
			glfwShowWindow(window);
			

		
		while(! glfwWindowShouldClose(window)) {
			//glfwPollEvents();
			
			//glClear(GL_COLOR_BUFFER_BIT);	//Need it, Otherwise rendering on top of the scene for the previous
			
			
			//glfwSwapBuffers(window);	//Swaps the front and back buffers of the specified window
			// Clear the screen and depth buffer
			System.out.println("AAA");
			// clear the screen and depth buffer
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
			         
			// set the color of the quad (R,G,B,A)
			//glColor3f(0.5f,0.5f,1.0f);
			     
			// draw quad
			glBegin(GL_QUADS);
			    glVertex2f(100,100);
			    glVertex2f(100+200,100);
			    glVertex2f(100+200,100+200);
			    glVertex2f(100,100+200);
			glEnd();
		}

		glfwTerminate();
	}
	
}
