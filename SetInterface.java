package assignment2;

/** ADT for the class Set
 * 
 * @author 
 * 		Nousha van Dijk & Jan Rezelman
 * @elements
 * 		Objects of the type E
 * @structure
 * 		no structure
 * @domain
 * 		0 or more Objects
 * 
 * 		set()
 * 		PRE		-
 * 		POST 	set object is empty
 * 
 * 		set (set src)
 * 		PRE		-		
 * 		POST	set object contains copy of src
 */
public interface SetInterface<E extends Comparable<E>> {
	
	/** Initializes the set object
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		set object is empty
	 */
	void init();
	
	/** Get element at index i
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		element at index i is returned, or null if element does not exist
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
	
	/** Checks whether set contains src
	 * @precondition
	 * 		-
	 * @postcondition
	 * 		true: set contains src
	 * 		false: set does not contain src
	 */
	boolean contains(E src);
	
	/** Add element to set
	 * @precondition
	 * 		set does not contain element
	 * @postcondition
	 * 		src is added to set
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
	 * 		-
	 * @postcondition
	 * 		return list representation of the set, of data type List<E>
	 */
	ListInterface<E> getSet();
	
	/** Returns set of all values of the first collection that are not equal to any value in the second collection
	 * @precondition
	 * 		-
	 * @postcondition
	 *		only values in first collection that are not equal to any value in second collection are returned
	**/
	SetInterface<E> complement(SetInterface<E> secondSet);
	
	/** Returns set of all values that occur in both the first and second collection
	 * @precondition
	 *		-
	 * @postcondition
	 *		Equal values from first and second collection are returned
	**/
	SetInterface<E> intersection(SetInterface<E> secondSet);
	
	/** Returns values of first and second collection
	 * @precondition
	 *		-
	 * @postcondition
	 *		All values of first and second collection are returned, but no duplicates
	**/
	SetInterface<E> union(SetInterface<E> secondSet);
	
	/** Returns values of first and second collection, except values which are in both collections
	 * @precondition
	 *		-
	 * @postcondition
	 *		All values of first and second collection that do not occur in both are returned
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