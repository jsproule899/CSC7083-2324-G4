package beeopoly;

import java.util.List;

/**
 * Class to represent a garden tile on the Beeopoly game board.
 */
public class Garden extends BoardTile {

	/**
	 * Constants for the minimum and maximum number of Hives and Apiaries that can
	 * be owned on a garden tile.
	 */
	public static final int MIN_Value = 0;
	public static final int MAX_HIVES = 3;
	public static final int MAX_APIARY = 1;

	private Field field;
	private int tileCost;
	private int rent;
	private int hives = 0;
	private int apiary = 0;
	private Player owner = null;

	/**
	 * Constructor for garden objects.
	 * 
	 * @param name     - The name of the garden tile.
	 * @param field    - The field the garden tile belongs to.
	 * @param tileCost - The cost of the garden tile.
	 * @param rent     - The rent of the garden tile.
	 */
	public Garden(String name, Field field, int tileCost, int rent) {
		super(name);
		this.setField(field);
		this.setTileCost(tileCost);
		this.setRent(rent);
		this.getHives();
	}

	/**
	 * Method to handle actions when a player lands on the garden tile.
	 * 
	 * @param player - The player who lands on the garden tile.
	 */
	@Override
	public void landOn(Player player) {
		System.out.printf("You've landed on %s (%s).%n", this.getName(), this.getField().getName());
		Player owner = this.getOwner();

		// If garden tile is owned by another player, pay required rent
		if (owner != null) {
			if (owner != player) {
				System.out.printf("The owner of this garden tile is %s.%n", this.getOwner().getName());
				this.payRent(player);
			} else {
				System.out.printf("You already own this garden tile and your swarm is buzzing to be back! ");
			}
		} else {
			System.out.printf("This garden tile isn't owned by anyone.%n");
			// If garden tile is not owned, offer to player to purchase
			System.out.println(
					"Do you want to trade " + this.getTileCost() + " honey jars to colonise this garden tile? [Y/N]");

			if (Player.getPlayerDecision()) {
				this.purchase(player);
			} else {
				// If player declines to purchase garden tile, offer to other players to
				// purchase
				this.offerToOtherPlayers(player);
			}
		}
	}

	/**
	 * Method to allow a player to purchase a garden tile.
	 * 
	 * @param player - The player who purchases the garden tile.
	 */
	public void purchase(Player player) {
		// Check if player has sufficient honey jars for the transaction
		if (player.getHoney() < this.getTileCost()) {
			System.out.println("Sorry you don't have enough honey jars to colonise this garden tile.... ");
			// If insufficient honey jars, offer to other players to purchase garden tile
			this.offerToOtherPlayers(player);
		} else {
			// If player has sufficient honey jars, complete transaction
			this.setOwner(player);
			System.out.println("The new owner of " + this.getName() + " is " + player.getName());
			player.updateHoney(-this.getTileCost());
			player.showHoney();
		}
	}

	/**
	 * Method to offer garden tiles for purchase to other players in the game if the
	 * player that landed on the garden tile declines to purchase it.
	 * 
	 * @param player - The player that landed on the garden tile who declined the purchase.
	 * 
	 */
	public void offerToOtherPlayers(Player player) {
		Player currentPlayer = player;

		// Display that the garden tile will be offered to other players to purchase
		System.out.println(this.getName() + " will now be offered to all other Beekeepers");

		// Get the list of active players from the BoardGame class
		List<Player> activePlayers = BoardGame.getActivePlayers();

		for (Player otherPlayer : activePlayers) {
			if (otherPlayer != currentPlayer) {
				System.out.println(otherPlayer.getName() + ", do you want to trade " + this.getTileCost()
						+ " honey jars to colonise " + this.getName() + "? [Y/N]");

				if (Player.getPlayerDecision()) {
					if (otherPlayer.getHoney() >= this.getTileCost()) {
						this.purchase(otherPlayer);
						return;// Exit the method after selling
					} else {
						System.out.println("Sorry you don't have enough honey jars to colonise this garden tile.... ");
					}
				}
			}
		}
		System.out.println("No other Beekeeper wants to purchase " + this.getName() + ". The game continues.");

	}

	/**
	 * Method to get the number of Hives that have been developed on the garden
	 * tile.
	 * 
	 * @return - The number of Hives that has been developed on the garden tile.
	 */
	public int getHives() {
		return hives;
	}

	/**
	 * Method to set the number of Hives that have been developed on the garden
	 * tile.
	 * 
	 * @param hives - The number of Hives to set.
	 * @throws IllegalArgumentException - If number of Hives to be set is outside
	 *                                  the rules of the game.
	 */
	public void setHives(int hives) throws IllegalArgumentException {
		// Check if the number of Hives to set is within the rules of the game
		if (hives <= MAX_HIVES && hives >= MIN_Value) {
			this.hives = hives;
		} else if (hives < MIN_Value) {
			throw new IllegalArgumentException("Buzz off! Hives cannot be set to less than 0");
		} else {
			throw new IllegalArgumentException("Buzz off! The maximum Hives a garden tile can have is " + MAX_HIVES);
		}
	}

	/**
	 * Method to increase the number of Hives on the garden tile by one, provided
	 * the maximum number of Hives has not been exceeded.
	 * 
	 * @throws IllegalArgumentException - If the maximum number of Hives has been
	 *                                  reached.
	 */
	public void buildHive() throws IllegalArgumentException {
		// Check that the maximum number of Hives has not been exceeded
		if (this.hives < MAX_HIVES) {
			this.hives += 1;
		} else {
			throw new IllegalArgumentException("Buzz off! The maximum Hives a garden tile can have is " + MAX_HIVES);
		}
	}

