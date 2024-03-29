package beeopoly;

public class NectarOasis extends BoardTile {

	public NectarOasis(String name) {
		super(name);
	}

	@Override
	public void displayMessage() {
		System.out.println("Welcome to the "+ this.getName() + "your Bees rest in the nectar until your next turn");
	}
}
