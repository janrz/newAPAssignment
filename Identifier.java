package assignment2;

public class Identifier implements IdentifierInterface{

private String identifierString;
	
	Identifier() {
		identifierString = "";
	}
	
	Identifier(Identifier src) {
		identifierString = src.get();
	}
	
	public void init () {
		identifierString = "";
	}
	
	public void add(String input) {
		identifierString += input;
	}
	
	public String get() {
		return identifierString;
	}
	
	public int compareTo(Identifier src) {
		return (this.get().compareTo(src.get()));
	}

	public boolean equals(Identifier src) {
		return (this.get().equals(src.get()));
	}
	
}
