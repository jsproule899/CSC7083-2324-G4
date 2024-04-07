package beeopoly;

/**
 * Class to represent the Honey Haven tile on the Beeopoly game board.
 *
 */
public class HoneyHaven extends BoardTile {

	/**
	 * Constructor for the Honey Haven object.
	 * 
	 * @param name - The name of the Honey Haven tile.
	 */
	public HoneyHaven(String name) {
		super(name);
	}

	/**
	 * Method to add 50 honey jars to the resources of a player that passes the
	 * Honey Haven tile on their turn.
	 * 
	 * @param player - The player passing through the Honey Haven tile.
	 */
	public static void passHoneyHaven(Player player) {
		System.out.println("Your Bees have flown through the Honey Haven and collected an extra 50 Honey Jars");
		player.updateHoney(+50);
		player.showHoney();
		System.out.println();
	}
}
