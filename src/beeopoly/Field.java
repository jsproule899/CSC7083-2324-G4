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
		//is the super needed here?
		super();
		this.setName(name);
	}

	public ArrayList<Garden> getGardens() {
		return gardens;
	}

	public void addGarden(Garden garden) {
		this.gardens.add(garden);
	}
	
	public String getName() {
		return name;
	}
	
	
	 public void setName(String name)throws IllegalArgumentException {
		 if(name != null) {
			 this.name = name; 
		 }else {
			 throw new IllegalArgumentException("Cannot be a null value");
		 }
		
	}

	@Override
	    public String toString() {
	        return name;
	    }

}
