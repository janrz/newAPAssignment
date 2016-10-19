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
		//System.out.print("amountElements = " + amountElements + "\n");
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
		return set.find(src);
	}
	
	public void add(E src) {
		System.out.print("add \n");
		if (!this.contains(src)) {
			//System.out.print("src = " + src + "\n");
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
	
	public String toString() {
		System.out.print("to string: ");
		String setString = "{";
		
		set.goToFirst();
		for (int i = 0; i < this.size(); i++) {
			setString += set.retrieve().toString();
			if (i < this.size() - 1) {
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
			System.out.print(secondSet.get(i) + "\n");
			union.add(secondSet.get(i));
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
