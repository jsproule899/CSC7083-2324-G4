package beeopoly;

import java.util.List;
import java.util.Scanner;

public class Garden extends BoardTile {

	// Constant for minimum value
	public static final int MIN_Value = 0;
	public static final int MAX_HIVES = 3;
	public static final int MAX_APIARY = 1;

	private Field field;
	private int tileCost;
	private int rent;
	private int hives = 0;
	private int apiary = 0;
	private Player owner = null;

	public Garden(String name, Field field, int tileCost, int rent) {
		super(name);
		this.setField(field);
		this.setTileCost(tileCost);
		this.setRent(rent);
		this.getHives();
		
	}

	@Override
	public void landOn(Player player) {
		Scanner sc = new Scanner(System.in);
		boolean input = false;
		System.out.printf("You've landed on %s (%s).%n", this.getName(), this.getField());

		if (this.getOwner() != null) {
			System.out.printf("The owner of this Garden is %s.%n", this.getOwner().getName());
			this.payRent(player);
		} else {
			// Keep looping while input is false
			while (!input) {
				try {
					System.out.printf("This Garden isn't owned by anyone.%n");
					System.out.println("Do you want to trade " + this.getTileCost()
							+ " Honey Jars to colonise this Garden? [Y/N]");
					String choice = sc.nextLine().trim();
					// Think equals ignore case would work better here?
//					if (choice.contains("y") || choice.contains("Yes") || choice.contains("y") || choice.contains("yes")) {
					if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
						this.purchase(player);
						input = true;
					} else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")) {
						this.auction(player);
						input = true;
					} else {
						System.out.println("Invalid entry, please enter [Y/N]");
						// sc.nextLine();
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
		
	
		

	/**
	 * Gets the number of Hives for the Garden
	 * 
	 * @return
	 */
	public int getHives() {
		return hives;
	}

	/**
	 * Sets the number of Hives for the Garden
	 * 
	 * @param hives
	 * @throws IllegalArgumentException
	 */
	public void setHives(int hives) throws IllegalArgumentException {
		if (hives <= MAX_HIVES) {
			this.hives = hives;
		} else if(hives < MIN_Value){
			throw new IllegalArgumentException("Hives cannot be set to less than 0");
		}else {
			throw new IllegalArgumentException("The maximum Hives a garden can have is " + MAX_HIVES);

		}
	}

	public void buildHive() throws IllegalArgumentException {
		if (this.hives < MAX_HIVES) {
			this.hives += 1;
		} else {
			throw new IllegalArgumentException("The maximum Hives a garden can have is " + MAX_HIVES);
		}
	}

	public void buildApiary() throws IllegalArgumentException {
		if (this.apiary < MAX_APIARY) {
			this.apiary += 1;
		} else {
			throw new IllegalArgumentException("The maximum Apiaries a garden can have is " + MAX_APIARY);
		}

	}

	public int getApiary() {
		return apiary;
	}

	public void setApiary(int apiary) throws IllegalArgumentException {
		if (apiary <= MAX_APIARY) {
			this.apiary = apiary;
		} else {
			throw new IllegalArgumentException("The maximum Apiaries a garden can have is " + MAX_APIARY);
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

	public void setField(Field field) {
		if (field != null) {
			this.field = field;
		} else {
			throw new IllegalArgumentException("Cannot be a null value");
		}

	}

	public int getTileCost() {
		return tileCost;
	}

	public void setTileCost(int tileCost) throws IllegalArgumentException {
		if (tileCost >= MIN_Value) {
			this.tileCost = tileCost;
		} else {
			throw new IllegalArgumentException("Value cannot be less than 0");
		}

	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) throws IllegalArgumentException {
		if (rent >= MIN_Value) {
			this.rent = rent;
		} else {
			throw new IllegalArgumentException("Value cannot be less than 0");
		}
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
	

}
