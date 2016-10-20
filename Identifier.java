package assignment2;

public class Identifier implements IdentifierInterface{

private String identifierString;
	
	Identifier() {
		identifierString = "";
	}
	
	Identifier(Identifier src) {
		identifierString = src.get();
	}
	
	public void init() {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifierString == null) ? 0 : identifierString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identifier other = (Identifier) obj;
		if (identifierString == null) {
			if (other.identifierString != null)
				return false;
		} else if (!identifierString.equals(other.identifierString))
			return false;
		return true;
	}

}
