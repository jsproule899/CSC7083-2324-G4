package beeopoly;

public class HoneyHaven extends BoardTile {

	public HoneyHaven(String name) {
		super(name);
	}
	
	public static void passHoneyHaven(Player player) {
		System.out.println("Your Bees have flown through the Honey Haven and collected an extra 200 Honey Jars");
		player.updateHoney(+200);
		player.showHoney();			
	}
}
