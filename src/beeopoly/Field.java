/**
 * 
 */
package beeopoly;

import java.util.ArrayList;

/**
 * Class to represent a field on the Beeopoly game board.
 */
public class Field {

	public static final int MIN_NAME = 3;
	public static final int MAX_NAME = 25;

	private String name;
	private ArrayList<Garden> gardens = new ArrayList<Garden>();

	/**
	 * Constructor for Field objects.
	 * 
	 * @param name - The name of the field.
	 */
	public Field(String name) {
		this.setName(name);
	}

	/**
	 * Method to get the list of gardens in the field.
	 * 
	 * @return - The list of gardens.
	 */
	public ArrayList<Garden> getGardens() {
		return gardens;
	}

	/**
	 * Method to add a garden to the field.
	 * 
	 * @param garden - The garden to be added.
	 */
	public void addGarden(Garden garden) {
		this.gardens.add(garden);
	}

	/**
	 * Method to get the name of the field.
	 * 
	 * @return - The name of the field.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the name of the field.
	 * 
	 * @param name - The name of the field to set.
	 * @throws IllegalArgumentException - If the name is less than 3 or greater than
	 *                                  25.
	 */
	public void setName(String name) throws IllegalArgumentException {
		
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		} else if (name.length() >= MIN_NAME && name.length() <= MAX_NAME) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Field must be 3-25 chars long");
		}

	}

}
