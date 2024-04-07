/**
 * 
 */
package beeopoly;

/**
 * Abstract parent class for all board tiles in the Beeopoly game.
 */
public abstract class BoardTile {

	private String name;

	/**
	 * Constructor for BoardTile objects.
	 * 
	 * @param name - The name of the board tile.
	 */
	public BoardTile(String name) {
		// Don't think the super is needed
		super();
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
	 * @throws IllegalArgumentException - If the name is null.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Cannot be a null value");
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
