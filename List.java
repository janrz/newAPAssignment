package assignment2;


public class List<E extends Comparable<E>> implements ListInterface<E> {
	
    Node current, first, last;
    private int amountElements;
    
    List() {
        current = null;
        amountElements = 0;
    }
    
    public List<E> init() {
        amountElements = 0;
        return this;
    }

    public int size() {
        return amountElements;
    }
    
    public boolean isEmpty() {
        return (amountElements == 0);
    }
    
    public List<E> insert(E d) {
    	
    	if (d == null) {
        	return this;
        }
    	
        find(d);
        if (this.isEmpty()) {
            first = current = last = new Node(d);
        } else if (current.data.compareTo(d) > 0) {
            current = new Node(d, null, first);
            first = current;
            current.next.prior = current;
        } else if (current.data.compareTo(d) <= 0){
            current = new Node (d, last, null);
            last = current;
            current.prior.next = current;
        } else {
            current = new Node (d, current, current.next);
            current.next.prior = current;
            current.prior.next = current;
        }   
        amountElements ++;
        
        return this;
    }
 
    public E retrieve() {
    	
        if (!this.isEmpty()) {
            return current.data;
        } else {
        	return null;
        }
    }
 
    public List<E> remove() {
    	
    	if (this.isEmpty()) {
    		return this;
    	} else if (amountElements == 1) {
            current = null;
            current = new Node(null , current, null);
        } else if (current.prior == null) {       	
            current.next.prior = null;
            current = current.next;
            first = current;
        } else if (current.next == null) {
            current.prior.next = null;
            current = current.prior;
            last = current;
        } else {
            current.next.prior = current.prior;
            current.prior.next = current.next;
            current = current.next;
        }
    	amountElements --;
    	
        return this;    
    }
 
    public boolean find(E d) {
    	
        current = first;
        if (d == null) {
        	return false;
        }
        for (int i = 0; i < amountElements; i++) {
            E o = current.data;
            if (o == null) {
            	return false;
            }
            if (o.compareTo(d) == 0) {
                return true;
            } else if ((o.compareTo(d) != 0) && current.next != null) {
                current = current.next;
            } else {
                return false;
            }
        }
        
        return false;
    }
     
    public boolean goToFirst() {
    	
        if (this.isEmpty()) {
            return false;
        } else {
        	current = first;
            return true;
        }
    }
 
    public boolean goToLast() {
    	
        if (this.isEmpty()) {
            return false;
        } else {
        	current=last;
            return true;
        }
    }
 
    public boolean goToNext() {
    	
        if (this.isEmpty() || current == last) {
            return false;
        } else {
            current = current.next;
            return true;
        }
    }
 
    public boolean goToPrevious() {
    	
        if (this.isEmpty() || (current == first)) {
            return false;
        } else {
            current = current.prior;
            return true;
        }
    }
    
    public ListInterface<E> copy() {
    	
    	ListInterface<E> copiedList = new List<E>();
    	this.goToFirst();
    	copiedList.insert(this.retrieve());
    	
    	while (this.goToNext()) {
    		copiedList.insert(this.retrieve());
    	}
    	
    	return copiedList;
    }
     
    private class Node {
 
        E data;
        Node prior,
             next;
 
        public Node(E d) {
            this(d, null, null);
        }
 
        public Node(E data, Node prior, Node next) {
            this.data = data == null ? null : data;
            this.prior = prior;
            this.next = next;
        }
    }
    
}

