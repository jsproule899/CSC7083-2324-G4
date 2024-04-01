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
		this.honey = 500;
		this.position = 0; // indexed 0-11
	}

	public String getName() {
		return name;
	}

	public int getHoney() {
		return this.honey;
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
		dice1 = rand.nextInt(6) + 1;
		dice2 = rand.nextInt(6) + 1;
		roll = dice1 + dice2;

		System.out.println("Rolling dice...");
		BoardGame.delay();
		System.out.println(this.getName() + " You have rolled a " + dice1 + " and a " + dice2 + " that makes " + roll);
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
			garden1 = ownedGardens1.get(choice - 1);
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
		choice = sc.nextInt();
		sc.nextLine();
		if (choice == j || choice > j || choice <= 0) {
			System.out.println("Trade cancelled...");
			return;
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

		choice = sc.nextInt();
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
			choice = sc.nextInt();
			sc.nextLine();
			if (choice == k || choice > k || choice <= 0) {
				System.out.println("Trade cancelled...");
				return;
			} else {
				garden2 = ownedGardens2.get(choice - 1);
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
				while(this.getHoney()<honey) {
					System.out.println("You don't have that many Honey Jars, you currently have "+ this.getHoney()+" please enter a lower amount:");
					honey = sc.nextInt();
					sc.nextLine();
				}
				
				
				break;
			case 2:
				break;
			default:
				System.out.println("Trade cancelled...");
				return;

			}

			break;
		case 2:
			sell = true;
			System.out.println("How much Honey do you want to sell this for? [Enter a number]");
			honey = sc.nextInt();
			sc.nextLine();
			while(player2.getHoney()<honey) {
				System.out.println(player2.getName()+" doesn't have that many Honey Jars, They currently have "+player2.getHoney()+"Honey Jars please enter a lower amount:");
				honey = sc.nextInt();
				sc.nextLine();
			}
			break;
		default:
			System.out.println("Trade cancelled...");
			return;
		}

		System.out.println("Does " + player2.getName() + " agree with the trade? [Y/N]");
		String agree = sc.nextLine().trim();

		if (agree.contains("Y") || agree.contains("Yes") || agree.contains("y") || agree.contains("yes")) {
			
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

		} else {
			System.out.println("Trade cancelled...");
			return;
		}
	}

	public void develop() {
		// TO DO DMcL
	
		
		
	}

	public boolean showMenu() {
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
			case 1:
				this.trade(BoardGame.gardens, BoardGame.activePlayers);
				return this.showMenu();
				
			case 2:
				this.develop();
				return this.showMenu();
	
			default:
				return this.showMenu();
			}
			
		case 2:
			this.move(this.rollDice());
			return true;
		case 3:
			BoardGame.removePlayer(this);
			System.out.printf("Beekeeper %s has decided to retire and is letting their Bees rest their wings!%n",this.getName());
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
