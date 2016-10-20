package assignment2;


/** ADT for the class Identifier
 * 
 * @author 
 * 		Nousha van Dijk & Jan Rezelman
 * @elements
 * 		alphanumeric characters 
 * @structure
 * 		linear
 * @domain
 * 		letters a-z and combination of letters and numbers
 * @constructor
 * 		identifier()
 * 		PRE		-
 * 		POST 	identifier object is empty
 * 
 * 		identifier (identifier src)
 * 		PRE		-
 * 		POST 	identifier object contains copy of src
 */

public interface IdentifierInterface {
	
	/** Initializes the identifier object
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		identifier contains identifierString
	 */
	void init();

	/** Adds character to identifier
	 * @precondition
	 * 		identifier exists
	 * @postcondition
	 * 		Character is added to identifier object
	 */
	void add(String input);

	/** Get identifier
	 * @precondition
	 * 		identifier exists
	 * @postcondition
	 * 		identifierString is returned
	 */
	String get();
	
	/** Compare identifiers
	 * @precondition
	 * 		identifiers exist
	 * @postcondition
	 * 		return whether identifier is smaller than,
	 * 		equal to or greater than src
	 */
	int compareTo(Identifier src);
	
	/** Get identifier hash code
	 * @precondition
	 * 		identifier exists
	 * @postcondition
	 * 		identifier hash code is returned
	 */
	int hashCode();
	
	/** Check whether two objects are equal
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		true: objects are equal 
	 * 		false: objects are not equal
	 */
	boolean equals(Object obj);
	
}