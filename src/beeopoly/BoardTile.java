/**
 * 
 */
package beeopoly;

/**
 * Abstract parent class for all board tiles in the Beeopoly game.
 */
public abstract class BoardTile {

	/**
	 * Constants for the minimum and maximum number of characters in a board tile
	 * name.
	 */
	public static final int MIN_NAME = 3;
	public static final int MAX_NAME = 35;

	private String name;

	/**
	 * Constructor for BoardTile objects.
	 * 
	 * @param name - The name of the board tile.
	 */
	public BoardTile(String name) {
		this.setName(name);
	}

	/**
	 * Method to get the name of the board tile.
	 * 
	 * @return - The name of the board tile.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the name of the board tile.
	 * 
	 * @param name - The name of the board tile to set.
	 * @throws IllegalArgumentException - If the name is null, less than 3 or
	 *                                  greater than 35.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Buzz off! Your Beekeeper name cannot be null");
		} else if (name.length() >= MIN_NAME && name.length() <= MAX_NAME) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Buzz off! Your Beekeeper name must be 3-35 chars long");
		}

	}

	/**
	 * Method to display board tile landed on by player.
	 * 
	 * @param player - The player who landed on the board tile.
	 */
	public void landOn(Player player) {
		System.out.println(player.getName() + ", You've landed on " + name);
	}

}
