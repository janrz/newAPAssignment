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
        find(d); // TODO waarom is dit?
        if (this.isEmpty()) {
            first = current = last = new Node(d);
            amountElements ++;
        } else if (current.data.compareTo(d) > 0) {
            current = new Node(d, null, first);
            first = current;
            current.next.prior = current;
            amountElements ++;
        } else if (current.data.compareTo(d) <= 0){
            current = new Node (d, last, null);
            last = current;
            current.prior.next = current;
            amountElements ++;
        } else {
            current = new Node (d, current, current.next);
            current.next.prior = current;
            current.prior.next = current;
            amountElements ++;
        }   
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
    	}
    	else if (amountElements == 1) {
            current = null;
            current = new Node(null , current, null);
            amountElements --;
        } else if (current.prior == null) {
            current.next.prior = null;
            current = current.next;  
            amountElements --;
        } else if (current.next == null) {
            current.prior.next = null;
            current = current.prior;
            amountElements --;
        } else {
            current.next.prior = current.prior;
            current.prior.next = current.next;
            current = current.next;
            amountElements --;
        }
        
        return this;    
    }
 
    public boolean find(E d) {
        current = first;
       // System.out.print("eerste d:" + d + "\n");
        //System.out.print("this:" + this.size() + "\n");
        for (int i = 0; i < this.size(); i++) {
            E o = this.current.data;
        //    System.out.print("set o:" + o + "\n");
        //    System.out.print("set d:" + d + "\n");
            if (d.compareTo(o) == 0) {
            	//System.out.print("true" + "\n");
                return true;
            } else if ((d.compareTo(o) < 0) && current.next != null) {
                current = current.next;
//            } else if ((d.compareTo(o) > 0) && current.next != null) {
//            	System.out.print("check");
            } else {
            	//System.out.print("false" + "\n");
                return false;
            }
        }
   //     System.out.print("false2" + "\n");
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

