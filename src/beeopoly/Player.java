/**
 * 
 */
package beeopoly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * 
 */
public class Player {

	// Constants to set min/max names
	public static final int MIN_NAME = 3;
	public static final int MAX_NAME = 15;

	private String name;
	private double honey;
	private int position;

	public Player(String name) {
		super();
		this.setName(name);
		this.honey = 500;
		this.position = 0; // indexed 0-11
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name.length() >= MIN_NAME && name.length() <= MAX_NAME) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Name length is invalid");
		}

	}

	public double getHoney() {
		return this.honey;
	}

	public void updateHoney(double jars) {
		this.honey += jars;
	}

	public void showHoney() {
		System.out.printf("BeeKeeper %s now has %.0f Honey Jars %n", this.name, this.honey);
	}

	public int rollDice() {
		int roll, dice1, dice2 = 0;

		Random rand = new Random();
		dice1 = rand.nextInt(6) + 1;
		dice2 = rand.nextInt(6) + 1;
		roll = dice1 + dice2;

		System.out.println("Rolling dice...");
		BoardGame.delay();
		System.out.println(this.getName() + " has rolled a " + dice1 + " and a " + dice2 + " that makes " + roll);
		return roll;
	}

	public void move(int places) {
		if (this.position + places >= 12) {
			places = (this.position + places) - 12;
			this.position = places;
			HoneyHaven.passHoneyHaven(this);
		} else {
			this.position += places;
		}
	}

	public int getPosition() {
		return position;
	}

	public boolean trade(ArrayList<Garden> gardens, ArrayList<Player> players) {
		int honey = 0;
		Garden garden1 = null, garden2 = null;
		ArrayList<Player> otherPlayers = new ArrayList<Player>();
		Player player2 = null;
		boolean sell = false, swap = false;
		ArrayList<Garden> ownedGardens1 = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardens2 = new ArrayList<Garden>();
		for (Garden garden : gardens) {
			if (garden.getOwner() == this) {
				ownedGardens1.add(garden);
			}
		}
		if (ownedGardens1.isEmpty()) {
			System.out.println("You haven't purchased a garden yet, keep playing and build your empire");
			return false;
		} else {
			System.out.println("Which Garden would you like to trade? [Enter a number]");
			int i = 1;
			for (Garden garden : ownedGardens1) {
				System.out.printf("%d. %s (%s)", i, garden.getName(), garden.getField().getName());
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
		System.out.println("Which Player would you like to trade with? [Enter a number]");
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

		for (Garden garden : gardens) {
			if (garden.getOwner() == player2) {
				ownedGardens2.add(garden);
			}
		}

		System.out.println("Swap or Sell? [Enter a number]");
		System.out.println("1. Swap");
		System.out.println("2. Sell");
		System.out.println("3. Cancel trade");

		choice = getPlayerChoice(3);
		switch (choice) {
		case 1:
			swap = true;
			System.out.println("Which Garden do you wish to swap for?");
			int k = 1;
			for (Garden garden : ownedGardens2) {
				System.out.printf("%d. %s (%s)", k, garden.getName(), garden.getField().getName());
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

			System.out.println("Do you wish to add Honey to sweeten the deal? [Enter a number]");
			System.out.println("1. Yes - Add Honey with garden");
			System.out.println("2. No - Just trade gardens");
			System.out.println("3. Cancel trade");

			choice = getPlayerChoice(3);
			switch (choice) {
			case 1:
				System.out.println("How much Honey do you want to add to sweeten the deal? [Enter a number]");
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
			System.out.println("How much Honey do you want to sell this for? [Enter a number]");
			honey = getHoneyAmountFromPlayer(player2.getHoney());
			break;
		default:
			System.out.println("Trade cancelled...");
			return false;
		}

		System.out.println("Does " + player2.getName() + " agree with the trade? [Y/N]");
		if (getPlayerDecision()) {
			garden1.setOwner(player2);
			if (swap) {
				this.updateHoney(-honey);
				player2.updateHoney(+honey);
				garden2.setOwner(this);
				System.out.println(this.getName() + " now owns " + garden2.getName());
			} else if (sell) {
				this.updateHoney(+honey);
				player2.updateHoney(-honey);
			}

			this.showHoney();
			System.out.println();
			System.out.println(player2.getName() + " now owns " + garden1.getName());
			player2.showHoney();
			System.out.println();

			return true;

		} else {
			System.out.println("Trade cancelled...");
			return false;
		}
	}

	public boolean develop(ArrayList<Garden> gardens) {
		// TO DO DMcL
		// In progress
 
		System.out.println();
 
		Garden gardenToDevelop = null;
		ArrayList<Garden> ownedGardens = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardensToAddHives = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardensToAddApiary = new ArrayList<Garden>();
 
		for (Garden garden : gardens) {
			if (garden.getOwner() == this) {
				ownedGardens.add(garden);
			}
		}
 
		if (ownedGardens.size() > 0) {
			for (Garden garden : ownedGardens) {
				boolean ownsField = true;
				for (Garden fieldGarden : garden.getField().getGardens()) {
					if (fieldGarden.getOwner() != this) {
						ownsField = false;
					}
				}
 
				if (ownsField && garden.getHives() == 3 && garden.getApiary() == 0) {
					ownedGardensToAddApiary.add(garden);
				} else if (ownsField) {
					ownedGardensToAddHives.add(garden);
				}
			}
 
		} else {
			System.out.println("You haven't purchased a garden tile yet, keep playing and build your empire");
			return false;
		}
		boolean hasAddHiveGardens = false;
		boolean hasAddApiaryGardens = false;
		if (ownedGardensToAddHives.size() > 0 || ownedGardensToAddApiary.size() > 0) {
 
			int i = 1;
			double addHiveCost = 0;
			double addApiaryCost = 0;
			if (ownedGardensToAddHives.size() > 0) {
				System.out.println("Which Garden would you like to develop? [Enter a number]");
				hasAddHiveGardens = true;
				System.out.println("You can add a hive to the following garden tiles: /n");
 
				for (Garden garden : ownedGardensToAddHives) {
					if (garden.getHives() == 0) {
						addHiveCost = (garden.getTileCost()) * (0.1);
						System.out.printf("%d. %s (%s) for %.0f honey jars", i, garden.getName(),
								garden.getField().getName(), addHiveCost);
 
					} else if (garden.getHives() == 1) {
						addHiveCost = (garden.getTileCost()) * (0.2);
						System.out.printf("%d. %s (%s) for %.0f honey jars", i, garden.getName(),
								garden.getField().getName(), addHiveCost);
					} else if (garden.getHives() == 2) {
						addHiveCost = (garden.getTileCost()) * (0.3);
						System.out.printf("%d. %s (%s) for %.0f honey jars", i, garden.getName(),
								garden.getField().getName(), addHiveCost);
					}
					i++;
				}
			}
			if (ownedGardensToAddApiary.size() > 0) {
				hasAddApiaryGardens = true;
				System.out.println("You can develop the following garden tiles into an apiary: /n");
 
				for (Garden garden : ownedGardensToAddApiary) {
					if (garden.getApiary() == 0) {
						addApiaryCost = (garden.getTileCost()) * (0.5);
						System.out.printf("%d. %s (%s) for %.0f honey jars", i, garden.getName(),
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
					gardenToDevelop = ownedGardensToAddHives.get(choice - 1);
					System.out.println(this.getName() + ", you have added a hive to " + gardenToDevelop.getName()
							+ " which costs " + addHiveCost + " Honey Jars");
					this.updateHoney(-addHiveCost);
					gardenToDevelop.buildHive();
					return true;
				} else if (hasAddApiaryGardens) {
					gardenToDevelop = ownedGardensToAddApiary.get(choice - 1);
					System.out.println(this.getName() + ", you have developed an apiary on " + gardenToDevelop.getName()
							+ " which costs " + addApiaryCost + " Honey Jars.");
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

	public boolean showMenu() {
		System.out.println("Select an option:");
		// Could implement that this first option doesn't show unless you have a garden
		// but might confuse the players if the options change.
		System.out.println("1. Manage Gardens");
		System.out.println("2. Roll Dice");
		System.out.println("3. Quit Game");
		int choice = getPlayerChoice(3);

		switch (choice) {
		case 1:
			System.out.println("Select an option:");
			System.out.println("1. Trade Gardens");
			System.out.println("2. Develop Gardens");
			System.out.println("3. Back to turn menu");
			int option = getPlayerChoice(3);

			switch (option) {
			case 1:
				if (this.trade(BoardGame.gardens, BoardGame.activePlayers)) {
					this.move(this.rollDice());
					return true;
				} else {
					return this.showMenu();
				}

			case 2:
				if (this.develop(BoardGame.gardens)) {
					this.move(this.rollDice());
					return true;
				} else {
					return this.showMenu();
				}

			case 3:
				return this.showMenu();
			default:
				return this.showMenu();
			}

		case 2:
			this.move(this.rollDice());
			return true;
		case 3:
			BoardGame.removePlayer(this);
			System.out.printf("Beekeeper %s has decided to retire and is letting their Bees rest their wings!%n",
					this.getName());
			return false;

		default:
			return this.showMenu();
		}

	}

	public static int getPlayerChoice(int num) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice > num) {
					if (num == 1) {
						System.out.printf("Input can only be 1, try again:%n");
					} else {
						System.out.printf("Input must be 1 - %d, try again:%n", num);
					}
					choice = getPlayerChoice(num);
				}
				return choice;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must enter a number:");
				sc.next();
				continue;
			}
		}

	}

	public static boolean getPlayerDecision() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				String decision = sc.nextLine().trim();
				if (decision.equalsIgnoreCase("yes") || decision.equalsIgnoreCase("y")) {
					return true;
				} else if (decision.equalsIgnoreCase("no") || decision.equalsIgnoreCase("n")) {
					return false;
				}
				throw new InputMismatchException(
						"Buzz off! Invalid input detected. Please flutter with either a \"Y\" for yes or an \"N\" for no to indicate your choice. Let's keep this hive buzzing smoothly!");

			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

	}

	public static int getHoneyAmountFromPlayer(double playersHoney) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				int honey = sc.nextInt();
				sc.nextLine();
				while (honey < 0 || honey > playersHoney) {
					if (honey < 0) {
						System.out.println("You must enter a positive amount, please try again:");
					} else {
						System.out.println("Not enough Honey Jars, must be " + playersHoney
								+ " or less. please enter a lower amount:");
					}
					honey = sc.nextInt();
					sc.nextLine();
				}
				return honey;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must enter a number:");
				sc.next();
				continue;
			}
		}
	}
}