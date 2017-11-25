public class Bot{

	/**
   * botLocation stores the co-ordinates of the bot and the room number the bot is in.
   */
	private Location botLocation = new Location();

	// =========================
  // Constructors
  // =========================

  /**
   * Default constructor - this initialises botLocation to (0, 0, 1).
   */
	public Bot(){
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
   * Mutator for instance variables - sets the bot parameter.
   *
   * @param botLocation - new location of the bot.
   */
  public void setBot(Location botLocation){
  	this.botLocation = botLocation;
  }

  /**
   * Accessor for botLocation. 
   *
   * @param  none.
   * @return The location of the bot.
   */   
	public Location getBotLocation(){
		return botLocation;
	}

}