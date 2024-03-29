/**
 * 
 */
package beeopoly;

/**
 * Parent class for all board tiles
 * 
 */
public abstract class BoardTile {

	private String name;

	public BoardTile(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void displayMessage() {
		System.out.println("You've landed on " + name);
	}

}
