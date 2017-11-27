
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

//version v1.0: Ray: Initial Create

public class MainMenu extends BasicGameState{

	public MainMenu(int startmenu) {
	}

	@Override
	//Starting point
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	//Rendering stuff
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException{ 
		graphics.drawString("Play", 375, 400);	//drawing play button
		graphics.drawString("Exit", 375, 500);	//drawing exit button
	}

	@Override
	//Continue update
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//Tracking mouse position
		int mousePosX = Mouse.getX();
		int mousePosY = Mouse.getY();
		
		Input input = gc.getInput();
		
		System.out.println("X: " + mousePosX + " Y: " + mousePosY);
		
		// After pressing play button-> go to state 1(Game)
		if((mousePosX > 375&& mousePosX < 410) && (mousePosY >185 && mousePosY < 198 )){	//Pointing to the button range
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		
		// After pressing exit button-> Exit
		if((mousePosX >375 && mousePosX <410) && (mousePosY > 85 && mousePosY <98)) {	//Pointing to the button range
			if(Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
		
		if(input.isKeyPressed(input.KEY_ENTER)) {
			sbg.enterState(1);
		}
	}

	@Override
	//return number of state
	public int getID() {	
		return 0;
	}
	
}
