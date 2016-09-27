package assignment2;


public class List<E extends Comparable> implements ListInterface<E> {
	
    Node current, first, last;
    private int amountElements;
    
    List(){
        current = null;
        amountElements = 0;
    }
     
    public boolean isEmpty() {
        return (amountElements == 0);
    }
     
    public List<E> init() {
        amountElements = 0;
        return this;
    }
 
    public int size() {
        return amountElements;
    }
 
    public List<E> insert(E d) {
        find(d);
        if (this.isEmpty()) {
            first = current = last = new Node(d, null, null);
            amountElements ++;
        } else if (first.data.compareTo(d) > 0) {
            current = new Node(d, null,first);
            first = current;
            current.next.prior = current;
            amountElements ++;
        } else if (last.data.compareTo(d) <= 0){
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
 
    public E retrieve(){
        if (!this.isEmpty()) {
            return current.data.clone();
        } else {
        	return null;
        }
    }
 
    public List<E> remove() {
    	if (this.isEmpty()){
    		return this;
    	}
    	else if (amountElements == 1) {
            current = null;
            current = new Node(null , current, null);
            amountElements --;
        } else if (current.prior == null){
            current.next.prior = null;
            current = current.next;  
            amountElements --;
        } else if (current.next == null){
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
        for (int i = 0; i < this.size(); i++) {
            E o = current.data;
            if (d.compareTo(o) == 0) {
                return true;
            } else if (d.compareTo(o) < 0 && current.next!=null) {
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
        if (this.isEmpty()||current==last) {
            return false;
        } else {
            current = current.next;
            return true;
        }
    }
 
 
    public boolean goToPrevious() {
        if (this.isEmpty()||(current==first)) {
            return false;
        } else {
            current = current.prior;
            return true;
        }
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

