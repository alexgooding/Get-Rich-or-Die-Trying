
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


//version v1.0: Ray: Initial Create

public class Main extends StateBasedGame{
	public static final int winWidth = 1080;
    public static final int winHeight = 720;
    public static final int halfWidth = winWidth / 2;
    public static final int halfHeight = winHeight / 2;
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
			appGame.setDisplayMode(winWidth, winHeight, false);	//Windows resolution
			appGame.start();
		}catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
