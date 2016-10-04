package assignment2;

public class Set<E extends Comparable<E>> implements SetInterface<E>{
	
	int amountElements;
	List<E> set;
	
	Set() {
		amountElements = 0;
		set = new List<E>();
	}
	
	//TODO copy constructor
	
	public void init() {
		amountElements = 0;
	}

	public boolean isEmpty() {
		return amountElements == 0;
	}

	public boolean contains(E number) {
		return set.find(number);
	}
	
	public void add(E src) {
		if (!this.contains(src)) {
			set.insert(src);
			amountElements++;
		}
	}

	public void delete(E src) {
		if (this.contains(src)) {
			set = set.remove();
			amountElements--;
		}
	}

	public Set<E> difference(Set<E> secondSet) {
		Set<E> difference = this.clone();
        List<E> differenceList = this.set.clone();
        secondSet.set.goToFirst();
        differenceList.goToFirst();
        
        for (int i = 0; i < secondSet.size(); i++) {
            for (int x = 0; x < difference.size(); x++) {
                if (secondSet.get(i).equals(difference.get(x))) {
                	difference.delete(difference.get(x));
                differenceList.goToNext();
                }
            secondSet.set.goToNext();
            }
        }
        return difference;
	}

	public Set<E> intersection(Set<E> secondSet) {
		Set<E> intersection = new Set<>();
        set.goToFirst();
        secondSet.set.goToFirst();
        
        for (int i = 0; i < secondSet.size(); i++) {
            for (int x = 0; x < set.size(); x++) {
                if (secondSet.get(i).equals(set.retrieve())) {
                	intersection.add(set.retrieve());
                set.goToNext();
                }
            secondSet.set.goToNext();
            }
        }
        return intersection;
	}

	public Set<E> union(Set<E> secondSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<E> symmetricDifference(Set<E> secondSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		return amountElements;
	}
	
}
