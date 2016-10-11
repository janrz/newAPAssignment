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
	
	public E get(int index) {
		this.set.goToFirst();
		for (int i = 0; i < index; i++) {
			this.set.goToNext();
		}
		return set.retrieve();
	}

	public boolean isEmpty() {
		return amountElements == 0;
	}
	
	public boolean contains(E src) {
		return set.find(src);
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
	
	public String toString() {
		String setString = "";
		set.goToFirst();
		
		setString += "{";
		for (int i = 0; i < amountElements; i++) {
			setString += set.retrieve().toString();
			if (i < amountElements - 1) {
				setString += ", ";
			}
		}
		setString += "}";
		return setString;
	}

	public SetInterface<E> complement(SetInterface<E> secondSet) {
		Set<E> complement = new Set<>(this);
	
		for (int i = 0; i < secondSet.size(); i++) {
			for (int x = 0; x < this.size(); x++) {
				if (secondSet.get(i).equals(this.get(x))) {
					complement.delete(this.get(x));
				}
			}
		}
		
		return complement;
	}

	public SetInterface<E> intersection(SetInterface<E> secondSet) {
		Set<E> intersection = new Set<>();
        
        for (int i = 0; i < secondSet.size(); i++) {
            for (int x = 0; x < this.size(); x++) {
                if (secondSet.get(i).equals(this.get(x))) {
                	intersection.add(this.get(x));
                }
            }
        }
        return intersection;
	}

	public SetInterface<E> union(SetInterface<E> secondSet) {
		Set<E> union = new Set<>(this);
		
		for (int i = 0; i < secondSet.size(); i++) {
			if (!union.contains(secondSet.get(i))) {
				union.add(secondSet.get(i));
			}
		}
		return union;
	}

	public SetInterface<E> symmetricDifference(SetInterface<E> secondSet) {
		SetInterface<E> union = this.union(secondSet);
		SetInterface<E> intersection = this.intersection(secondSet);
		SetInterface<E> symmetricDifference = union.complement(intersection);
		
		return symmetricDifference;
	}

	public int size() {
		return amountElements;
	}

}
