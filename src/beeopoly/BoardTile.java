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
		//Don't think the super is needed
		super();
		this.setName(name);
	}

	public String getName() {
		return name;
	}
	
	

	public void setName(String name) throws IllegalArgumentException {
		if (name!= null) {
			this.name = name;
		}else {
			throw new IllegalArgumentException("Cannot be a null value");
		}
		
	}

	public void landOn(Player player) {
		System.out.println(player.getName()+", You've landed on " + name);
	}

}
