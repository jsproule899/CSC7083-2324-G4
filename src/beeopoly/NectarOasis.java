package beeopoly;

/**
 * Class to represent the Nectar Oasis tile on the Beeopoly game board.
 *
 */
public class NectarOasis extends BoardTile {

	/**
	 * Constructor for the Nectar Oasis object.
	 * 
	 * @param name - The name of the Nectar Oasis tile.
	 */
	public NectarOasis(String name) {
		super(name);
	}

	/**
	 * Overrides the landOn method to display a Nectar Oasis specific message when a
	 * player lands on the Nectar Oasis tile.
	 *  
	 * @param player - The player landing on the Nectar Oasis tile.
	 */
	@Override
	public void landOn(Player player) {
		System.out.println("Welcome to the "+ this.getName() + " your Bees rest in the nectar until your next turn");
	}
}


