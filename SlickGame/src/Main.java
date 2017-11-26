
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


//version v1.0: Ray: Initial Create

public class Main extends StateBasedGame{
	
	public static final String gameName = "Dungeon Game";
	public static final int startMenu = 0;	//Game state level (Like page number)
	public static final int map = 1;
	
	public Main(String gameName) {
		super(gameName);
		this.addState(new MainMenu(startMenu));
		this.addState(new Map(map));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(startMenu).init(gc, this);
		this.getState(map).init(gc, this);
		this.enterState(startMenu);	//Starting page / screen
		
	}
	
	public static void main(String[] args) {
		AppGameContainer appGame;
		try {
			appGame = new AppGameContainer(new Main(gameName));
			appGame.setDisplayMode(800, 600, false);	//Windows resolution
			appGame.start();
		}catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
