package assignment2;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.HashMap;
import java.math.BigInteger;

public class Main {
	
	PrintStream out;
	HashMap<Identifier, SetInterface<BigInteger>> hashmap = new HashMap<Identifier, SetInterface<BigInteger>>();
	
	Main() {
		out = new PrintStream(System.out);
	}
	
	void start() {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
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
		statement = deleteSpaces(statement);
		if (!statement.hasNext()) {
			throw new APException("Encountered empty line");
		} else if (statement.hasNext("\\/")) {
			return;
		} else if (statement.hasNext("[a-zA-Z]")) {
			processAssignment(statement);
		} else if (statement.hasNext("\\?")){
			statement.next();
			processPrintStatement(statement).toString();
		} else {
			throw new APException("Invalid line");
		}
	}

	void processAssignment(Scanner statement) throws APException {
		statement = deleteSpaces(statement);
		Identifier identifier = getIdentifier(statement);
		System.out.print("created id");
		while (!statement.hasNext("=")) {
			if (!statement.hasNext()) {
				throw new APException("= expected, not found");
			} else {
				statement.next();
			}
		}
		statement.next();
		statement.next();
		SetInterface<BigInteger> collection = processExpression(statement);
		
		if (hashmap.containsKey(identifier)) {
			hashmap.remove(identifier);
		}
		hashmap.put(identifier, collection);
		for(int i = 0; i < hashmap.get(identifier).size(); i++) {
			System.out.print(hashmap.get(identifier).get(i) + ", ");
		}
		return;
	}

	SetInterface<BigInteger> processPrintStatement (Scanner statement) throws APException {
		statement = deleteSpaces(statement);
		SetInterface<BigInteger> set = new Set<>();
		set = processExpression(statement);
		return set;
	}

	SetInterface<BigInteger> processExpression(Scanner expression) throws APException {
		SetInterface<BigInteger> firstCollection = new Set<>();
		SetInterface<BigInteger> secondCollection = new Set<>();
		firstCollection = processTerm(expression);
		String additiveOperator;
		
		while (expression.hasNext() && hasAdditiveOperator(expression) && !expression.hasNext("\\)")) {
			expression = deleteSpaces(expression);
			System.out.print("check of + wordt gevonden");
			additiveOperator = expression.next();
			secondCollection = processTerm(expression);
			if (additiveOperator.equals("+")) {
				System.out.print("check of + wordt gevonden");
				firstCollection = firstCollection.union(secondCollection);
			} else if (additiveOperator.equals("-")) {
				firstCollection = firstCollection.complement(secondCollection);
			} else {
				firstCollection = firstCollection.symmetricDifference(secondCollection);
			}
		}
		return firstCollection;
	}
	
	SetInterface<BigInteger> processTerm(Scanner expression) throws APException {
		
		SetInterface<BigInteger> firstCollection = new Set<>();
		SetInterface<BigInteger> secondCollection = new Set<>();
		firstCollection = processFactor(expression);
		while (expression.hasNext() && hasMultiplicativeOperator(expression)) {
			if (expression.hasNext("\\)")) {
				expression.next();
				return firstCollection;
			}
			secondCollection = processFactor(expression);
			firstCollection = firstCollection.intersection(secondCollection);
		}
		return firstCollection;
	}
	
	SetInterface<BigInteger> processFactor(Scanner expression) throws APException {
		SetInterface<BigInteger> collection = new Set<>();
		if (expression.hasNext("\\{")) {
			collection = processSet(expression);
		} else if (expression.hasNext("[a-zA-Z]")) {
			collection = hashmap.get(getIdentifier(expression));
		} else if (expression.hasNext("\\(")) {
			collection = processComplexFactor(expression);
			if (!expression.hasNext("\\)")) {
				throw new APException("Expected closing bracket, not found.");
			}
			expression.next();
		} else {
			throw new APException("No valid factor found, found character");
		}
		return collection;
	}
	
	Identifier getIdentifier(Scanner expression) throws APException {
		Identifier identifier = new Identifier();
		while (expression.hasNext("[a-zA-Z0-9]")) {
			identifier.add(expression.next());
		}
		return identifier;
	}
	
	SetInterface<BigInteger> processComplexFactor (Scanner expression) throws APException {
		expression.next();
		return processExpression(expression);
	}
	
	SetInterface<BigInteger> processSet(Scanner set) throws APException {
		set.next();
		SetInterface<BigInteger> collection = new Set<>();
		
		while (!(set.hasNext("}"))) {
			String numberString = "";
			set.skip("\\s*");
			while (set.hasNextInt()){
				
				numberString += set.nextInt();
			}
			collection.add(new BigInteger(numberString));
			set.skip("\\s*");
			if (set.hasNext(",")) {
				set.next();
			}
		}
		set.next();
		return collection;
	}

	boolean hasAdditiveOperator(Scanner expression) {
		return (expression.hasNext("[\\|\\+\\-]"));
	}
	
	boolean hasMultiplicativeOperator(Scanner expression) {
		return (expression.hasNext("\\*"));
	}
	
	void checkEOL(Scanner expression) throws APException {
		if (expression.hasNext()) {
			throw new APException("End of line expected, but encountered more characters.");
		}
	}
	
	Scanner deleteSpaces (Scanner expression){
		expression.useDelimiter("");
		expression.skip("\\s*");
		return expression;
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}