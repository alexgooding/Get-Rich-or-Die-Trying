package items;
import dungeon.Location;

public class Item {

	/**
	* itemLocation stores the coordinates of the item and the room number the item is in.
	*/
	protected Location itemLocation = new Location();

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - this initialises itemLocation to (0, 0, 1).
	*/
	public Item() {
		Location defaultLocation = new Location(0, 0 ,1);
		setItem(defaultLocation);
	}

	/**
	* One-parameter version of the constructor.
	* Initialiases itemLocation to a location, which is supplied to the function.
	*
	* @param itemLocation - the location of the door.
	*/
	public Item(Location itemLocation) {
		setItem(itemLocation);
	}

	// =========================
	// Mutators and Accessors
	// =========================
    
	/**
	* Mutator for instance variables - sets the item parameter.
	*
	* @param doorLocation - new location of the item.
	*/
	public void setItem(Location itemLocation) {
	this.itemLocation = itemLocation;
	}

	/**
	* Accessor for itemLocation. 
	*
	* @param  none.
	* @return The location of the item.
	*/   
	public Location getItemLocation() {
		return itemLocation;
	}

}