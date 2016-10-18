package assignment2;

/** ADT for the class Set
 * 
 * @author Nousha van Dijk & Jan Rezelman
 * @elements
 * 		Objects of the type E
 * @structure
 * 		no structure
 * @domain
 * 		0 or more Objects
 * 
 * 		set()
 * 		PRE
 * 		POST set object contains only the empty collection
 */
public interface SetInterface<E extends Comparable<E>> {
	
	/** Initializes the set object to the array
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		the set is empty
	 */
	void init();
	
	/**
	 * TODO
	 * @return
	 */
	E get(int i);
	
	/** Returns whether the set is empty
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		true: the set is empty
	 * 		false: the set is not empty
	 */
	boolean isEmpty();
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	boolean contains(E number);
	
	/** Add element to set
	 * @precondition
	 * 		Element is not in set
	 * @postcondition
	 * 		src is in set
	 */
	void add(E src);
	
	
	/** Delete element from set
	 * @precondition
	 *		-
	 * @postcondition
	 * 		src is not in set
	 */
	void delete(E src);
	
	/** Returns the set represented as a list
	 * @precondition
	 * 		- TODO list exists?
	 * 		- list is not empty?
	 * @postcondition
	 * 		return list representation of the set, of data type List<E>
	 */
	List<E> getSet();
	
	/** Returns the set represented as a string
	 * @precondition
	 * 		- TODO set exists? zo nauwkeurig?
	 * @postcondition
	 * 		return string representation of the set, of data type String
	 */
	String toString();
	
	/** Adds all identifiers of the first collection that are not equal to any identifier in the second collection to the array
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		return new set of ....
	 *		only identifiers in first collection that are not equal to any identifier in second collection are returned
	**/
	SetInterface<E> complement(SetInterface<E> secondSet);
	
	/** Adds identifiers which are in both the first and second collection to the array
	 * @precondition
	 *		-
	 * @postcondition
	 *		Equal identifiers from first and second collection are returned
	**/
	SetInterface<E> intersection(SetInterface<E> secondSet);
	
	/** Adds identifiers of first and second collection to the array
	 * @precondition
	 *		-
	 * @postcondition
	 *		All identifiers of first and second collection are returned, but no duplicates
	**/
	SetInterface<E> union(SetInterface<E> secondSet);
	
	/** Adds identifiers of first and second collection to the array, except identifiers which are in both collections
	 * @precondition
	 *		-
	 * @postcondition
	 *		All non-equal identifiers of first and second collection are returned
	**/
	SetInterface<E> symmetricDifference(SetInterface<E> secondSet);
	
	/** Returns the amount of elements of the set.
	 * @precondition
	 *    	-
	 * @postcondition
	 *    	The amount of elements of the set is returned.
	 **/
	int size();
	
}