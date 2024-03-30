package beeopoly;

import java.util.Scanner;

public class Garden extends BoardTile {

	private Field field;
	private int tileCost;
	private int rent;
	private int buildCost;
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

	@Override
	public void landOn(Player player) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("You've landed on %s.%n", this.getName());
		if (this.getOwner() != null) {
			System.out.printf("The owner of this Garden is %s.%n", this.getOwner());
			this.payRent(player);
		} else {
			System.out.printf("This Garden isn't owned by anyone.%n");
			System.out.println("Do you want to give up some of your Honey Jars to own this Garden? [Y/N]");
			String choice = sc.nextLine().trim();
			if (choice.contains("Y") || choice.contains("Yes") || choice.contains("y") || choice.contains("yes")) {
				this.setOwner(player);
				System.out.println("The new owner of " + this.getName() + " is " + player.getName());
				player.updateHoney(-this.getTileCost());
				player.showHoney();
			} else {
				// TO DO - implement auction feature to pass onto other players
			}

		}
	}

	public void purchase(Player player) {
		// TO DO
	}

	public void swap(Player player) {
		// TO DO
	}

	public void sell(Player player) {
		// TO DO
	}

	public int getHives() {
		return hives;
	}

	public void setHives(int hives) {
		this.hives = hives;
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

	public void buildApiary() {

		if (this.getHives() < 3) {
			System.out.println("Your garden " + this.getName()
					+ " needs 3 Hives before you can build an Apiary... do you want to build a Hive instead? [Y/N]");
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
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

		rent = this.rent;
		int hives = this.getHives();
		int apiary = this.getApiary();
		if (hives > 0 && apiary > 0) {
			rent = this.rent * (2 * hives + 4 * apiary);
		} else if (hives > 0) {
			rent = this.rent * (2 * hives);
		}

		player.updateHoney(-rent);
		this.getOwner().updateHoney(+rent);

		System.out.println(
				player + " has visited " + this.getName() + " and paid " + rent + " Honey Jars to " + this.getOwner());

	}

	public int getBuildCost() {
		return buildCost;
	}

}
