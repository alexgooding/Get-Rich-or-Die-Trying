package items;
import dungeon.Location;

public class Gold extends Item {
	
	/**
	* The amount of gold stored and where it is on the map.
	*/
	private int amount;
	
	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - Initialises goldAmount to 1 and itemLocation is inherited from Item.
	*/
	public Gold() {	
		super();
		setAmount(1);
	}

	/**
	* Two-parameter version of the constructor. 
	* Initialiases goldLocation and goldAmount to values, 
	* which are supplied to the function.
	*
	* @param goldLocation - The location of the gold.
	* @param amount - The amount of gold stored.
	*/
	public Gold(Location goldLocation, int amount) {
		super(goldLocation);
		setAmount(amount);
	}

	// =========================
	// Mutators and Accessors
	// =========================
    
 	/**
	* Mutator for instance variables - Sets the room parameters.
	*  
	* @param amount - New amount of gold stored.
	*/
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	* Accessor for goldAmount. 
	*
	* @param  none.
	* @return The amount of gold.
	*/   
	public int getGoldAmount() {
		return amount;
	}

}