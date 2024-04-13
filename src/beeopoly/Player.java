/**
 * 
 */
package beeopoly;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Class to represent a player of the Beeopoly game.
 */
public class Player {

	/**
	 * Constants for the minimum and maximum number of characters in a player name.
	 */
	public static final int MIN_NAME = 3;
	public static final int MAX_NAME = 15;

	private String name;
	private double honey;
	private int position;

	/**
	 * Constructor for player objects, which sets starting honey jars to 500 and
	 * starting position on game board.
	 * 
	 * @param name - The name of the player object.
	 */
	public Player(String name) {
		this.setName(name);
		this.honey = 500;
		this.position = 0; // indexed 0-11
	}

	/**
	 * Method to get the name of the player.
	 * 
	 * @return - The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the name of the player.
	 * 
	 * @param name - The name of the player to set.
	 * @throws IllegalArgumentException - If the length of the name is null
	 *                                  or set is outside the range of minimum 
	 *                                  and maximum characters.
	 */
	public void setName(String name) throws IllegalArgumentException {
		// Check the name is not null and that the length is within the set minimum and maximum number of
		// characters
		if (name == null) {
			throw new IllegalArgumentException("Buzz off! Your Beekeeper name cannot be null");
		} else if (name.length() >= MIN_NAME && name.length() <= MAX_NAME) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Buzz off! Your Beekeeper name must be 3-15 chars");
		}
	}

	/**
	 * Method to get the number of honey jars the player has.
	 * 
	 * @return - The number of honey jars.
	 */
	public double getHoney() {
		return this.honey;
	}

	/**
	 * Method to update the number of honey jars the player has when a transaction
	 * happens.
	 * 
	 * @param jars
	 */
	public void updateHoney(double jars) {
		this.honey += jars;
	}

	/**
	 * Method to display to the console the number of honey jars the player has.
	 */
	public void showHoney() {
		System.out.printf("BeeKeeper %s now has %.0f honey jars %n", this.name, this.honey);
	}

	/**
	 * Method to simulate the rolling of a pair of dice by the player to generate a
	 * random number between 2 and 12.
	 * 
	 * @return - The total value rolled.
	 */
	public int rollDice() {
		int roll, dice1, dice2 = 0;

		// Generate random numbers between 1 and 6 for each dice
		Random rand = new Random();
		dice1 = rand.nextInt(6) + 1;
		dice2 = rand.nextInt(6) + 1;

		// Calculate total dice roll
		roll = dice1 + dice2;

		System.out.println("Rolling dice...");
		BoardGame.delay();
		System.out.println(this.getName() + " has rolled a " + dice1 + " and a " + dice2 + " that makes " + roll);
		return roll;
	}

	/**
	 * Method to move the player on the game board by the specified number of places
	 * based on the dice roll, handling passing through Honey Haven where
	 * applicable.
	 * 
	 * @param places - The number of places to move.
	 */
	public void move(int places) {
		// Check if moving to a position beyond or at Honey Haven
		if (this.position + places >= 12) {
			// Calculate the adjusted position after passing through Honey Haven
			places = (this.position + places) - 12;
			this.position = places;
			HoneyHaven.passHoneyHaven(this);
		} else {
			this.position += places;
		}
	}

	/**
	 * Method to get the position of the player on the game board.
	 * 
	 * @return - The position of the player on the game board.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Method to facilitate trading garden tiles (i.e. by selling or swapping)
	 * between the current player and the other players of the game.
	 * 
	 * @param gardens - The list of all garden tiles in the game.
	 * @param players - The list of all players in the game.
	 * @return - True if the trade is successful, False otherwise.
	 */
	public boolean trade(ArrayList<Garden> gardens, ArrayList<Player> players) {
		int honey = 0;
		Garden garden1 = null, garden2 = null;
		ArrayList<Player> otherPlayers = new ArrayList<Player>();
		Player player2 = null;
		boolean sell = false, swap = false;
		ArrayList<Garden> ownedGardens1 = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardens2 = new ArrayList<Garden>();

		// Identify garden tiles owned by current player
		for (Garden garden : gardens) {
			if (garden.getOwner() == this) {
				ownedGardens1.add(garden);
			}
		}

		// If the player doesn't own any garden tiles, display message to console that
		// they can't trade
		if (ownedGardens1.isEmpty()) {
			System.out.println("You haven't purchased a garden tile yet, keep playing and build your empire");
			return false;
		} else {
			// Prompt the player to select a garden tile for trade
			System.out.println("Which garden tile would you like to trade? [Enter a number]");
			int i = 1;
			for (Garden garden : ownedGardens1) {
				System.out.printf("%d. %s (%s)%n", i, garden.getName(), garden.getField().getName());
				i++;
			}
			System.out.println(i + ". Cancel trade");
			int choice = getPlayerChoice(i);
			if (choice == i || choice > i || choice <= 0) {
				System.out.println("Trade cancelled...");
				return false;
			} else {
				garden1 = ownedGardens1.get(choice - 1);
			}
		}
		// Prompt the player to select a player to trade with
		System.out.println("Which Beekeeper would you like to trade with? [Enter a number]");
		int j = 1;
		for (Player player : players) {
			if (player != this) {
				System.out.println(j + ". " + player.getName());
				otherPlayers.add(player);
				j++;
			}

		}
		System.out.println(j + ". Cancel trade");
		int choice = getPlayerChoice(j);
		if (choice == j || choice > j || choice <= 0) {
			System.out.println("Trade cancelled...");
			return false;
		} else {
			player2 = otherPlayers.get(choice - 1);
		}

		// Identify garden tiles owned by player selected to trade with
		for (Garden garden : gardens) {
			if (garden.getOwner() == player2) {
				ownedGardens2.add(garden);
			}
		}

		// Prompt the player to specify whether they wish to swap or sell
		System.out.println("Swap or Sell? [Enter a number]");
		System.out.println("1. Swap");
		System.out.println("2. Sell");
		System.out.println("3. Cancel trade");

		choice = getPlayerChoice(3);
		switch (choice) {
		case 1:
			swap = true;
			// Prompt the player to select a garden tile to swap
			System.out.println("Which garden tile do you wish to swap for?");
			int k = 1;
			for (Garden garden : ownedGardens2) {
				System.out.printf("%d. %s (%s)%n", k, garden.getName(), garden.getField().getName());
				k++;
			}
			System.out.println(k + ". Cancel trade");

			choice = getPlayerChoice(k);
			if (choice == k || choice > k || choice <= 0) {
				System.out.println("Trade cancelled...");
				return false;
			} else {
				garden2 = ownedGardens2.get(choice - 1);
			}

			// Prompt the player to indicate whether they wish to add honey jars as part of
			// trade offer
			System.out.println("Do you wish to add honey jars to sweeten the deal? [Enter a number]");
			System.out.println("1. Yes - Add honey jars with garden tile");
			System.out.println("2. No - Just trade garden tiles");
			System.out.println("3. Cancel trade");

			choice = getPlayerChoice(3);
			switch (choice) {
			case 1:
				// Prompt the player to specify the amount of honey jars they wish to offer in
				// addition to the garden tile for the trade
				System.out.println("How many honey jars do you want to add to sweeten the deal? [Enter a number]");
				honey = getHoneyAmountFromPlayer(this.getHoney());
				break;
			case 2:
				break;
			default:
				System.out.println("Trade cancelled...");
				return false;
			}
			break;
		case 2:
			sell = true;
			// Prompt the player to specify the amount of honey jars they wish to sell the
			// garden tile for
			System.out.println("How many honey jars do you want to sell this for? [Enter a number]");
			honey = getHoneyAmountFromPlayer(player2.getHoney());
			break;
		default:
			System.out.println("Trade cancelled...");
			return false;
		}

		// Confirm the other player agrees to the terms of the trade
		System.out.println("Does " + player2.getName() + " agree with the trade? [Y/N]");
		if (getPlayerDecision()) {
			// Complete transaction
			garden1.setOwner(player2);
			if (swap) {
				this.updateHoney(-honey);
				player2.updateHoney(+honey);
				garden2.setOwner(this);
				System.out.println(
						this.getName() + " now owns " + garden2.getName() + "(" + garden2.getField().getName() + ")");
			} else if (sell) {
				this.updateHoney(+honey);
				player2.updateHoney(-honey);
			}

			// Display updated garden tile ownership and honey jars following completion of
			// the transaction
			this.showHoney();
			System.out.println();
			System.out.println(
					player2.getName() + " now owns " + garden1.getName() + "(" + garden1.getField().getName() + ")");
			player2.showHoney();
			System.out.println();

			return true;

		} else {
			System.out.println("Trade cancelled...");
			return false;
		}
	}

	/**
	 * Method to facilitate development of the garden tiles by adding Hives or an
	 * Apiary.
	 * 
	 * @param gardens - The list of all garden tiles in the game.
	 * @return - True if the development is successful, False otherwise.
	 */
	public boolean develop(ArrayList<Garden> gardens) {

		System.out.println();

		Garden gardenToDevelop = null;
		ArrayList<Garden> ownedGardens = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardensToAddHives = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardensToAddApiary = new ArrayList<Garden>();

		// Identify garden tiles owned by current player
		for (Garden garden : gardens) {
			if (garden.getOwner() == this) {
				ownedGardens.add(garden);
			}
		}

		// Determine whether current player owns all garden tiles in a field
		if (ownedGardens.size() > 0) {
			for (Garden garden : ownedGardens) {
				boolean ownsField = true;
				for (Garden fieldGarden : garden.getField().getGardens()) {
					if (fieldGarden.getOwner() != this) {
						ownsField = false;
					}
				}

				// If the player owns all garden tiles in a field, determine whether the garden
				// tiles are eligible to add Hives or add an Apiary
				if (ownsField && garden.getHives() == 3 && garden.getApiary() == 0) {
					ownedGardensToAddApiary.add(garden);
				} else if (ownsField) {
					ownedGardensToAddHives.add(garden);
				}
			}

		} else {
			// If the player doesn't own any garden tiles, display message to console that
			// they can't develop
			System.out.println("You haven't purchased a garden tile yet, keep playing and build your empire");
			return false;
		}
		boolean hasAddHiveGardens = false;
		boolean hasAddApiaryGardens = false;

		// If there are eligible garden tiles for development, prompt the player to
		// choose whether they want to add a Hive or an Apiary
		if (ownedGardensToAddHives.size() > 0 || ownedGardensToAddApiary.size() > 0) {

			int i = 1;
			double addHiveCost = 0;
			double addApiaryCost = 0;

			if (ownedGardensToAddHives.size() > 0) {
				System.out.println("Which garden tile would you like to develop? [Enter a number]");
				hasAddHiveGardens = true;
				System.out.println("You can add a Hive to the following garden tiles: %n");

				// If the garden tile is eligible for Hive development, calculate and display
				// the cost of adding a Hive, depending on existing developments
				for (Garden garden : ownedGardensToAddHives) {
					if (garden.getHives() == 0) {
						addHiveCost = (garden.getTileCost()) * (0.1);
						System.out.printf("%d. %s (%s) for %.0f honey jars %n", i, garden.getName(),
								garden.getField().getName(), addHiveCost);

					} else if (garden.getHives() == 1) {
						addHiveCost = (garden.getTileCost()) * (0.2);
						System.out.printf("%d. %s (%s) for %.0f honey jars %n", i, garden.getName(),
								garden.getField().getName(), addHiveCost);
					} else if (garden.getHives() == 2) {
						addHiveCost = (garden.getTileCost()) * (0.3);
						System.out.printf("%d. %s (%s) for %.0f honey jars %n", i, garden.getName(),
								garden.getField().getName(), addHiveCost);
					}
					i++;
				}
			}
			if (ownedGardensToAddApiary.size() > 0) {
				hasAddApiaryGardens = true;
				System.out.println("You can develop the following garden tiles into an Apiary: /n");


				// If the garden tile is eligible for Apiary development, calculate and display
				// the cost of adding an Apiary
				for (Garden garden : ownedGardensToAddApiary) {
					if (garden.getApiary() == 0) {
						addApiaryCost = (garden.getTileCost()) * (0.5);
						System.out.printf("%d. %s (%s) for %.0f honey jars %n", i, garden.getName(),
								garden.getField().getName(), addApiaryCost);
						i++;
					}
				}
			}

			System.out.println(i + ". Cancel development");
			int choice = getPlayerChoice(i);
			if (choice == i || choice > i || choice <= 0) {
				System.out.println("Development cancelled...");
				return false;
			} else {
				if (hasAddHiveGardens) {
					// Process adding Hives to the chosen garden tile
					gardenToDevelop = ownedGardensToAddHives.get(choice - 1);
					System.out.println(this.getName() + ", you have added a Hive to " + gardenToDevelop.getName()
							+ " which costs " + (int) addHiveCost + " honey jars");
					this.updateHoney(-addHiveCost);
					gardenToDevelop.buildHive();
					return true;

				} else if (hasAddApiaryGardens) {
					// Process adding an Apiary to the chosen garden tile
					gardenToDevelop = ownedGardensToAddApiary.get(choice - 1);
					System.out.println(this.getName() + ", you have developed an Apiary on " + gardenToDevelop.getName()
							+ " which costs " + (int) addApiaryCost + " honey jars.");
					this.updateHoney(-addApiaryCost);
					gardenToDevelop.buildApiary();
					return true;
				}
			}
		} else {
			System.out.println(
					"You currently don't have enough garden tiles purchased to develop, keep playing and keep purchasing!");
			return false;
		}
		return false;
	}

	/**
	 * Method to display the menu to the player during their turn and handle menu
	 * navigation.
	 * 
	 * @return - True if the player continues their turn after making a selection,
	 *         False if the player quits the game.
	 */
	public boolean showMenu() {
		// Display main menu options
		System.out.println("Select an option:");
		// Could implement that this first option doesn't show unless you have a garden tile
		// but might confuse the players if the options change.
		System.out.println("1. Manage Gardens");
		System.out.println("2. Roll Dice");
		System.out.println("3. Quit Game");

		// Get player's choice
		int choice = getPlayerChoice(3);

		switch (choice) {
		case 1:
			// Display sub-menu options for managing garden tiles
			System.out.println("Select an option:");
			System.out.println("1. Trade Gardens");
			System.out.println("2. Develop Gardens");
			System.out.println("3. Back to turn menu");
			// Get player's sub-menu choice
			int option = getPlayerChoice(3);

			switch (option) {
			case 1:
				// Trade garden tile
				if (this.trade(BoardGame.gardens, BoardGame.activePlayers)) {
					System.out.println("Now that you've traded a garden tile, you must roll the dice.");

					// Roll dice and move player's position after trading garden tile
					this.move(this.rollDice());
					return true;
				} else {
					// If trade is not completed, return to main menu
					return this.showMenu();
				}

			case 2:

				// Develop garden tile
				if (this.develop(BoardGame.gardens)) {
					System.out.println("Now that you've developed a garden tile, you must roll the dice.");

					// Roll dice and move player's position after trading garden tile
					this.move(this.rollDice());
					return true;
				} else {
					// If development is not completed, return to main menu
					return this.showMenu();
				}

			case 3:
				// Return to main menu
				return this.showMenu();
			default:
				// Invalid selection, return to main menu
				return this.showMenu();
			}

		case 2:
			// Roll dice and move player's position
			this.move(this.rollDice());
			return true;
		case 3:
			// Remove player from game and display message to console
			BoardGame.removePlayer(this);
			System.out.printf("Beekeeper %s has decided to retire and is letting their Bees rest their wings!%n",
					this.getName());
			return false;

		default:
			// Invalid selection, return to main menu
			return this.showMenu();
		}

	}

	/**
	 * Method to prompt the player to enter a choice from a menu.
	 * 
	 * @param num - The number of options in the menu.
	 * @return - The number representing the player's choice.
	 */
	public static int getPlayerChoice(int num) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				int choice = sc.nextInt();
				sc.nextLine();
				// Check if the player's choice is within the valid range
				if (choice > num) {
					// Display error message if player enters invalid choice
					if (num == 1) {
						System.out.printf("Buzz off! Input can only be 1, try again:%n");
					} else {
						System.out.printf("Buzz off! Input must be 1 - %d, try again:%n", num);
					}
					// Prompt player to enter choice again
					choice = getPlayerChoice(num);
				}
				return choice;
			} catch (InputMismatchException e) {
				// If the player input is of the incorrect type, catch the exception and prompt
				// player to make a choice again
				System.out.println("Buzz off! Invalid input, you must enter a number:");
				sc.next();
				continue;
			}
		}
	}

	/**
	 * Method to prompt the player to enter a Y/N response and to process the
	 * player's decision.
	 * 
	 * @return - True if the player's decision is yes, False if the player's
	 *         decision is no.
	 */
	public static boolean getPlayerDecision() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				// Player's input
				String decision = sc.nextLine().trim();

				// Check if player's input is "yes" or "y" ignoring case
				if (decision.equalsIgnoreCase("yes") || decision.equalsIgnoreCase("y")) {
					return true;

					// Check if player's input is "no" or "n" ignoring case
				} else if (decision.equalsIgnoreCase("no") || decision.equalsIgnoreCase("n")) {
					return false;
				}
				// If the input is invalid, throw an InputMismatchException
				throw new InputMismatchException(
						"Buzz off! Invalid input detected. Please flutter with either a \"Y\" for yes or an \"N\" for no to indicate your choice. Let's keep this Hive buzzing smoothly!");

			} catch (InputMismatchException e) {
				// Catch any InputMismatchException and display an error message to the console
				System.out.println(e.getMessage());
				continue;
			}
		}

	}

	/**
	 * Method to prompt the player to enter the number of honey jars they wish to
	 * use.
	 * 
	 * @param playersHoney - The number of honey jars the player has.
	 * @return - The number of honey jars selected by the player.
	 */
	public static int getHoneyAmountFromPlayer(double playersHoney) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				// Prompt the player to enter the number of honey jars they wish to use
				int honey = sc.nextInt();
				sc.nextLine();

				// Check if the selected amount is within the number of honey jars the player
				// has
				while (honey < 0 || honey > playersHoney) {
					if (honey < 0) {
						System.out.println("You must enter a positive amount, please try again:");
					} else {
						System.out.println("Not enough honey jars, must be " + playersHoney
								+ " or less. please enter a lower amount:");
					}
					honey = sc.nextInt();
					sc.nextLine();
				}
				return honey;
			} catch (InputMismatchException e) {
				// Catch InputMismatchException if the player inputs a value that is not an
				// integer
				System.out.println("Buzz off! Invalid input, you must enter a number:");
				sc.next();
				continue;
			}
		}
	}
}