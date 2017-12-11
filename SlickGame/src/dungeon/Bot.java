package dungeon;
public class Bot {

	/**
	* botLocation stores the coordinates of the bot and the room number the bot is in.
	*/
	private Location botLocation = new Location();

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - Initialises botLocation to (0, 0, 1).
	*/
	public Bot() {
		Location defaultLocation = new Location(0, 0 ,1);
		setBot(defaultLocation);
	}

	/**
	* One-parameter version of the constructor. Initialiases botLocation 
	* to the value supplied to the function.
	*
	* @param botLocation - the location of the bot.
	*/
	public Bot(Location botLocation){
		setBot(botLocation);
	}

	// =========================
	// Mutators and Accessors
	// =========================

	/**
	* Mutator for instance variables - Sets the bot parameter.
	*
	* @param botLocation - new location of the bot.
	*/
	public void setBot(Location botLocation) {
		this.botLocation = botLocation;
	}

	/**
	* Accessor for botLocation. 
	*
	* @param  none.
	* @return The location of the bot.
	*/   
	public Location getBotLocation( ){
		return botLocation;
	}

	// =========================
	// Additional Methods
	// =========================

	/**
	* updates the bots location dependent on direction of movement. 
	*
	* @param c - the direction the bot moves in.
	* @return void.
	*/
	public void updateBotLocation(char c) {
		if(c == 'n'){
			botLocation.setY(botLocation.getY()+1);
		}
		else if(c == 'e'){
			botLocation.setX(botLocation.getX()+1);
		}
		else if(c == 's'){
			botLocation.setY(botLocation.getY()-1);
		}
		else if(c == 'w'){
			botLocation.setX(botLocation.getX()-1);
		}
		else{
			return;
		}
	}

}