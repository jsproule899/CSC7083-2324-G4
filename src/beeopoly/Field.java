/**
 * 
 */
package beeopoly;

import java.util.ArrayList;

/**
 * 
 */
public class Field {

	private String name;
	private ArrayList<Garden> gardens;

	public Field(String name, ArrayList<Garden> gardens) {
		super();
		this.name = name;
		this.gardens = gardens;
	}

	public ArrayList<Garden> getGardens() {
		return gardens;
	}

	public void addGarden(Garden garden) {
		this.gardens.add(garden);
	}

}
