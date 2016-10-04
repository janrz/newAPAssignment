package assignment2;

public class Set<E extends Comparable<E>> implements SetInterface<E>{
	
	int amountElements;
	List<E> set;
	
	Set() {
		amountElements = 0;
		set = new List<E>();
	}
	
	Set(Set<E> src) {
		amountElements = src.size();
		set = src.getSet();
	}
	
	public void init() {
		amountElements = 0;
	}
	
	public List<E> get(int i) {
		//TODO iets met index
	}

	public boolean isEmpty() {
		return amountElements == 0;
	}
	
	//TODO naam variabele number veranderen, want pedantic
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
	
	public List<E> getSet() {
		return this.set;
	}

	public Set<E> difference(SetInterface<E> secondSet) {
		Set<E> difference = new Set<E>(this);
        List<E> differenceList = new List<E>(this.set);
        secondSet.getSet().goToFirst();
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

	public Set<E> intersection(SetInterface<E> secondSet) {
		Set<E> intersection = new Set<>();
        set.goToFirst();
        secondSet.getSet().goToFirst();
        
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

	public Set<E> union(SetInterface<E> secondSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<E> symmetricDifference(SetInterface<E> secondSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		return amountElements;
	}

}
