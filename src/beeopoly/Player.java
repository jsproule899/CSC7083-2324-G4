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
	private double honey; // why does this need to be a double? all of the rent and developments are
							// integer numbers.
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
		Scanner sc = new Scanner(System.in);
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
				System.out.println(i + ". " + garden.getName());
				i++;
			}
			System.out.println(i + ". Cancel trade");
			while (true) {
				try {
					int choice = sc.nextInt();
					sc.nextLine();
					if (choice == i || choice > i || choice <= 0) {
						System.out.println("Trade cancelled...");
						return false;
					} else {
						garden1 = ownedGardens1.get(choice - 1);
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, you must enter a number:");
					sc.next();
					continue;
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
			while (true) {
				try {
					int choice = sc.nextInt();
					sc.nextLine();
					if (choice == j || choice > j || choice <= 0) {
						System.out.println("Trade cancelled...");
						return false;
					} else {
						player2 = otherPlayers.get(choice - 1);
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, you must enter a number:");
					sc.next();
					continue;
				}
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

			while (true) {
				try {
					int choice = sc.nextInt();
					sc.nextLine();
					switch (choice) {
					case 1:
						swap = true;
						System.out.println("Which Garden do you wish to swap for?");
						int k = 1;
						for (Garden garden : ownedGardens2) {
							System.out.println(k + ". " + garden.getName());
							k++;
						}
						System.out.println(k + ". Cancel trade");
						while (true) {
							try {
								choice = sc.nextInt();
								sc.nextLine();
								if (choice == k || choice > k || choice <= 0) {
									System.out.println("Trade cancelled...");
									return false;
								} else {
									garden2 = ownedGardens2.get(choice - 1);
								}
								break;
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, you must enter a number:");
								sc.next();
								continue;
							}
						}

						System.out.println("Do you wish to add Honey to sweeten the deal? [Enter a number]");
						System.out.println("1. Yes - Add Honey with garden");
						System.out.println("2. No - Just trade gardens");
						System.out.println("3. Cancel trade");
						while (true) {
							try {
								choice = sc.nextInt();
								sc.nextLine();
								switch (choice) {
								case 1:
									System.out.println(
											"How much Honey do you want to add to sweeten the deal? [Enter a number]");
									while (true) {
										try {
											honey = sc.nextInt();
											sc.nextLine();
											while (this.getHoney() < honey || honey < 0) {
												if (honey < 0) {
													System.out.println(
															"You must enter a positive amount, please try again:");
												} else {
													System.out.println(
															"You don't have that many Honey Jars, you currently have "
																	+ this.getHoney()
																	+ " please enter a lower amount:");
												}
												honey = sc.nextInt();
												sc.nextLine();
											}
											break;
										} catch (InputMismatchException e) {
											System.out.println("Invalid input, you must enter a number:");
											sc.next();
											continue;
										}
									}
									break;
								case 2:
									break;
								default:
									System.out.println("Trade cancelled...");
									return false;
								}

								break;
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, you must enter a number:");
								sc.next();
								continue;
							}
						}
						break;
					case 2:
						sell = true;
						System.out.println("How much Honey do you want to sell this for? [Enter a number]");
						while (true) {
							try {
								honey = sc.nextInt();
								sc.nextLine();
								while (player2.getHoney() < honey || honey < 0) {
									if (honey < 0) {
										System.out.println("You must enter a positive amount, please try again:");
									} else {
										System.out.println(player2.getName()
												+ " doesn't have that many Honey Jars, They currently have "
												+ player2.getHoney() + "Honey Jars please enter a lower amount:");
									}
									honey = sc.nextInt();
									sc.nextLine();
								}
								break;
							} catch (InputMismatchException e) {
								System.out.println("Invalid input, you must enter a number:");
								sc.next();
								continue;
							}
						}
						break;
					default:
						System.out.println("Trade cancelled...");
						return false;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, you must enter a number:");
					sc.next();
					continue;
				}
			}

			System.out.println("Does " + player2.getName() + " agree with the trade? [Y/N]");
			String agree = sc.nextLine().trim();

			if (agree.equalsIgnoreCase("Y") || agree.equalsIgnoreCase("Yes")) {

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
	}

	public boolean develop(ArrayList<Garden> gardens) {
		// TO DO DMcL
		// In progress
		int fieldCount1 = 0;
		int fieldCount2 = 0;
		int fieldCount3 = 0;
		int fieldCount4 = 0;

		System.out.println();

		Garden gardenToDevelop = null;
		Map<String, Integer> fieldCount = new HashMap<>();
		ArrayList<Garden> ownedGardens = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardensToAddHives = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardensToAddApiary = new ArrayList<Garden>();

		Scanner sc = new Scanner(System.in);
		for (Garden garden : gardens) {
			if (garden.getOwner() == this) {
				ownedGardens.add(garden);
			}
		}

		if (ownedGardens.size() > 0) {

// I think these lines of code can replace lines 326-329, 334 and 364-405 and cut out some repetition			
//			for (Garden garden : ownedGardens) {
//				boolean ownsField = true;
//				for(Garden fieldGarden : garden.getField().getGardens()) {
//					if(fieldGarden.getOwner() != this) {
//						ownsField=false;
//					}
//				}
//				
//				if (ownsField && garden.getHives() == 3 && garden.getApiary() == 0) {
//					ownedGardensToAddApiary.add(garden);
//				} else if (ownsField) {
//					ownedGardensToAddHives.add(garden);
//				}
//				
//			}
			for (Garden garden : ownedGardens) {
				String fieldName = garden.getField().getName();
				if ("Wildflower Wilderness".equals(fieldName)) {
					fieldCount1++;
					fieldCount.put("Wildflower Wilderness", fieldCount1);
					if (fieldCount1 == 2 && garden.getHives() == 3 && garden.getApiary() == 0) {
						ownedGardensToAddApiary.add(garden);
					} else if (fieldCount1 == 2) {
						ownedGardensToAddHives.add(garden);
					}
					System.out.println("fieldcount1: " + fieldCount1);

				} else if ("Pollen Meadow".equals(fieldName)) {
					fieldCount2++;
					fieldCount.put("Pollen Meadow", fieldCount2);
					if (fieldCount2 == 3 && garden.getHives() == 3 && garden.getApiary() == 0) {
						ownedGardensToAddApiary.add(garden);
					} else if (fieldCount2 == 3) {
						ownedGardensToAddHives.add(garden);
					}
					System.out.println("fieldcount2: " + fieldCount2);

				} else if ("Golden Orchards".equals(fieldName)) {
					fieldCount3++;
					fieldCount.put("Golden Orchards", fieldCount3);
					if (fieldCount3 == 3 && garden.getHives() == 3 && garden.getApiary() == 0) {
						ownedGardensToAddApiary.add(garden);
					} else if (fieldCount3 == 3) {
						ownedGardensToAddHives.add(garden);
					}
					System.out.println("fieldcount3: " + fieldCount3);

				} else if ("Blossom Estate".equals(fieldName)) {
					fieldCount4++;
					fieldCount.put("Blossom Estate", fieldCount4);
					if (fieldCount4 == 2 && garden.getHives() == 3 && garden.getApiary() == 0) {
						ownedGardensToAddApiary.add(garden);
					} else if (fieldCount4 == 2) {
						ownedGardensToAddHives.add(garden);
					}
					System.out.println("fieldcount4: " + fieldCount4);
				}
				System.out.printf("Owned Gardens: %s (%s)%n", garden.getName(), garden.getField().toString());
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
				System.out.println("You can add a hive to the following garden tiles:");

				for (Garden garden : ownedGardensToAddHives) {
					if (garden.getHives() == 0) {
						addHiveCost = (garden.getTileCost()) * (0.1);
						System.out.println(i + ". " + garden.getName() + " for " + (addHiveCost) + " honey jars");
					} else if (garden.getHives() == 1) {
						addHiveCost = (((garden.getTileCost()) * (0.1)) + ((garden.getTileCost()) * (0.2)));
// these shouldn't be cumulative as this is 30% and below is 50%
						System.out.println(i + ". " + garden.getName() + " for " + (addHiveCost) + " honey jars");
					} else if (garden.getHives() == 2) {
						addHiveCost = (((garden.getTileCost()) * (0.1)) + ((garden.getTileCost()) * (0.2))
								+ ((garden.getTileCost()) * (0.3)));
						System.out.println(i + ". " + garden.getName() + " for " + (addHiveCost) + " honey jars");
					}
					i++;
				}
			}
			if (ownedGardensToAddApiary.size() > 0) {
				hasAddApiaryGardens = true;
				System.out.println("You can develop the following garden tiles into an apiary:");

				for (Garden garden : ownedGardensToAddApiary) {
					if (garden.getApiary() == 0) {
						addApiaryCost = (garden.getTileCost()) * (0.5);
						System.out.println(i + ". " + garden.getName() + " for " + (addApiaryCost) + " honey jars");
						i++;
					}
				}
			}

			System.out.println(i + ". Cancel trade");
			int choice = sc.nextInt();
			sc.nextLine();
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
							+ " which costs" + addHiveCost + " Honey Jars.");
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
		Scanner sc = new Scanner(System.in);
		boolean input = false;
		int choice = 0;

		while (!input) {
			try {
				System.out.println("Select an option:");
				// Could implement that this first option doesn't show unless you have a garden
				// but might confuse the players if the options change.
				System.out.println("1. Manage Gardens");
				System.out.println("2. Roll Dice");
				System.out.println("3. Quit Game");
				choice = sc.nextInt();
				sc.nextLine();
				if (choice >= 1 && choice <= 3) {
					input = true;
				} else {
					System.out.println("You must enter a number between 1 & 3 inclusive");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid data type, please enter a number");
				sc.nextLine();
			}
		}

		switch (choice) {
		case 1:
			input = false;
			int option = 0;
			while (!input) {
				try {
					System.out.println("Select an option:");
					System.out.println("1. Trade Gardens");
					System.out.println("2. Develop Gardens");
					System.out.println("3. Back to turn menu");
					option = sc.nextInt();
					sc.nextLine();
					if (option >= 1 && option <= 3) {
						input = true;
					} else {
						System.out.println("You must enter a number between 1 & 3 inclusive");
					}
				} catch (Exception e) {
					System.out.println("Invalid data type, please enter a number");
					sc.nextLine();
				}
			}

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

	@Override
	public String toString() {
		return name;
	}
}
