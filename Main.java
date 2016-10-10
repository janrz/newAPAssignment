package assignment2;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.HashMap;
import java.math.BigInteger;

public class Main {
	PrintStream out;
	APException e;
	HashMap<Set<BigInteger>,Identifier> table = new HashMap<Set<BigInteger>,Identifier>();
	
	Main() {
		out = new PrintStream(System.out);
	}
	
	void start() {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()){
			//TODO leest niet de laatste regel van de input
			try {
				Scanner statement = new Scanner(in.nextLine());
				processStatement(statement);
			} catch (APException e) {
				out.print("\n" + e);
			}
		}
		in.close();
	}
	
	void processStatement(Scanner statement) throws APException {
		statement.useDelimiter("");
		statement.skip("\\s*");
		if (!statement.hasNext()) {
			throw new APException("Encountered empty line"); // TODO moet hier een exception/error message?
		} else if (statement.hasNext("\\/")) {
			return;
		} else if (statement.hasNext("[a-zA-Z]")) {
			processAssignment(statement);
		} else if (statement.hasNext("\\?")){
			statement.next();
			out.print("\n");
			printCollection(processPrintStatement(statement));
		} else {
			throw new APException("Invalid line: " + statement.nextLine());
		}
	}

	void processAssignment(Scanner statement) throws APException {
		statement.useDelimiter("");
		statement.skip("\\s*");
		
		// Create identifier
		Identifier identifier = new Identifier();
		while (statement.hasNext("[a-zA-Z0-9]")) {
			String string = statement.next();
			identifier.add(string);
		}
		
		// Check if = is present, if present skip =
		while (!statement.hasNext("=")) {
			if (!statement.hasNext()) {
				throw new APException("= expected, not found");
			} else {
				statement.next();
			}
		}
		statement.next();
		
		// Create collection
		Set<BigInteger> collection = new Set<>();
		collection = processExpression(statement);
//		checkEOL(statement);
		
		// Insert variable into table
		table.insert(collection, identifier);
		
		return;
	}

		Set<BigInteger> processPrintStatement (Scanner statement) throws APException {
		statement.useDelimiter("");
		statement.skip("\\s*");
		Set<BigInteger> collection = new Set<>();

		collection = processExpression(statement);
		System.out.println(collection.get().toString());
		return collection;
	}
	
	void printCollection(Set<BigInteger> collection) {
		System.out.println("dit is " + collection.toString());
		List<BigInteger> collectionList = collection.clone().collection;

		collectionList.goToFirst();
		out.print("{");
		if (collectionList.size() > 0) { //collectionList.size() > 0
			for (int i = 0; i < collection.size(); i++) {
				out.print(collectionList.retrieve().toString());
				if (i < collection.size() - 1) {
					out.print(", ");
				}
				collectionList.goToNext();
			}
		}
		out.print(" }");
	}

	Set<BigInteger> processExpression(Scanner expression) throws APException {
		expression.useDelimiter("");
		expression.skip("\\s*");
		Set<BigInteger> firstCollection = new Set<>();
		Set<BigInteger> secondCollection = new Set<>();
		
		firstCollection = processTerm(expression);
		String additiveOperator;
		
		while (expression.hasNext() && hasAdditiveOperator(expression) && !expression.hasNext("\\)")) {
			additiveOperator = expression.next();
			secondCollection = processTerm(expression);
			if (additiveOperator.equals("+")) {
				firstCollection = firstCollection.union(secondCollection);
			} else if (additiveOperator.equals("-")) {
				firstCollection = firstCollection.difference(secondCollection);
			} else {
				firstCollection = firstCollection.symmetricDifference(secondCollection);
			}
		}
		return firstCollection;
	}
	
	Set<BigInteger> processTerm(Scanner expression) throws APException {
		expression.useDelimiter("");
		
		Set<BigInteger> firstCollection = new Set<>();
		Set<BigInteger> secondCollection = new Set<>();
		
		firstCollection = processFactor(expression);
		while (expression.hasNext() && !hasAdditiveOperator(expression) && hasMultiplicativeOperator(expression) == true) {
			if (expression.hasNext("\\)")) {
				expression.next();
				return firstCollection;
			}
			secondCollection = processFactor(expression);
			firstCollection = firstCollection.intersection(secondCollection);
		}
		return firstCollection;
	}
	
	Set<BigInteger> processFactor(Scanner expression) throws APException {
		expression.useDelimiter("");
		expression.skip("\\s*");
		Set<BigInteger> collection = new Set<>();
		if (expression.hasNext("\\{")) {
			collection = processSet(expression);
		} else if (expression.hasNext("[a-zA-Z]")) {
			collection = getIdentifier(expression);
		} else if (expression.hasNext("\\(")) {
			collection = processComplexFactor(expression);
			if (!expression.hasNext("\\)")) {
				throw new APException("Expected closing bracket, not found.");
			}
			expression.next();
		} else {
			throw new APException("No valid factor found.");
		}
		return collection;
	}
	
	Set<BigInteger> getIdentifier(Scanner expression) throws APException {
		expression.useDelimiter("");
		expression.skip("\\s*");
		IdentifierInterface identifier = new Identifier();
		while (expression.hasNext("[a-zA-Z0-9]")) {
			identifier.add(expression.next());
		}
		if (table.contains(identifier)) {
			System.out.println(table.retrieve(identifier).toString());
			return table.retrieve(identifier);
		} else {
			throw new APException("Identifier " + identifier.get() + " not found.");
		}
	}
	
	Set<BigInteger> processComplexFactor (Scanner expression) throws APException {
		expression.next();
		return processExpression(expression);
	}
	
	Set<BigInteger> processSet(Scanner set) throws APException {
		set.next();
		set.useDelimiter("");
		set.skip("\\s*");
	
		
		Set<BigInteger> collection = new Set<>();
		while ((set.hasNext("}"))) {
			BigInteger newNumber = new BigInteger();
			set.skip("\\s*");
			while (set.hasNextInt()){
				newNumber.add(set.nextInt());
			}
			collection.add(newNumber);
			set.skip("\\s*");
			if (set.hasNext(",")) {
				set.next();
			}
		}
		set.next();
		return collection;
	}

	boolean hasAdditiveOperator(Scanner expression) {
		expression.useDelimiter("");
		expression.skip("\\s*");
		if (expression.hasNext("\\|") || expression.hasNext("\\+") || expression.hasNext("\\-")) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean hasMultiplicativeOperator(Scanner expression) {
		expression.useDelimiter("");
		expression.skip("\\s*");
		if (expression.hasNext("\\*")) {
			return true;
		} else {
			return false;
		}
	}
	
	void checkEOL(Scanner expression) throws APException {
		if (expression.hasNext()) {
			throw new APException("End of line expected, but encountered more characters.");
		}
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}