	/**
	 * Method to increase the number of Apiaries on the garden tile by one, provided
	 * the maximum number of Apiaries has not been exceeded.
	 * 
	 * @throws IllegalArgumentException - If the maximum number of Apiaries has been
	 *                                  reached.
	 */
	public void buildApiary() throws IllegalArgumentException {
		// Check that the maximum number of Apiaries has not been exceeded
		if (this.apiary < MAX_APIARY) {
			this.apiary += 1;
		} else {
			throw new IllegalArgumentException("Buzz off! The maximum Apiaries a garden tile can have is " + MAX_APIARY);
		}
	}

	/**
	 * Method to get the number of Apiaries that have been developed on the garden
	 * tile.
	 * 
	 * @return - The number of Apiaries that has been developed on the garden tile.
	 */
	public int getApiary() {
		return apiary;
	}

	/**
	 * Method to set the number of Apiaries that have been developed on the garden
	 * tile.
	 * 
	 * @param apiary - The number of Apiaries to set.
	 * @throws IllegalArgumentException - If number of Apiaries to be set is outside
	 *                                  the rules of the game.
	 */
	public void setApiary(int apiary) throws IllegalArgumentException {
		// Check if the number of Apiaries to set is within the rules of the game
		if (apiary <= MAX_APIARY && apiary >= MIN_Value) {
			this.apiary = apiary;
		} else if (apiary < MIN_Value) {
			throw new IllegalArgumentException("Buzz off! Apiaries canot be set to less than 0");
		} else {
			throw new IllegalArgumentException("Buzz off! The maximum Apiaries a garden tile can have is " + MAX_APIARY);
		}
	}

	/**
	 * Method to get the name of the player that owns the garden tile.
	 * 
	 * @return - The name of the player that owns the garden tile.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Method to set the name of the player that owns the garden tile.
	 * 
	 * @param owner - The name to set of the player that owns the garden tile.
	 */
	public void setOwner(Player owner){
			this.owner = owner;

	
	}

	/**
	 * Method to get the field that the garden tile belongs to.
	 * 
	 * @return - The name of the field that the garden tile belongs to.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Method to set the field that the garden tile belongs to.
	 * 
	 * @param field - The name to set of the field that the garden tile belongs to.
	 * @throws IllegalArgumentException - If name of the field is null.
	 */
	public void setField(Field field) {
		// Check name for field is not null
		if (field != null) {
			this.field = field;
		} else {
			throw new IllegalArgumentException("Buzz off! Field cannot be a null value");
		}

	}

	/**
	 * Method to get the cost of purchasing the garden tile.
	 * 
	 * @return - The cost of purchasing the garden tile.
	 */
	public int getTileCost() {
		return tileCost;
	}

	/**
	 * Method to set the cost of purchasing the garden tile.
	 * 
	 * @param tileCost - The cost of purchasing the garden tile to set.
	 * @throws IllegalArgumentException - If the cost of purchasing the garden tile
	 *                                  to set is less than zero.
	 */
	public void setTileCost(int tileCost) throws IllegalArgumentException {
		// Check tileCost is not less than minimum value
		if (tileCost >= MIN_Value) {
			this.tileCost = tileCost;
		} else {
			throw new IllegalArgumentException("Buzz off! The cost of purchasing a garden tile cannot be less than 0");
		}

	}

	/**
	 * Method to get the cost of landing on the garden tile.
	 * 
	 * @return - The cost of landing on the garden tile.
	 */
	public int getRent() {
		return rent;
	}

	/**
	 * Method to set the cost of landing on the garden tile.
	 * 
	 * @param rent - The cost of landing on the garden tile to set.
	 * @throws IllegalArgumentException - If the cost of landing on the garden tile
	 *                                  to set is less than zero.
	 */
	public void setRent(int rent) throws IllegalArgumentException {
		// Check rent is not less than minimum value
		if (rent >= MIN_Value) {
			this.rent = rent;
		} else {
			throw new IllegalArgumentException("Buzz off! The cost of landing on a garden tile cannot be less than 0");
		}
	}

	/**
	 * Method to calculate and handle the rent payment when a player lands on the
	 * garden tile.
	 * 
	 * @param player - The player that landed on the garden tile.
	 */
	public void payRent(Player player) {
		int developedRent = this.rent;
		int hives = this.getHives();
		int apiary = this.getApiary();
	
		// Calculate rent based on the number of Hives and Apiaries
		if (hives > 0 && apiary > 0) {
			developedRent = this.rent * (2 * hives + 4 * apiary);
		} else if (hives > 0) {
			developedRent = this.rent * (2 * hives);
		}

		// Process rent payment
		if (player.getHoney() < developedRent) {
			double remainingHoney = player.getHoney();
			player.updateHoney(-remainingHoney);
			this.getOwner().updateHoney(+remainingHoney);
			System.out.println(player.getName() + " has visited " + this.getName() + " and paid " + remainingHoney
					+ " honey jars to " + this.getOwner().getName());
			BoardGame.eliminatePlayer(player);
		} else {
			player.updateHoney(-developedRent);
			this.getOwner().updateHoney(+developedRent);
			System.out.println(player.getName() + " has visited " + this.getName() + " and paid " + developedRent
					+ " honey jars to " + this.getOwner().getName());
		}

	}

}
