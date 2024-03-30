package beeopoly;

public class NectarOasis extends BoardTile {

	public NectarOasis(String name) {
		super(name);
	}

	@Override
	public void landOn(Player player) {
		System.out.println("Welcome to the "+ this.getName() + " your Bees rest in the nectar until your next turn");
	}
}
