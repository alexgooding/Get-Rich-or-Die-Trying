public class Gold extends Item{
	
	/**
	 * The amount of gold stored and where it is on the map.
	 */
	private int goldAmount;
	// =========================
	// Constructors
	// =========================

	/**
	 * Default constructor - this initialises goldAmount to 1 and itemLocation is inherited from Item.
	 */
	public Gold(){	
		super();
		setGoldAmount(1);
	}

	/**
	 * Two-parameter version of the constructor. Initialiases goldLocation and goldAmount 
	 * to values, which are supplied to the function.
	 *
	 * @param goldLocation - the location of the gold.
	 * @param goldAmount - the amount of gold stored.
	 */
	public Gold(Location goldLocation, int goldAmount){
		super(goldLocation);
		setGoldAmount(goldAmount);
	}

	// =========================
	// Mutators and Accessors
	// =========================
    
	/*	
	 * Mutator for instance variables - sets the room parameters.
	 *  
	 * @param goldAmount   - new amount of gold stored.
	 */
  	public void setGoldAmount(int goldAmount){
  		this.goldAmount = goldAmount;
  	}

	/**
     * Accessor for goldAmount. 
     *
     * @param  none.
     * @return The amount of gold.
     */   
	public int getGoldAmount(){
		return goldAmount;
	}

}