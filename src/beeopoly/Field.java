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
	private ArrayList<Garden> gardens = new ArrayList<Garden>();

	public Field(String name) {
		super();
		this.name = name;
	}

	public ArrayList<Garden> getGardens() {
		return gardens;
	}

	public void addGarden(Garden garden) {
		this.gardens.add(garden);
	}
	
	 @Override
	    public String toString() {
	        return name;
	    }

}
