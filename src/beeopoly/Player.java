/**
 * 
 */
package beeopoly;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 */
public class Player {

	private String name;
	private int honey;
	private int position;

	public Player(String name) {
		super();
		this.name = name;
		this.honey = 1500;
		this.position = 0; // indexed 0-11
	}

	public String getName() {
		return name;
	}
	
	public int getHoney() {
		return honey;
	}

	public void updateHoney(int jars) {
		this.honey += jars;
	}

	public void showHoney() {
		System.out.printf("BeeKeeper %s now has %d Honey Jars %n", this.name, this.honey);
	}

	public int rollDice() {
		int roll, dice1, dice2 = 0;
	
		Random rand = new Random();
		dice1 = rand.nextInt(6)+1;
		dice2 = rand.nextInt(6)+1;
		roll = dice1+dice2;
		
		System.out.println("Rolling dice...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(this.getName()+" You have rolled a "+dice1+" and a "+ dice2+ " that makes "+roll);
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


	public void trade(ArrayList<Garden> gardens, ArrayList<Player> players) {
		// TO DO
		int honey = 0;
		Garden garden1, garden2 = null;
		Player player2 = null;

		ArrayList<Garden> ownedGardens1 = new ArrayList<Garden>();
		ArrayList<Garden> ownedGardens2 = new ArrayList<Garden>();
		Scanner sc = new Scanner(System.in);
		for (Garden garden : gardens) {
			if (garden.getOwner() == this) {
				ownedGardens1.add(garden);
			}
		}
		System.out.println("Which Garden would you like to trade? [Enter a number]");
		int i = 1;
		for (Garden garden : ownedGardens1) {
			System.out.println(i + ". " + garden.getName());
			i++;
		}
		System.out.println(i + ". Cancel trade");
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == i || choice > i || choice <= 0) {
			System.out.println("Trade cancelled...");
			return;
		} else {
			garden1 = gardens.get(choice-1);
		}

		System.out.println("Which Player would you like to trade with? [Enter a number]");
		int j = 1;
		for (Player player : players) {
			System.out.println(j + ". " + player.getName());
			j++;
		}
		System.out.println(j + ". Cancel trade");
		choice = sc.nextInt();
		sc.nextLine();
		if (choice == j || choice > j || choice <= 0) {
			System.out.println("Trade cancelled...");
			return;
		} else {
			player2 = players.get(choice-1);
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

		choice = sc.nextInt();
		sc.nextLine();
		switch (choice) {
		case 1:
			System.out.println("Which Garden do you wish to swap for?");
			int k = 1;
			for (Garden garden : ownedGardens2) {
				System.out.println(k + ". " + garden.getName());
				k++;
			}
			System.out.println(k + ". Cancel trade");
			choice = sc.nextInt();
			sc.nextLine();
			if (choice == k || choice > k || choice <= 0) {
				System.out.println("Trade cancelled...");
				return;
			} else {
				garden2 = gardens.get(choice-1);
			}

			System.out.println("Do you wish to add Honey to sweeten the deal? [Enter a number]");
			System.out.println("1. Yes - Add Honey with garden");
			System.out.println("2. No - Just trade gardens");
			System.out.println("3. Cancel trade");
			choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				System.out.println("How much Honey do you want to add to sweeten the deal? [Enter a number]");
				honey = sc.nextInt();
				sc.nextLine();
				break;
			case 2:
				break;
			default:
				System.out.println("Trade cancelled...");
				return;

			}

			break;
		case 2:
			System.out.println("How much Honey do you want to sell this for? [Enter a number]");
			honey = sc.nextInt();
			sc.nextLine();
			break;
		default:
			System.out.println("Trade cancelled...");
			return;
		}

		System.out.println("Does " + player2.getName() + " agree with the trade? [Y/N]");
		String agree = sc.nextLine().trim();

		if (agree.contains("Y") || agree.contains("Yes") || agree.contains("y") || agree.contains("yes")) {
			garden1.setOwner(player2);
			garden2.setOwner(this);
			this.updateHoney(-honey);
			player2.updateHoney(+honey);

			System.out.println(this.getName() + " now owns " + garden2.getName());
			this.showHoney();
			System.out.println(player2.getName() + " now owns " + garden1.getName());
			player2.showHoney();

		} else {
			System.out.println("Trade cancelled...");
			return;
		}
	}

	public void develop() {
		// TO DO
	}

	public void showMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select an option:");
		System.out.println("1. Manage Gardens");
		System.out.println("2. Roll Dice");
		System.out.println("3. Quit Game");
		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {
		case 1:
			System.out.println("Select an option:");
			System.out.println("1. Trade Gardens");
			System.out.println("2. Develop Gardens");
			System.out.println("3. Back to turn menu");
			int option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 1: this.trade(BoardGame.gardens, BoardGame.activePlayers);
				break;
			case 2: this.develop();
				break;
			default: this.showMenu();
			}
			break;
		case 2: this.move(this.rollDice());;
			break;
		case 3: BoardGame.removePlayer(this);
			break;
		default: this.showMenu();
		
		}

	}
}
