package assignment2;


public class Set<E extends Comparable<E>> implements SetInterface<E>{
	
	int amountElements;
	ListInterface<E> set;
	
	Set() {
		amountElements = 0;
		set = new List<E>();
	}
	
	Set(SetInterface<E> src) {
		amountElements = src.size();
		set = src.getSet().copy();
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
		return this.set.find(src);
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
	
	public ListInterface<E> getSet() {
		return set;
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
			union.add(secondSet.get(i));
		}
		
		return union;
	}

	public SetInterface<E> symmetricDifference(SetInterface<E> secondSet) {
		
		SetInterface<E> copyOfSecondSet = new Set<E>(secondSet);
		SetInterface<E> complementAB = this.complement(secondSet);
		SetInterface<E> complementBA = copyOfSecondSet.complement(this);
		SetInterface<E> symmetricDifference = complementAB.union(complementBA);
		
		return symmetricDifference;
	}

	public int size() {
		return amountElements;
	}

}
