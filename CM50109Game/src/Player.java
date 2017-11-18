

import static org.lwjgl.opengl.GL11.*;
 
import java.awt.Color;
 
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.util.*;
import org.joml.Vector2f;

import org.lwjgl.glfw.*;
 
public class Player {
     
    public static float x,y;
    public static float vel=0.96f;
    private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private int health;	// Health of the player
	private int numOfGold;	// Num of gold the player collected
	private float speed;	// Moving speed of the player
	private Vector2f location;
     
    public Player(float x, float y){
         
        this.x=x;
        this.y=y;
        glViewport((int)x, (int)y, 800, 600);
    }
    
    public void glfwkeyCallback(long window, int key, int scancode, int action, int mods) {
    	switch (key) {
    	case GLFW_KEY_UP:
    		moveUp = (action != GLFW_RELEASE);
    		y++;
    		
    		break;
    	case GLFW_KEY_DOWN:
    		moveUp = (action != GLFW_RELEASE);
    		y--;
    		break;
    	case GLFW_KEY_LEFT:
    		moveUp = (action != GLFW_RELEASE);
    		x--;
    		break;
    	case GLFW_KEY_RIGHT:
    		moveUp = (action != GLFW_RELEASE);
    		x++;
    		break;
    	}
    }
    
   /*public void pickUpGold(Items gold) {
    	// Collision with the gold then auto pick up
    	if(Items.collide()) {
    		numOfGold++;
    	}
    }*/
    
     
    public void playerMovement(){
    	
        drawPlayer();      
        
       /* while(glfwGetKey().next()){
             
            if (Keyboard.getEventKeyState()){
                if(Keyboard.isKeyDown(Keyboard.KEY_D)){                 
                    x++;
                     
                 
                 
                }
             
         
            }
             
        }      */
    }
     
    public static void drawPlayer(){
         
        glBegin(GL_QUADS); //Begins drawing the quad
        glColor3f(0.5f,0.4f,0.3f); //Paints the quad
        glVertex2f(x, y);//Top left corner
        glVertex2f(x + 45,y);//Top right corner
        glVertex2f(x + 45, y+ 45);//Bottom right corner
        glVertex2f(x, y+45);//Bottom left corner
        glEnd();// Ends quad drawing
    }
}
