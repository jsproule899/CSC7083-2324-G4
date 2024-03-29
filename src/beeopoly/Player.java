/**
 * 
 */
package beeopoly;

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
		this.position = 0; //indexed 0-11
	}

	public void updateHoney(int jars) {
		this.honey += jars;
	}

	public void showHoney() {
		System.out.printf("BeeKeeper %s now has %d Honey Jars", this.name, this.honey);
	}
	
	public void move(int places) {
		if(this.position + places >= 12) {
			places = (this.position + places) - 12;
			this.position = places;
		}else {
			this.position += places;
		}
	}
	
	public void trade() {
		//TO DO
	}
	
	public void develop() {
		//TO DO
	}
}
