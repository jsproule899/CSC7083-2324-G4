package beeopoly;

import java.util.List;
import java.util.Scanner;

public class Garden extends BoardTile {

	private Field field;
	private int tileCost;
	private int rent;
	private int buildCost; // is this needed anymore if we are calculating as a percentage of tileCost?
	private int hives = 0;
	private int apiary = 0;
	private Player owner = null;

	public Garden(String name, Field field, int tileCost, int rent, int buildCost) {
		super(name);
		this.field = field;
		this.tileCost = tileCost;
		this.rent = rent;
		this.buildCost = buildCost;
	}

	//Working on validation Ciaran
	@Override
	public void landOn(Player player) {
		Scanner sc = new Scanner(System.in);
		boolean input = false; 
		System.out.printf("You've landed on %s (%s).%n", this.getName(), this.getField());

		if (this.getOwner() != null) {
			System.out.printf("The owner of this Garden is %s.%n", this.getOwner().getName());
			this.payRent(player);
		} else {
			//Keep looping while input is false
			while(!input) {
				try {
					System.out.printf("This Garden isn't owned by anyone.%n");
					System.out.println(
							"Do you want to trade " + this.getTileCost() + " Honey Jars to colonise this Garden? [Y/N]");
					String choice = sc.nextLine().trim();
					//Think equals ignore case would work better here?
//					if (choice.contains("y") || choice.contains("Yes") || choice.contains("y") || choice.contains("yes")) {
					if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
						this.purchase(player);
						input = true;
					} else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")){
						this.auction(player);
						input = true;
					}else {
						System.out.println("Invalid entry, please enter [Y/N]");
						//sc.nextLine();
					}
				} catch (Exception e) {
					System.err.println("Error, please try again");
				}
			}
			


		}
	}

	public void purchase(Player player) {
		if (player.getHoney() < this.getTileCost()) {
			System.out.println("Sorry you don't have enough Honey Jars to colonise this Garden.... ");
		} else {
			this.setOwner(player);
			System.out.println("The new owner of " + this.getName() + " is " + player.getName());
			player.updateHoney(-this.getTileCost());
			player.showHoney();
		}
	}

	public void auction(Player player) {
	    Scanner scanner = new Scanner(System.in);
	    Player currentPlayer = player;

	    // Display that the garden will be auctioned to other players
	    System.out.println(this.getName() + " will now be auctioned to all other beekeepers");

	    // Get the list of active players from the BoardGame class
	    List<Player> activePlayers = BoardGame.getActivePlayers();
	    
	    for (Player otherPlayer : activePlayers) {
	        if (otherPlayer != currentPlayer) {
	            System.out.println(otherPlayer.getName() + ", do you want to purchase " + this.getName() +"? [Y/N]");
	            String choice = scanner.nextLine().trim();
	            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
	            	if (otherPlayer.getHoney()>= this.getTileCost()) {
	            		this.purchase(otherPlayer);
	            		return;// Exit the method after selling
	            	} else {
	            		System.out.println("Sorry you don't have enough Honey Jars to colonise this Garden.... \"");
	            	}
	            }
	        }
	    }

	    System.out.println("No other player wants to purchase " + this.getName() + ". The game continues.");
	}
		
	
		

	public int getHives() {
		return hives;
	}

	public void setHives(int hives) {
		this.hives = hives;
	}
	
	public void updateHives() {
		this.hives += 1;
	}
	
	public void updateApiaries() {
		this.apiary += 1;
	}

	public void buildHive(int hives) {

		// TO DO field logic to confirm that owner owns all gardens of the field

		this.hives = hives;
		System.out.println("Your garden " + this.getName() + " now has " + this.getHives() + " Hives");
	}

	public int getApiary() {
		return apiary;
	}

	public void setApiary(int apiary) {
		this.apiary = apiary;
	}

	//TODO - update validation - ciaran
	public void buildApiary() {

		if (this.getHives() < 3) {
			System.out.println("Your garden " + this.getName()
					+ " needs 3 Hives before you can build an Apiary... do you want to build a Hive instead? [Y/N]");
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			//TODO - update validation - include equalsIgnoreCase
			if (choice.contains("Y") || choice.contains("Yes") || choice.contains("y") || choice.contains("yes")) {
				this.buildHive(1);
			} else {
				return;
			}
		} else {
			if (this.apiary == 1) {
				System.out.println(
						"Your garden " + this.getName() + " can only have a single Apiary... try a different garden");
			} else {
				this.apiary = 1;
				System.out.println("Your garden " + this.getName() + " now has an Apiary");
			}
		}

	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Field getField() {
		return field;
	}

	public int getTileCost() {
		return tileCost;
	}

	public int getRent() {
		return rent;
	}

	public void payRent(Player player) {

		int developedRent = this.rent;
		int hives = this.getHives();
		int apiary = this.getApiary();
		if (hives > 0 && apiary > 0) {
			developedRent = this.rent * (2 * hives + 4 * apiary);
		} else if (hives > 0) {
			developedRent = this.rent * (2 * hives);
		}

		if (player.getHoney() < developedRent) {
			double remainingHoney = player.getHoney();
			player.updateHoney(-remainingHoney);
			this.getOwner().updateHoney(+remainingHoney);
			System.out.println(player.getName() + " has visited " + this.getName() + " and paid " + remainingHoney
					+ " Honey Jars to " + this.getOwner().getName());
			BoardGame.eliminatePlayer(player);
		} else {
			player.updateHoney(-developedRent);
			this.getOwner().updateHoney(+developedRent);
			System.out.println(player.getName() + " has visited " + this.getName() + " and paid " + developedRent
					+ " Honey Jars to " + this.getOwner().getName());
		}

	}

	public int getBuildCost() {
		return buildCost;
	}

}
