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

	public void landOn(Player player) {
		System.out.println(player.getName()+", You've landed on " + name);
	}

}
