import org.lwjgl.*;

public enum GameState {
	Game, MainMenu;
	private static GameState state = GameState.MainMenu;
	switch (state) {
	case Game:
		glColor3f(1.0f, 1.0f, 1.0f);
		glRectf(0, 0, 800, 600);
		break;
	case MainMenu:
		glColor3f(0.0f, 0.0f, 0.0f);
		glRectf(0, 0, 800, 600);
		break;
	}
}
